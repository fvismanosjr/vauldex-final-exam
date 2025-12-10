<script setup lang="ts">
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogFooter,
    DialogTitle,
    DialogTrigger,
    DialogClose,
} from '@/components/ui/dialog'

import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue,
} from '@/components/ui/select'

import { Button } from '@/components/ui/button'
import { Label } from '@/components/ui/label'
import { Textarea } from '@/components/ui/textarea'
import { getActivityTypes, saveActivity, findActivity, updateActivity } from '@/services/activity'
import type { ActivityTypeType, ActivityRequestType, ActivityType } from '@/types/types'
import { ref } from 'vue'

const props = defineProps<{
    isOpen: boolean,
    id: number,
}>()

const emit = defineEmits<{
    (e: "update:open", value: boolean): void,
    (e: "reload:list", value: boolean): void,
}>();

const activityRequest = ref<ActivityRequestType>({
    activityTypeId: 0,
    description: "",
});

const activityTypes = ref<ActivityTypeType[]>([]);

if (props.isOpen) {
    getActivityTypes().then((response) => {
        activityTypes.value = response;
    })
}

if (props.id) {
    findActivity(props.id).then((response: ActivityType) => {
        activityRequest.value = {
            activityTypeId: response.type.id,
            description: response.description,
        }
    })
}

const saveActivityEvent = async () => {

    if (props.id) {
        await updateActivity(props.id, activityRequest.value);
    } else {
        await saveActivity(activityRequest.value);
    }

    emit("update:open", false);
    emit("reload:list", true);
}

const updateOpen = (val: boolean) => {
    emit("update:open", val)
}

</script>

<template>
    <Dialog :open="isOpen" @update:open="updateOpen">
        <DialogTrigger as-child>
            <Button size="sm">
                New Activity
            </Button>
        </DialogTrigger>
        <DialogContent>
            <DialogHeader>
                <DialogTitle>Activity</DialogTitle>
                <DialogDescription>Do something in here</DialogDescription>
            </DialogHeader>

            <div class="grid gap-4">
                <div class="grid gap-3">
                    <Label for="activity-type">Activity Type</Label>
                    <Select v-model="activityRequest.activityTypeId" id="activity-type" class="w-full">
                        <SelectTrigger class="w-full">
                            <SelectValue placeholder="Select a type" />
                        </SelectTrigger>
                        <SelectContent class="w-full">
                            <template v-for="activityType in activityTypes" :key="activityType.id">
                                <SelectItem :value="activityType.id">
                                    {{ activityType.name }}
                                </SelectItem>
                            </template>
                        </SelectContent>
                    </Select>
                </div>
                <div class="grid gap-3">
                    <Label for="description">Description</Label>
                    <Textarea v-model="activityRequest.description" placeholder="Type your something here." />
                </div>
            </div>
            <DialogFooter>
                <DialogClose as-child>
                    <Button variant="outline">
                        Cancel
                    </Button>
                </DialogClose>
                <Button type="submit" @click.prevent="saveActivityEvent">
                    Save changes
                </Button>
            </DialogFooter>
        </DialogContent>
    </Dialog>
</template>
