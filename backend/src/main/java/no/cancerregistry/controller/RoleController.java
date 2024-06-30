package no.cancerregistry.controller;

import no.cancerregistry.model.RoleDTO;
import no.cancerregistry.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/roles")
// TODO: Add and use a global CORS handling
@CrossOrigin(origins = "http://localhost:5173")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getRoles(@RequestParam Optional<String> name) {
        List<RoleDTO> roles = roleService.getRoles(name);

        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleDTO> getRole(@PathVariable("id") Long id) {
        RoleDTO role = roleService.getRole(id);

        return ResponseEntity.status(HttpStatus.OK).body(role);
    }


    @PostMapping
    public ResponseEntity<Long> createRole(@RequestBody RoleDTO role) {
        RoleDTO savedRole = roleService.createRole(role);

        Long savedId = savedRole.getId().orElse(null);
        if (savedId == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable("id") Long id, @RequestBody RoleDTO role) {
        roleService.updateRole(id, role);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RoleDTO> deleteRole(@PathVariable("id") Long id) {
        roleService.deleteRole(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
