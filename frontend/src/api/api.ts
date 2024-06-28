import { User } from "../types/user";
import { BASE_URL } from "../utils/baseurl";

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
  const response = await fetch(`${BASE_URL}/api/users/${id}`, {
    method: "PUT",
    body: JSON.stringify({ id, version }),
  });
  if (!response.ok) {
    console.error("Error putting", response.status, await response.text());
  }
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
