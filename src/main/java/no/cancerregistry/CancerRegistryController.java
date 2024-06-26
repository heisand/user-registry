package no.cancerregistry;

import no.cancerregistry.model.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class CancerRegistryController {

	private final CancerRegistryService cancerRegistryService;

	public CancerRegistryController(CancerRegistryService cancerRegistryService) {
		this.cancerRegistryService = cancerRegistryService;
	}
	@PostMapping("")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO user) {

		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO user) {
		cancerRegistryService.updateUser(id, user);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

	}
}
