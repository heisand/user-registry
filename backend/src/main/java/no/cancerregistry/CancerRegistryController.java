package no.cancerregistry;

import no.cancerregistry.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class CancerRegistryController {

	private final CancerRegistryService cancerRegistryService;

	public CancerRegistryController(CancerRegistryService cancerRegistryService) {
		this.cancerRegistryService = cancerRegistryService;
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
		UserDTO user = cancerRegistryService.getUser(id);

		return ResponseEntity.status(HttpStatus.OK).body(user);
	}


	@PostMapping("")
	public ResponseEntity<Long> createUser(@RequestBody UserDTO user) {

		UserDTO savedUser = cancerRegistryService.createUser(user);

		Long savedId = savedUser.getId().orElse(null);
		if (savedId == null) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO user) {
		cancerRegistryService.updateUser(id, user);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
		cancerRegistryService.deleteUser(id);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
}
