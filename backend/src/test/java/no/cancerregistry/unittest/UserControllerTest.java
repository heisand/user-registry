package no.cancerregistry.unittest;

import no.cancerregistry.controller.UserController;
import no.cancerregistry.service.UserService;
import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController cancerRegistryController;

    @Test
    public void testCreateUser_200OK() {
        UserDTO user = new UserDTO(
                Optional.of(1L),
                Optional.of(2),
                "Forsker Forskersen");

        ResponseEntity<Long> response = cancerRegistryController.createUser(user);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void testUpdateUser_204NoContent() {
        UserDTO user = new UserDTO(
                Optional.of(1L),
                Optional.of(2),
                "Forsker Forskersen");

        ResponseEntity<UserDTO> response = cancerRegistryController.updateUser(1L, user);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }
}