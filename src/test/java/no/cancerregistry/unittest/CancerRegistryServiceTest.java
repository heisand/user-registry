package no.cancerregistry.unittest;

import no.cancerregistry.CancerRegistryService;
import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.UserDTO;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CancerRegistryServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private CancerRegistryService cancerRegistryService;

	@Test
	public void testCreateUser_200OK() {
		UserDTO user = new UserDTO(1L, 2, "John Doe");

		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("John Doe");

		when(userRepository.save(userMock)).thenReturn(userMock);

		UserDTO savedUser = cancerRegistryService.createUser(user);

		assertNotNull(savedUser);
	}

	@Test
	public void testUpdateUser_WrongVersion() {
		UserDTO user = new UserDTO(1L, 2, "John Doe");

		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("John Doe");

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(userMock));

		WrongVersionException exception = assertThrows(WrongVersionException.class, () -> {
			cancerRegistryService.updateUser(1L, user);
		});

		//assertEquals("", exception.getMessage());
	}

	@Test
	public void testUpdateUser_userNotFound() {
		UserDTO user = new UserDTO(1L, 1, "John Doe");

		when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			cancerRegistryService.updateUser(1L, user);
		});

		//assertEquals("", exception.getMessage());
	}
}