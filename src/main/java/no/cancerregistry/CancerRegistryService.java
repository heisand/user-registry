package no.cancerregistry;

import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongIdException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.*;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.entity.User;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CancerRegistryService {

    private final UserRepository userRepository;

    public CancerRegistryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setVersion(1);

        User savedUser = userRepository.save(user);

        return new UserDTO(savedUser.getId(), savedUser.getVersion(), savedUser.getName());
    }

    public void updateUser(Long id, UserDTO userDTO) {
        if (!Objects.equals(userDTO.getId(), id)) {
            throw new WrongIdException("");
        }

        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User with id " + userDTO.getId() + " does not exist."));

        if (existingUser.getVersion() != userDTO.getVersion()) {
            throw new WrongVersionException(
                    "There is a version mismatch between the existing user" +
                            userDTO.getId() + "and the requested one." +
                            "Expected: " + existingUser.getVersion() + "" +
                            "Found: " + userDTO.getId());
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setVersion(userDTO.getVersion() + 1);

        userRepository.save(user);
    }
}
