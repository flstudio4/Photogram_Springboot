package photogram.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import photogram.models.User;
import photogram.repositories.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        User testUser = new User("testUser", "test@example.com", "avatar.png", "password123");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(testUser));

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testUser");

        assertNotNull(userDetails);
        assertEquals("testUser", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            customUserDetailsService.loadUserByUsername("unknownUser");
        });
    }
}
