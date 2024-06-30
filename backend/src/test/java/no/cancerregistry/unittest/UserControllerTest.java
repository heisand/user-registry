package no.cancerregistry.unittest;

import no.cancerregistry.controller.UserController;
import no.cancerregistry.controller.UserRoleController;
import no.cancerregistry.model.RoleDTO;
import no.cancerregistry.model.UnitDTO;
import no.cancerregistry.model.UserWithRolesDTO;
import no.cancerregistry.service.UserRoleService;
import no.cancerregistry.service.UserService;
import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.UserDTO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;
    static private UserRoleService userRoleService;

    @InjectMocks
    private UserController cancerRegistryController;

    static private UserRoleController userRoleController;

    @BeforeAll
    static void setup() {
        userRoleService = mock(UserRoleService.class);
        userRoleController = new UserRoleController(userRoleService);
    }

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

    @Test
    public void testMe() {
        UserDTO user = new UserDTO(
                null, null, "Forskern");

        UnitDTO unit = new UnitDTO(
                null, null, "FHI");

        RoleDTO role = new RoleDTO(
                Optional.of(1L), Optional.of(1), "Forsker");
        List<RoleDTO> roles = new ArrayList<>();
        roles.add(role);

        UserWithRolesDTO userWithRole = new UserWithRolesDTO(
                1L, "Heidi", roles);
        List<UserWithRolesDTO> usersWithRoles = new ArrayList<>();
        usersWithRoles.add(userWithRole);

        when(userRoleService.getUsersWithRolesByUnitId(1L)).thenReturn(usersWithRoles);

        ResponseEntity<List<UserWithRolesDTO>> response = userRoleController.getUsersWithRolesByUnitId(1L);

        assertNotNull(response);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}