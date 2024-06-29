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
import { createRole, createUnit, createUser, createUserRole } from "../api/api";
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
  const [input, setInput] = useState("");
  const [userId, setUserId] = useState("");
  const [unitId, setUnitId] = useState("");
  const [roleId, setRoleId] = useState("");
  const [validFrom, setValidFrom] = useState<Date | null>(null);
  const [validTo, setValidTo] = useState<Date | null>(null);

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

  function handleValidFrom(date: Date | null) {
    setValidFrom(date);
  }

  function handleValidTo(date: Date | null) {
    setValidTo(date);
  }

  const isError = input === "";

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
            createUser(input);
            break;
          case Operation.Update:
            //updateUser();
            break;
          case Operation.Delete:
            //deleteUser();
            break;
        }
        break;
      case Entity.Unit:
        switch (props.operation) {
          case Operation.Create:
            createUnit(input);
            break;
          case Operation.Update:
            //updateUnit();
            break;
          case Operation.Delete:
            //deleteUnit();
            break;
        }
        break;
      case Entity.Role:
        switch (props.operation) {
          case Operation.Create:
            createRole(input);
            break;
          case Operation.Update:
            //updateRole();
            break;
          case Operation.Delete:
            //deleteRole();
            break;
        }
        break;
      case Entity.UserRole:
        switch (props.operation) {
          case Operation.Create:
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
            {props.entity !== Entity.UserRole ? (
              <FormControl isRequired>
                <FormLabel>Name</FormLabel>
                <Input
                  ref={initialRef}
                  placeholder="Name"
                  onChange={handleInputChange}
                />
                {!isError ? (
                  <FormHelperText>Enter the name on the entity.</FormHelperText>
                ) : (
                  <FormErrorMessage>Name is required.</FormErrorMessage>
                )}
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
                  {!isError ? (
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
                  {!isError ? (
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
                  {!isError ? (
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
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
