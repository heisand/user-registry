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

    public UserRoleDTO(
            Optional<Long> id,
            Optional<Integer> version,
            Long userId,
            Long unitId,
            Long roleId) {
        this.id = id;
        this.version = version;
        this.userId = userId;
        this.unitId = unitId;
        this.roleId = roleId;
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
