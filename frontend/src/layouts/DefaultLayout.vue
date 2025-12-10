<script setup lang="ts">
import AppSidebar from '@/components/AppSidebar.vue'
import { SidebarProvider, SidebarTrigger } from '@/components/ui/sidebar'
</script>

<template>
    <SidebarProvider>
        <AppSidebar />
        <main class="w-full">
            <SidebarTrigger class="p-5"/>
            <slot />
        </main>
    </SidebarProvider>
</template>
