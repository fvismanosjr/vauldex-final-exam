<script setup lang="ts">
import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
} from "@/components/ui/card"

import {
    Field,
    FieldDescription,
    FieldGroup,
    FieldLabel,
} from "@/components/ui/field"

import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { registerUser } from "@/services/auth"
import type { RegisterType } from "@/types/types"
import { useRouter } from "vue-router";
import { ref } from "vue"

const router = useRouter();
const user = ref<RegisterType>({
    email: "",
    name: "",
    password: "",
    confirmPassword: "",
})

const register = async () => {
    await registerUser(user.value)
    router.push({
        name: "login"
    })
}
</script>

<template>
    <Card>
        <CardHeader>
            <CardTitle>Create an account</CardTitle>
            <CardDescription>
                Enter your information below to create your account
            </CardDescription>
        </CardHeader>
        <CardContent>
            <form>
                <FieldGroup>
                    <Field>
                        <FieldLabel for="name">
                            Full Name
                        </FieldLabel>
                        <Input v-model="user.name" id="name" type="text" placeholder="John Doe" required />
                    </Field>
                    <Field>
                        <FieldLabel for="email">
                            Email
                        </FieldLabel>
                        <Input v-model="user.email" id="email" type="email" placeholder="m@example.com" required />
                    </Field>
                    <Field>
                        <FieldLabel for="password">
                            Password
                        </FieldLabel>
                        <Input v-model="user.password" id="password" type="password" required />
                    </Field>
                    <Field>
                        <FieldLabel for="confirm-password">
                            Confirm Password
                        </FieldLabel>
                        <Input v-model="user.confirmPassword" id="confirm-password" type="password" required />
                    </Field>
                    <FieldGroup>
                        <Field>
                            <Button type="submit" @click.prevent="register">
                                Create Account
                            </Button>
                            <FieldDescription class="px-6 text-center">
                                Already have an account? <a href="/login">Sign in</a>
                            </FieldDescription>
                        </Field>
                    </FieldGroup>
                </FieldGroup>
            </form>
        </CardContent>
    </Card>
</template>
