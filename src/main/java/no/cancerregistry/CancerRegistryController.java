package no.cancerregistry;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CancerRegistryController {

	@GetMapping("/register")
	public void register() {
	}
}
