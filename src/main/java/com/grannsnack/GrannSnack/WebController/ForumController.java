package com.grannsnack.GrannSnack.WebController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/posts")
public class ForumController {

    @GetMapping("/u/forum")
    public String forum() {
        return "forum";
    }
}
