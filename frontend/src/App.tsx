import { useEffect, useState } from "react";
import "./App.css";
import {
  Box,
  Button,
  Heading,
  Spinner,
  Table,
  TableContainer,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
} from "@chakra-ui/react";
import { getUser, getUsers } from "./api/api";
import { User } from "./types/user";
import { Form } from "./view/Form";
import { Operation } from "./types/operation";
import { Entity } from "./types/entity";
import { FormButtons } from "./view/FormButtons";

function App() {
  const [users, setUsers] = useState<User[]>([]);
  const [userById, setUserById] = useState<User>();
  const [loading, setLoading] = useState(true);

  function handleGetAllUsers() {
    fetchAllUsers();
  }

  async function fetchAllUsers() {
    try {
      const response = await getUsers();
      setUsers(response);
    } catch (error) {
      console.error("Error fetching users:", error);
    } finally {
      setLoading(false);
    }
  }

  async function fetchUserById() {
    try {
      const response = await getUser(1);
      setUserById(response);
    } catch (error) {
      console.error("Error fetching users:", error);
    } finally {
      setLoading(false);
    }
  }

  return (
    <Box>
      <Heading as="h1" textAlign="center">
        Cancer Registry
      </Heading>
      <Button colorScheme="teal" size="lg" onClick={handleGetAllUsers}>
        Get all users
      </Button>
      {users.length > 0 ? (
        <Box marginTop="48px">
          <Heading>All users</Heading>
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
      <FormButtons />
    </Box>
  );
}

export default App;
