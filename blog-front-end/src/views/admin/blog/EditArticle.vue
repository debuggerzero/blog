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
               @click="dialogVisible = onUploadArticle()">
      <el-icon class="el-icon--left"><Finished /></el-icon>提交
    </el-button>
  </div>
  <div>
    <EditorMarkdown v-model:model-value="editor.modelValue"
                    :height="editor.height"
                    @change="change"
    />
    <UploadDialog v-model:dialog-visible="dialogVisible"
                  v-model:article-info="articleInfo"
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
  modelValue: '',
})

const article = router.currentRoute.value.query.article || JSON.stringify({ articleTitle: "" }) ;
const title = ref(JSON.parse(article).articleTitle);

let dialogVisible = ref(false);

const userId = VueCookie.get('userInfo').id;

const articleInfo = reactive({
  userId: userId,
  title: title,
  content: {
    htmlContent: '',
  },
})

const change = () => {
  articleInfo.content.markdownContent = editor.modelValue;
}

const onUploadArticle = () => {
  if (title.value.length < 3 || title.value.length > 100) {
    ElMessage.warning('标题长度应在 3 - 10 之间')
    return false;
  }
  if (editor.modelValue === '') {
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