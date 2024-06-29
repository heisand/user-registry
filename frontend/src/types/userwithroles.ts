import { Role } from "./role";

export type UserWithRoles = {
  id: number;
  version: number;
  name: string;
  roles: Role[];
};
