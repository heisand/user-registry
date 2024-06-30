package no.cancerregistry.unittest;

import no.cancerregistry.exception.UnitNotFoundException;
import no.cancerregistry.model.UnitDTO;
import no.cancerregistry.repository.UnitRepository;
import no.cancerregistry.repository.entity.Unit;
import no.cancerregistry.service.UnitService;
import no.cancerregistry.exception.WrongVersionException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {

    static private UnitService unitService;
    static private UnitRepository unitRepository;

    @BeforeAll
    static void setup() {
        unitRepository = mock(UnitRepository.class);
        unitService = new UnitService(unitRepository);
    }

    @Test
    public void whenCreateUnit_thenReturnSavedUnit() {
        UnitDTO unit = new UnitDTO(
                Optional.of(1L),
                Optional.of(2),
                "Researcher Researcher");

        Unit unitMock = new Unit();
        unitMock.setVersion(1);
        unitMock.setName("Researcher Researcher");

        when(unitRepository.save(any(Unit.class))).thenReturn(unitMock);

        UnitDTO savedUnit = unitService.createUnit(unit);

        assertNotNull(savedUnit);
        assertEquals(savedUnit.getName(), "Researcher Researcher");
    }

    @Test
    public void givenAbundantUnit_whenGetUnit_thenThrowUnitNotFoundException() {
        when(unitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UnitNotFoundException.class, () -> unitService.getUnit(1L));
    }

	/*@Test
	public void givenNullId_whenGetUnits_thenThrowUNoSuchElementException() {
		assertThrows(NoSuchElementException.class, () -> unitService.getUnits(null, null, null));
	}*/

    @Test
    public void givenNoParameters_whenGetUnits_thenReturnsSuccessfully() {
        when(unitRepository.findAll()).thenReturn(List.of(new Unit()));

        List<UnitDTO> units =
                unitService.getUnits(Optional.empty(), Optional.empty());

        assertFalse(units.isEmpty());
    }

    @Test
    public void givenWrongVersion_whenUpdateUnit_thenThrowWrongVersionException() {
        UnitDTO unit = new UnitDTO(
                Optional.of(1L),
                Optional.of(2),
                "Researcher Researcher");

        Unit unitMock = new Unit();
        unitMock.setVersion(1);
        unitMock.setName("Researcher Researcher");

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unitMock));

        assertThrows(WrongVersionException.class, () -> unitService.updateUnit(1L, unit));
    }

    @Test
    public void givenNullVersion_whenUpdateUnit_thenThrowWrongVersionException() {
        UnitDTO unit = new UnitDTO(
                Optional.of(1L),
                Optional.ofNullable(null),
                "Researcher Researcher");

        assertThrows(WrongVersionException.class, () -> unitService.updateUnit(1L, unit));
    }

    @Test
    public void givenAbundantUnit_whenUpdateUnit_thenThrowUnitNotFoundException() {
        UnitDTO unit = new UnitDTO(
                Optional.of(1L),
                Optional.of(1),
                "Researcher Researcher");

        Unit unitMock = new Unit();
        unitMock.setVersion(1);
        unitMock.setName("Researcher Researcher");

        when(unitRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UnitNotFoundException.class, () -> unitService.updateUnit(1L, unit));
    }

    @Test
    public void givenExistingUnit_whenUpdateUnit_thenUpdatesSuccessfully() {
        UnitDTO unit = new UnitDTO(
                Optional.of(1L),
                Optional.of(1),
                "Researcher Researcher");

        Unit unitMock = new Unit();
        unitMock.setVersion(1);
        unitMock.setName("Researcher Researcher");

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unitMock));

        assertDoesNotThrow(() -> unitService.updateUnit(1L, unit));
    }
}