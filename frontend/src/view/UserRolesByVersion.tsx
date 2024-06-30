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
  
  export function UserRolesByVersion() {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const initialRef = useRef(null);
    const [version, setVersion] = useState("");
    const [userRoles, setUserRoles] = useState<UserRole[]>([]);
    const [loading, setLoading] = useState(true);
  
    const handleVersionChange = (e: React.ChangeEvent<HTMLInputElement>) => {
      setVersion(e.target.value);
    };
  
    function handleGetUserRoles() {
      if (version !== "") {
        fetchUsers();
      }
    }
  
    async function fetchUsers() {
      try {
        const response = await getUserRoles(undefined, undefined, undefined, version)
        setUserRoles(response);
      } catch (error) {
        console.error("Error fetching users:", error);
      } finally {
        setLoading(false);
      }
    }
  
    return (
      <Box>
        <Button colorScheme="brand" size="lg" color="#393c61" onClick={onOpen}>
          Get user roles by version
        </Button>
        <Modal isOpen={isOpen} onClose={onClose} isCentered>
          <ModalOverlay />
          <ModalContent>
            <ModalHeader>Which version?</ModalHeader>
            <ModalCloseButton />
            <ModalBody pb={6}>
              <FormControl isRequired marginTop="24px">
                <FormLabel>Version</FormLabel>
                <Input
                  ref={initialRef}
                  placeholder="Version"
                  type="number"
                  onChange={handleVersionChange}
                />
              </FormControl>
            </ModalBody>
            <ModalFooter>
              <Button
                colorScheme="brand"
                mr={3}
                color="#393c61"
                onClick={handleGetUserRoles}
              >
                OK
              </Button>
              <Button onClick={onClose}>Cancel</Button>
            </ModalFooter>
          </ModalContent>
        </Modal>
        {userRoles.length > 0 ? (
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
  