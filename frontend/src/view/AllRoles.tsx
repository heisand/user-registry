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
import { getRoles } from "../api/api";
import { useState } from "react";
import { Role } from "../types/role";

export function AllRoles() {
  const [roles, setRoles] = useState<Role[]>([]);
  const [loading, setLoading] = useState(true);

  function handleGetAllRoles() {
    fetchAllRoles();
  }

  async function fetchAllRoles() {
    try {
      const response = await getRoles();
      setRoles(response);
    } catch (error) {
      console.error("Error fetching roles:", error);
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
        onClick={handleGetAllRoles}
      >
        Get all roles
      </Button>
      {roles.length > 0 ? (
        <Box marginTop="48px">
          <Heading>All roles</Heading>
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
                  {roles.map((role) => (
                    <Tr key={role.id}>
                      <Td>{role.id}</Td>
                      <Td>{role.version}</Td>
                      <Td>{role.name}</Td>
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
