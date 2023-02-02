<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="浏览文章"
      width="50%"
      align-center
      :before-close="close"
      :destroy-on-close="true"
      :open-delay="500"
      @open="init"
  >
    <el-scrollbar height="calc(100vh - 200px)">
      <v-md-preview :text="articleContent" />
    </el-scrollbar>
  </el-dialog>
</template>

<script setup>

import VMdPreview from '@kangc/v-md-editor/lib/preview';
import '@kangc/v-md-editor/lib/style/preview.css';
import githubTheme from '@kangc/v-md-editor/lib/theme/github.js';
import '@kangc/v-md-editor/lib/theme/style/github.css';

import hljs from 'highlight.js';
import globalRouter from "../utils/GlobalRouter.js";
import {ElMessage} from "element-plus";
import {getCurrentInstance, ref} from "vue";

const {proxy} = getCurrentInstance();

VMdPreview.use(githubTheme, {
  Hljs: hljs,
});

const props = defineProps({
  dialogVisible: Boolean,
  query: {
    type: Object,
    default: {
      userId: '',
      articleId: '',
    }
  }
})

const emit = defineEmits();
const close = () => {
  emit('update:dialogVisible', false);
}

let articleContent = ref('')

const getArticleContent = async (userId, articleId) => {
  const results = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_GET_ARTICLE_CONTENT + '/' + userId + '/' + articleId,
    methods: 'GET',
  })
  if (!results) {
    emit('update:dialogVisible', false);
    ElMessage.error('获取失败');
    return;
  }
  articleContent.value = results;
}

const init = () => {
  getArticleContent(props.query.userId, props.query.articleId);
}

</script>

<style scoped>

</style>