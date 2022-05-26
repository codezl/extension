package com.codezl.dd_team.controller;

import com.alibaba.fastjson.JSONObject;
import com.codezl.dd_team.dao.mapper1.Sql1Mapper;
import com.codezl.dd_team.dao.mapper2.Sql2Mapper;
import com.codezl.dd_team.pojo.entity.MyBean;
import com.codezl.dd_team.pojo.entity.Team;
import com.mysql.cj.jdbc.MysqlDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MySqlController {

//    @Autowired
//    TeamMapper teamMapper;

    public void tst() {
        MysqlDataSourceFactory mysqlDataSourceFactory = new MysqlDataSourceFactory();
        //DataSource dataSource = mysqlDataSourceFactory.getObjectInstance(,null,null,null);
    }

    @GetMapping("getAll")
    public void getAll() {
//        List<JSONObject> users = teamMapper.find();
//        System.out.println(users);
//        String s = teamMapper.selectById();
//        System.out.println(s);
//        Team team = teamMapper.selectById2();
//        System.out.println(team);
        //因为bean的xml是懒加载，所以下面的方式未初始化
        MyBean beam = new MyBean();
        System.out.println(beam.getName());

        //通过getBean方式才能实例化调用
        XmlBeanFactory factory = new XmlBeanFactory (new ClassPathResource("conf/bean.xml"));
        MyBean obj = (MyBean) factory.getBean("helloWorld");
        String name = obj.getName();
        System.out.println(name);

        //新的方式。预加载,详情参考公众号"快乐携带者"公众号
        ApplicationContext context = new ClassPathXmlApplicationContext("conf/bean.xml");
        MyBean helloWorld = (MyBean) context.getBean("helloWorld");
        System.out.println("context获取："+helloWorld.getName());
    }

    @Autowired
    Sql1Mapper sql1Mapper;

    @Autowired
    Sql2Mapper sql2Mapper;
    //多数据库测试
    @GetMapping("sqlTest")
    public void multipleDatesource() {
        Team team = sql1Mapper.selectById();
        System.out.println("test1数据库："+team);
        String s = sql2Mapper.selectNameById();
        System.out.println("test2数据库："+s);
    }
}
