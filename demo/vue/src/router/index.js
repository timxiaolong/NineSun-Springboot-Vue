import { createRouter, createWebHistory } from 'vue-router';

import index from '@/pages/users/index.vue'
import login from "@/pages/users/login.vue";

const routes = [
    {
        path:"/",
        name:"index",
        component:index
    },
    {
        path: '/login',
        name: 'login',
        component: login
    }
];
const router = createRouter({
    history:createWebHistory(),
    routes,
})
export default router;
