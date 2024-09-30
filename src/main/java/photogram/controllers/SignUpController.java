package photogram.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import photogram.models.User;
import photogram.services.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/sign_up")
public class SignUpController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignUpController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String signUpForm(Model model) {
        model.addAttribute("user", new User());
        return "sign_up";
    }

    @PostMapping
    public String signUp(@Valid User newUser,
                         String confirmPassword,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "sign_up";
        }

        if (!newUser.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "sign_up";
        }

        if (!isValidPassword(newUser.getPassword())) {
            model.addAttribute("error", "Password must be more complex.");
            return "sign_up";
        }

        if (!isValidUsername(newUser.getUsername())) {
            model.addAttribute("error", "Username should only contain letters and numbers");
            return "sign_up";
        }

        if (!isValidUsernameLength(newUser.getUsername())) {
            model.addAttribute("error", "Username must be at least 6 characters and no longer than 12 characters");
            return "sign_up";
        }

        if (!isRepeatingCharacters(newUser.getUsername())) {
            model.addAttribute("error", "Username should not contain repeating characters");
            return "sign_up";
        }

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        try {
            userService.saveUser(newUser);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("error", "Username or email already exists");
            return "sign_up";
        }

        return "redirect:/profile";
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 &&
                password.length() <= 16 &&
                password.matches(".*[0-9].*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[!@#&()–[{}]:;',?/*~$^+=<>].*");
    }

    private boolean isValidUsername(String username) {
        return username.matches("^(?!.*[^a-zA-Z0-9]).*$");

    }

    private boolean isRepeatingCharacters(String username) {
        return username.matches("^(?!.*(.)\\1{2}).*$");
    }

    private boolean isValidUsernameLength(String username) {
        return username.length() >= 6 && username.length() <= 12;
    }
}
