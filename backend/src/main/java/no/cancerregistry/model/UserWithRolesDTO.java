package no.cancerregistry.model;

import java.util.List;

public class UserWithRolesDTO {
    private Long userId;
    private String name;
    private List<RoleDTO> roles;

    public UserWithRolesDTO(Long userId, String name, List<RoleDTO> roles) {
        this.userId = userId;
        this.name = name;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.userId = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }
    public Long getId() {
        return userId;
    }
    public String getName() {
        return name;
    }
    public List<RoleDTO> getRoles() {
        return roles;
    }
}