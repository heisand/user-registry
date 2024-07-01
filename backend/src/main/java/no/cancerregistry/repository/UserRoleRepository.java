package no.cancerregistry.repository;

import no.cancerregistry.repository.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    @Query("SELECT ur FROM UserRole ur " +
            "WHERE ur.user.id = :userId " +
            "AND ur.unit.id = :unitId " +
            "AND (:timestamp BETWEEN ur.validFrom AND COALESCE(ur.validTo, :timestamp))")
    List<UserRole> findValidUserRoles(Long userId, Long unitId, ZonedDateTime timestamp);

    @Query("SELECT ur FROM UserRole ur WHERE ur.user.id = :userId")
    List<UserRole> findUserRolesByUserId(Long userId);

    @Query("SELECT ur FROM UserRole ur WHERE ur.unit.id = :unitId")
    List<UserRole> findUserRolesByUnitId(Long unitId);

    @Query("SELECT ur FROM UserRole ur WHERE ur.role.id = :roleId")
    List<UserRole> findUserRolesByRoleId(Long roleId);

    @Query("SELECT ur FROM UserRole ur WHERE ur.version = :version")
    List<UserRole> findUserRolesByVersion(Integer version);

    @Query("SELECT ur FROM UserRole ur WHERE :timestamp BETWEEN ur.validFrom AND COALESCE(ur.validTo, :timestamp)")
    List<UserRole> findUserRolesByTimestamp(ZonedDateTime timestamp);

    @Query("SELECT CASE WHEN EXISTS (" +
            "SELECT 1 FROM UserRole ur " +
            "WHERE ur.user.id = :userId " +
            "AND ur.unit.id = :unitId " +
            "AND ur.role.id = :roleId " +
            "AND (:validFrom BETWEEN ur.validFrom AND COALESCE(ur.validTo, :validFrom) " +
            "OR :validTo BETWEEN ur.validFrom AND COALESCE(ur.validTo, :validTo))" +
            ") THEN true ELSE false END")
    boolean hasOverlappingUserRole(
            Long userId,
            Long unitId,
            Long roleId,
            ZonedDateTime validFrom,
            ZonedDateTime validTo);
}
