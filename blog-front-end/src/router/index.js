import { createRouter, createWebHistory } from 'vue-router'
import globalRouter from "../utils/GlobalRouter.js";
import Login from "../views/admin/Login.vue";
import Framework from "../views/admin/Framework.vue";
import VueCookie from "vue-cookies";

const routes = [
    {
        name: 'index',
        path: globalRouter.clientRouter.CLIENT_INDEX,
        component: Login
    },
    {
        name: 'login',
        path: globalRouter.adminRouter.ADMIN_LOGIN,
        component: Login
    },
    {
        name: 'home',
        path: globalRouter.adminRouter.ADMIN_HOME,
        component: Framework
    }
];

//这里不是通过new来创建，通过createRouter 方法创建，使用的模式不是通过mode来定义
const router = createRouter({
    routes,
    history: createWebHistory(),
})

router.beforeEach((to, from, next) => {
    if (!VueCookie.isKey("userInfo") && to.path !== globalRouter.adminRouter.ADMIN_LOGIN) {
        router.push(globalRouter.adminRouter.ADMIN_LOGIN);
    }
    next();
})

export default router