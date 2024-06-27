package no.cancerregistry.controller;

import no.cancerregistry.service.UserService;
import no.cancerregistry.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("")
	public ResponseEntity<List<UserDTO>> getUsers() {
		List<UserDTO> users = userService.getUsers();

		return ResponseEntity.status(HttpStatus.OK).body(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
		UserDTO user = userService.getUser(id);

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}


	@PostMapping("")
	public ResponseEntity<Long> createUser(@RequestBody UserDTO user) {

		UserDTO savedUser = userService.createUser(user);

		Long savedId = savedUser.getId().orElse(null);
		if (savedId == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO user) {
		userService.updateUser(id, user);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
		userService.deleteUser(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
}
