package com.cg.controller;

import com.cg.exception.DataInputException;
import com.cg.model.Role;
import com.cg.model.User;
import com.cg.service.user.IUserService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("/dashboards")
public class AdminController {

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IUserService userService;

    @GetMapping()
    public ModelAndView showDashboardPage() {
        String userName = appUtils.getPrincipalUsername();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userName",userName);
        modelAndView.setViewName("dashboard/list-product");
        return modelAndView;
    }

    @GetMapping("/revenue")
    public String showRevenue(Model model) {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByName(username);

        if (!userOptional.isPresent()) {
            throw new DataInputException("User not valid");
        }
        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();

        model.addAttribute("userName", username);
        model.addAttribute("roleCode", roleCode);
        return "/dashboard/revenue";
    }

    @GetMapping("/products")
    public String showListProduct(Model model) {
        String username = appUtils.getPrincipalUsername();

        Optional<User> userOptional = userService.findByName(username);

        if (!userOptional.isPresent()) {
            throw new DataInputException("User not valid");
        }

        Role role = userOptional.get().getRole();
        String roleCode = role.getCode();

//        username = username.substring(0, username.indexOf("@"));
        model.addAttribute("userName", username);
        model.addAttribute("roleCode", roleCode);
        return "dashboard/list-product";
    }
}