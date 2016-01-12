package com.cxjhihihi.finger.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.cxjhihihi.finger.domain.User;
import com.cxjhihihi.finger.utils.Utils;

@Controller
public class UserController extends BaseController {
    @RequestMapping("/user/add-user.m")
    public void addUser(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jv = new JSONObject();
        User user = new User();
        user.setPassword("testcxj");
        user.setUsername("cxjhihihi");
        user.setUser_type(0);
        userService.add(user);
        Utils.writeBack(request, response, jv);

    }
}
