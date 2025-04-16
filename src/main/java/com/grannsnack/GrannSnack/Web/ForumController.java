package com.grannsnack.GrannSnack.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForumController {

    @GetMapping("/u/forum")
    public String forum() {
        return "forum";
    }
}
