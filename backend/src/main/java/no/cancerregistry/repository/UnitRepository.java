package no.cancerregistry.repository;

import no.cancerregistry.repository.entity.Unit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UnitRepository extends CrudRepository<Unit, Long> {

    @Query("SELECT u FROM Unit u WHERE u.name LIKE %:name%")
    List<Unit> findUnitsByName(String name);

    @Query("SELECT u FROM UserRole ur JOIN ur.unit u WHERE ur.unit.id = :unitId")
    List<Unit> findUnitsByUnitId(Long unitId);

    @Query("SELECT u FROM UserRole ur JOIN ur.unit u WHERE ur.unit.id = :unitId AND u.name LIKE %:name%")
    List<Unit> findUnitsByUnitIdAndName(Long unitId, String name);
}
