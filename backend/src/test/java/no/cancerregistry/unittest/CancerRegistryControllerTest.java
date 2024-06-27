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
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CancerRegistryControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController cancerRegistryController;

    @Test
    public void testCreateUser_200OK() {
        UserDTO user = new UserDTO(
                Optional.of(1L),
                Optional.of(2),
                "John Doe");

        ResponseEntity<Long> response = cancerRegistryController.createUser(user);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(201));
    }

    @Test
    public void testUpdateUser_204NoContent() {
        UserDTO user = new UserDTO(
                Optional.of(1L),
                Optional.of(2),
                "John Doe");

        ResponseEntity<UserDTO> response = cancerRegistryController.updateUser(1L, user);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(204));

    }

    @Test
    public void testUpdateUser_userNotFound() {
        UserDTO user = new UserDTO(
                Optional.of(1L),
                Optional.of(2),
                "John Doe");

        doThrow(new UserNotFoundException("")).when(userService).updateUser(1L, user);

        ResponseEntity<UserDTO> response = cancerRegistryController.updateUser(1L, user);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(404));
    }

    @Test
    public void testUpdateUser_wrongVersion() {
        UserDTO user = new UserDTO(
                Optional.of(1L),
                Optional.of(2),
                "John Doe");

        doThrow(new WrongVersionException("")).when(userService).updateUser(1L, user);

        ResponseEntity<UserDTO> response = cancerRegistryController.updateUser(1L, user);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(400));
    }
}