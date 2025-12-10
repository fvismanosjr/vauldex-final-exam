import type { LoginType, RegisterType } from "@/types/types";
import { useUserStore } from "@/stores/userStore";
import { useRouter } from "vue-router";

const AUTH_API_URL = "http://localhost:8080/auth"

export const loginUser = async (auth: LoginType) => {
    const user = useUserStore();
    const router = useRouter();

    await fetch(`${AUTH_API_URL}/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(auth)
    }).then(async (response) => {
        const result = await response.json();

        if (response.ok) {
            user.setUser(result);

            router.push({
                name: "dashboard"
            })
        }
    })
}

export const logoutUser = async () => {
    const user = useUserStore();
    const router = useRouter();

    await fetch(`${AUTH_API_URL}/logout`, {
        method: "POST",
        credentials: "include",
    }).then(async (response) => {
        if (response.ok) {
            user.clearUser();

            router.push({
                name: "login"
            })
        }
    })
}

export const registerUser = async (auth: RegisterType) => {
    const router = useRouter();

    await fetch(`${AUTH_API_URL}/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(auth)
    }).then(async (response) => {
        if (response.ok) {
            router.push({
                name: "login"
            })
        }
    })
}
