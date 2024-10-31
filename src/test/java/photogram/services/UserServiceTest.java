package photogram.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import photogram.models.User;
import photogram.repositories.UserRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User("testUser", "test@example.com", "avatar.png", "password123");
    }

    @Test
    void testGetAllUsers() {
        List<User> users = Collections.singletonList(testUser);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testUser, result.get(0));
    }

    @Test
    void testGetUserByIdFound() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        User result = userService.getUserById(1);

        assertNotNull(result);
        assertEquals(testUser, result);
    }

    @Test
    void testGetUserByIdNotFound() {
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        User result = userService.getUserById(1);

        assertNull(result);
    }

    @Test
    void testGetUserByUsernameFound() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        User result = userService.getUserByUsername("testUser");

        assertNotNull(result);
        assertEquals(testUser, result);
    }

    @Test
    void testGetUserByUsernameNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        User result = userService.getUserByUsername("unknownUser");

        assertNull(result);
    }

    @Test
    void testUsernameExistsTrue() {
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        boolean exists = userService.usernameExists("testUser");

        assertTrue(exists);
    }

    @Test
    void testUsernameExistsFalse() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        boolean exists = userService.usernameExists("unknownUser");

        assertFalse(exists);
    }

    @Test
    void testIfEmailExistsTrue() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        boolean exists = userService.ifEmailExists("test@example.com");

        assertTrue(exists);
    }

    @Test
    void testIfEmailExistsFalse() {
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        boolean exists = userService.ifEmailExists("unknown@example.com");

        assertFalse(exists);
    }

    @Test
    void testSaveUser() {
        userService.saveUser(testUser);

        verify(userRepository, times(1)).save(testUser);
    }
}
