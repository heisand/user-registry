package no.cancerregistry.service;

import no.cancerregistry.exception.OverlappingRoleException;
import no.cancerregistry.model.UserRoleDTO;
import no.cancerregistry.model.UserWithRolesDTO;
import no.cancerregistry.repository.RoleRepository;
import no.cancerregistry.repository.UnitRepository;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.UserRoleRepository;
import no.cancerregistry.repository.entity.Role;
import no.cancerregistry.repository.entity.Unit;
import no.cancerregistry.repository.entity.User;
import no.cancerregistry.repository.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public UserRoleDTO createUserRole(UserRoleDTO userRoleDTO) {
        User user = userRepository.findById(userRoleDTO.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found"));
        Unit unit = unitRepository.findById(userRoleDTO.getUnitId()).orElseThrow(() ->
                new RuntimeException("Unit not found"));
        Role role = roleRepository.findById(userRoleDTO.getRoleId()).orElseThrow(() ->
                new RuntimeException("Role not found"));

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setUnit(unit);
        userRole.setRole(role);
        userRole.setValidFrom(userRoleDTO.getValidFrom());
        userRole.setValidTo(userRoleDTO.getValidTo());

        if (hasOverlappingRole(userRole)) {
            throw new OverlappingRoleException("A valid user role already exists");
        }

        UserRole savedUserRole = userRoleRepository.save(userRole);

        return new UserRoleDTO(
                Optional.ofNullable(savedUserRole.getId()),
                Optional.ofNullable(savedUserRole.getVersion()),
                savedUserRole.getUser().getId(),
                savedUserRole.getUnit().getId(),
                savedUserRole.getRole().getId(),
                savedUserRole.getValidFrom(),
                savedUserRole.getValidTo()
        );
    }

    public UserRole updateUserRole(UserRoleDTO userRoleDTO) {
        User user = userRepository.findById(userRoleDTO.getUserId()).orElseThrow(() ->
                new RuntimeException("User not found"));
        Unit unit = unitRepository.findById(userRoleDTO.getUnitId()).orElseThrow(() ->
                new RuntimeException("Unit not found"));
        Role role = roleRepository.findById(userRoleDTO.getRoleId()).orElseThrow(() ->
                new RuntimeException("Role not found"));

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setUnit(unit);
        userRole.setRole(role);

        return userRoleRepository.save(userRole);
    }


    public List<UserWithRolesDTO> getUsersWithRolesByUnitId(Long unitId) {
        List<UserRole> userRoles = userRoleRepository.findUserRolesByUnitId(unitId);

        Map<User, List<UserRole>> userRolesMap = userRoles.stream()
                .collect(Collectors.groupingBy(UserRole::getUser));

        return userRolesMap.entrySet().stream()
                .map(entry -> {
                    User user = entry.getKey();
                    List<UserRoleDTO> roles = entry.getValue().stream()
                            .map(userRole -> new UserRoleDTO(
                                    Optional.ofNullable(userRole.getId()),
                                    Optional.ofNullable(userRole.getVersion()),
                                    userRole.getUser().getId(),
                                    userRole.getUnit().getId(),
                                    userRole.getRole().getId(),
                                    userRole.getValidFrom(),
                                    userRole.getValidTo()))
                            .collect(Collectors.toList());
                    return new UserWithRolesDTO(user.getId(), user.getName(), roles);
                })
                .collect(Collectors.toList());
    }

    public boolean hasOverlappingRole(UserRole userRole) {
        return userRoleRepository.hasOverlappingUserRole(
                userRole.getUser().getId(),
                userRole.getUnit().getId(),
                userRole.getRole().getId(),
                userRole.getValidFrom(),
                userRole.getValidTo()
        );
    }
}