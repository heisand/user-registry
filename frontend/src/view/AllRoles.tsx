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
import { getRoles, getUsers } from "../api/api";
import { User } from "../types/user";
import { useEffect, useState } from "react";
import { Role } from "../types/role";

export function AllRoles() {
  const [users, setUsers] = useState<Role[]>([]);
  const [loading, setLoading] = useState(true);

  function handleGetAllUsers() {
    fetchAllUsers();
  }

  async function fetchAllUsers() {
    try {
      const response = await getRoles();
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
        Get all roles
      </Button>
      {users.length > 0 ? (
        <Box marginTop="48px">
          <Heading>All roles</Heading>
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
