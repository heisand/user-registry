package no.cancerregistry.service;

import no.cancerregistry.exception.UserNotFoundException;
import no.cancerregistry.exception.WrongIdException;
import no.cancerregistry.exception.WrongVersionException;
import no.cancerregistry.model.*;
import no.cancerregistry.repository.UnitRepository;
import no.cancerregistry.repository.entity.Unit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitService {

    private final UnitRepository unitRepository;

    public UnitService(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    public List<UnitDTO> getUnits(Optional<String> name, Optional<Long> unitId) {
        List<Unit> units;

        if (unitId.isPresent() && name.isPresent()) {
            units = unitRepository.findUnitsByUnitIdAndName(unitId.orElseThrow(), name.orElseThrow());
        } else if (unitId.isPresent()) {
            units = unitRepository.findUnitsByUnitId(unitId.orElseThrow());
        } else if (name.isPresent()) {
            units = unitRepository.findUnitsByName(name.orElseThrow());
        } else {
            units = (List<Unit>) unitRepository.findAll();
        }

        return units.stream().map(
                unit -> new UnitDTO(
                        Optional.ofNullable(unit.getId()),
                        Optional.ofNullable(unit.getVersion()),
                        unit.getName())
        ).collect(Collectors.toList());
    }


    public UnitDTO getUnit(Long id) {
        Unit unit = unitRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        "Unit with id " + id + " does not exist."));

        return new UnitDTO(
                Optional.ofNullable(unit.getId()),
                Optional.ofNullable(unit.getVersion()),
                unit.getName());
    }

    public UnitDTO createUnit(UnitDTO unitDTO) {
        Unit unit = new Unit();
        unit.setName(unitDTO.getName());
        unit.setVersion(1);

        Unit savedUnit = unitRepository.save(unit);

        return new UnitDTO(
                Optional.ofNullable(savedUnit.getId()),
                Optional.ofNullable(savedUnit.getVersion()),
                savedUnit.getName());
    }

    public void updateUnit(Long id, UnitDTO unitDTO) {
        Long unwrappedId = unitDTO.getId().orElse(id);
        Integer unwrappedVersion = unitDTO.getVersion().orElse(null);

        if (unwrappedVersion == null) {
            throw new WrongVersionException("Version is missing");
        }

        if (!Objects.equals(unwrappedId, id)) {
            throw new WrongIdException("The specified id does mot match the requested body");
        }

        Unit existingUnit = unitRepository.findById(unwrappedId)
                .orElseThrow(() -> new UserNotFoundException(
                        "Unit with id " + unwrappedId + " does not exist."));

        if (!Objects.equals(existingUnit.getVersion(), unwrappedVersion)) {
            throw new WrongVersionException(
                    "There is a version mismatch between the existing unit" +
                            unwrappedId + "and the requested one." +
                            "Expected: " + existingUnit.getVersion() +
                            "Found: " + unitDTO.getVersion());
        }

        Unit unit = new Unit();
        unit.setName(unitDTO.getName());
        unit.setVersion(unwrappedVersion + 1);

        unitRepository.save(unit);
    }

    public void deleteUnit(Long id) {
        unitRepository.deleteById(id);
    }
}
