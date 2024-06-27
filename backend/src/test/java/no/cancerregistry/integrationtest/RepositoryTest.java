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
    public void createUser() {
        User newUser = new User();
        newUser.setVersion(1);
        newUser.setName("Forsker Forskersen");

        User savedUser = userRepository.save(newUser);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals("Forsker Forskersen", foundUser.getName());
        assertEquals(1, foundUser.getId());
        assertEquals(1, foundUser.getVersion());

        userRepository.deleteById(foundUser.getId());
        User deletedUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertNull(deletedUser);
    }

    @Test
    public void getUnitUsersWithRoles() {
        User user = new User();
        user.setVersion(2);
        user.setName("Forsker Forskersen");

        User savedUser = userRepository.save(user);

        Unit unit = new Unit();
        unit.setName("Forsker");
        unit.setVersion(2);

        Unit savedUnit = unitRepository.save(unit);

        Unit unit2 = new Unit();
        unit.setName("Lege");
        unit.setVersion(2);

        unitRepository.save(unit2);

        Role role = new Role();
        role.setName("Forsker");
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
}
