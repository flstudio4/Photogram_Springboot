package photogram.controllers;

import org.junit.jupiter.api.Test;
import photogram.HomeController;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    @Test
    public void testGetHomePage_ReturnsHomeView() {
        HomeController homeController = new HomeController();
        String result = homeController.getHomePage();

        assertEquals("home", result, "The view name should be 'home'");
    }
}
