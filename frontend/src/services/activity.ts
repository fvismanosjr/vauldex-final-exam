import type { ActivityRequestType } from "@/types/types";
import { useUserStore } from "@/stores/userStore";

const USER_API_URL = "http://localhost:8080/users"

export const getActivityTypes = async () => {
    return await fetch("http://localhost:8080/activity-types", {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    })
}

export const getActivities = async () => {
    const user = useUserStore();
    user.loadUser()

    return await fetch(`${USER_API_URL}/${user.user.id}/activities`, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    })
}

export const saveActivity = async (payload: ActivityRequestType) => {
    const user = useUserStore();
    user.loadUser()

    return await fetch(`${USER_API_URL}/${user.user.id}/activities`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload)
    }).then(async (response) => {
        return await response.json();
    })
}

export const findActivity = async (id: number) => {
    const user = useUserStore();
    user.loadUser()

    return await fetch(`${USER_API_URL}/${user.user.id}/activities/${id}`, {
        method: "GET",
        credentials: "include",
    }).then(async (response) => {
        return await response.json();
    });
}

export const updateActivity = async (id:number, payload: ActivityRequestType) => {
    const user = useUserStore();
    user.loadUser()

    return await fetch(`${USER_API_URL}/${user.user.id}/activities/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(payload)
    }).then(async (response) => {
        return await response.json();
    })
}

export const deleteActivity = async (id:number) => {
    const user = useUserStore();
    user.loadUser()

    return await fetch(`${USER_API_URL}/${user.user.id}/activities/${id}`, {
        method: "DELETE",
        credentials: "include",
    })
}
