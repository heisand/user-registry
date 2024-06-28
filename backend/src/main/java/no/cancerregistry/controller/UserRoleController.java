package no.cancerregistry.controller;

import jakarta.validation.Valid;
import no.cancerregistry.model.UserRoleDTO;
import no.cancerregistry.model.UserWithRolesDTO;
import no.cancerregistry.service.UserRoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
@CrossOrigin(origins = "http://localhost:5173")
public class UserRoleController {

    private final UserRoleService userRoleService;

    public UserRoleController(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserRoleDTO>> getUsers() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRoleDTO> getUser(@PathVariable("id") Long id) {
        return null;
    }


    @PostMapping
    public ResponseEntity<UserRoleDTO> createUserRole(@Valid @RequestBody UserRoleDTO userRole) {
        userRoleService.createUserRole(userRole);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDTO> updateUser(@Valid @RequestBody UserRoleDTO userRole) {
        userRoleService.updateUserRole(userRole);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserRoleDTO> deleteUser(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/units/{unitId}/users-with-roles")
    public ResponseEntity<List<UserWithRolesDTO>> getUsersWithRolesByUnitId(@PathVariable Long unitId) {
        List<UserWithRolesDTO> usersWithRoles = userRoleService.getUsersWithRolesByUnitId(unitId);

        return ResponseEntity.ok(usersWithRoles);
    }
}
