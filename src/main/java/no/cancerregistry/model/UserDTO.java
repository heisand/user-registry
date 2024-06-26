package no.cancerregistry.model;

public class UserDTO {

    private Long id;
    private String version;
    private String name;

    public UserDTO(Long id, String version, String name) {
        this.id = id;
        this.version = version;
        this.name = name;
    }
}
