import type { ActivityRequestType, FilterActivityType } from "@/types/types";
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

export const getActivities = async (filter: FilterActivityType) => {
    const user = useUserStore();
    user.loadUser()

    let endpointUrl = `${USER_API_URL}/${user.user.id}/activities`;

    if (filter) {
        const filterToString = Object.entries(filter)
            .filter(([, value]) => value !== null && value !== undefined && value !== "" && value !== 0)
            .map(([key, value]) => `${key}=${value}`)
            .join("and")

        endpointUrl += `?filters=${filterToString}`
    }

    return await fetch(endpointUrl, {
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
