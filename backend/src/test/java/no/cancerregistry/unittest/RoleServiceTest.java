package no.cancerregistry.unittest;

import no.cancerregistry.exception.RoleNotFoundException;
import no.cancerregistry.model.RoleDTO;
import no.cancerregistry.repository.RoleRepository;
import no.cancerregistry.repository.entity.Role;
import no.cancerregistry.service.RoleService;
import no.cancerregistry.exception.WrongVersionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoleServiceTest {

    static private RoleService roleService;
    static private RoleRepository roleRepository;

    @BeforeAll
    static void setup() {
        roleRepository = mock(RoleRepository.class);
        roleService = new RoleService(roleRepository);
    }

    @Test
    public void whenCreateRole_thenReturnSavedRole() {
        RoleDTO role = new RoleDTO(
                Optional.of(1L),
                Optional.of(2),
                "Researcher Researcher");

        Role roleMock = new Role();
        roleMock.setVersion(1);
        roleMock.setName("Researcher Researcher");

        when(roleRepository.save(any(Role.class))).thenReturn(roleMock);

        RoleDTO savedRole = roleService.createRole(role);

        assertNotNull(savedRole);
        assertEquals(savedRole.getVersion(), Optional.of(1));
    }

    @Test
    public void givenAbundantRole_whenGetRole_thenThrowRoleNotFoundException() {
        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.getRole(1L));
    }

	/*@Test
	public void givenNullId_whenGetRoles_thenThrowUNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () -> roleService.getRoles(null, null, null));
	}*/

    @Test
    public void givenNoParameters_whenGetRoles_thenReturnsSuccessfully() {
        when(roleRepository.findAll()).thenReturn(List.of(new Role()));

        List<RoleDTO> roles =
                roleService.getRoles(Optional.empty());

        assertFalse(roles.isEmpty());
    }

    @Test
    public void givenWrongVersion_whenUpdateRole_thenThrowWrongVersionException() {
        RoleDTO role = new RoleDTO(
                Optional.of(1L),
                Optional.of(2),
                "Researcher Researcher");

        Role roleMock = new Role();
        roleMock.setVersion(1);
        roleMock.setName("Researcher Researcher");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleMock));

        assertThrows(WrongVersionException.class, () -> roleService.updateRole(1L, role));
    }

    @Test
    public void givenNullVersion_whenUpdateRole_thenThrowWrongVersionException() {
        RoleDTO role = new RoleDTO(
                Optional.of(1L),
                Optional.ofNullable(null),
                "Researcher Researcher");

        assertThrows(WrongVersionException.class, () -> roleService.updateRole(1L, role));
    }

    @Test
    public void givenAbundantRole_whenUpdateRole_thenThrowRoleNotFoundException() {
        RoleDTO role = new RoleDTO(
                Optional.of(1L),
                Optional.of(1),
                "Researcher Researcher");

        Role roleMock = new Role();
        roleMock.setVersion(1);
        roleMock.setName("Researcher Researcher");

        when(roleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.updateRole(1L, role));
    }

    @Test
    public void givenExistingRole_whenUpdateRole_thenUpdatesSuccessfully() {
        RoleDTO role = new RoleDTO(
                Optional.of(1L),
                Optional.of(1),
                "Researcher Researcher");

        Role roleMock = new Role();
        roleMock.setVersion(1);
        roleMock.setName("Researcher Researcher");

        when(roleRepository.findById(1L)).thenReturn(Optional.of(roleMock));

        assertDoesNotThrow(() -> roleService.updateRole(1L, role));
    }
}