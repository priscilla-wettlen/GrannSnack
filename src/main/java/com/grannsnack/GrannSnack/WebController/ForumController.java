package com.grannsnack.GrannSnack.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({"/u/forum", "/a/forum"})
public class ForumController {

    @GetMapping
    public String forum() {
        return "forum";
    }

}
