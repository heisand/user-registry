package no.cancerregistry.repository;

import no.cancerregistry.repository.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.name LIKE %:name%")
    List<User> findUserByName(String name);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId")
    List<User> findUserByUnitId(Long unitId);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.role.id = :roleId")
    List<User> findUserByRoleId(Long roleId);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId AND ur.role.id = :roleId")
    List<User> findUserByUnitIdAndRoleId(Long unitId, Long roleId);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId AND u.name LIKE %:name%")
    List<User> findUserByUnitIdAndName(Long unitId, String name);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId AND u.name LIKE %:name%")
    List<User> findUserByRoleIdAndName(Long unitId, String name);

    @Query("SELECT u FROM UserRole ur JOIN ur.user u WHERE ur.unit.id = :unitId AND ur.role.id = :roleId AND u.name LIKE %:name%")
    List<User> findUserBydUnitIdRoleIdAndName(Long unitId, Long roleId, String name);
}

