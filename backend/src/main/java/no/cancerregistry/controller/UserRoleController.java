package no.cancerregistry.controller;

import no.cancerregistry.model.UserRoleDTO;
import no.cancerregistry.model.UserWithRolesDTO;
import no.cancerregistry.repository.entity.UserRole;
import no.cancerregistry.service.UserRoleService;
import no.cancerregistry.model.UserDTO;
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
    public ResponseEntity<List<UserDTO>> getUsers() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long id) {
        return null;
    }


    @PostMapping
    public ResponseEntity<UserRole> createUserRole(
            @RequestParam Long userId,
            @RequestParam Long unitId,
            @RequestParam Long roleId) {
        // TODO:
        // 1. Request body
        // 2. Validation of request
        userRoleService.createUserRole(userId, unitId, roleId);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserRoleDTO> updateUser(
            @RequestParam Long userId,
            @RequestParam Long unitId,
            @RequestParam Long roleId) {
        // TODO:
        // 1. Request body
        // 2. Validation of request
        userRoleService.updateUserRole(userId, unitId, roleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable("id") Long id) {
        return null;
    }

    @GetMapping("/units/{unitId}/users-with-roles")
    public ResponseEntity<List<UserWithRolesDTO>> getUsersWithRolesByUnitId(@PathVariable Long unitId) {
        List<UserWithRolesDTO> usersWithRoles = userRoleService.getUsersWithRolesByUnitId(unitId);

        return ResponseEntity.ok(usersWithRoles);
    }
}
