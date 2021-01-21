package com.vslab.ClientApplication.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DefaultController {

    @GetMapping("/index")
    public String root() {
        return "index";
    }

    @GetMapping("/start")
    public String startPage() {
        return "start";
    }
}
