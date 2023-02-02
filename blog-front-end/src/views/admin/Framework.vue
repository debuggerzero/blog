<template>
  <div class="framework-layout">
    <el-container>
      <el-header class="framework-header">
        <div class="logo">〇の博客</div>
        <div class="user-info">
          <el-avatar v-if="userInfo.avatar == 'default.png'">
            <el-icon>
              <UserFilled/>
            </el-icon>
          </el-avatar>
          <el-avatar v-else :src="userInfo.avatar"/>
        </div>
      </el-header>
      <el-container class="framework-container">
        <el-aside width="200px" class="left-aside">
          <div>
            <el-button class="post-btn" type="primary" @click="putArticle" round>发布</el-button>
          </div>
          <div class="menu">
            <el-menu
                router
                :default-active="$route.path"
                :unique-opened="true"
            >
              <el-sub-menu :index="item.id" v-for="item in menuList">
                <template #title>
                  <el-icon v-html="item.icon"></el-icon>
                  <span>{{ item.title }}</span>
                </template>
                <el-menu-item :index="subMenu.path" v-for="subMenu in item.children">
                  <span>
                    {{ subMenu.title }}
                  </span>
                </el-menu-item>
              </el-sub-menu>
            </el-menu>
          </div>
        </el-aside>
        <el-main class="right-main">
          <router-view/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script setup>

import {reactive, ref} from 'vue'
import globalRouter from "../../utils/GlobalRouter.js";
import router from "../../router/index.js";
import VueCookie from "vue-cookies"
import { ElLoading } from 'element-plus'

const userInfo = reactive(VueCookie.get('userInfo') || {})

const menuList = ref([
  {
    id: '1',
    title: '管理',
    icon: '<svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-029747aa=""><path fill="currentColor" d="M576 128v288l96-96 96 96V128h128v768H320V128h256zm-448 0h128v768H128V128z"></path></svg>',
    children: [
      {
        title: '内容管理',
        path: globalRouter.adminRouter.ADMIN_BLOG_LIST,
      },
    ],
  },
  {
    id: '2',
    title: '回收站',
    icon: '<svg viewBox="0 0 1024 1024" xmlns="http://www.w3.org/2000/svg" data-v-029747aa=""><path fill="currentColor" d="M160 256H96a32 32 0 0 1 0-64h256V95.936a32 32 0 0 1 32-32h256a32 32 0 0 1 32 32V192h256a32 32 0 1 1 0 64h-64v672a32 32 0 0 1-32 32H192a32 32 0 0 1-32-32V256zm448-64v-64H416v64h192zM224 896h576V256H224v640zm192-128a32 32 0 0 1-32-32V416a32 32 0 0 1 64 0v320a32 32 0 0 1-32 32zm192 0a32 32 0 0 1-32-32V416a32 32 0 0 1 64 0v320a32 32 0 0 1-32 32z"></path></svg>',
    open: true,
    children: [
      {
        title: '回收站',
        path: globalRouter.adminRouter.ADMIN_BLOG_TRASHCAN,
      },
    ],
  },
])

const redirectView = (path) => {
  router.push(path);
}

const putArticle = () => {
  const loading = ElLoading.service({
    lock: true,
    text: 'Loading',
    background: 'rgba(0, 0, 0, 0.7)',
  })
  setTimeout(() => {
    loading.close()
    router.push(globalRouter.adminRouter.ADMIN_BLOG_PUT);
  }, 500)
}

</script>

<style scoped>
.el-menu {
  border-right: 0;
}

.framework-layout {

}

.framework-layout .framework-header {
  border-bottom: 1px solid #ddd;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.logo {
  font-size: 30px;
}

.framework-layout .framework-container {
  padding-top: 10px;
  background: #f5f6f7;
  height: calc(100vh - 60px);
}

.framework-container .left-aside {
  padding: 0 15px;
  width: 250px;
}

.left-aside .post-btn {
  color: #fff;
  height: 40px;
  width: 100%;
}

.left-aside .menu {
  margin-top: 15px;
}

.framework-container .right-main {
  background: #fff;
}

</style>