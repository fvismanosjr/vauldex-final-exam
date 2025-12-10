import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserType } from '@/types/types'

export const useUserStore = defineStore('user', () => {
    const user = ref<UserType>({
        id: 0,
        name: "",
        email: ""
    })

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
        user.value = {
            id: 0,
            name: "",
            email: ""
        }
        
        localStorage.removeItem('user')
    }

    return { user, loadUser, setUser, clearUser }
})
