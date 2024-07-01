import {
  Box,
  Button,
  Heading,
  Spinner,
  TableContainer,
  Table,
  Thead,
  Tr,
  Th,
  Tbody,
  Td,
} from "@chakra-ui/react";
import { getUnits } from "../api/api";
import { useState } from "react";
import { Unit } from "../types/unit";

export function AllUnits() {
  const [units, setUnits] = useState<Unit[]>([]);
  const [loading, setLoading] = useState(true);

  async function fetchAllUnits() {
    try {
      const response = await getUnits();
      setUnits(response);
    } catch (error) {
      console.error("Error fetching units:", error);
    } finally {
      setLoading(false);
    }
  }

  return (
    <Box>
      <Button
        colorScheme="brand"
        size="lg"
        color="#393c61"
        onClick={fetchAllUnits}
      >
        Get all units
      </Button>
      {units.length > 0 ? (
        <Box marginTop="48px">
          <Heading>All units</Heading>
          {loading ? (
            <Spinner size="xl" />
          ) : (
            <TableContainer>
              <Table colorScheme="brand">
                <Thead>
                  <Tr>
                    <Th>ID</Th>
                    <Th>Version</Th>
                    <Th>Name</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {units.map((unit) => (
                    <Tr key={unit.id}>
                      <Td>{unit.id}</Td>
                      <Td>{unit.version}</Td>
                      <Td>{unit.name}</Td>
                    </Tr>
                  ))}
                </Tbody>
              </Table>
            </TableContainer>
          )}
        </Box>
      ) : null}
    </Box>
  );
}
