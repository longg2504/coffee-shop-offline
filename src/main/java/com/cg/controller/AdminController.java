package com.cg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboards")
public class AdminController {

    @GetMapping()
    public String showDashboradPage() {
        return "/dashboard/list-product";
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 39e04d2 (upload)
