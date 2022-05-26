package com.codezl.ex_two_consumer.mapper;


import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface TestSqlMapper {

    @Select("select * from users limit 10")
    List<JSONObject> findUsers();
}
