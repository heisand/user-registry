package no.cancerregistry.controller;

import jakarta.validation.Valid;
import no.cancerregistry.service.UserService;
import no.cancerregistry.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(
			@RequestParam Optional<String> name,
			@RequestParam Optional<Long> unitId,
			@RequestParam Optional<Long> roleId
	) {
		List<UserDTO> users = userService.getUsers(name, unitId, roleId);

		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
		UserDTO user = userService.getUser(id);

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}


	@PostMapping
	public ResponseEntity<Long> createUser(@Valid @RequestBody UserDTO user) {
		UserDTO savedUser = userService.createUser(user);

		Long savedId = savedUser.getId().orElse(null);
		if (savedId == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDTO user) {
		userService.updateUser(id, user);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
}
