const globalRouter = {
    adminRouter: {
        ADMIN_LOGIN: "/admin/login",
        ADMIN_HOME: '/admin/',
        ADMIN_BLOG_LIST: '/admin/blog/list',
        ADMIN_BLOG_PUT: '/admin/blog/put',
        ADMIN_BLOG_EDIT: '/admin/blog/edit',
        ADMIN_BLOG_TRASHCAN: '/admin/blog/trashcan',
    },
    clientRouter: {
        CLIENT_INDEX: '/',
    },
    // 后端路由
    backEndRouter: {
        BACK_END_LOGIN_PASSWORD: '/login/password',
        BACK_END_GET_TOTAL_ARTICLE_COUNT: '/articles/total',
        BACK_END_POST_ARTICLE_PUT: '/articles/put',
        BACK_END_POST_ARTICLE_QUERY: '/articles/query',
        BACK_END_POST_IMAGE_UPLOAD: "/file/upload/image",
        BACK_END_DELETE_ARTICLE: "/articles/delete",
        BACK_END_GET_ARTICLE_CONTENT: '/articles/content',
        BACK_END_PUT_ARTICLE_UPDATE: '/articles/update',
        BACK_END_DELETE_ARTICLE_COMPLETE: '/articles/trashcan/delete',
        BACK_END_PUT_ARTICLE_RESTORE: '/articles/trashcan/restore'
    },
}

export default globalRouter;