package photogram.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testDefaultConstructor() {
        assertNotNull(user);
        assertNull(user.getUsername());
        assertNull(user.getEmail());
        assertNull(user.getAvatar());
        assertNull(user.getPassword());
        assertNull(user.getConfirmPassword());
        assertNull(user.getCreatedAt());
        assertNull(user.getUpdatedAt());
    }

    @Test
    void testParameterizedConstructor() {
        User testUser = new User("testUser", "test@example.com", "avatar.png", "password123");
        assertEquals("testUser", testUser.getUsername());
        assertEquals("test@example.com", testUser.getEmail());
        assertEquals("avatar.png", testUser.getAvatar());
        assertEquals("password123", testUser.getPassword());
    }

    @Test
    void testGettersAndSetters() {
        user.setUsername("newUser");
        user.setEmail("new@example.com");
        user.setAvatar("newAvatar.png");
        user.setPassword("newPassword123");
        user.setConfirmPassword("newPassword123");

        assertEquals("newUser", user.getUsername());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("newAvatar.png", user.getAvatar());
        assertEquals("newPassword123", user.getPassword());
        assertEquals("newPassword123", user.getConfirmPassword());
    }
}
