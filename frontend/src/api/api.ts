import { Role } from "../types/role";
import { Unit } from "../types/unit";
import { User } from "../types/user";
import { UserRole } from "../types/userrole";
import { UserWithRoles } from "../types/userwithroles";
import { BASE_URL } from "../utils/baseurl";

async function postEntity(url: string, name: string) {
  try {
    const response = await fetch(`${BASE_URL}${url}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify({ name }),
    });

    if (!response.ok) {
      console.error(`HTTP error! Status: ${response.status}`);
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error("Error posting:", error);
  }
}

async function postUserRole(
  userId: number,
  unitId: number,
  roleId: number,
  validFrom: string,
  validTo: string
) {
  try {
    const response = await fetch(`${BASE_URL}/api/user-roles`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify({
        userId,
        unitId,
        roleId,
        validFrom,
        validTo,
      }),
    });

    if (!response.ok) {
      console.error(`HTTP error! Status: ${response.status}`);
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error("Error posting:", error);
  }
}

async function updateEntity(
  url: string,
  id: string,
  version: string,
  name: string
) {
  try {
    const response = await fetch(`${BASE_URL}${url}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ id, version, name }),
    });

    if (!response.ok) {
      console.error(`HTTP error! Status: ${response.status}`);
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error("Error posting:", error);
  }
}

async function deleteEntity(url: string, id: string, version: string) {
  try {
    const response = await fetch(`${BASE_URL}${url}`, {
      method: "DELETE",
      body: JSON.stringify({ id, version }),
    });

    if (!response.ok) {
      console.error(`HTTP error! Status: ${response.status}`);
    }

    const responseData = await response.json();
    return responseData;
  } catch (error) {
    console.error("Error posting:", error);
  }
}

export async function getUsers(
  name: string | undefined = undefined,
  unitId: string | undefined = undefined,
  roleId: string | undefined = undefined
) {
  const params = new URLSearchParams();

  if (name !== undefined) {
    params.append("name", name);
  }
  if (unitId !== undefined) {
    params.append("unitId", unitId);
  }
  if (roleId !== undefined) {
    params.append("roleId", roleId);
  }

  const queryString = params.toString();

  const response = await fetch(
    `${BASE_URL}/api/users${queryString ? `?${queryString}` : ""}`,
    {
      method: "GET",
      headers: {
        Accept: "application/json",
      },
    }
  );
  if (!response.ok) {
    console.error("Error fetching", response.status, await response.text());
    return [];
  }
  const users: User[] = await response.json();
  return users;
}

export async function getUsersWithRoles(unitId: number) {
  const response = await fetch(
    `${BASE_URL}/api/user-roles/units/${unitId}/users-with-roles`,
    {
      method: "GET",
      headers: {
        Accept: "application/json",
      },
    }
  );
  if (!response.ok) {
    console.error("Error fetching", response.status, await response.text());
    return [];
  }
  const users: UserWithRoles[] = await response.json();
  return users;
}

export async function getValidUsers(
  unitId: number,
  userId: number,
  timestamp: string
) {
  const response = await fetch(
    `${BASE_URL}/api/user-roles?unitId=${unitId}&userId=${userId}&timestamp=${timestamp}&isValid=true`,
    {
      method: "GET",
      headers: {
        Accept: "application/json",
      },
    }
  );
  if (!response.ok) {
    console.error("Error fetching", response.status, await response.text());
    return [];
  }
  const users: UserRole[] = await response.json();
  return users;
}

export async function getUnits() {
  const response = await fetch(`${BASE_URL}/api/units`, {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  });
  if (!response.ok) {
    console.error("Error fetching", response.status, await response.text());
    return [];
  }
  const users: Unit[] = await response.json();
  return users;
}

export async function getRoles() {
  const response = await fetch(`${BASE_URL}/api/roles`, {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  });
  if (!response.ok) {
    console.error("Error fetching", response.status, await response.text());
    return [];
  }
  const users: Role[] = await response.json();
  return users;
}

export async function getUserRoles(
  userId: string | undefined = undefined,
  unitId: string | undefined = undefined,
  roleId: string | undefined = undefined,
  version: string | undefined = undefined
) {
  const params = new URLSearchParams();

  if (userId !== undefined) {
    params.append("userId", userId);
  }
  if (unitId !== undefined) {
    params.append("unitId", unitId);
  }
  if (roleId !== undefined) {
    params.append("roleId", roleId);
  }
  if (version !== undefined) {
    params.append("roleId", version);
  }
  const queryString = params.toString();

  const response = await fetch(
    `${BASE_URL}/api/user-roles${queryString ? `?${queryString}` : ""}`,
    {
      method: "GET",
      headers: {
        Accept: "application/json",
      },
    }
  );
  if (!response.ok) {
    console.error("Error fetching", response.status, await response.text());
    return [];
  }
  const users: UserRole[] = await response.json();
  return users;
}

export async function getUser(id: number) {
  const response = await fetch(`${BASE_URL}/api/users/${id}`, {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  });
  if (!response.ok) {
    console.error("Error fetching", response.status, await response.text());
  }
  const user = await response.json();
  return user as User;
}

export async function createUser(name: string) {
  postEntity(`/api/users`, name);
}

export async function updateUser(id: string, version: string, name: string) {
  updateEntity(`/api/users/${id}`, id, version, name);
}

export async function deleteUser(id: string, version: string) {
  deleteEntity(`/api/users/${id}`, id, version);
}

export async function createUnit(name: string) {
  postEntity("/api/units", name);
}

export async function updateUnit(id: string, version: string, name: string) {
  updateEntity(`/api/units/${id}`, id, version, name);
}

export async function deleteUnit(id: string, version: string) {
  deleteEntity(`/api/units/${id}`, id, version);
}

export async function createRole(name: string) {
  postEntity("/api/roles", name);
}

export async function updateRole(id: string, version: string, name: string) {
  updateEntity(`/api/roles/${id}`, id, version, name);
}

export async function deleteRole(id: string, version: string) {
  deleteEntity(`/api/roles/${id}`, id, version);
}

export async function createUserRole(
  userId: number,
  unitId: number,
  roleId: number,
  validFrom: string,
  validTo: string
) {
  postUserRole(userId, unitId, roleId, validFrom, validTo);
}

export async function updateUserRole(
  id: string,
  version: string,
  name: string
) {
  updateEntity(`/api/user-roles/${id}`, id, version, name);
}

export async function deleteUseRole(id: string, version: string) {
  deleteEntity(`/api/user-roles/${id}`, id, version);
}
