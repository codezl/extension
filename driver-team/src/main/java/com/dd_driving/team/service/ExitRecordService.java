package com.dd_driving.team.service;

import com.dd_driving.team.mapper.DriverMapper;
import com.dd_driving.team.mapper.DriverTeamExitRecordMapper;
import com.dd_driving.team.mapper.DriverTeamMapper;
import com.dd_driving.team.pojo.entity.DriverTeam;
import com.dd_driving.team.pojo.entity.DriverTeamExitRecord;
import com.dd_driving.team.utils.ResInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: code-zl
 * @Date: 2022/03/05/15:14
 * @Description:
 */
@Service
@Slf4j
public class ExitRecordService {

    @Resource
    DriverMapper  driverMapper;
    @Autowired
    DriverTeamMapper teamMapper;
    @Autowired
    DriverTeamExitRecordMapper exitRecordMapper;

    @Transactional(rollbackFor = Exception.class)
    public void oneExit(Long driverId,Long teamId) {
        Long integer = driverMapper.teamId(driverId);
        if (integer.equals(teamId)) {
            teamMapper.exitChange(teamId);
            int exit = driverMapper.exitTeam(teamId,driverId);
            DriverTeamExitRecord exitRecord = new DriverTeamExitRecord();
            exitRecord.setCreateTime(LocalDateTime.now());
            exitRecord.setDriverId(driverId);
            exitRecord.setExitType(1);
            exitRecord.setTeamId(teamId);
            int insert = exitRecordMapper.insert(exitRecord);
            if (insert>0&&exit>0) {
                log.info(ResInfo.SUCCESS_MSG);
            }else {
                log.info(ResInfo.FAIL_MSG);
            }
        }else {
            log.info("不在此队伍");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void removeOne(Long teamId,Long driverId,Long operateId) {
        DriverTeam driverTeam = teamMapper.selectById(teamId);
        boolean equals = driverTeam.getDriverId().equals(operateId);
        if (equals) {
            teamMapper.exitChange(teamId);
            int exit = driverMapper.exitTeam(teamId, driverId);
            DriverTeamExitRecord exitRecord = new DriverTeamExitRecord();
            exitRecord.setTeamId(teamId);
            exitRecord.setDriverId(driverId);
            exitRecord.setCreateTime(LocalDateTime.now());
            exitRecord.setExitType(1);
            exitRecord.setOperateId(operateId);
            if (exit>0) {
                log.info(ResInfo.SUCCESS_MSG);
            }
        }else {
            log.info("不是队长，权限不足");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void disband(Long teamId,Long driverId) {
        DriverTeam driverTeam = teamMapper.selectById(teamId);
        boolean equals = driverTeam.getDriverId().equals(driverId);
        if (equals) {
            driverMapper.disbandTeamMember(teamId);
            int i = teamMapper.deleteById(teamId);
            if (i>0) {
                log.info(ResInfo.SUCCESS_MSG);
            }else {
                log.info(ResInfo.FAIL_MSG);
            }
        }else {
            log.info("不是队长，无权解散");
        }
        teamMapper.deleteById(teamId);
    }

}
