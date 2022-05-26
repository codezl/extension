package com.dd_driving.team.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class DriverTeam extends DriverTeamRegister {

  @TableId(type = IdType.AUTO)
  private Long id;
  private String name;
  private Integer teamNo;
  private Long driverId;
  private String info;
  private Integer state;
  private Integer totalOrder;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createTime;
  private Integer totalMember;


//  public long getId() {
//    return id;
//  }
//
//  public void setId(long id) {
//    this.id = id;
//  }
//
//
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }


  public long getTeamNo() {
    return teamNo;
  }

  public void setTeamNo(Integer teamNo) {
    this.teamNo = teamNo;
  }


//  public long getDriverId() {
//    return driverId;
//  }
//
//  public void setDriverId(long driverId) {
//    this.driverId = driverId;
//  }
//
//
//  public String getInfo() {
//    return info;
//  }
//
//  public void setInfo(String info) {
//    this.info = info;
//  }
//
//  public Integer getState() {
//    return state;
//  }
//
//  public void setState(Integer state) {
//    this.state = state;
//  }
//
//  public LocalDateTime getCreateTime() {
//    return createTime;
//  }
//
//  public void setCreateTime(LocalDateTime createTime) {
//    this.createTime = createTime;
//  }

}
