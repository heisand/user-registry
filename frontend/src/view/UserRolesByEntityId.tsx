import React, { useState, useRef } from "react";
import {
  Box,
  Button,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  ModalFooter,
  FormControl,
  FormLabel,
  Input,
  Spinner,
  Table,
  TableContainer,
  Thead,
  Tr,
  Th,
  Tbody,
  Td,
  Heading,
  useDisclosure,
} from "@chakra-ui/react";
import { UserRole } from "../types/userrole";
import { getUserRoles } from "../api/api";

type EntityType = "Unit" | "Role" | "User" | "Version";

interface UserRolesByEntityIdProps {
  entityType: EntityType;
  buttonText: string;
  modalTitle: string;
  entityIdLabel: string;
}

export function UserRolesByEntityId({
  entityType,
  buttonText,
  modalTitle,
  entityIdLabel,
}: UserRolesByEntityIdProps) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const initialRef = useRef(null);
  const [entityId, setEntityId] = useState("");
  const [userRoles, setUserRoles] = useState<UserRole[]>([]);
  const [loading, setLoading] = useState(false);

  const handleEntityIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEntityId(e.target.value);
  };

  const handleGetUserRoles = async () => {
    setLoading(true);
    try {
      let userIdParam;
      let unitIdParam;
      let roleIdParam;
      let versionParam;

      switch (entityType) {
        case "User":
          userIdParam = entityId;
          break;
        case "Unit":
          unitIdParam = entityId;
          break;
        case "Role":
          roleIdParam = entityId;
          break;
        case "Version":
          versionParam = entityId;
            break;
        default:
          break;
      }

      const response = await getUserRoles(userIdParam, unitIdParam, roleIdParam, versionParam);
      setUserRoles(response);
    } catch (error) {
      console.error("Error fetching user roles:", error);
    } finally {
      setLoading(false);
    }
  };

  return (
    <Box>
      <Button colorScheme="brand" size="lg" color="#393c61" onClick={onOpen}>
        {buttonText}
      </Button>
      <Modal isOpen={isOpen} onClose={onClose} isCentered>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{modalTitle}</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <FormControl isRequired marginTop="24px">
              <FormLabel>{entityIdLabel}</FormLabel>
              <Input
                ref={initialRef}
                placeholder={entityIdLabel}
                type="text"
                onChange={handleEntityIdChange}
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
      {userRoles.length > 0 && (
        <Box marginTop="48px">
          <Heading color="#393c61">User roles by {entityIdLabel.toLowerCase()}</Heading>
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
      )}
    </Box>
  );
}
