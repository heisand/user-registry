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
import { AllUsers } from "./view/AllUsers";
import { AllUnits } from "./view/AllUnits";
import { AllRoles } from "./view/AllRoles";
import { AllUserRoles } from "./view/AllUserRoles";
import { UsersWithRolesByUnitId } from "./view/UsersWithRolesByUnitId";
import { ValidUserRolesByUnitAndTimestamp } from "./view/ValidUserRolesByUnitAndTimestamp";

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
      <Box display="row" marginTop="48px" gap="12px">
        <AllUsers />
        <AllUnits />
        <AllRoles />
        <AllUserRoles />
        <UsersWithRolesByUnitId />
        <ValidUserRolesByUnitAndTimestamp />
      </Box>
      <FormButtons />
    </Box>
  );
}

export default App;
