import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserType } from '@/types/types'

export const useUserStore = defineStore('user', () => {
    const user = ref<UserType | null>(null)

    // Load from localStorage on init
    const loadUser = () => {
        const saved = localStorage.getItem('user')
        if (saved) {
            user.value = JSON.parse(saved)
        }
    }

    const setUser = (userData: UserType) => {
        user.value = userData
        localStorage.setItem('user', JSON.stringify(userData))
    }

    const clearUser = () => {
        user.value = null
        localStorage.removeItem('user')
    }

    return { user, loadUser, setUser, clearUser }
})
