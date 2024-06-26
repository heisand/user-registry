package no.cancerregistry.model;

import java.util.UUID;

public class UserResponse extends UserRequest {
    private Long id;

    public UserResponse(UserRequest request) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
