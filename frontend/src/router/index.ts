import { createRouter, createWebHistory } from 'vue-router'
import AuthLayout from '@/layouts/AuthLayout.vue'
import LoginForm from '@/components/LoginForm.vue'
import RegisterForm from '@/components/RegisterForm.vue'
import Dashboard from '@/pages/dashboardPage.vue'
import DefaultLayout from '@/layouts/DefaultLayout.vue'

const router = createRouter({
	history: createWebHistory(import.meta.env.BASE_URL),
	routes: [
		{
            path: "/login",
            name: "login",
            alias: "/",
            component: LoginForm,
            meta: {
                guestOnly: true,
                layout: AuthLayout
            }
        },
        {
            path: "/register",
            name: "register",
            component: RegisterForm,
            meta: {
                guestOnly: true,
                layout: AuthLayout
            }
        },
        {
            path: "/dashboard",
            name: "dashboard",
            component: Dashboard,
            meta: {
                layout: DefaultLayout
            }
        }
	],
})

export default router
