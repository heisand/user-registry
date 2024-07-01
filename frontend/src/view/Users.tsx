import {
  Box,
  Button,
  FormControl,
  FormLabel,
  Heading,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Spinner,
  Table,
  TableContainer,
  Tbody,
  Td,
  Th,
  Thead,
  Tr,
  useDisclosure,
} from "@chakra-ui/react";
import { AllUsers } from "./AllUsers";
import { Form } from "./Form";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";
import React, { useState, useRef } from "react";
import { getUsers } from "../api/api";
import { User } from "../types/user";
import { UsersWithRolesByUnitId } from "./UsersWithRolesByUnitId";

export function Users() {
  return (
    <>
      <Heading as="h1" textAlign="center" color="#393c61">
        Users
      </Heading>
      <Box
        display="flex"
        flexDirection="row"
        flexWrap="wrap"
        gap="48px"
        justifyContent="center"
        marginTop="48px"
        marginBottom="48px"
      >
        <Form entity={Entity.User} operation={Operation.Create} />
        <Form entity={Entity.User} operation={Operation.Update} />
        <Form entity={Entity.User} operation={Operation.Delete} />
      </Box>
      <Box display="flex" flexDirection="column" gap="48px">
      <SearchForUser />
      <UsersWithRolesByUnitId />
      <AllUsers />
      </Box>
    </>
  );

  function SearchForUser() {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const initialRef = useRef(null);
    const [name, setName] = useState("");
    const [unitId, setUnitId] = useState("");
    const [roleId, setRoleId] = useState("");
    const [users, setUsers] = useState<User[]>([]);
    const [loading, setLoading] = useState(true);

    const handleNameChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setName(e.target.value);
    };

    const handleUnitIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setUnitId(e.target.value);
    };

    const handleRoleIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setRoleId(e.target.value);
    };

    function handleGetUsersByQuery() {
      fetchUsersByQuery();
    }

    async function fetchUsersByQuery() {
      try {
        const response = await getUsers(
          name === "" ? undefined : name,
          unitId === "" ? undefined : unitId,
          roleId === "" ? undefined : roleId
        );
        setUsers(response);
      } catch (error) {
        console.error("Error fetching users:", error);
      } finally {
        setLoading(false);
      }
    }

    return (
      <Box>
        <Button colorScheme="brand" size="lg" color="#393c61" onClick={onOpen}>
          Search for users
        </Button>
        <Modal isOpen={isOpen} onClose={onClose} isCentered>
          <ModalOverlay />
          <ModalContent>
            <ModalHeader>Search for users (specify all or some)</ModalHeader>
            <ModalCloseButton />
            <ModalBody pb={6}>
              <FormControl marginTop="24px">
                <FormLabel>Name</FormLabel>
                <Input
                  ref={initialRef}
                  placeholder="Name"
                  onChange={handleNameChange}
                />
              </FormControl>
              <FormControl marginTop="24px">
                <FormLabel>Unit ID</FormLabel>
                <Input
                  ref={initialRef}
                  placeholder="Unit ID"
                  type="number"
                  onChange={handleUnitIdChange}
                />
              </FormControl>
              <FormControl marginTop="24px">
                <FormLabel>Role ID</FormLabel>
                <Input
                  ref={initialRef}
                  placeholder="Unit ID"
                  type="number"
                  onChange={handleRoleIdChange}
                />
              </FormControl>
            </ModalBody>
            <ModalFooter>
              <Button
                colorScheme="brand"
                mr={3}
                color="#393c61"
                onClick={handleGetUsersByQuery}
              >
                Search
              </Button>
              <Button onClick={onClose}>Cancel</Button>
            </ModalFooter>
          </ModalContent>
        </Modal>
        {users.length > 0 ? (
          <Box marginTop="48px">
            <Heading color="#393c61">Søketreff</Heading>
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
}
