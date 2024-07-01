package no.cancerregistry.model;

import jakarta.validation.constraints.NotNull;
import no.cancerregistry.validator.ValidDateRange;

import java.time.ZonedDateTime;
import java.util.Optional;

@ValidDateRange
public class UpdateUserRoleDTO {

    private Optional<Long> id;
    private Optional<Integer> version;
    private Optional<ZonedDateTime> validFrom;
    private Optional<ZonedDateTime> validTo;

    public UpdateUserRoleDTO(
            Optional<Long> id,
            Optional<Integer> version,
            ZonedDateTime validFrom,
            ZonedDateTime validTo) {
        this.id = id;
        this.version = version;
        this.validFrom = Optional.ofNullable(validFrom);
        this.validTo = Optional.ofNullable(validTo);
    }

    public Optional<Long> getId() {return id;}
    public Optional<Integer> getVersion() {return version;}
    public ZonedDateTime getValidFrom() { return validFrom.orElse(ZonedDateTime.now()); }
    public ZonedDateTime getValidTo() { return validTo.orElse(null); }
    public void setId() { this.id = id; }
    public void setVersion() { this.version = version; }
}
