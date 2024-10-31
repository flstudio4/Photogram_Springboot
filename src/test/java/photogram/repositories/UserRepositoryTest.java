package photogram.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import photogram.models.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUsernameReturnsUser() {
        User testUser = new User("testUser", "test@example.com", "avatar.png", "password123");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userRepository.findByUsername("testUser");

        assertTrue(foundUser.isPresent());
        assertEquals("testUser", foundUser.get().getUsername());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void testFindByUsernameReturnsEmpty() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByUsername("unknownUser");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void testFindByEmailReturnsUser() {
        User testUser = new User("testUser", "test@example.com", "avatar.png", "password123");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
        assertEquals("testUser", foundUser.get().getUsername());
    }

    @Test
    void testFindByEmailReturnsEmpty() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        Optional<User> foundUser = userRepository.findByEmail("unknown@example.com");

        assertFalse(foundUser.isPresent());
    }
}
