<template>
  <div>
    <v-md-editor v-model="modelValue"
                 :height="props.height + 'px'"
                 :disabled-menus="[]"
                 :include-level="[1, 2, 3, 4, 5, 6]"
                 @upload-image="handleUploadImage">
    </v-md-editor>
  </div>
</template>

<script setup>
import VMdEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';
import {getCurrentInstance, ref} from 'vue';

const { proxy } = getCurrentInstance();

import hljs from 'highlight.js';
import VueCookie from "vue-cookies";
import {ElMessage} from "element-plus";
import globalRouter from "../utils/GlobalRouter.js";
import router from "../router/index.js";

VMdEditor.use(githubTheme, {
  Hljs: hljs,
});

const userId = VueCookie.get('userInfo').id;

const props = defineProps({
  height: {
    type: Number,
    default: 500,
  }
})

const modelValue = ref('');
defineExpose(modelValue)

const handleUploadImage = async (event, insertImage, files) => {
  let result = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_POST_IMAGE_UPLOAD + "/" + userId,
    params: {
      file: files[0],
    },
    methods: 'POST',
    dataType: 'file',
  });

  if (!result) {
    return;
  }
  ElMessage.success("上传成功");
  insertImage({
    url: result.url,
    desc: result.name,
  });
}

const init = () => {
  if (router.currentRoute.value.name === 'blog/edit') {
    const article = router.currentRoute.value.query.article || JSON.stringify({});
    let articleId = JSON.parse(article).articleId;
    getArticleContent(userId, articleId);
  }
  if (router.currentRoute.value.name === 'blog/put') {
    modelValue.value = '';
  }
}

const getArticleContent = async (userId, articleId) => {
  const results = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_GET_ARTICLE_CONTENT + '/' + userId + '/' + articleId,
    methods: 'GET',
  })
  if (!results) {
    await router.push(globalRouter.adminRouter.ADMIN_BLOG_LIST);
  }
  modelValue.value = results;
}

init();

</script>

<style scoped>
</style>