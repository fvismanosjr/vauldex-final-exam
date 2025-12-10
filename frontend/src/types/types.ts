export interface LoginType {
    email: string,
    password: string
}

export interface RegisterType {
    email: string,
    name: string,
    password: string,
    confirmPassword: string,
}

export interface UserType {
    id: number,
    name: string,
    email: string,
}

export interface ActivityRequestType {
    activityTypeId: number,
    description: string,
}

export interface ActivityTypeType {
    id: number,
    name: string,
}

export interface ActivityType {
    id: number,
    user: UserType,
    type: ActivityTypeType,
    description: string,
    createdAt: string,
}
