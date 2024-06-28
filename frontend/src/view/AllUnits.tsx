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
import { getUnits, getUsers } from "../api/api";
import { User } from "../types/user";
import { useEffect, useState } from "react";
import { Unit } from "../types/unit";

export function AllUnits() {
  const [users, setUsers] = useState<Unit[]>([]);
  const [loading, setLoading] = useState(true);

  function handleGetAllUsers() {
    fetchAllUsers();
  }

  async function fetchAllUsers() {
    try {
      const response = await getUnits();
      setUsers(response);
    } catch (error) {
      console.error("Error fetching users:", error);
    } finally {
      setLoading(false);
    }
  }

  return (
    <Box>
      <Button colorScheme="teal" size="lg" onClick={handleGetAllUsers}>
        Get all units
      </Button>
      {users.length > 0 ? (
        <Box marginTop="48px">
          <Heading>All units</Heading>
          {loading ? (
            <Spinner size="xl" />
          ) : (
            <TableContainer>
              <Table colorScheme="teal">
                <Thead>
                  <Tr>
                    <Th>ID</Th>
                    <Th>Version</Th>
                    <Th>Name</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {users.map((user) => (
                    <Tr key={user.id}>
                      <Td>{user.id}</Td>
                      <Td>{user.version}</Td>
                      <Td>{user.name}</Td>
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
