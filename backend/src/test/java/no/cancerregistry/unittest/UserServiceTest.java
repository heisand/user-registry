package no.cancerregistry.unittest;

import no.cancerregistry.service.UserService;
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
public class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@Test
	public void testCreateUser_200OK() {
		UserDTO user = new UserDTO(
				Optional.of(1L),
				Optional.of(2),
				"Forsker Forskersen");

		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("Forsker Forskersen");

		when(userRepository.save(userMock)).thenReturn(userMock);

		UserDTO savedUser = userService.createUser(user);

		assertNotNull(savedUser);
	}

	@Test
	public void testUpdateUser_WrongVersion() {
		UserDTO user = new UserDTO(
				Optional.of(1L),
				Optional.of(2),
				"Forsker Forskersen");

		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("Forsker Forskersen");

		when(userRepository.findById(1L)).thenReturn(Optional.of(userMock));

		WrongVersionException exception = assertThrows(WrongVersionException.class, () -> {
			userService.updateUser(1L, user);
		});

		//assertEquals("", exception.getMessage());
	}

	@Test
	public void testUpdateUser_userNotFound() {
		UserDTO user = new UserDTO(
				Optional.of(1L),
				Optional.of(1),
				"Forsker Forskersen");

		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			userService.updateUser(1L, user);
		});

		//assertEquals("", exception.getMessage());
	}
}