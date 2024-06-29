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
  import { getUserRoles, getUsers, getUsersWithRoles } from "../api/api";
  import { User } from "../types/user";
  import { useEffect, useState } from "react";
  import { UserRole } from "../types/userrole";
import { UserWithRoles } from "../types/userwithroles";
  
  export function UsersWithRolesByUnitId() {
    const [users, setUsers] = useState<UserWithRoles[]>([]);
    const [loading, setLoading] = useState(true);
  
    function handleGetAllUsers() {
      fetchAllUsers();
    }
  
    async function fetchAllUsers() {
      try {
        const response = await getUsersWithRoles(1);
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
          Get users by unit ID
        </Button>
        {users.length > 0 ? (
          <Box marginTop="48px">
            <Heading>Users by unit ID</Heading>
            {loading ? (
              <Spinner size="xl" />
            ) : (
              <TableContainer>
                <Table colorScheme="teal">
                  <Thead>
                    <Tr>
                      <Th>ID</Th>
                      <Th>Name</Th>
                      <Th>Role</Th>
                    </Tr>
                  </Thead>
                  <Tbody>
                    {users.map((user) => (
                      <Tr key={user.id}>
                        <Td>{user.id}</Td>
                        <Td>{user.name}</Td>
                        <Td>{user.roles.map((r) => r.name)}</Td>
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
  