package com.paucls.runbookDDD.api.runbook

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class RunbookController {

    @RequestMapping("/")
    @ResponseBody
    fun home(): String {
        return "Hello!"
    }

}