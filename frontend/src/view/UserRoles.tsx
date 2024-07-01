import { Box, Heading } from "@chakra-ui/react";
import { Form } from "./Form";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";
import { AllUserRoles } from "./AllUserRoles";
import { ValidUserRolesByUnitAndTimestamp } from "./ValidUserRolesByUnitAndTimestamp";
import { UserRolesByEntityId } from "./UserRolesByEntityId";

export function UserRoles() {
  return (
    <>
      <Heading as="h1" textAlign="center" color="#393c61">
        User roles
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
        <Form entity={Entity.UserRole} operation={Operation.Create} />
        <Form entity={Entity.UserRole} operation={Operation.Update} />
        <Form entity={Entity.UserRole} operation={Operation.Delete} />
      </Box>
      <Box display="flex" flexDirection="column" gap="48px">
        <AllUserRoles />
        <UserRolesByEntityId
          entityType={"User"}
          buttonText={"Get user roles by user ID"}
          modalTitle={"Which user ID?"}
          entityIdLabel={"User ID"}
        />
        <UserRolesByEntityId
          entityType={"Unit"}
          buttonText={"Get user roles by unit ID"}
          modalTitle={"Which unit ID?"}
          entityIdLabel={"Unit ID"}
        />
        <UserRolesByEntityId
          entityType={"Role"}
          buttonText={"Get user roles by role ID"}
          modalTitle={"Which role ID?"}
          entityIdLabel={"Role ID"}
        />
        <UserRolesByEntityId
          entityType={"Version"}
          buttonText={"Get user roles by version"}
          modalTitle={"Which version?"}
          entityIdLabel={"Version ID"}
        />
        <ValidUserRolesByUnitAndTimestamp />
      </Box>
    </>
  );
}
