import { Role } from "../types/role";
import { Unit } from "../types/unit";
import { User } from "../types/user";
import { UserRole } from "../types/userrole";
import { BASE_URL } from "../utils/baseurl";

async function postEntity(url: string, name: string) {
  try {
    const response = await fetch(`${BASE_URL}${url}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
      },
      body: JSON.stringify(name),
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

async function updateEntity(url: string, id: number, version: number) {
  try {
    const response = await fetch(`${BASE_URL}${url}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
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

async function deleteEntity(url: string, id: number, version: number) {
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

export async function getUsers() {
  const response = await fetch(`${BASE_URL}/api/users`, {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  });
  if (!response.ok) {
    console.error("Error fetching", response.status, await response.text());
    return [];
  }
  const users: User[] = await response.json();
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

export async function getUserRoles() {
  const response = await fetch(`${BASE_URL}/api/user-roles`, {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  });
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
  const response = await fetch(`${BASE_URL}/api/users`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Accept: "application/json",
    },
    body: JSON.stringify({ name }),
  });
  if (!response.ok) {
    console.error("Error posting", response.status, await response.text());
  }
}

export async function updateUser(id: number, version: number) {
  updateEntity(`/api/users/${id}`, id, version);
}

export async function deleteUser(id: number, version: number) {
  const response = await fetch(`${BASE_URL}/api/users/${id}`, {
    method: "DELETE",
    body: JSON.stringify({ id, version }),
  });
  if (!response.ok) {
    console.error("Error deleting", response.status, await response.text());
  }
}

export async function createUnit(name: string) {
  postEntity("/api/units", name);
}

export async function updateUnit(id: number, version: number) {
  const response = await fetch(`${BASE_URL}/api/units/${id}`, {
    method: "PUT",
    body: JSON.stringify({ id, version }),
  });
  if (!response.ok) {
    console.error("Error putting", response.status, await response.text());
  }
}

export async function deleteUnit(id: number, version: number) {
  const response = await fetch(`${BASE_URL}/api/units/${id}`, {
    method: "DELETE",
    body: JSON.stringify({ id, version }),
  });
  if (!response.ok) {
    console.error("Error deleting", response.status, await response.text());
  }
}

export async function createRole(name: string) {
  postEntity("/api/roles", name);
}

export async function updateRole(id: number, version: number) {
  updateEntity(`/api/roles/${id}`, id, version);
}

export async function deleteRole(id: number, version: number) {
  const response = await fetch(`${BASE_URL}/api/roles/${id}`, {
    method: "DELETE",
    body: JSON.stringify({ id, version }),
  });
  if (!response.ok) {
    console.error("Error deleting", response.status, await response.text());
  }
}

export async function createUserRole(name: string) {
  postEntity("/api/user-roles", name);
}

export async function updateUserRole(id: number, version: number) {
  updateEntity(`/api/user-roles/${id}`, id, version);
}

export async function deleteUseRole(id: number, version: number) {
  const response = await fetch(`${BASE_URL}/api/user-roles/${id}`, {
    method: "DELETE",
    body: JSON.stringify({ id, version }),
  });
  if (!response.ok) {
    console.error("Error deleting", response.status, await response.text());
  }
}
