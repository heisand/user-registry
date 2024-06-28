package no.cancerregistry.service;

import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongIdException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.*;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.entity.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getUsers() {
        List<User> users = (ArrayList<User>) userRepository.findAll();

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
            throw new WrongVersionException("");
        }

        if (!Objects.equals(unwrappedId, id)) {
            throw new WrongIdException("");
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
        // TODO: Only delete when there are no user roles for that user
        userRepository.deleteById(id);
    }
}
