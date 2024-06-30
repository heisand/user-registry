package no.cancerregistry.repository;

import no.cancerregistry.repository.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RoleRepository extends CrudRepository<Role, Long> {

    @Query("SELECT r FROM Role r WHERE r.name LIKE %:name%")
    List<Role> findRolesByName(String name);
}
