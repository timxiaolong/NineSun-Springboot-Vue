import { createRouter, createWebHistory } from 'vue-router';

import index from '@/pages/users/index.vue'
import login from "@/pages/users/login.vue";
import main from "@/pages/admin/main.vue";
import cart from "@/pages/users/cart.vue";
import userinfo from "@/pages/users/userinfo.vue";
import detial from "@/pages/users/detial.vue";
import signup from "@/pages/users/signup.vue";
import FAQ from "@/pages/users/FAQ.vue";
import types from "@/pages/users/types.vue";

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
    },
    {
        path:'/admin',
        name: 'main',
        component: main
    },
    {
        path: '/cart',
        name: 'cart',
        component: cart
    },
    {
        path: '/userinfo',
        name: 'userinfo',
        component: userinfo
    },
    {
        path: '/detial',
        name: 'detial',
        component: detial
    },
    {
        path: '/signup',
        name: 'signup',
        component: signup
    },
    {
        path: '/faq',
        name: 'faq',
        component: FAQ
    },
    {
        path: '/types',
        name: 'types',
        component: types
    }

];
const router = createRouter({
    history:createWebHistory(),
    routes,
})
export default router;
