<template>
  <el-dialog
      v-model="props.dialogVisible"
      title="发布文章"
      width="40%"
      align-center
      :before-close="close"
  >
    <div>
      <el-form :model="form" label-width="120px">
        <el-form-item label="标题:">
          <span>{{ props.articleInfo.title }}</span>
        </el-form-item>
        <el-form-item label="封面:">
          <el-radio-group v-model="form.resource" @change="change">
            <el-radio label="无封面"/>
            <el-radio label="单图"/>
          </el-radio-group>
        </el-form-item>
        <el-form-item>
          <el-upload
              class="uploader"
              action="#"
              :show-file-list="false"
              :http-request="handleUploadImage"
              :before-upload="beforeAvatarUpload"
              v-if="form.resource === '单图'"
          >
            <el-image v-if="imageUrl && imageUrl !== DEFAULT_IMAGE" :src="imageUrl" fit="scale-down" class="image"/>
            <el-icon v-else class="avatar-uploader-icon">
              <Plus/>
            </el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item label="摘要:">
          <el-input
              v-model="form.comment"
              :autosize="{ minRows: 2, maxRows: 6 }"
              type="textarea"
              placeholder="Please input"
          />
        </el-form-item>
        <el-form-item>
          <span class="dialog-footer">
            <el-button type="primary" @click="onSubmit">
              确定
            </el-button>
            <el-button @click="close">
              取消
            </el-button>
          </span>
        </el-form-item>
      </el-form>
    </div>
  </el-dialog>
</template>

<script setup>

import {getCurrentInstance, reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import globalRouter from "../utils/GlobalRouter.js";
import router from "../router/index.js";

const {proxy} = getCurrentInstance();

const props = defineProps({
  dialogVisible: Boolean,
  articleInfo: {
    type: Object,
    default: {
      userId: '',
      title: '',
      comment: '',
      url: '',
      content: {
        markdownContent: '',
      },
    }
  },
})

const form = reactive({
  resource: props.articleInfo.url === '' ? '无封面' : '单图',
  comment: props.articleInfo.comment,
})

const DEFAULT_IMAGE = 'assets/default.jpg';
let imageUrl = ref(props.articleInfo.url === '' ? DEFAULT_IMAGE : props.articleInfo.url);
let tempUrl = imageUrl;

const emit = defineEmits();
const close = () => {
  emit('update:dialogVisible', false);
  imageUrl = tempUrl = props.articleInfo.url === '' ? DEFAULT_IMAGE : props.articleInfo.url;
}

const change = (label) => {
  if (label === '无封面') {
    tempUrl = imageUrl.value;
    imageUrl.value = DEFAULT_IMAGE;
  }
  else if (label === '单图') {
    imageUrl.value = tempUrl;
  }
}

const handleUploadImage = async (content) => {
  let result = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_POST_IMAGE_UPLOAD + "/" + props.articleInfo.userId,
    params: {
      file: content.file,
    },
    methods: 'POST',
    dataType: 'file',
  });
  if (!result) {
    return;
  }
  imageUrl.value = result.url;
  ElMessage.success("上传成功");
}

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg' && rawFile.type !== 'image/png') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error('Avatar picture size can not exceed 2MB!')
    return false
  }
  return true
}

const onSubmit = () => {
  const name = router.currentRoute.value.name;
  if (name === 'blog/put') {
    putArticle();
  }
  else if (name === 'blog/edit') {
    editArticle();
  }
}

const putArticle = async () => {
  let articleId = crypto.randomUUID().replaceAll("-", '');
  let result = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_POST_ARTICLE_PUT,
    params: {
      userId: props.articleInfo.userId,
      articleId: articleId,
      articleTitle: props.articleInfo.title,
      articleContent: props.articleInfo.content.markdownContent,
      comment: form.comment,
      articleImage: imageUrl.value
    },
    methods: 'POST',
  });
  if (!result) return;
  ElMessage.success("上传成功");
  close();
  await router.push(globalRouter.adminRouter.ADMIN_BLOG_LIST);
}

const editArticle = async () => {
  const str = router.currentRoute.value.query.article || JSON.stringify({});
  const article = JSON.parse(str);
  let result = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_PUT_ARTICLE_UPDATE,
    params: {
      userId: props.articleInfo.userId,
      id: article.id,
      articleId: article.articleId,
      articleTitle: props.articleInfo.title,
      articleContent: props.articleInfo.content.markdownContent,
      articleImage: imageUrl.value,
      comment: form.comment,
      createTime: article.createTime
    },
    methods: 'PUT',
  });
  if (!result) return;
  ElMessage.success("更新成功");
  close();
  await router.push(globalRouter.adminRouter.ADMIN_BLOG_LIST);
}

</script>

<style scoped>

.uploader .image {
  width: 178px;
  height: 178px;
  display: block;
}

</style>

<style>
.uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.el-textarea .el-textarea__inner::-webkit-scrollbar {
  width: 7px;
  height: 7px;
}

.el-textarea .el-textarea__inner::-webkit-scrollbar-track {
  background-color: #ffffff;
}

.el-textarea .el-textarea__inner::-webkit-scrollbar-thumb {
  background-color: rgba(144, 147, 153, 0.3);
  border-radius: 5px;
  border: 1px solid #f1f1f1;
  box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
}

.el-textarea .el-textarea__inner::-webkit-scrollbar-thumb:hover {
  background-color: rgba(144, 147, 153, 0.3);
}

.el-textarea .el-textarea__inner::-webkit-scrollbar-thumb:active {
  background-color: rgba(144, 147, 153, 0.3);
}

.el-textarea .el-textarea__inner::-webkit-scrollbar-corner {
  background-color: #ffffff;
}
</style>