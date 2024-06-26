package no.cancerregistry;

import no.cancerregistry.model.UserDTO;
import no.cancerregistry.model.UserRequest;
import no.cancerregistry.model.UserResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CancerRegistryController {

	private final CancerRegistryService cancerRegistryService;

	public CancerRegistryController(CancerRegistryService cancerRegistryService) {
		this.cancerRegistryService = cancerRegistryService;
	}
	@PostMapping("/users")
	public UserResponse createUser(@RequestBody UserDTO user) {
		return cancerRegistryService.createUser(user);
	}
}
