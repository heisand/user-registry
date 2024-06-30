package no.cancerregistry.unittest;

import no.cancerregistry.repository.UserRoleRepository;
import no.cancerregistry.repository.entity.UserRole;
import no.cancerregistry.service.UserService;
import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.UserDTO;
import no.cancerregistry.repository.UserRepository;
import no.cancerregistry.repository.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	static private UserService userService;
	static private UserRepository userRepository;
	static private UserRoleRepository userRoleRepository;

	@BeforeAll
	static void setup() {
		userRepository = mock(UserRepository.class);
		userRoleRepository = mock(UserRoleRepository.class);
		userService = new UserService(userRepository, userRoleRepository);
	}

	@Test
	public void whenCreateUser_thenReturnSavedUser() {
		UserDTO user = new UserDTO(
				Optional.of(1L),
				Optional.of(2),
				"Researcher Researcher");

		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("Researcher Researcher");

		when(userRepository.save(any(User.class))).thenReturn(userMock);

		UserDTO savedUser = userService.createUser(user);

		assertNotNull(savedUser);
		assertEquals(savedUser.getName(), "Researcher Researcher");
	}

	@Test
	public void givenAbundantUser_whenGetUser_thenThrowUserNotFoundException() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.getUser(1L));
	}

	/*@Test
	public void givenNullId_whenGetUsers_thenThrowUNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () -> userService.getUsers(null, null, null));
	}*/

	@Test
	public void givenNoParameters_whenGetUsers_thenReturnsSuccessfully() {
		when(userRepository.findAll()).thenReturn(List.of(new User()));

		List<UserDTO> users =
				userService.getUsers(Optional.empty(), Optional.empty(), Optional.empty());

		assertFalse(users.isEmpty());
	}

	@Test
	public void givenWrongVersion_whenUpdateUser_thenThrowWrongVersionException() {
		UserDTO user = new UserDTO(
				Optional.of(1L),
				Optional.of(2),
				"Researcher Researcher");

		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("Researcher Researcher");

		when(userRepository.findById(1L)).thenReturn(Optional.of(userMock));

		assertThrows(WrongVersionException.class, () -> userService.updateUser(1L, user));
	}

	@Test
	public void givenNullVersion_whenUpdateUser_thenThrowWrongVersionException() {
		UserDTO user = new UserDTO(
				Optional.of(1L),
				Optional.ofNullable(null),
				"Researcher Researcher");

		assertThrows(WrongVersionException.class, () -> userService.updateUser(1L, user));
	}

	@Test
	public void givenAbundantUser_whenUpdateUser_thenThrowUserNotFoundException() {
		UserDTO user = new UserDTO(
				Optional.of(1L),
				Optional.of(1),
				"Researcher Researcher");

		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("Researcher Researcher");

		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.updateUser(1L, user));
	}

	@Test
	public void givenExistingUser_whenUpdateUser_thenUpdatesSuccessfully() {
		UserDTO user = new UserDTO(
				Optional.of(1L),
				Optional.of(1),
				"Researcher Researcher");

		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("Researcher Researcher");

		when(userRepository.findById(1L)).thenReturn(Optional.of(userMock));

		assertDoesNotThrow(() -> userService.updateUser(1L, user));
	}

	@Test
	public void givenUserWithRoles_whenDeleteUser_thenDoesNotDelete() {
		User userMock = new User();
		userMock.setVersion(1);
		userMock.setName("Researcher Researcher");

		when(userRoleRepository.findUserRolesByUserId(1L)).thenReturn((List.of(new UserRole())));

		assertThrows(IllegalStateException.class, () -> userService.deleteUser(1L));
	}
}