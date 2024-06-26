package no.cancerregistry;

import no.cancerregistry.model.UserDTO;
import no.cancerregistry.model.UserResponse;
import no.cancerregistry.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class CancerRegistryServiceTest {

    @Mock
    private static UserRepository userRepositoryMock;
    private static CancerRegistryService cancerRegistryService;


    @BeforeAll
    static void setup() {
        cancerRegistryService = new CancerRegistryService(userRepositoryMock);
    }

    @Test
    void canCreateUser() {
        UserDTO userDTO = new UserDTO(null, null, "Test");
        UserResponse user = cancerRegistryService.createUser(userDTO);

        assertNotNull(user);
    }

}
