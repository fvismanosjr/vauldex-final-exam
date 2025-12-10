<script setup lang="ts">
import {
    Table,
    TableBody,
    TableCell,
    TableHead,
    TableHeader,
    TableRow,
} from '@/components/ui/table'

import {
    Pagination,
    PaginationContent,
    PaginationEllipsis,
    PaginationItem,
    PaginationNext,
    PaginationPrevious,
} from '@/components/ui/pagination'

import { Button } from '@/components/ui/button'
import ActivityListAlertDialog from './ActivityListAlertDialog.vue';
import { Pencil } from 'lucide-vue-next';
import { getActivities } from '@/services/activity';
import type { ActivityType, FilterActivityType, PaginationType } from '@/types/types';
import { DateFormatter } from '@internationalized/date'
import { ref } from 'vue';

const emit = defineEmits<{
    (e: "dialog:edit", value: number): void,
}>();

const props = defineProps<{
    filter: FilterActivityType
}>();

const pagination = ref<PaginationType>({
    totalElements: 0,
    size: 10,
    page: 0,
});

const activities = ref<ActivityType[]>([]);

getActivities(props.filter, pagination.value).then((result) => {
    activities.value = result.content as ActivityType[];
    pagination.value.totalElements = result.totalElements;
    pagination.value.size = result.size;
})

const reloadList = async (val: boolean) => {
    if (val) {
        await getActivities(props.filter, pagination.value).then((result) => {
            activities.value = result.content as ActivityType[];
        })
    }
}

const formatDateFromIso = (iso: string): string => {
    const jsDate = new Date(iso.slice(0, 23)) // truncate microseconds
    const df = new DateFormatter('en-US', { dateStyle: 'long' })
    return df.format(jsDate)
}

const paginate = (page: number) => {
    pagination.value.page = page - 1;
    reloadList(true);
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
                    <TableCell>{{ formatDateFromIso(activity.createdAt) }}</TableCell>
                    <TableCell class="flex gap-1">
                        <Button size="icon-sm" variant="secondary" @click.prevent="emit('dialog:edit', activity.id)">
                            <Pencil />
                        </Button>
                        <ActivityListAlertDialog :id="activity.id" @reload:list="reloadList" />
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
    <div class="flex flex-col gap-6">
        <Pagination
            v-if="activities.length"
            class="justify-end"
            v-slot="{ page }"
            :items-per-page="pagination.size"
            :total="pagination.totalElements"
            :default-page="1"
        >
            <PaginationContent v-slot="{ items }">
                <PaginationPrevious @click.prevent="paginate(page - 1)"/>
                <template v-for="(item, index) in items" :key="index">
                    <PaginationItem
                        v-if="item.type === 'page'"
                        :value="item.value"
                        :is-active="item.value === page"
                        @click.prevent="paginate(item.value)"
                    >
                        {{ item.value }}
                    </PaginationItem>
                </template>
                <PaginationEllipsis :index="4" />
                <PaginationNext @click.prevent="paginate(page + 1)"/>
            </PaginationContent>
        </Pagination>
    </div>
</template>
