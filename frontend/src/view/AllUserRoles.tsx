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
import { getUserRoles } from "../api/api";
import { useState } from "react";
import { UserRole } from "../types/userrole";

export function AllUserRoles() {
  const [userRoles, setUserRoles] = useState<UserRole[]>([]);
  const [loading, setLoading] = useState(true);

  function handleGetAllUserRoles() {
    fetchAllUserRoles();
  }

  async function fetchAllUserRoles() {
    try {
      const response = await getUserRoles();
      setUserRoles(response);
    } catch (error) {
      console.error("Error fetching user roles:", error);
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
        onClick={handleGetAllUserRoles}
      >
        Get all user roles
      </Button>
      {userRoles.length > 0 ? (
        <Box marginTop="48px">
          <Heading>All user roles</Heading>
          {loading ? (
            <Spinner size="xl" />
          ) : (
            <TableContainer>
              <Table colorScheme="brand">
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
                  {userRoles.map((userRole) => (
                    <Tr key={userRole.id}>
                      <Td>{userRole.id}</Td>
                      <Td>{userRole.version}</Td>
                      <Td>{userRole.userId}</Td>
                      <Td>{userRole.unitId}</Td>
                      <Td>{userRole.roleId}</Td>
                      <Td>{userRole.validFrom}</Td>
                      <Td>{userRole.validTo}</Td>
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
