package no.cancerregistry.model;


public class RoleResponse extends RoleRequest {
    private Long id;

    public RoleResponse(RoleRequest request) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
