package no.cancerregistry.model;

import jakarta.validation.constraints.NotNull;

import java.util.Optional;

public class UnitDTO {

    private Optional<Long> id;
    private Optional<Integer> version;
    @NotNull
    private String name;

    public UnitDTO(Optional<Long> id, Optional<Integer> version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public Optional<Long> getId() {
        return id;
    }
    public Optional<Integer> getVersion() {
        return version;
    }
    public String getName() {
        return name;
    }
    public void setId() {
        this.id = id;
    }
    public void setVersion() {
        this.version = version;
    }
    public void setName() {
        this.name = name;
    }

}
