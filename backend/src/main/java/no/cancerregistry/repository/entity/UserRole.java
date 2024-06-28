package no.cancerregistry.repository.entity;


import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "user_roles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer version;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    private ZonedDateTime validFrom;

    private ZonedDateTime validTo;

    public Long getId() {
        return id;
    }
    public User getUser() {return user;}

    public Unit getUnit() {
        return unit;
    }

    public Role getRole() {
        return role;
    }

    public Integer getVersion() { return version; }

    public ZonedDateTime getValidFrom() { return validFrom; }

    public ZonedDateTime getValidTo() { return validTo; }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUnit(Unit unit) { this.unit = unit; }

    public void setRole(Role role) { this.role = role; }

    public void setValidFrom(ZonedDateTime validFrom) { this.validFrom = validFrom; }

    public void setValidTo(ZonedDateTime validTo) { this.validTo = validTo; }
}