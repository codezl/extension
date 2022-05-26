package com.dd_driving.team.mapper;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface DriverMapper {
    @Select("SELECT TEAM_ID FROM DRIVER WHERE ID=#{id}")
    Long teamId(Long id);

    @Update("UPDATE driver SET team_id=#{teamId} WHERE id=#{id}")
    void joinTeam(Long teamId,Long id);

    @Update("UPDATE driver SET team_id=NULL WHERE id=#{id} AND team_id=#{teamId}")
    int exitTeam(Long teamId,Long id);

    @Update("UPDATE DRIVER SET TEAM_ID=NULL WHERE TEAM_ID=#{teamId}")
    void disbandTeamMember(Long teamId);
}
