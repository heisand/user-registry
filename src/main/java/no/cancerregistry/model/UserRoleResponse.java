package no.cancerregistry.model;


public class UserRoleResponse extends UserRequest {
    private Long id;

    public UserRoleResponse(UserRequest request) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
