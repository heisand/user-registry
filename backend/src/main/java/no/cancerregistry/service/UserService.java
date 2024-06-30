package no.cancerregistry.service;

import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongIdException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.*;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.UserRoleRepository;
import no.cancerregistry.repository.entity.User;
import no.cancerregistry.repository.entity.UserRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository) {

        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserDTO> getUsers(Optional<String> name, Optional<Long> unitId, Optional<Long> roleId) {
        List<User> users;

            if (name.isPresent() && unitId.isPresent() && roleId.isPresent()) {
                users = userRepository.findUsersBydUnitIdRoleIdAndName(
                        unitId.orElseThrow(), roleId.orElseThrow(), name.orElseThrow());
            } else if (unitId.isPresent() && roleId.isPresent()) {
                users = userRepository.findUsersByUnitIdAndRoleId(
                        unitId.orElseThrow(), roleId.orElseThrow());
            } else if (unitId.isPresent() && name.isPresent()) {
                users = userRepository.findUsersByUnitIdAndName(
                        unitId.orElseThrow(), name.orElseThrow());
            } else if (roleId.isPresent() && name.isPresent()) {
                users = userRepository.findUsersByRoleIdAndName(
                        roleId.orElseThrow(), name.orElseThrow());
            } else if (unitId.isPresent()) {
                users = userRepository.findUsersByUnitId(unitId.orElseThrow());
            } else if (roleId.isPresent()) {
                users = userRepository.findUsersByRoleId(roleId.orElseThrow());
            } else if (name.isPresent()) {
                users = userRepository.findUsersByName(name.orElseThrow());
            } else {
                users = (List<User>) userRepository.findAll();
            }

        return users.stream().map(
                user -> new UserDTO(
                        Optional.ofNullable(user.getId()),
                        Optional.ofNullable(user.getVersion()),
                        user.getName())
        ).collect(Collectors.toList());
    }


    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id " + id + " does not exist."));

        return new UserDTO(
                Optional.ofNullable(user.getId()),
                Optional.ofNullable(user.getVersion()),
                user.getName());
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setVersion(1);

        User savedUser = userRepository.save(user);

        return new UserDTO(
                Optional.ofNullable(savedUser.getId()),
                Optional.ofNullable(savedUser.getVersion()),
                savedUser.getName());
    }

    public void updateUser(Long id, UserDTO userDTO) {
        Long unwrappedId = userDTO.getId().orElse(id);
        Integer unwrappedVersion = userDTO.getVersion().orElse(null);

        if (unwrappedVersion == null) {
            throw new WrongVersionException("Version is missing");
        }

        if (!Objects.equals(unwrappedId, id)) {
            throw new WrongIdException("The specified id does mot match the requested body");
        }

        User existingUser = userRepository.findById(unwrappedId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id " + unwrappedId + " does not exist."));

        if (!Objects.equals(existingUser.getVersion(), unwrappedVersion)) {
            throw new WrongVersionException(
                    "There is a version mismatch between the existing user" +
                            unwrappedId + "and the requested one." +
                            "Expected: " + existingUser.getVersion() +
                            "Found: " + userDTO.getVersion());
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setVersion(unwrappedVersion + 1);

        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        List<UserRole> userRoles = userRoleRepository.findUserRolesByUserId(id);

        if (userRoles.isEmpty()) {
            userRepository.deleteById(id);
        } else {
            throw new IllegalStateException("User " + id + " cannot be deleted because it has a user role.");
        }
    }
}
