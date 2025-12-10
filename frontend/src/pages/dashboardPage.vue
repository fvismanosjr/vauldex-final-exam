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
import type { ActivityTypeType, FilterActivityType } from '@/types/types'
import { ref, watch } from 'vue'
import type { DateValue } from '@internationalized/date'
import { DateFormatter, getLocalTimeZone, today } from '@internationalized/date'
import { CalendarIcon } from 'lucide-vue-next'
import { cn } from '@/lib/utils'
import { Button } from '@/components/ui/button'
import { Calendar } from '@/components/ui/calendar'
import {
    Popover,
    PopoverContent,
    PopoverTrigger,
} from '@/components/ui/popover'

const defaultPlaceholder = today(getLocalTimeZone())
const date = ref<DateValue>()
const df = new DateFormatter('en-US', {
    dateStyle: 'long',
})

const filter = ref<FilterActivityType>({
    activityTypeId: 0,
    createdAt: "",
})

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

watch(date, (newval) => {
    filter.value.createdAt = newval ? newval.toString() : ""
})

watch(filter, () => {
    activityListKey.value++;
}, { deep: true })

</script>

<template>
    <div class="flex flex-col w-full p-5 gap-3">
        <div class="flex flex-col md:flex-row align-middle justify-between">
            <div class="flex align-middle gap-3">
                <Select v-model="filter.activityTypeId">
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
                <Popover v-slot="{ close }">
                    <PopoverTrigger as-child>
                        <Button variant="outline"
                            :class="cn('ustify-start text-left font-normal', !date && 'text-muted-foreground')">
                            <CalendarIcon />
                            {{ date ? df.format(date.toDate(getLocalTimeZone())) : "Pick a date" }}
                        </Button>
                    </PopoverTrigger>
                    <PopoverContent class="w-auto p-0" align="start">
                        <Calendar v-model="date" :default-placeholder="defaultPlaceholder" layout="month-and-year"
                            initial-focus @update:model-value="close" />
                    </PopoverContent>
                </Popover>
            </div>
            <ActivityListDialog :key="dialogKey" :is-open="isDialogOpen" :id="activityId"
                @update:open="updateOpenDialog" @reload:list="reloadList" />
        </div>
        <ActivityList :key="activityListKey" :filter="filter" @dialog:edit="openAndEditDialog" />
    </div>
</template>
