//package com.lauheng.controller;
//
//import com.github.pagehelper.PageInfo;
//import com.lauheng.service.IUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@Controller
//@RestController
//public class TestController {
//
//
//    @Autowired
//    private IUserService userService;
//
////    在页面上输出打印一句话
//    @RequestMapping("/testContent")
//    public  String testContent(){
//        return "我在使用springboot....";
//    }

//    @RequestMapping("/queryUserList")
//    public List<UserInfo> queryUserList(){
//        List<UserInfo> list = userService.list();
//        return list;
//    }
//
//    @RequestMapping("/insertUserInfo")
//    public String insertUserInfo(){
//        UserInfo info=new UserInfo();
//        info.setUsername("xiaogao");
//        info.setPassword("123");
//        boolean bs=userService.save(info);
//        if(bs){
//            return "ok";
//        }
//        return "no";
//    }
////    测试分页效果
//    @RequestMapping("/queryListByPage")
//    public List<UserInfo> queryListByPage(){
//        PageInfo pageInfo=userService.queryUserPage(1,1);
//        return pageInfo.getList();
//
//    }

//}
