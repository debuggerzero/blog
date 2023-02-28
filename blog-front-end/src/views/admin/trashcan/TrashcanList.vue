<template>
  <div style="width: 100%">
    <el-empty description="No Data" v-if="articleList.length === 0" />
    <el-table
        :data="articleList"
        style="width: 100%; height: calc(100vh - 155px);"
        v-else
    >
      <el-table-column prop="articleImage" label="封面" width="130" align="left">
        <template #default="scope">
          <div class="cover">
            <el-image style="width: 90px; height: 60px"
                      :src="scope.row.articleImage"
                      fit='scale-down'
                      lazy/>
          </div>
        </template>
      </el-table-column>
      <el-table-column prop="id" label="id" width="100" align="left"/>
      <el-table-column label="文章信息" width="500" align="left">
        <template #default="scope">
          <div>
            <div>
              名称: &nbsp {{ scope.row.articleTitle }}
              <br>
              描述: &nbsp {{ scope.row.comment }}
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="时间" align="left">
        <template #default="scope">
          <div>
            <div class="create-time">
              创建时间: &nbsp&nbsp {{ scope.row.createTime }}
              <br>
              更新时间: &nbsp&nbsp {{ scope.row.updateTime }}
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="操作" fixed="right" width="400">
        <template #default="scope">
          <el-button-group>
            <el-button type="primary" plain @click="openRestoreMessageBox(scope.row.id)">
              <el-icon class="el-icon--left">
                <Edit/>
              </el-icon>
              还原
            </el-button>
            <el-button type="primary" plain @click="openDeleteMessageBox(scope.row.articleId)">
              <el-icon class="el-icon--left">
                <Delete/>
              </el-icon>
              彻底删除
            </el-button>
            <el-button type="primary" plain @click="dialogVisible = openBrowseDialog(scope.row.articleId)">
              <el-icon class="el-icon--left">
                <View/>
              </el-icon>
              浏览
            </el-button>
          </el-button-group>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <div class="pagination">
    <el-pagination v-if="paging.totalCount"
                   background
                   :total="paging.totalCount"
                   :page-sizes="[15, 20, 25, 30]"
                   :page-size="paging.pageSize"
                   :current-page.sync="paging.pageNumber"
                   layout="total, sizes, prev, pager, next, jumper"
                   @size-change="handleSizeChange"
                   @current-change="handleCurrentChange"
                   style="text-align: right">
    </el-pagination>
  </div>
  <BrowseDialog
      v-model:dialog-visible="dialogVisible"
      :query="query"
  />
</template>

<script setup>

import {getCurrentInstance, reactive, ref} from "vue";
import globalRouter from "../../../utils/GlobalRouter.js";
import md5 from "js-md5";
import VueCookie from "vue-cookies"
import router from "../../../router/index.js";
import BrowseDialog from "../../../components/BrowseDialog.vue";
import {ElMessage, ElMessageBox} from "element-plus";

const {proxy} = getCurrentInstance();

const userInfo = reactive(VueCookie.get('userInfo') || {});
let articleList = ref([]);

let paging = reactive({
  pageNumber: 1,
  pageSize: 15,
  totalCount: 0
});

let dialogVisible = ref(false);
let query = reactive({
  userId: userInfo.id,
  articleId: '',
})

const handleSizeChange = (val) => {
  paging.pageSize = val;
  getArticles();
}

const handleCurrentChange = (val) => {
  paging.pageNumber = val;
  getArticles();
}

const init = () => {
  getTotalCount();
  getArticles();
}

const getTotalCount = async () => {
  const count = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_GET_TOTAL_ARTICLE_COUNT + '/' + userInfo.id + '/1',
    methods: 'GET',
  })
  paging.totalCount = count;
}

const getArticles = async () => {
  const results = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_POST_ARTICLE_QUERY,
    params: {
      "userId": userInfo.id,
      "isDelete": 1,
      "paging": {
        "pageNumber": paging.pageNumber,
        "pageSize": paging.pageSize
      }
    },
    methods: 'POST'
  })
  articleList.value = results;
}

const openDeleteMessageBox = (articleId) => {
  ElMessageBox.confirm(
      '如果删除将无法恢复，确定删除这篇文章吗?',
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(() => {
        ElMessage({
          type: 'success',
          message: 'Delete successed',
        })
        deleteArticles(articleId);
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: 'Delete canceled',
        })
      })
}

const deleteArticles = async (articleId) => {
  const results = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_DELETE_ARTICLE_COMPLETE + '/' + userInfo.id + '/' + articleId,
    methods: 'DELETE',
  })
  if (!results) return;
  init();
}

const openBrowseDialog = (articleId) => {
  query.articleId = articleId;
  return true;
}

const openRestoreMessageBox = (id) => {
  ElMessageBox.confirm(
      '确定恢复这篇文章吗?',
      'Warning',
      {
        confirmButtonText: 'OK',
        cancelButtonText: 'Cancel',
        type: 'warning',
      }
  )
      .then(() => {
        restoreArticle(id);
      })
      .catch(() => {
        ElMessage({
          type: 'info',
          message: '取消恢复',
        })
      })
}

const restoreArticle = async (id) => {
  const results = await proxy.Request({
    url: globalRouter.backEndRouter.BACK_END_PUT_ARTICLE_RESTORE + '/' + id,
    methods: 'PUT',
  })
  if (!results) return;
  ElMessage({
    type: 'success',
    message: '恢复成功',
  })
  init();
}

init()

</script>

<style scoped>

.pagination {
  position: fixed;
  width: 100%;
  padding: 15px 10px;
  bottom: 0;
}

</style>