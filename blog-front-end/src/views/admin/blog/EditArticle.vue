<template>
  <div class="input-upload-header">
    <el-input v-model="title"
              placeholder="请输入文章标题"
              size="large"
              class="title-input"
              clearable>
    </el-input>
    <el-button type="primary"
               class="upload-button"
               @click="dialogVisible = onUploadArticle(markdownContent)">
      <el-icon class="el-icon--left"><Finished /></el-icon>提交
    </el-button>
  </div>
  <div>
    <EditorMarkdown ref="markdownContent"
                    :height="editor.height"
    />
    <UploadDialog v-model:dialog-visible="dialogVisible"
                  :article-info="articleInfo"
    />
  </div>
</template>

<script setup>
import EditorMarkdown from "../../../components/EditorMarkdown.vue";
import {getCurrentInstance, reactive, ref} from "vue";
import UploadDialog from "../../../components/UploadDialog.vue";

import {ElMessage} from "element-plus";
import VueCookie from "vue-cookies";
import router from "../../../router/index.js";

const {proxy} = getCurrentInstance();

let editor = reactive({
  height: window.innerHeight - 168,
})

const article = router.currentRoute.value.query.article || JSON.stringify({ articleTitle: "" , comment: ""}) ;
const title = ref(JSON.parse(article).articleTitle);

let dialogVisible = ref(false);

const userId = VueCookie.get('userInfo').id;

const markdownContent = ref();
const articleInfo = reactive({
  userId: userId,
  title: title,
  comment: JSON.parse(article).comment,
  url: JSON.parse(article).articleImage,
  content: {
    markdownContent: '',
  },
})

const onUploadArticle = (markContent) => {
  articleInfo.content.markdownContent = markContent;
  if (title.value.length < 3 || title.value.length > 100) {
    ElMessage.warning('标题长度应在 3 - 10 之间')
    return false;
  }
  if (!articleInfo.content.markdownContent) {
    ElMessage.warning('文章内容不能为空');
    return false;
  }
  return true;
}

</script>

<style scoped>
.input-upload-header {
  display: flex;
  align-items: center;
  padding-bottom: 15px;
}

.title-input {
  padding-right: 5px;
  width: 85%;
}

.upload-button {
  width: 15%;
  height: 38px;
}
</style>