package no.cancerregistry;

import no.cancerregistry.model.UserDTO;
import no.cancerregistry.model.UserResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CancerRegistryServiceTest {

    private static CancerRegistryService cancerRegistryService;

    @BeforeAll
    static void setup() {
        cancerRegistryService = new CancerRegistryService();
    }

    @Test
    void canCreateUser() {
        UserDTO userDTO = new UserDTO(null, null, "Test");

        UserResponse user = cancerRegistryService.createUser(userDTO);

        assertNotNull(user);
    }

}
