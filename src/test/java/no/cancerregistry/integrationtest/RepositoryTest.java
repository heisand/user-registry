package no.cancerregistry.integrationtest;

import no.cancerregistry.repository.entity.User;
import org.junit.jupiter.api.Test;
import no.cancerregistry.repository.UserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class RepositoryTest {

    private final UserRepository userRepository;

    @Autowired
    public RepositoryTest(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Test
    public void createUser() {
        User newUser = new User();
        newUser.setVersion(1);
        newUser.setName("John Doe");

        User savedUser = userRepository.save(newUser);
        User foundUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals("John Doe", foundUser.getName());
        assertEquals(1, foundUser.getId());
        assertEquals(1, foundUser.getVersion());

        userRepository.deleteById(foundUser.getId());
        User deletedUser = userRepository.findById(savedUser.getId()).orElse(null);

        assertNull(deletedUser);
    }
}
