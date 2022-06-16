package com.perfumeworld.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.perfumeworld.service.CategoryService;



public class CategoryInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    CategoryService categoryService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        request.setAttribute("menuCate", categoryService.viewAllService());
        return true;
    }
}
