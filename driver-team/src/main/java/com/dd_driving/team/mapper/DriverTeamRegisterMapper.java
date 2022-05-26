package com.dd_driving.team.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dd_driving.team.pojo.entity.DriverTeam;
import com.dd_driving.team.pojo.entity.DriverTeamRegister;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface DriverTeamRegisterMapper extends BaseMapper<DriverTeamRegister> {

    @Select("SELECT state FROM driver_team_register WHERE ID=#{id}")
    Integer teamState(Long id);

    @Select("SELECT * FROM driver_team_register WHERE ID=#{id}")
    DriverTeam findById(Long id);
}
