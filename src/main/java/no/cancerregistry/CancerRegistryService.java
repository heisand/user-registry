package no.cancerregistry;

import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongIdException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.*;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.entity.User;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CancerRegistryService {

    private final UserRepository userRepository;

    public CancerRegistryService(UserRepository userRepository) {

        this.userRepository = userRepository;
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
        Long unwrappedId = userDTO.getId().orElse(null);
        Integer unwrappedVersion = userDTO.getVersion().orElse(null);

        if (unwrappedId == null || unwrappedVersion == null) {
            throw new WrongVersionException("");
        }

        if (!Objects.equals(unwrappedId, id)) {
            throw new WrongIdException("");
        }

        User existingUser = userRepository.findById(unwrappedId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id " + userDTO.getId() + " does not exist."));

        if (!Objects.equals(existingUser.getVersion(), unwrappedVersion)) {
            throw new WrongVersionException(
                    "There is a version mismatch between the existing user" +
                            userDTO.getId() + "and the requested one." +
                            "Expected: " + existingUser.getVersion() + "" +
                            "Found: " + userDTO.getId());
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
