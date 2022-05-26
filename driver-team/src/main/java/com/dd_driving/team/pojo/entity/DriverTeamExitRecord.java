package com.dd_driving.team.pojo.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
* @Description: 退队记录
* @Author: code-zl
* @Date: 2022/3/5
*/
@Data
@TableName("driver_team_exit_record")
public class DriverTeamExitRecord {

  @TableId(type = IdType.AUTO)
  private Long id;
  private Long driverId;
  private Long teamId;
  private Integer exitType;

  public Integer getExitType() {
    return exitType;
  }

  public void setExitType(Integer exitType) {
    this.exitType = exitType;
  }

  private Long operateId;
  private LocalDateTime createTime;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getDriverId() {
    return driverId;
  }

  public void setDriverId(Long driverId) {
    this.driverId = driverId;
  }

  public Long getTeamId() {
    return teamId;
  }

  public void setTeamId(Long teamId) {
    this.teamId = teamId;
  }

  public Long getOperateId() {
    return operateId;
  }

  public void setOperateId(Long operateId) {
    this.operateId = operateId;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }
}
