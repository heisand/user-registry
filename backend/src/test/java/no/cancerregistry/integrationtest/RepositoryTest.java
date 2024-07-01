package no.cancerregistry.integrationtest;

import no.cancerregistry.model.UserWithRolesDTO;
import no.cancerregistry.repository.RoleRepository;
import no.cancerregistry.repository.UnitRepository;
import no.cancerregistry.repository.UserRoleRepository;
import no.cancerregistry.repository.entity.Role;
import no.cancerregistry.repository.entity.Unit;
import no.cancerregistry.repository.entity.User;
import no.cancerregistry.repository.entity.UserRole;
import no.cancerregistry.service.UserRoleService;
import org.junit.jupiter.api.Test;
import no.cancerregistry.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class RepositoryTest {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserRoleService userRoleService;

    @Autowired
    public RepositoryTest(UserRepository userRepository,
                          UserRoleRepository userRoleRepository,
                          UnitRepository unitRepository,
                          RoleRepository roleRepository,
                          UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.unitRepository = unitRepository;
        this.roleRepository = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRoleService = userRoleService;
    }

    @Test
    public void whenCreateUser_thenReturnSavedUserWithNewId() {
        User newUser = new User();
        newUser.setVersion(1);
        newUser.setName("Researcher Researcher");

        User savedUser = userRepository.save(newUser);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals("Researcher Researcher", foundUser.getName());
        assertEquals(1, foundUser.getId());
        assertEquals(1, foundUser.getVersion());

        userRepository.deleteById(foundUser.getId());
        User deletedUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertNull(deletedUser);
    }

    @Test
    public void whenGetUnitUsersWithRoles_thenReturnUsersWithRoles() {
        User user = new User();
        user.setVersion(2);
        user.setName("Researcher Researchersen");

        User savedUser = userRepository.save(user);

        Unit unit = new Unit();
        unit.setName("Researcher");
        unit.setVersion(2);

        Unit savedUnit = unitRepository.save(unit);

        Unit unit2 = new Unit();
        unit.setName("Lege");
        unit.setVersion(2);

        unitRepository.save(unit2);

        Role role = new Role();
        role.setName("Researcher");
        role.setVersion(2);

        Role savedRole = roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setVersion(2);
        userRole.setUser(savedUser);
        userRole.setUnit(savedUnit);
        userRole.setRole(savedRole);

        userRoleRepository.save(userRole);

        List<UserWithRolesDTO> usersWithRoles = userRoleService.getUsersWithRolesByUnitId(1L);

        assertEquals(1, usersWithRoles.size());
    }

    @Test
    public void whenGetValidUserRoles_thenReturnOnlyValidUserRoles() {
        User user = new User();
        user.setVersion(2);
        user.setName("Researcher Researchersen");

        User savedUser = userRepository.save(user);

        Unit unit = new Unit();
        unit.setName("Researcher");
        unit.setVersion(2);

        Unit savedUnit = unitRepository.save(unit);

        Role role = new Role();
        role.setName("Researcher");
        role.setVersion(2);

        Role savedRole = roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setVersion(2);
        userRole.setUser(savedUser);
        userRole.setUnit(savedUnit);
        userRole.setRole(savedRole);
        userRole.setValidFrom(ZonedDateTime.now());
        userRole.setValidTo(ZonedDateTime.now().plusDays(3));

        userRoleRepository.save(userRole);

        UserRole userRole2 = new UserRole();
        userRole2.setVersion(2);
        userRole2.setUser(savedUser);
        userRole2.setUnit(savedUnit);
        userRole2.setRole(savedRole);
        userRole2.setValidFrom(ZonedDateTime.now().plusDays(4));
        userRole2.setValidTo(ZonedDateTime.now().plusDays(5));

        userRoleRepository.save(userRole2);

        List<UserRole> validUserRoles = userRoleRepository.findValidUserRoles(1L, 1L, ZonedDateTime.now());

        assertEquals(1, validUserRoles.size());
    }

    @Test
    public void givenOverlappingRoles_whenFindUserRoles_thenFindOverlappingRoles() {
        User user = new User();
        user.setVersion(2);
        user.setName("Researcher Researchersen");

        User savedUser = userRepository.save(user);

        Unit unit = new Unit();
        unit.setName("Researcher");
        unit.setVersion(2);

        Unit savedUnit = unitRepository.save(unit);

        Role role = new Role();
        role.setName("Researcher");
        role.setVersion(2);

        Role savedRole = roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setVersion(2);
        userRole.setUser(savedUser);
        userRole.setUnit(savedUnit);
        userRole.setRole(savedRole);
        userRole.setValidFrom(ZonedDateTime.now());
        userRole.setValidTo(ZonedDateTime.now().plusDays(1));

        userRoleRepository.save(userRole);

        boolean isOverlappingRoles = userRoleService.hasOverlappingRole(userRole);

        assertTrue(isOverlappingRoles);
    }

    @Test
    public void givenNoOverlappingRoles_whenFindUserRoles_thenFindNoOverlappingRoles() {
        User user = new User();
        user.setVersion(2);
        user.setName("Researcher Researchersen");

        User savedUser = userRepository.save(user);

        Unit unit = new Unit();
        unit.setName("Researcher");
        unit.setVersion(2);

        Unit savedUnit = unitRepository.save(unit);

        Unit unit2 = new Unit();
        unit.setName("Lege");
        unit.setVersion(2);

        Unit savedUnit2 = unitRepository.save(unit2);

        Role role = new Role();
        role.setName("Researcher");
        role.setVersion(2);

        Role savedRole = roleRepository.save(role);

        UserRole userRole = new UserRole();
        userRole.setVersion(2);
        userRole.setUser(savedUser);
        userRole.setUnit(savedUnit);
        userRole.setRole(savedRole);
        userRole.setValidFrom(ZonedDateTime.now());
        userRole.setValidTo(ZonedDateTime.now().plusDays(1));

        userRoleRepository.save(userRole);

        UserRole userRole2 = new UserRole();
        userRole2.setVersion(2);
        userRole2.setUser(savedUser);
        userRole2.setUnit(savedUnit2);
        userRole2.setRole(savedRole);
        userRole2.setValidFrom(ZonedDateTime.now());
        userRole2.setValidTo(ZonedDateTime.now().plusDays(1));

        boolean isOverlappingRoles = userRoleService.hasOverlappingRole(userRole2);

        assertFalse(isOverlappingRoles);
    }

    @Test
    public void whenFindUserRolesByTimestamp_thenGetCorrectUserRoles() {
        User user = new User();
        user.setVersion(2);
        user.setName("Researcher Researchersen");

        User savedUser = userRepository.save(user);

        Unit unit = new Unit();
        unit.setName("Researcher");
        unit.setVersion(2);

        Unit savedUnit = unitRepository.save(unit);

        Unit unit2 = new Unit();
        unit.setName("Lege");
        unit.setVersion(2);

        Unit savedUnit2 = unitRepository.save(unit2);

        Role role = new Role();
        role.setName("Researcher");
        role.setVersion(2);

        Role savedRole = roleRepository.save(role);

        ZonedDateTime validFrom = ZonedDateTime.now();
        ZonedDateTime validTo = ZonedDateTime.now().plusDays(1);

        ZonedDateTime validFrom2 = ZonedDateTime.now().plusDays(2);
        ZonedDateTime validTo2 = ZonedDateTime.now().plusDays(3);

        UserRole userRole = new UserRole();
        userRole.setVersion(2);
        userRole.setUser(savedUser);
        userRole.setUnit(savedUnit);
        userRole.setRole(savedRole);
        userRole.setValidFrom(validFrom);
        userRole.setValidTo(validTo);

        userRoleRepository.save(userRole);

        UserRole userRole2 = new UserRole();
        userRole2.setVersion(2);
        userRole2.setUser(savedUser);
        userRole2.setUnit(savedUnit2);
        userRole2.setRole(savedRole);
        userRole2.setValidFrom(validFrom2);
        userRole2.setValidTo(validTo2);

        userRoleRepository.save(userRole2);

        List<UserRole> userRoles = userRoleRepository.findUserRolesByTimestamp(validFrom);

        assertEquals(1, userRoles.size());
    }
}
