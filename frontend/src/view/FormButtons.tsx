import { Box } from "@chakra-ui/react";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";
import { Form } from "./Form";

export function FormButtons() {
  return (
    <Box
      display="flex"
      flexDirection="row"
      gap="48px"
      marginTop="56px"
      justifyContent="center"
    >
      <Box display="flex" flexDirection="column" gap="24px" alignItems="center">
        <Form entity={Entity.User} operation={Operation.Create} />
        <Form entity={Entity.Unit} operation={Operation.Create} />
        <Form entity={Entity.Role} operation={Operation.Create} />
        <Form entity={Entity.UserRole} operation={Operation.Create} />
      </Box>
      <Box display="flex" flexDirection="column" gap="24px" alignItems="center">
        <Form entity={Entity.User} operation={Operation.Update} />
        <Form entity={Entity.Unit} operation={Operation.Update} />
        <Form entity={Entity.Role} operation={Operation.Update} />
        <Form entity={Entity.UserRole} operation={Operation.Update} />
      </Box>
      <Box display="flex" flexDirection="column" gap="24px" alignItems="center">
        <Form entity={Entity.User} operation={Operation.Delete} />
        <Form entity={Entity.Unit} operation={Operation.Delete} />
        <Form entity={Entity.Role} operation={Operation.Delete} />
        <Form entity={Entity.UserRole} operation={Operation.Delete} />
      </Box>
    </Box>
  );
}
