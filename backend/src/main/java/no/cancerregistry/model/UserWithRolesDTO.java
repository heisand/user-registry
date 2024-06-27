package no.cancerregistry.model;

import java.util.List;

public class UserWithRolesDTO {
    private Long userId;
    private String name;
    private List<UserRoleDTO> roles;

    public UserWithRolesDTO(Long userId, String name, List<UserRoleDTO> roles) {
        this.userId = userId;
        this.name = name;
        this.roles = roles;
    }
}