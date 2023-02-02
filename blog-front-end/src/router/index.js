import {createRouter, createWebHistory} from 'vue-router'
import globalRouter from "../utils/GlobalRouter.js";
import VueCookie from "vue-cookies";

const routes = [
    {
        name: 'index',
        path: globalRouter.clientRouter.CLIENT_INDEX,
        component: () => import('../views/admin/Login.vue'),
    },
    {
        name: 'login',
        path: globalRouter.adminRouter.ADMIN_LOGIN,
        component: () => import('../views/admin/Login.vue'),
    },
    {
        name: 'home',
        path: globalRouter.adminRouter.ADMIN_HOME,
        component: () => import('../views/admin/Framework.vue'),
        redirect: globalRouter.adminRouter.ADMIN_BLOG_LIST,
        children: [
            {
                path: globalRouter.adminRouter.ADMIN_BLOG_PUT,
                name: 'blog/put',
                component: () => import('../views/admin/blog/EditArticle.vue')
            },
            {
                path: globalRouter.adminRouter.ADMIN_BLOG_LIST,
                name: 'blog/list',
                component: () => import('../views/admin/blog/BlogList.vue'),
            },
            {
                path: globalRouter.adminRouter.ADMIN_BLOG_TRASHCAN,
                name: 'blog/trashcan',
                component: () => import('../views/admin/blog/BlogList.vue'),
            },
            {
                path: globalRouter.adminRouter.ADMIN_BLOG_EDIT,
                name: 'blog/edit',
                component: () => import('../views/admin/blog/EditArticle.vue')
            }
        ],
    },
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