package no.cancerregistry.service;

import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongIdException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.*;
import no.cancerregistry.repository.RoleRepository;
import no.cancerregistry.repository.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleDTO> getRoles(Optional<String> name) {
        List<Role> roles;

        if (name.isPresent()) {
            roles = roleRepository.findRolesByName(name.orElseThrow());
        } else {
            roles = (List<Role>) roleRepository.findAll();
        }

        return roles.stream().map(
                role -> new RoleDTO(
                        Optional.ofNullable(role.getId()),
                        Optional.ofNullable(role.getVersion()),
                        role.getName())
        ).collect(Collectors.toList());
    }


    public RoleDTO getRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "Role with id " + id + " does not exist."));

        return new RoleDTO(
                Optional.ofNullable(role.getId()),
                Optional.ofNullable(role.getVersion()),
                role.getName());
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        role.setVersion(1);

        Role savedRole = roleRepository.save(role);

        return new RoleDTO(
                Optional.ofNullable(savedRole.getId()),
                Optional.ofNullable(savedRole.getVersion()),
                savedRole.getName());
    }

    public void updateRole(Long id, RoleDTO roleDTO) {
        Long unwrappedId = roleDTO.getId().orElse(id);
        Integer unwrappedVersion = roleDTO.getVersion().orElse(null);

        if (unwrappedVersion == null) {
            throw new WrongVersionException("Version is missing");
        }

        if (!Objects.equals(unwrappedId, id)) {
            throw new WrongIdException("The specified id does mot match the requested body");
        }

        Role existingRole = roleRepository.findById(unwrappedId)
                .orElseThrow(() -> new UserNotFoundException(
                        "Role with id " + unwrappedId + " does not exist."));

        if (!Objects.equals(existingRole.getVersion(), unwrappedVersion)) {
            throw new WrongVersionException(
                    "There is a version mismatch between the existing role" +
                            unwrappedId + "and the requested one." +
                            "Expected: " + existingRole.getVersion() +
                            "Found: " + roleDTO.getVersion());
        }

        existingRole.setName(roleDTO.getName());
        existingRole.setVersion(unwrappedVersion + 1);

        roleRepository.save(existingRole);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}
