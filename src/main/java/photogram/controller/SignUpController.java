package photogram.controller;

import jakarta.validation.Valid;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import photogram.model.User;
import photogram.service.UserService;
import photogram.utility.UserValidation;

@Controller
public class SignUpController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/sign_up")
    public String signUpForm(Model model) {
        model.addAttribute("user", new User());
        return "sign_up";
    }

    @PostMapping("/sign_up")
    public String signUp(@Valid @ModelAttribute("user") User newUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        UserValidation.validateUser(newUser, bindingResult, userService);

        if (bindingResult.hasErrors()) {
            return "sign_up";
        }
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        try {
            userService.saveUser(newUser);
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful! Please log in to your account.");
        } catch (Exception e) {
            bindingResult.reject("registrationError", "An error occurred during registration");
            return "sign_up";
        }

        return "redirect:/sign_in";
    }
}
