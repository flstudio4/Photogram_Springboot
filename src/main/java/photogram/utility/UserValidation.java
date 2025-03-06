package photogram.utility;

import org.springframework.validation.BindingResult;
import photogram.model.User;
import photogram.service.UserService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidation {

    public static void validateUser(User newUser, BindingResult bindingResult, UserService userService) {
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

    private static boolean isValidPassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–{}:;',?/*~$^+=<>]).{8,16}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]+$");
    }

    private static boolean isRepeatingCharacters(String username) {
        return username.matches("^(?!.*(.)\\1{2}).*$");
    }

    private static boolean isValidUsernameLength(String username) {
        return username.length() >= 6 && username.length() <= 12;
    }
}
