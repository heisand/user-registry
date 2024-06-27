package no.cancerregistry.model;

import java.time.ZonedDateTime;
import java.util.Optional;

public class UserRoleDTO {

    private Optional<Long> id;
    private Optional<Integer> version;
    private Long userId;
    private Long unitId;
    private Long roleId;
    private ZonedDateTime validFrom;
    private ZonedDateTime validTo;

    public UserRoleDTO(Optional<Long> id, Optional<Integer> version) {
        this.id = id;
        this.version = version;
    }

    public Optional<Long> getId() {
        return id;
    }
    public Optional<Integer> getVersion() {
        return version;
    }
    public Long getUserId() {
        return userId;
    }
    public Long getUnitId() {return unitId;}
    public Long getRoleId() {return roleId;}
    public void setId() {this.id = id;}
    public void setVersion() {this.version = version;}
}
