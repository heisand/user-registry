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
  import { getUserRoles } from "../api/api";
  import { useRef, useState } from "react";
  import { UserRole } from "../types/userrole";
  
  export function UserRolesByRoleId() {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const initialRef = useRef(null);
    const [roleId, setRoleId] = useState("");
    const [users, setUsers] = useState<UserRole[]>([]);
    const [loading, setLoading] = useState(true);
  
    const handleUserIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      const value = e.target.value;
      setRoleId(e.target.value);
    };
  
    function handleGetUsers() {
      if (roleId !== "") {
        fetchUsers();
      }
    }
  
    async function fetchUsers() {
      try {
        const response = await getUserRoles(undefined, undefined, roleId)
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
          Get user roles by role ID
        </Button>
        <Modal isOpen={isOpen} onClose={onClose} isCentered>
          <ModalOverlay />
          <ModalContent>
            <ModalHeader>Which role ID?</ModalHeader>
            <ModalCloseButton />
            <ModalBody pb={6}>
              <FormControl isRequired marginTop="24px">
                <FormLabel>Role ID</FormLabel>
                <Input
                  ref={initialRef}
                  placeholder="Role ID"
                  type="number"
                  onChange={handleUserIdChange}
                />
              </FormControl>
            </ModalBody>
            <ModalFooter>
              <Button
                colorScheme="brand"
                mr={3}
                color="#393c61"
                onClick={handleGetUsers}
              >
                OK
              </Button>
              <Button onClick={onClose}>Cancel</Button>
            </ModalFooter>
          </ModalContent>
        </Modal>
        {users.length > 0 ? (
          <Box marginTop="48px">
            <Heading>Valid user roles</Heading>
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
  