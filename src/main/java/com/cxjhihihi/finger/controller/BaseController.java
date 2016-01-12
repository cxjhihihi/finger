package com.cxjhihihi.finger.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.cxjhihihi.finger.service.UserService;

public class BaseController {
    @Autowired
    UserService userService;

}
