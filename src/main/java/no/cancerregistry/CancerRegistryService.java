package no.cancerregistry;

import no.cancerregistry.model.UserDTO;
import no.cancerregistry.model.UserRequest;
import no.cancerregistry.model.UserResponse;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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


        User savedUser = userRepository.save(user);

        return new UserDTO(savedUser.getId(), savedUser.getVersion(), savedUser.getName());
    }
}
