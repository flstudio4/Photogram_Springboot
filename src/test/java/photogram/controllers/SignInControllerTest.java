package photogram.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import photogram.SignInController;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class SignInControllerTest {

    @Test
    public void testSignIn_WithError_ReturnsSignInViewWithErrorMessage() {
        SignInController signInController = new SignInController();
        Model model = mock(Model.class);
        String errorParam = "someError";

        String result = signInController.signIn(errorParam, null, model);
        assertEquals("sign_in", result, "The view name should be 'sign_in'");
    }

    @Test
    public void testSignIn_WithLogout_ReturnsSignInViewWithLogoutMessage() {
        SignInController signInController = new SignInController();
        Model model = mock(Model.class);
        String logoutParam = "true";

        String result = signInController.signIn(null, logoutParam, model);

        assertEquals("sign_in", result, "The view name should be 'sign_in'");
    }

    @Test
    public void testSignInPost_ReturnsRedirectToProfile() {
        SignInController signInController = new SignInController();

        String result = signInController.signInPost();

        assertEquals("redirect:/profile", result, "The result should redirect to '/profile'");
    }
}
