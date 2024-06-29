package no.cancerregistry.controller;

import no.cancerregistry.service.UnitService;
import no.cancerregistry.model.UnitDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
@CrossOrigin(origins = "http://localhost:5173")
public class UnitController {

    private final UnitService unitService;

    public UnitController(UnitService unitService) {
        this.unitService = unitService;
    }

    @GetMapping
    public ResponseEntity<List<UnitDTO>> getUnits() {
        List<UnitDTO> units = unitService.getUnits();

        return ResponseEntity.status(HttpStatus.OK).body(units);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnitDTO> getUnit(@PathVariable("id") Long id) {
        UnitDTO unit = unitService.getUnit(id);

        return ResponseEntity.status(HttpStatus.OK).body(unit);
    }


    @PostMapping
    public ResponseEntity<Long> createUnit(@RequestBody UnitDTO unit) {

        UnitDTO savedUnit = unitService.createUnit(unit);

        Long savedId = savedUnit.getId().orElse(null);
        if (savedId == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(savedId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UnitDTO> updateUnit(@PathVariable("id") Long id, @RequestBody UnitDTO unit) {
        unitService.updateUnit(id, unit);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UnitDTO> deleteUnit(@PathVariable("id") Long id) {
        unitService.deleteUnit(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
