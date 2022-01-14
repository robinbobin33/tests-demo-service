package com.robinbobin.test.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import springfox.documentation.annotations.ApiIgnore

@ApiIgnore
@Controller
class DefaultController {
    @RequestMapping("/")
    fun home(): String {
        return "redirect:swagger-ui.html"
    }
}