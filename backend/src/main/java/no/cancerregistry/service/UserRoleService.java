package no.cancerregistry.service;

import no.cancerregistry.exception.*;
import no.cancerregistry.model.RoleDTO;
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

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        userRole.setVersion(1);
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

    public UserRole updateUserRole(Long id, UserRoleDTO userRoleDTO) {
        Long unwrappedId = userRoleDTO.getId().orElse(id);
        Integer unwrappedVersion = userRoleDTO.getVersion().orElse(null);

        if (unwrappedVersion == null) {
            throw new WrongVersionException("Version is missing");
        }

        if (!Objects.equals(unwrappedId, id)) {
            throw new WrongIdException("The specified id does mot match the requested body");
        }

        UserRole existingRole = userRoleRepository.findById(userRoleDTO.getUserId())
                .orElseThrow(() -> new UserRoleNotFoundException(
                        "User role with id " + userRoleDTO.getUserId() + " does not exist."));

        if (!Objects.equals(existingRole.getVersion(), unwrappedVersion)) {
            throw new WrongVersionException(
                    "There is a version mismatch between the existing user role" +
                            unwrappedId + "and the requested one. " +
                            "Expected: " + existingRole.getVersion() +
                            "Found: " + userRoleDTO.getVersion());
        }



        // Only the valid from and valid to timestamps can be changed
        existingRole.setValidFrom(userRoleDTO.getValidFrom());
        existingRole.setValidTo(userRoleDTO.getValidTo());

        if (hasOverlappingRole(existingRole)) {
            throw new OverlappingRoleException("A valid user role already exists");
        }

        return userRoleRepository.save(existingRole);
    }


    public List<UserWithRolesDTO> getUsersWithRolesByUnitId(Long unitId) {
        List<UserRole> userRoles = userRoleRepository.findUserRolesByUnitId(unitId);

        Map<User, List<UserRole>> userRolesMap = userRoles.stream()
                .collect(Collectors.groupingBy(UserRole::getUser));

        return userRolesMap.entrySet().stream()
                .map(entry -> {
                    User user = entry.getKey();
                    List<RoleDTO> roles = entry.getValue().stream()
                            .map(userRole -> new RoleDTO(
                                    Optional.ofNullable(userRole.getId()),
                                    Optional.ofNullable(userRole.getVersion()),
                                    userRole.getRole().getName()))
                            .collect(Collectors.toList());
                    return new UserWithRolesDTO(user.getId(), user.getName(), roles);
                })
                .collect(Collectors.toList());
    }

    public List<UserRoleDTO> getUserRoles(
            Optional<Long> unitId,
            Optional<Long> userId,
            Optional<Long> roleId,
            Optional<Integer> version,
            Optional<ZonedDateTime> timestamp,
            Optional<Boolean> isValid) {

        List<UserRole> userRoles;

        if (unitId.isEmpty() && userId.isEmpty() && timestamp.isEmpty() && isValid.isEmpty()) {
            userRoles = (List<UserRole>) userRoleRepository.findAll();
        } else if (userId.isPresent() && unitId.isPresent() && timestamp.isPresent() && isValid.isPresent()) {
            userRoles = userRoleRepository.findValidUserRoles(
                    userId.orElseThrow(), unitId.orElseThrow(), timestamp.orElseThrow());
        } else if (userId.isPresent()) {
            userRoles = userRoleRepository.findUserRolesByUserId(userId.orElseThrow());
        } else if (unitId.isPresent()) {
            userRoles = userRoleRepository.findUserRolesByUnitId(unitId.orElseThrow());
        } else if (roleId.isPresent()) {
            userRoles = userRoleRepository.findUserRolesByRoleId(roleId.orElseThrow());
        } else if (version.isPresent()) {
            userRoles = userRoleRepository.findUserRolesByVersion(version.orElseThrow());
        } else if (timestamp.isPresent()) {
            // TODO: Get by timestamp
            userRoles = (List<UserRole>) userRoleRepository.findAll();
        } else {
            throw new FilterNotSupportedException("The provided filter is not supported");
        }

        List<UserRole> userRoles2 = (List<UserRole>) userRoleRepository.findAll();

        return userRoles.stream().map(
                userRole -> new UserRoleDTO(
                        Optional.ofNullable(userRole.getId()),
                        Optional.ofNullable(userRole.getVersion()),
                        userRole.getUser().getId(),
                        userRole.getUnit().getId(),
                        userRole.getRole().getId(),
                        userRole.getValidFrom(),
                        userRole.getValidTo())
        ).collect(Collectors.toList());
    }

    public UserRoleDTO getUserRole(Long id) {
        UserRole userRole = userRoleRepository.findById(id)
                .orElseThrow(() -> new UserRoleNotFoundException(
                        "User role with id " + id + " does not exist."));

        return new UserRoleDTO(
                Optional.ofNullable(userRole.getId()),
                Optional.ofNullable(userRole.getVersion()),
                userRole.getUser().getId(),
                userRole.getUnit().getId(),
                userRole.getRole().getId(),
                userRole.getValidFrom(),
                userRole.getValidTo());
    }

    public void deleteUserRole(Long id) {
        userRoleRepository.deleteById(id);
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