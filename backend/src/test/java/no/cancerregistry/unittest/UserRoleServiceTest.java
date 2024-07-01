package no.cancerregistry.unittest;

import no.cancerregistry.exception.UserRoleNotFoundException;
import no.cancerregistry.model.UpdateUserRoleDTO;
import no.cancerregistry.model.UserRoleDTO;
import no.cancerregistry.repository.RoleRepository;
import no.cancerregistry.repository.UnitRepository;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.UserRoleRepository;
import no.cancerregistry.repository.entity.Role;
import no.cancerregistry.repository.entity.Unit;
import no.cancerregistry.repository.entity.User;
import no.cancerregistry.repository.entity.UserRole;
import no.cancerregistry.service.UserRoleService;
import no.cancerregistry.exception.WrongVersionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    static private UserRoleService userRoleService;

    static private UserRoleRepository userRoleRepository;
    static private UserRepository userRepository;
    static private UnitRepository unitRepository;
    static private RoleRepository roleRepository;

    @BeforeAll
    static void setup() {
        userRoleRepository = mock(UserRoleRepository.class);
        userRepository = mock(UserRepository.class);
        unitRepository = mock(UnitRepository.class);
        roleRepository = mock(RoleRepository.class);
        userRoleService = new UserRoleService(userRoleRepository, userRepository, unitRepository, roleRepository);
    }

    @Test
    public void whenCreateUserRole_thenReturnSavedUserRole() {
        UserRoleDTO userRole = new UserRoleDTO(
                Optional.of(1L),
                Optional.of(1),
                1L,
                1L,
                1L,
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(3));

        UserRole userRoleMock = new UserRole();
        userRoleMock.setVersion(1);
        userRoleMock.setUser(new User());
        userRoleMock.setUnit(new Unit());
        userRoleMock.setRole(new Role());

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(unitRepository.findById(1L)).thenReturn(Optional.of(new Unit()));
        when(roleRepository.findById(1L)).thenReturn(Optional.of(new Role()));
        when(userRoleRepository.save(any(UserRole.class))).thenReturn(userRoleMock);

        UserRoleDTO savedUserRole = userRoleService.createUserRole(userRole);

        assertNotNull(savedUserRole);
        assertEquals(savedUserRole.getVersion(), Optional.of(1));
    }

    // Overlapping roles

    @Test
    public void givenAbundantUserRole_whenGetUserRole_thenThrowUserRoleNotFoundException() {
        when(userRoleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserRoleNotFoundException.class, () -> userRoleService.getUserRole(1L));
    }

	/*@Test
	public void givenNullId_whenGetUserRoles_thenThrowUNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () -> UserRoleService.getUserRoles(null, null, null));
	}*/

    @Test
    public void givenNoParameters_whenGetUserRoles_thenReturnsSuccessfully() {
        UserRole userRoleMock = new UserRole();
        userRoleMock.setVersion(1);
        userRoleMock.setUser(new User());
        userRoleMock.setUnit(new Unit());
        userRoleMock.setRole(new Role());

        when(userRoleRepository.findAll()).thenReturn(List.of(userRoleMock));

        List<UserRoleDTO> userRoles = userRoleService.getUserRoles(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty());

        assertFalse(userRoles.isEmpty());
    }

    @Test
    public void givenWrongVersion_whenUpdateUserRole_thenThrowWrongVersionException() {
        UpdateUserRoleDTO userRole = new UpdateUserRoleDTO(
                Optional.of(1L),
                Optional.of(2),
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(3));

        UserRole userRoleMock = new UserRole();
        userRoleMock.setVersion(1);
        userRoleMock.setUser(new User());
        userRoleMock.setUnit(new Unit());
        userRoleMock.setRole(new Role());

        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(userRoleMock));

        assertThrows(WrongVersionException.class, () -> userRoleService.updateUserRole(1L, userRole));
    }

    @Test
    public void givenNullVersion_whenUpdateUserRole_thenThrowWrongVersionException() {
        UpdateUserRoleDTO userRole = new UpdateUserRoleDTO(
                Optional.of(1L),
                Optional.ofNullable(null),
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(3));

        assertThrows(WrongVersionException.class, () -> userRoleService.updateUserRole(1L, userRole));
    }

    @Test
    public void givenAbundantUserRole_whenUpdateUserRole_thenThrowUserRoleNotFoundException() {
        UpdateUserRoleDTO userRole = new UpdateUserRoleDTO(
                Optional.of(1L),
                Optional.of(5),
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(3));

        UserRole userRoleMock = new UserRole();
        userRoleMock.setVersion(1);
        userRoleMock.setUser(new User());
        userRoleMock.setUnit(new Unit());
        userRoleMock.setRole(new Role());

        when(userRoleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserRoleNotFoundException.class, () -> userRoleService.updateUserRole(1L, userRole));
    }

    @Test
    public void givenExistingUserRole_whenUpdateUserRole_thenUpdatesSuccessfully() {
        UpdateUserRoleDTO userRole = new UpdateUserRoleDTO(
                Optional.of(1L),
                Optional.ofNullable(1),
                ZonedDateTime.now(),
                ZonedDateTime.now().plusDays(3));

        UserRole userRoleMock = new UserRole();
        userRoleMock.setVersion(1);
        userRoleMock.setUser(new User());
        userRoleMock.setUnit(new Unit());
        userRoleMock.setRole(new Role());

        when(userRoleRepository.findById(1L)).thenReturn(Optional.of(userRoleMock));

        assertDoesNotThrow(() -> userRoleService.updateUserRole(1L, userRole));
    }
}