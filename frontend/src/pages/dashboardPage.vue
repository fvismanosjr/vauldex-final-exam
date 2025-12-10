<script setup lang="ts">
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from '@/components/ui/select'

import ActivityList from '@/components/ActivityList.vue';
import ActivityListDialog from '@/components/ActivityListDialog.vue';
import { getActivityTypes } from '@/services/activity'
import type { ActivityTypeType } from '@/types/types'
import { ref } from 'vue'

const activityListKey = ref(0);
const dialogKey = ref(0);
const isDialogOpen = ref(false);
const activityTypes = ref<ActivityTypeType[]>([]);
const activityId = ref(0);

getActivityTypes().then((response) => {
    activityTypes.value = response;
})

const updateOpenDialog = (val: boolean) => {
    isDialogOpen.value = val;
    dialogKey.value++;

    if (!val) {
        activityId.value = 0;
    }
}

const reloadList = (val: boolean) => {
    if (val) {
        activityListKey.value++;
        dialogKey.value++;
    }
}

const openAndEditDialog = (val: number) => {
    isDialogOpen.value = true;
    activityId.value = val;
    dialogKey.value++;
}

</script>

<template>
    <div class="flex flex-col w-full p-5 gap-3">
        <div class="flex align-middle justify-between">
            <Select>
                <SelectTrigger>
                    <SelectValue placeholder="Select a type" />
                </SelectTrigger>
                <SelectContent>
                    <template v-for="activityType in activityTypes" :key="activityType.id">
                        <SelectItem :value="activityType.id">
                            {{ activityType.name }}
                        </SelectItem>
                    </template>
                </SelectContent>
            </Select>
            <ActivityListDialog
                :key="dialogKey"
                :is-open="isDialogOpen"
                :id="activityId"
                @update:open="updateOpenDialog"
                @reload:list="reloadList"
            />
        </div>
        <ActivityList
            :key="activityListKey"
            @dialog:edit="openAndEditDialog"
        />
    </div>
</template>
