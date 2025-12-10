<script setup lang="ts">
import { ref, type HTMLAttributes } from "vue"
import { cn } from "@/lib/utils"
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
import { loginUser } from "@/services/auth"
import type { LoginType } from "@/types/types"

const props = defineProps<{
    class?: HTMLAttributes["class"]
}>()

const user = ref<LoginType>({
    email: "",
    password: "",
})

const login = async () => {
    await loginUser(user.value)
}

</script>

<template>
    <div :class="cn('flex flex-col gap-6', props.class)">
        <Card>
            <CardHeader>
                <CardTitle>Login to your account</CardTitle>
                <CardDescription>
                    Enter your email below to login to your account
                </CardDescription>
            </CardHeader>
            <CardContent>
                <form>
                    <FieldGroup>
                        <Field>
                            <FieldLabel for="email">
                                Email
                            </FieldLabel>
                            <Input v-model="user.email" id="email" type="email" placeholder="m@example.com" required />
                        </Field>
                        <Field>
                            <div class="flex items-center">
                                <FieldLabel for="password">
                                    Password
                                </FieldLabel>
                                <a tabindex="-1" href="#" class="ml-auto inline-block text-sm underline-offset-4 hover:underline">
                                    Forgot your password?
                                </a>
                            </div>
                            <Input v-model="user.password" id="password" type="password" required />
                        </Field>
                        <Field>
                            <Button type="submit" @click.prevent="login">
                                Login
                            </Button>
                            <FieldDescription class="text-center">
                                Don't have an account?
                                <a href="/register">
                                    Sign up
                                </a>
                            </FieldDescription>
                        </Field>
                    </FieldGroup>
                </form>
            </CardContent>
        </Card>
    </div>
</template>
