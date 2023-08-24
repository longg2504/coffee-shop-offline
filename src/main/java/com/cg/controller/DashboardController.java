package com.cg.controller;

import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboards")
public class DashboardController {
    @Autowired
    private AppUtils appUtils;
    @GetMapping
    public ModelAndView showDashboard(){
        String userName = appUtils.getPrincipalUsername();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName",userName);
        modelAndView.setViewName("/dashboard/list-product");
        return modelAndView;
    }
}
