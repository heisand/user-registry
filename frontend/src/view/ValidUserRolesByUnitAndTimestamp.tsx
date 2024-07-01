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
import { getValidUsers } from "../api/api";
import { useRef, useState } from "react";
import { UserRole } from "../types/userrole";
import { Calendar } from "../component/Calendar";

export function ValidUserRolesByUnitAndTimestamp() {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const initialRef = useRef(null);
  const [unitId, setUnitId] = useState("");
  const [userId, setUserId] = useState("");
  const [timestamp, setTimestamp] = useState<Date | null>(null);
  const [users, setUsers] = useState<UserRole[]>([]);
  const [loading, setLoading] = useState(true);

  const handleUnitIdtChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUnitId(e.target.value);
  };

  const handleUserIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserId(e.target.value);
  };

  function handleTimestampChange(date: Date | null) {
    setTimestamp(date);
  }

  function handleGetValidUsers() {
    if (unitId !== "" && userId !== "" && timestamp !== null) {
      fetchValiUsers();
    }
  }

  async function fetchValiUsers() {
    try {
      const response = await getValidUsers(
        Number.parseInt(unitId),
        Number.parseInt(userId),
        timestamp?.toISOString() ?? ""
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
        Get valid user roles
      </Button>
      <Modal isOpen={isOpen} onClose={onClose} isCentered>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{"Which unit and user ID?"}</ModalHeader>
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
            <FormControl isRequired marginTop="24px">
              <FormLabel>User ID</FormLabel>
              <Input
                ref={initialRef}
                placeholder="Unit ID"
                type="number"
                onChange={handleUserIdChange}
              />
            </FormControl>
            <FormLabel marginTop="24px">Valid from</FormLabel>
            <Calendar handleDate={handleTimestampChange} />
          </ModalBody>
          <ModalFooter>
            <Button
              colorScheme="brand"
              mr={3}
              color="#393c61"
              onClick={handleGetValidUsers}
            >
              OK
            </Button>
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
      {users.length > 0 ? (
        <Box marginTop="48px">
          <Heading color="#393c61">Valid user roles</Heading>
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
