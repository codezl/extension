package com.dd_driving.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd_driving.team.pojo.entity.DriverTeamJoinRecord;
import org.apache.ibatis.annotations.Update;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: code-zl
 * @Date: 2022/03/03/17:24
 * @Description: 入队记录
 */
public interface DriverTeamJoinRecordMapper extends BaseMapper<DriverTeamJoinRecord> {
    @Update("UPDATE driver_team_join_record SET \"state\"=3 WHERE driver_id=#{driverId}")
    int abandonApply(Long driverId);
}
