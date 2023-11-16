package com.sch.selenium.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class testController {

    @GetMapping("/")
    public String test(@RequestParam("url") String url) {
        Selenium sel = new Selenium();
        return sel.useDriver(url);
    }
}
