package com.dd_driving.team.mapper;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd_driving.team.pojo.entity.DriverTeam;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
public interface DriverTeamMapper extends BaseMapper<DriverTeam> {

    @Select("SELECT MAX(TEAM_NO) FROM driver_team")
    Integer maxTeamNo();

    @Select("SELECT state FROM driver_team WHERE ID=#{id}")
    Integer teamState(Integer id);

    @Update("UPDATE DRIVER_TEAM SET TOTAL_MEMBER=TOTAL_MEMBER-1 WHERE ID=#{id}")
    int exitChange(Long id);
}
