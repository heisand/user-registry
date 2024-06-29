import { Box, Heading } from "@chakra-ui/react";
import { AllUsers } from "../view/AllUsers";
import { Form } from "../view/Form";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";

export function Users() {
  return (
    <>
      <Heading as="h1" textAlign="center" color="#393c61">Users</Heading>
      <Box
        display="flex"
        flexDirection="row"
        flexWrap="wrap"
        gap="48px"
        justifyContent="center"
        marginTop="48px"
        marginBottom="48px"
      >
        <Form entity={Entity.User} operation={Operation.Create} />
        <Form entity={Entity.User} operation={Operation.Update} />
        <Form entity={Entity.User} operation={Operation.Delete} />
      </Box>
      <AllUsers />
    </>
  );
}
