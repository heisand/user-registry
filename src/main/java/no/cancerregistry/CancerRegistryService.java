package no.cancerregistry;

import no.cancerregistry.model.UserDTO;
import no.cancerregistry.model.UserRequest;
import no.cancerregistry.model.UserResponse;
import no.cancerregistry.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class CancerRegistryService {

    private UserRepository userRepository;

    public UserResponse createUser(UserDTO user) {
        // TODO: Create user entity
        //userRepository.save(user);

        return null;
    }
}
