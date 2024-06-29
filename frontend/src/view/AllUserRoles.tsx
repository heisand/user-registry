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
import { getUserRoles, getUsers } from "../api/api";
import { User } from "../types/user";
import { useEffect, useState } from "react";
import { UserRole } from "../types/userrole";

export function AllUserRoles() {
  const [users, setUsers] = useState<UserRole[]>([]);
  const [loading, setLoading] = useState(true);

  function handleGetAllUsers() {
    fetchAllUsers();
  }

  async function fetchAllUsers() {
    try {
      const response = await getUserRoles();
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
        Get all user roles
      </Button>
      {users.length > 0 ? (
        <Box marginTop="48px">
          <Heading>All user roles</Heading>
          {loading ? (
            <Spinner size="xl" />
          ) : (
            <TableContainer>
              <Table colorScheme="teal">
                <Thead>
                  <Tr>
                    <Th>ID</Th>
                    <Th>Version</Th>
                    <Th>UserId</Th>
                    <Th>UnitID</Th>
                    <Th>RoleId</Th>
                    <Th>ValidFrom</Th>
                    <Th>ValidTo</Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {users.map((user) => (
                    <Tr key={user.id}>
                      <Td>{user.id}</Td>
                      <Td>{user.version}</Td>
                      <Td>{user.userId}</Td>
                      <Td>{user.unitId}</Td>
                      <Td>{user.roleId}</Td>
                      <Td>{user.validFrom}</Td>
                      <Td>{user.validTo}</Td>
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
