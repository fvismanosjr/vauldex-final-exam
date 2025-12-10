<script setup lang="ts">
import {
    AlertDialog,
    AlertDialogAction,
    AlertDialogCancel,
    AlertDialogContent,
    AlertDialogDescription,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogTitle,
    AlertDialogTrigger,
} from '@/components/ui/alert-dialog'

import { Button } from '@/components/ui/button'
import { X } from 'lucide-vue-next';
import { deleteActivity } from '@/services/activity';

const props = defineProps<{
    id: number,
}>()

const emit = defineEmits<{
    (e: "reload:list", value: boolean): void,
}>();

const deleteActivityEvent = async () => {
    await deleteActivity(props.id)
    emit('reload:list', true);
}

</script>

<template>
    <AlertDialog>
        <AlertDialogTrigger as-child>
            <Button size="icon-sm" variant="secondary">
                <X />
            </Button>
        </AlertDialogTrigger>
        <AlertDialogContent>
            <AlertDialogHeader>
                <AlertDialogTitle>Are you absolutely sure?</AlertDialogTitle>
                <AlertDialogDescription>
                    This action cannot be undone. This will permanently delete your account
                    and remove your data from our servers.
                </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
                <AlertDialogCancel>Cancel</AlertDialogCancel>
                <AlertDialogAction @click.prevent="deleteActivityEvent">Continue</AlertDialogAction>
            </AlertDialogFooter>
        </AlertDialogContent>
    </AlertDialog>
</template>
