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
  FormControl,
  FormLabel,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  useDisclosure,
} from "@chakra-ui/react";
import { getUsersWithRoles } from "../api/api";
import { useRef, useState } from "react";
import { UserWithRoles } from "../types/userwithroles";

export function UsersWithRolesByUnitId() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const initialRef = useRef(null);
  const [unitId, setUnitId] = useState("");
  const [users, setUsers] = useState<UserWithRoles[]>([]);
  const [loading, setLoading] = useState(true);

  const handleUnitIdtChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setUnitId(e.target.value);
  };

  function handleGetUsersByUnitId() {
    if (unitId !== "") {
      fetchUsersByUnitId();
    }
  }

  async function fetchUsersByUnitId() {
    try {
      const response = await getUsersWithRoles(Number.parseInt(unitId));
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
        Get users with roles by unit ID
      </Button>
      <Modal isOpen={isOpen} onClose={onClose} isCentered>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Which unit ID?</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <FormControl isRequired>
              <FormLabel>Unit ID</FormLabel>
              <Input
                ref={initialRef}
                placeholder="Unit ID"
                type="number"
                onChange={handleUnitIdtChange}
              />
            </FormControl>
          </ModalBody>

          <ModalFooter>
            <Button
              colorScheme="brand"
              mr={3}
              color="#393c61"
              onClick={handleGetUsersByUnitId}
            >
              OK
            </Button>
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
      {users.length > 0 ? (
        <Box marginTop="48px">
          <Heading>Users by unit ID</Heading>
          {loading ? (
            <Spinner size="xl" />
          ) : (
            <TableContainer>
              <Table colorScheme="brand">
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
