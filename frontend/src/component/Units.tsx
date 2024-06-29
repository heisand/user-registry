import { Box, Heading } from "@chakra-ui/react";
import { Form } from "../view/Form";
import { Entity } from "../types/entity";
import { Operation } from "../types/operation";
import { AllUnits } from "../view/AllUnits";

export function Units() {
  return (
    <>
      <Heading as="h1" textAlign="center" color="#393c61">
        Units
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
        <Form entity={Entity.Unit} operation={Operation.Create} />
        <Form entity={Entity.Unit} operation={Operation.Update} />
        <Form entity={Entity.Unit} operation={Operation.Delete} />
      </Box>
      <AllUnits />
    </>
  );
}
