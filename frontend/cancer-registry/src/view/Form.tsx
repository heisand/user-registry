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
} from "@chakra-ui/react";
import React, { useState } from "react";
import { createUser } from "../api/api";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";

type FormProps = {
  entity: Entity;
  operation: Operation;
};

export function Form(props: FormProps) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const initialRef = React.useRef(null);
  const [input, setInput] = useState("");

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) =>
    setInput(e.target.value);
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

  function onSave() {}

  return (
    <>
      <Button colorScheme="teal" size="lg" onClick={onOpen}>
        {title}
      </Button>
      <Modal isOpen={isOpen} onClose={onClose} isCentered>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{title}</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <FormControl isRequired>
              <FormLabel>Name</FormLabel>
              <Input
                ref={initialRef}
                placeholder="Name"
                onChange={handleInputChange}
              />
              {!isError ? (
                <FormHelperText>Enter the name on the user.</FormHelperText>
              ) : (
                <FormErrorMessage>Name is required.</FormErrorMessage>
              )}
            </FormControl>
          </ModalBody>

          <ModalFooter>
            <Button colorScheme="blue" mr={3} onClick={onSave}>
              Save
            </Button>
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
}
