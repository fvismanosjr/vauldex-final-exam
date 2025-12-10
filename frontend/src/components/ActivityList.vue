<script setup lang="ts">
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from '@/components/ui/table'

import { Button } from '@/components/ui/button'
import ActivityListAlertDialog from './ActivityListAlertDialog.vue';
import { Pencil } from 'lucide-vue-next';
import { getActivities } from '@/services/activity';
import type { ActivityType, FilterActivityType } from '@/types/types';
import { ref } from 'vue';

const emit = defineEmits<{
    (e: "dialog:edit", value: number): void,
}>();

const props = defineProps<{
    filter: FilterActivityType
}>();

const activities = ref<ActivityType[]>([]);

getActivities(props.filter).then((result) => {
    activities.value = result.content as ActivityType[];
})

const reloadList = async (val: boolean) => {
    if (val) {
        await getActivities(props.filter).then((result) => {
            activities.value = result.content as ActivityType[];
        })
    }
}
</script>

<template>
    <Table>
        <TableHeader>
            <TableRow>
                <TableHead>
                    Activity Type
                </TableHead>
                <TableHead>Description</TableHead>
                <TableHead>Created At</TableHead>
                <TableHead class="w-[100px]"></TableHead>
            </TableRow>
        </TableHeader>
        <TableBody>
            <template v-if="activities.length">
                <TableRow v-for="activity in activities" :key="activity.id">
                    <TableCell>{{ activity.type.name }}</TableCell>
                    <TableCell>{{ activity.description }}</TableCell>
                    <TableCell>{{ activity.createdAt }}</TableCell>
                    <TableCell class="flex gap-1">
                        <Button size="icon-sm" variant="secondary" @click.prevent="emit('dialog:edit', activity.id)">
                            <Pencil />
                        </Button>
                        <ActivityListAlertDialog :id="activity.id" @reload:list="reloadList"/>
                    </TableCell>
                </TableRow>
            </template>
            <template v-else>
                <TableRow>
                    <TableCell colspan="4" class="text-center">No result found</TableCell>
                </TableRow>
            </template>
        </TableBody>
    </Table>
</template>
