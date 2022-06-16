package com.perfumeworld.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.perfumeworld.interceptor.Auth;
import com.perfumeworld.interceptor.Auth.Role;



@Controller
public class Home {
    @Auth(role = Role.ADMIN)
    @RequestMapping(path = "/admin/trang-chu", method = RequestMethod.GET)
    public String viewHomePage() {
        return "admin/home";
    }
}
