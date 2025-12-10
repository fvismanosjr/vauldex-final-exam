import type { LoginType, RegisterType } from "@/types/types";
import { useUserStore } from "@/stores/userStore";

const AUTH_API_URL = "http://localhost:8080/auth"

export const isAuthenticated = async () => {
    try {
        const response = await fetch(`${AUTH_API_URL}/me`, {
            method: "GET",
            credentials: "include",
        });

        return response.ok;
    } catch (error) {
        console.error("Error checking authentication:", error);
        return false;
    }
}

export const loginUser = async (auth: LoginType) => {
    const user = useUserStore();

    await fetch(`${AUTH_API_URL}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(auth)
    }).then(async (response) => {
        const result = await response.json();

        if (response.ok) {
            user.setUser(result);
        }
    })
}

export const logoutUser = async () => {
    const user = useUserStore();

    await fetch(`${AUTH_API_URL}/logout`, {
        method: "POST",
        credentials: "include",
    }).then(async (response) => {
        if (response.ok) {
            user.clearUser();
        }
    })
}

export const registerUser = async (auth: RegisterType) => {
    await fetch(`${AUTH_API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(auth)
    })
}
