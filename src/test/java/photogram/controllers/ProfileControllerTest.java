package photogram.controllers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProfileControllerTest {

    @Test
    public void testProfile_ReturnsProfileView() {
        ProfileController profileController = new ProfileController();
        String result = profileController.profile();

        assertEquals("profile", result, "The view name should be 'profile'");
    }
}
