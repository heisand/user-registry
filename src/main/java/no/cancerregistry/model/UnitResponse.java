package no.cancerregistry.model;


public class UnitResponse extends UnitRequest {
    private Long id;

    public UnitResponse(UnitRequest request) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
