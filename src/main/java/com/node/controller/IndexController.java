package com.node.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/*
    @author www.github.com/Acc2020
    @date  2020/4/8
*/
@Controller
public class IndexController {



    @RequestMapping({"/" ,"/index"})
    public String index(){
        return "index";
    }
}
