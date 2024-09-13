package user_management_demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class profileController {

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
