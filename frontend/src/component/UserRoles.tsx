import { Box, Heading } from "@chakra-ui/react";
import { Form } from "../view/Form";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";
import { AllUserRoles } from "../view/AllUserRoles";
import { UsersWithRolesByUnitId } from "../view/UsersWithRolesByUnitId";
import { ValidUserRolesByUnitAndTimestamp } from "../view/ValidUserRolesByUnitAndTimestamp";

export function UserRoles() {
  return (
    <>
    <Heading as="h1" textAlign="center" color="#393c61">User roles</Heading>
      <Box
        display="flex"
        flexDirection="row"
        flexWrap="wrap"
        gap="48px"
        justifyContent="center"
        marginTop="48px"
        marginBottom="48px"
      >
        <Form entity={Entity.UserRole} operation={Operation.Create} />
        <Form entity={Entity.UserRole} operation={Operation.Update} />
        <Form entity={Entity.UserRole} operation={Operation.Delete} />
      </Box>
      <Box display="flex" flexDirection="column" gap="48px">
        <AllUserRoles />
        <UsersWithRolesByUnitId />
        <ValidUserRolesByUnitAndTimestamp />
      </Box>
    </>
  );
}
