package photogram;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import photogram.models.User;
import photogram.services.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public String signUp(@Valid @ModelAttribute("user") User newUser,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        // Validation checks
        validateUser(newUser, bindingResult);

        if (bindingResult.hasErrors()) {
            return "sign_up"; // Return to sign-up form if there are errors
        }

        // Password encoding and saving user
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));

        try {
            userService.saveUser(newUser);
            redirectAttributes.addFlashAttribute("registrationSuccess", "Registration successful! Please log in to your account.");
        } catch (Exception e) {
            bindingResult.reject("registrationError", "An error occurred during registration");
            return "sign_up";
        }

        return "redirect:/sign_in"; // Redirect to sign-in on success
    }

    private void validateUser(User newUser, BindingResult bindingResult) {
        if (!isValidUsername(newUser.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username should only contain letters and numbers");
        } else if (!isValidUsernameLength(newUser.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username must be at least 6 characters and no longer than 12 characters");
        } else if (!isRepeatingCharacters(newUser.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username should not contain repeating characters");
        } else if (userService.usernameExists(newUser.getUsername())) {
            bindingResult.rejectValue("username", "error.username", "Username is already taken");
        }

        if (userService.ifEmailExists(newUser.getEmail())) {
            bindingResult.rejectValue("email", "error.email", "Email is already taken");
        }

        if (!newUser.getPassword().equals(newUser.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match");
        }

        if (!isValidPassword(newUser.getPassword())) {
            bindingResult.rejectValue("password", "error.password", "Password must contain at least one uppercase letter, one number, and one special character");
        }
    }

    private boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]+$");
    }

    private boolean isRepeatingCharacters(String username) {
        return username.matches("^(?!.*(.)\\1{2}).*$");
    }

    private boolean isValidUsernameLength(String username) {
        return username.length() >= 6 && username.length() <= 12;
    }
}
