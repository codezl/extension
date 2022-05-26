package com.dd_driving.team.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dd_driving.team.mapper.DriverMapper;
import com.dd_driving.team.mapper.DriverTeamJoinRecordMapper;
import com.dd_driving.team.mapper.DriverTeamMapper;
import com.dd_driving.team.mapper.DriverTeamRegisterMapper;
import com.dd_driving.team.pojo.entity.DriverTeam;
import com.dd_driving.team.pojo.entity.DriverTeamJoinRecord;
import com.dd_driving.team.pojo.entity.DriverTeamRegister;
import com.dd_driving.team.utils.R;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

@RestController
@RequestMapping("team")
@Log4j2
public class TeamController {

    @Autowired
    DriverTeamMapper driverTeamMapper;
    @Autowired
    DriverMapper driverMapper;
    @Autowired
    DriverTeamRegisterMapper driverTeamRegisterMapper;
    @Autowired
    DriverTeamJoinRecordMapper driverTeamJoinRecordMapper;
    /**
     * ResourceBundleMessageSource messageSource;
    */
    @Autowired
    ResourceBundleMessageSource messageSource;
    @Autowired
    Environment environment;

    @PostMapping("teamRegister")
    @Transactional
    public R<String> newTeam(@RequestBody DriverTeam team) {
        team.setState(0);
        long driverId = team.getDriverId();
        Long oldTeam = driverMapper.teamId(driverId);
        /**
         * 不管是队员还是队长，在个人信息中绑定队伍号
         * 确认无队伍之后，再确认是否存在申请
         */
        if (oldTeam!=null&&oldTeam>0) {
            return R.fail("您已经在队伍中，请退队重试");
        }else {
            QueryWrapper<DriverTeamRegister> eq = new QueryWrapper<DriverTeamRegister>().eq("driver_id", driverId).eq("state", 0);
            List<DriverTeamRegister> driverTeamRegisters = driverTeamRegisterMapper.selectList(eq);
            if (driverTeamRegisters.size()>0) {
                log.info("您有待处理的建队申请");
                return R.fail("您有待处理的建队申请");
            }
            new QueryWrapper<DriverTeamRegister>().eq("driver_id",driverId).eq("state",0);
        }
        try {
            driverTeamRegisterMapper.insert(team);
        }catch (Exception e) {
            log.info("新建队伍失败>>"+e.getMessage());
            return R.fail();
        }
        return R.ok();
    }


    /**
     * 建队审核
     * @param: id
     * @param: state
     * @return:
     * 队长不能重复
     */
    @GetMapping("auth")
    @Transactional
    public R<String> auth(Long id,Integer state) {
        DriverTeam team = driverTeamRegisterMapper.findById(id);
        if (state<=0) {
            return R.fail("传入审核状态不正确");
        }
        if (team.getState().equals(state)) {
            return R.fail("已经进行了相应操作");
        }
        team.setState(state);
        driverTeamRegisterMapper.update(team,new QueryWrapper<DriverTeamRegister>().eq("id",team.getId()));
        //通过后加入正式队伍表
        if (state==1) {
            Integer teamNo = driverTeamMapper.maxTeamNo();
            if (teamNo==null||teamNo<=0) {
                teamNo = 1000;
            }
            teamNo+=1;
            //因为是顺序插入，严格遵守的情况下可以不验证teamNo是否已经存在
            team.setTeamNo(teamNo);
            driverTeamMapper.insert(team);
        }
        return R.ok();
    }

    @GetMapping("all")
    public R<List<DriverTeam>> all(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "10") int pageSize) {
        Integer offset = (pageNum-1)*pageSize;
        Page<DriverTeam> teamPage = driverTeamMapper.selectPage(new Page<>(offset,pageSize),new QueryWrapper<DriverTeam>().eq("id",1));
        List<DriverTeam> driverTeams;
        driverTeams = teamPage.getRecords();
        /*QueryWrapper<DriverTeam> id = new QueryWrapper<DriverTeam>().ge("id", 1);
        teamPage.getRecords().forEach(System.out::print);
        List<DriverTeam> driverTeams = driverTeamMapper.selectList(id);
        driverTeams.forEach(System.out::print);*/

        return R.success(driverTeams);
    }

    /**
     * 入队申请
     * @param: driverId
     * @param: teamId
     * @return:
     */
    @PostMapping("joinTeam")
    @Transactional
    public R<String> joinTeam(@RequestBody DriverTeamJoinRecord record) {
        /**
         * author: code-zl
         * 查询是否已入队
         * 查询队伍成员数
         * 队伍容量50
         */
        Long driverId = record.getDriverId();
        Long teamId = record.getTeamId();
        Long integer = driverMapper.teamId(driverId);
        if (integer!=null&&integer>0) {
            return R.fail("您已经在队伍中，请退队重试");
        }else {
            List<DriverTeamJoinRecord> ds = driverTeamJoinRecordMapper
                    .selectList(new QueryWrapper<DriverTeamJoinRecord>()
                    .eq("driver_id", driverId).eq("team_id",teamId));
            if (ds.size()>0) {
                //不允许提交多个相同申请
                return R.fail("您已提交过申请");
            }
            Integer exist = driverTeamMapper
                    .selectCount(new QueryWrapper<DriverTeam>()
                            .eq("id", teamId));
            if (exist<=0) {
                return R.fail("您申请的队伍不存在");
            }
        }
        record.setState(0);
        record.setCreateTime(LocalDateTime.now());
        driverTeamJoinRecordMapper.insert(record);
        return R.ok();
    }

    /**
     * 邀请入队
     * @param: driverId
     * @param: teamId
     * @return:
     * 记录邀请人信息，可添加邀请人关联字段
     */
    @PostMapping("InviteJoinTeam")
    @Transactional
    public R<String> inviteJoinTeam(Long driverId, Long teamId) {
        /**
         * author: code-zl
         * 查询是否已入队
         * 查询队伍成员数
         * 队伍容量50
         */

        return R.ok();
    }

    /**
     * 入队审核
     * 成功时需要把其他申请状态改成已入队状态
     */
    @PostMapping("authJoin")
    @Transactional
    public R<String> authJoin(@RequestBody DriverTeamJoinRecord record) {
        Long recordId = record.getId();
        Integer newState = record.getState();
        if (recordId==null||newState==null||newState==0||newState>3) {
            return R.fail("传入参数有误: id>"+recordId+",state>"+newState);
        }
        DriverTeamJoinRecord oldRecord = driverTeamJoinRecordMapper.selectById(recordId);
        if (oldRecord==null) {
            return R.fail("申请不存在");
        }
        Integer oldState = oldRecord.getState();
        if (oldState!=0) {
            return R.fail("已经审核");
        }
        /**
         * 要从数据库验证，如果对顺序无要求，遵守从小表开始到大表
        */
        Long teamId = oldRecord.getTeamId();
        DriverTeam driverTeam = driverTeamMapper.selectById(teamId);
        if (driverTeam==null)  {
            return R.fail("队伍不存在");
        }

        Long driverId = oldRecord.getDriverId();
        Long integer = driverMapper.teamId(driverId);
        if (integer!=null&&integer>0) {
            driverTeamJoinRecordMapper.abandonApply(driverId);
            return R.fail("已经加入队伍");
        }

        oldRecord.setState(newState);
        driverTeamJoinRecordMapper.update(oldRecord,new QueryWrapper<DriverTeamJoinRecord>().eq("id",recordId));
        driverMapper.joinTeam(teamId,driverId);
        /**
         * 更新成员总数
        */
        driverTeam.setTotalMember(driverTeam.getTotalMember()+1);
        driverTeamMapper.updateById(driverTeam);
        return R.ok();
    }


    @PostMapping("exitTeam")
    @Transactional
    public R<Void> exitTeam() {
        /**
         * 普通用户退队
         * 退队记录
         */

        /**
         * 队长需要先转让队伍权利
        */

        return R.fail();
    }

    /**
     * 转让队伍
     * 转让人没有队伍
     * 转让记录；历史队伍
     */
    @PostMapping("transferTeam")
    @Transactional
    public R<Void> transferTeam() {
        return R.fail();
    }


}
