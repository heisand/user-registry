package no.cancerregistry.unittest;

import no.cancerregistry.model.UserDTO;
import no.cancerregistry.model.UserResponse;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class CancerRegistryServiceTest {

    @Mock
    private static UserRepository userRepositoryMock;
    private static CancerRegistryService cancerRegistryService;
    @Mock
    private static CancerRegistryService cancerRegistryService2;

    @BeforeAll
    static void setup() {

        cancerRegistryService = new CancerRegistryService(userRepositoryMock);
        cancerRegistryService2 = new CancerRegistryService(userRepositoryMock);
    }

    @Test
    void canCreateUser() {
        UserDTO userDTO = new UserDTO(null, null, "Test");
        User mockUser = new User();
        mockUser.setName("Alice");
        UserDTO user = cancerRegistryService.createUser(userDTO);

        assertNotNull(user);
    }

}
