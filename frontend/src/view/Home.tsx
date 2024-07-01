import {
  Card,
  CardBody,
  Heading,
  Stack,
  Image,
  Box,
  Checkbox,
} from "@chakra-ui/react";
import { useState } from "react";

export function Home() {
  return (
    <Box display="flex" flexDirection="column" gap="48px">
      <Heading as="h1" textAlign="center" color="#393c61">
        Cancer Registry
      </Heading>
      <Card maxW="sm" alignSelf="center">
        <CardBody>
          <Image
            objectFit="cover"
            src="https://images.unsplash.com/photo-1531403009284-440f080d1e12?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"
            alt="Chakra UI"
          />
          <Stack mt="6" spacing="3">
            <Heading size="md" textAlign="center" color="#393c61">
              Task board
            </Heading>
            <TodoList />
            <TodoList />
          </Stack>
        </CardBody>
      </Card>
    </Box>
  );

  function TodoList() {
    const [checkedItems, setCheckedItems] = useState([false, false]);

    const allChecked = checkedItems.every(Boolean);
    const isIndeterminate = checkedItems.some(Boolean) && !allChecked;

    return (
      <>
        <Checkbox
          isChecked={allChecked}
          isIndeterminate={isIndeterminate}
          onChange={(e) =>
            setCheckedItems([e.target.checked, e.target.checked])
          }
        >
          Register new user role
        </Checkbox>
        <Stack pl={6} mt={1} spacing={1}>
          <Checkbox
            isChecked={checkedItems[0]}
            onChange={(e) =>
              setCheckedItems([e.target.checked, checkedItems[1]])
            }
          >
            Add user to AD
          </Checkbox>
          <Checkbox
            isChecked={checkedItems[1]}
            onChange={(e) =>
              setCheckedItems([checkedItems[0], e.target.checked])
            }
          >
            Send confirmation email to user
          </Checkbox>
        </Stack>
      </>
    );
  }
}
