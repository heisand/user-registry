import { Box, Heading } from "@chakra-ui/react";
import { Form } from "../view/Form";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";
import { AllRoles } from "../view/AllRoles";

export function Roles() {
  return (
    <>
      <Heading as="h1" textAlign="center" color="#393c61">
        Roles
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
        <Form entity={Entity.Role} operation={Operation.Create} />
        <Form entity={Entity.Role} operation={Operation.Update} />
        <Form entity={Entity.Role} operation={Operation.Delete} />
      </Box>
      <AllRoles />
    </>
  );
}
