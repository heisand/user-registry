package no.cancerregistry.model;

import java.time.ZonedDateTime;

public class RoleRequest {
    private String version;
    private String userId;
    private String unitId;
    private String roleId;
    private ZonedDateTime validFrom;
    private ZonedDateTime validTo;
}

