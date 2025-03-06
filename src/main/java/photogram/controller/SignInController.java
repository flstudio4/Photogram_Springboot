package photogram.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SignInController {

    @GetMapping("/sign_in")
    public String signIn(@RequestParam(value = "error", required = false) String error,
                         @RequestParam(value = "logout", required = false) String logout,
                         Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "sign_in";
    }

    @PostMapping("/sign_in")
    public String signInPost() {
        return "redirect:/profile";
    }
}
