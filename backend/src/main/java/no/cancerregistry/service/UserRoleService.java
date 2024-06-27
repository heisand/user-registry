package no.cancerregistry.service;

import no.cancerregistry.repository.RoleRepository;
import no.cancerregistry.repository.UnitRepository;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.UserRoleRepository;
import no.cancerregistry.repository.entity.Role;
import no.cancerregistry.repository.entity.Unit;
import no.cancerregistry.repository.entity.User;
import no.cancerregistry.repository.entity.UserRole;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(
            UserRoleRepository userRoleRepository,
            UserRepository userRepository,
            UnitRepository unitRepository,
            RoleRepository roleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.unitRepository = unitRepository;
        this.roleRepository = roleRepository;
    }

    public UserRole createUserRole(Long userId, Long unitId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));
        Unit unit = unitRepository.findById(unitId).orElseThrow(() ->
                new RuntimeException("Unit not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() ->
                new RuntimeException("Role not found"));

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setUnit(unit);
        userRole.setRole(role);

        return userRoleRepository.save(userRole);
    }

    public UserRole updateUserRole(Long userId, Long unitId, Long roleId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new RuntimeException("User not found"));
        Unit unit = unitRepository.findById(unitId).orElseThrow(() ->
                new RuntimeException("Unit not found"));
        Role role = roleRepository.findById(roleId).orElseThrow(() ->
                new RuntimeException("Role not found"));

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setUnit(unit);
        userRole.setRole(role);

        return userRoleRepository.save(userRole);
    }
}