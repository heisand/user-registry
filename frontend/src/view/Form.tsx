import {
  useDisclosure,
  Button,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  FormControl,
  FormLabel,
  Input,
  ModalFooter,
  FormHelperText,
  FormErrorMessage,
  Box,
} from "@chakra-ui/react";
import React, { useState } from "react";
import {
  createRole,
  createUnit,
  createUser,
  createUserRole,
  deleteRole,
  deleteUnit,
  deleteUser,
  updateRole,
  updateUnit,
  updateUser,
} from "../api/api";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";
import { Calendar } from "../component/Calendar";

type FormProps = {
  entity: Entity;
  operation: Operation;
};

export function Form(props: FormProps) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const initialRef = React.useRef(null);
  const [id, setId] = useState("");
  const [input, setInput] = useState("");
  const [userId, setUserId] = useState("");
  const [unitId, setUnitId] = useState("");
  const [roleId, setRoleId] = useState("");
  const [version, setVersion] = useState("");
  const [validFrom, setValidFrom] = useState<Date | null>(null);
  const [validTo, setValidTo] = useState<Date | null>(null);

  const handleIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setId(e.target.value);
  };

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setInput(e.target.value);
  };

  const handleUserIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setUserId(e.target.value);
  };

  const handleUnitIdtChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setUnitId(e.target.value);
  };

  const handleRoleIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setRoleId(e.target.value);
  };

  const handleVersionChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;
    setVersion(e.target.value);
  };

  function handleValidFrom(date: Date | null) {
    setValidFrom(date);
  }

  function handleValidTo(date: Date | null) {
    setValidTo(date);
  }

  function handleClose() {
    setId("");
    setInput("");
    setUserId("");
    setUnitId("");
    setRoleId("");
    setVersion("");
    setValidFrom(null);
    setValidTo(null);

    onClose();
  }

  const isMissingName = input === "";
  const isMissingId = id === "";
  const isMissingUserId = userId === "";
  const isMissingUnitId = unitId === "";
  const isMissingRoleId = roleId === "";
  const isMissingVersion = version === "";
  const isMissingValidFrom = validFrom === null;
  const isMissingValidTo = validTo === null

  const getEntity = (entity: Entity): string => {
    switch (entity) {
      case Entity.User:
        return "user";
      case Entity.Unit:
        return "unit";
      case Entity.Role:
        return "role";
      case Entity.UserRole:
        return "user role";
      default:
        return "";
    }
  };

  const getOperation = (operation: Operation): string => {
    switch (operation) {
      case Operation.Create:
        return "Create";
      case Operation.Update:
        return "Update";
      case Operation.Delete:
        return "Delete";
      default:
        return "";
    }
  };

  const title = `${getOperation(props.operation)} ${getEntity(props.entity)}`;

  function onSave() {
    switch (props.entity) {
      case Entity.User:
        switch (props.operation) {
          case Operation.Create:
            if(!isMissingName) createUser(input);
            break;
          case Operation.Update:
            if(!isMissingId && !isMissingVersion && !isMissingName) updateUser(id, version, input);
            break;
          case Operation.Delete:
            if(!isMissingId) deleteUser(id, version);
            break;
        }
        break;
      case Entity.Unit:
        switch (props.operation) {
          case Operation.Create:
            if(!isMissingName) createUnit(input);
            break;
          case Operation.Update:
            if(!isMissingId && !isMissingVersion && !isMissingName) updateUnit(id, version, input);
            break;
          case Operation.Delete:
            if(!isMissingId && !isMissingVersion) deleteUnit(id, version);
            break;
        }
        break;
      case Entity.Role:
        switch (props.operation) {
          case Operation.Create:
            if(!isMissingName) createRole(input);
            break;
          case Operation.Update:
            if(!isMissingId && !isMissingVersion && !isMissingName) updateRole(id, version, input);
            break;
          case Operation.Delete:
            if(!isMissingId && !isMissingVersion) deleteRole(id, version);
            break;
        }
        break;
      case Entity.UserRole:
        switch (props.operation) {
          case Operation.Create:
            if (!isMissingUserId && !isMissingUnitId && !isMissingRoleId)
            createUserRole(
              Number.parseInt(userId),
              Number.parseInt(unitId),
              Number.parseInt(roleId),
              validFrom?.toISOString() ?? "",
              validTo?.toISOString() ?? ""
            );
            break;
          case Operation.Update:
            //updateUserRole();
            break;
          case Operation.Delete:
            //deleteUserRole();
            break;
        }
        break;
      default:
        console.log("Invalid combination of entity and operation");
    }
  }

  return (
    <>
      <Button colorScheme="brand" size="lg" color="#393c61" onClick={onOpen}>
        {title}
      </Button>
      <Modal isOpen={isOpen} onClose={onClose} isCentered>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{title}</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            {props.entity !== Entity.UserRole &&
            props.operation !== Operation.Delete ? (
              <FormControl isRequired>
                <FormLabel>Name</FormLabel>
                <Input
                  ref={initialRef}
                  placeholder="Name"
                  onChange={handleInputChange}
                />
                {!isMissingName ? (
                  <FormHelperText>Enter the name on the entity.</FormHelperText>
                ) : (
                  <FormErrorMessage>Name is required.</FormErrorMessage>
                )}
              </FormControl>
            ) : null}
            {props.operation === Operation.Update ? (
              <Box>
                <FormControl isRequired marginTop="24px">
                  <FormLabel>Id</FormLabel>
                  <Input
                    ref={initialRef}
                    placeholder="ID"
                    onChange={handleIdChange}
                  />
                </FormControl>
                <FormControl isRequired marginTop="24px">
                  <FormLabel>Version</FormLabel>
                  <Input
                    ref={initialRef}
                    placeholder="Version"
                    onChange={handleVersionChange}
                  />
                </FormControl>
              </Box>
            ) : null}
            {props.operation === Operation.Delete ? (
              <FormControl isRequired>
                <FormLabel>Id</FormLabel>
                <Input
                  ref={initialRef}
                  placeholder="ID"
                  onChange={handleIdChange}
                />
              </FormControl>
            ) : null}
            {props.entity === Entity.UserRole ? (
              <Box marginTop="24px">
                <FormControl isRequired>
                  <FormLabel>User ID</FormLabel>
                  <Input
                    ref={initialRef}
                    placeholder="User ID"
                    type="number"
                    onChange={handleUserIdChange}
                  />
                  {!isMissingName ? (
                    <FormHelperText>
                      Enter the name on the entity.
                    </FormHelperText>
                  ) : (
                    <FormErrorMessage>Name is required.</FormErrorMessage>
                  )}
                </FormControl>
                <FormControl isRequired marginTop="24px">
                  <FormLabel>Unit ID</FormLabel>
                  <Input
                    ref={initialRef}
                    placeholder="Unit ID"
                    type="number"
                    onChange={handleUnitIdtChange}
                  />
                  {!isMissingName ? (
                    <FormHelperText>
                      Enter the name on the entity.
                    </FormHelperText>
                  ) : (
                    <FormErrorMessage>Name is required.</FormErrorMessage>
                  )}
                </FormControl>
                <FormControl isRequired marginTop="24px">
                  <FormLabel>Role ID</FormLabel>
                  <Input
                    ref={initialRef}
                    placeholder="Role ID"
                    type="number"
                    onChange={handleRoleIdChange}
                  />
                  {!isMissingName ? (
                    <FormHelperText>
                      Enter the name on the entity.
                    </FormHelperText>
                  ) : (
                    <FormErrorMessage>Name is required.</FormErrorMessage>
                  )}
                </FormControl>
                <FormLabel marginTop="24px">Valid from</FormLabel>
                <Calendar handleDate={handleValidFrom} />
                <FormLabel marginTop="24px">Valid to</FormLabel>
                <Calendar handleDate={handleValidTo} />
              </Box>
            ) : null}
          </ModalBody>

          <ModalFooter>
            <Button colorScheme="brand" mr={3} color="#393c61" onClick={onSave}>
              Save
            </Button>
            <Button onClick={handleClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
