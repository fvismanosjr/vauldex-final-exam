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
