package no.cancerregistry.repository;

import no.cancerregistry.repository.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findUsersByName(String name);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId")
    List<User> findUsersByUnitId(Long unitId);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.role.id = :roleId")
    List<User> findUsersByRoleId(Long roleId);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId AND ur.role.id = :roleId")
    List<User> findUsersByUnitIdAndRoleId(Long unitId, Long roleId);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId AND u.name LIKE %:name%")
    List<User> findUsersByUnitIdAndName(Long unitId, String name);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId AND u.name LIKE %:name%")
    List<User> findUsersByRoleIdAndName(Long unitId, String name);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId AND ur.role.id = :roleId AND u.name LIKE %:name%")
    List<User> findUsersBydUnitIdRoleIdAndName(Long unitId, Long roleId, String name);
}

