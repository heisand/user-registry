package no.cancerregistry.model;

public class UserDTO {

    private Long id;
    private Integer version;
    private String name;

    public UserDTO(Long id, Integer version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }

    public Long getId() {
        return id;
    }
    public int getVersion() {
        return version;
    }
    public String getName() {
        return name;
    }

}
