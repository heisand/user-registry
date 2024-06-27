package no.cancerregistry.unittest;

import no.cancerregistry.service.UserRoleService;
import no.cancerregistry.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRoleServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserRoleService userRoleService;
}