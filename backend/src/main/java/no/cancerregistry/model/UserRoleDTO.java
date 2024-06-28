package no.cancerregistry.model;

import jakarta.validation.constraints.NotNull;
import no.cancerregistry.validator.ValidDateRange;

import java.time.ZonedDateTime;
import java.util.Optional;

@ValidDateRange
public class UserRoleDTO {

    private Optional<Long> id;
    private Optional<Integer> version;
    @NotNull
    private Long userId;
    @NotNull
    private Long unitId;
    @NotNull
    private Long roleId;
    private Optional<ZonedDateTime> validFrom;
    private Optional<ZonedDateTime> validTo;

    public UserRoleDTO(
            Optional<Long> id,
            Optional<Integer> version,
            Long userId,
            Long unitId,
            Long roleId,
            ZonedDateTime validFrom,
            ZonedDateTime validTo) {
        this.id = id;
        this.version = version;
        this.userId = userId;
        this.unitId = unitId;
        this.roleId = roleId;
        this.validFrom = Optional.ofNullable(validFrom);
        this.validTo = Optional.ofNullable(validTo);
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
    public ZonedDateTime getValidFrom() { return validFrom.orElse(ZonedDateTime.now()); }
    public ZonedDateTime getValidTo() { return validTo.orElse(null); }
    public void setId() {this.id = id;}
    public void setVersion() {this.version = version;}
}
