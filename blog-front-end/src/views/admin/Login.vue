<template>
  <div class="login-body">
    <div class="login-panel">
      <div class="login-title">用户登录</div>
      <el-form :model="formData" :rules="rules" ref="formDataRef">
        <el-form-item prop="account" label="">
          <el-input placeholder="请输入账号" v-model="formData.account" size="large">
            <template #prefix>
              <span class="iconfont icon-account"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" label="">
          <el-input type="password" placeholder="请输入密码" v-model="formData.password" size="large">
            <template #prefix>
              <span class="iconfont icon-3701mima"></span>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="rememberMe" label="">
          <el-checkbox v-model="formData.remeberMe">
            记住密码
          </el-checkbox>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" style="width: 100%" @click="login">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>

import {getCurrentInstance, reactive, ref} from 'vue'
import axios from "axios"
import md5 from "js-md5"
import { ElMessage } from "element-plus"
import VueCookie from "vue-cookies"
import router from "../../router/index.js";
import globalRouter from "../../utils/GlobalRouter.js";

const { proxy } = getCurrentInstance();

//表单相关
const formDataRef = ref(null);
const formData = reactive({
  account: '',
  password: '',
  remeberMe: true,
})

const rules = {
  account: [{
    required: true,
    message: "请输入用户名",
  }],
  password: [{
    required: true,
    message: "请输入密码",
  }]
}

const init = () => {
  const loginInfo = VueCookie.get("loginInfo");
  if (loginInfo) {
    Object.assign(formData, loginInfo);
  }

  document.onkeydown = (e) => {
    if (e.keyCode === 13) login();
  }
}
init();

const login = () => {
  formDataRef.value.validate(async (valid) => {
    if (!valid) {
      return;
    }
    let results = await proxy.Request({
      url: globalRouter.backEndRouter.BACK_END_LOGIN_PASSWORD,
      params: {
        account: formData.account,
        password: md5(formData.password),
      },
      methods: 'POST'
    })
    if (results) {
      ElMessage.success('登录成功');
      const loginInfo = {
        account: formData.account,
        password: formData.password,
        rememberMe: true,
      }
      VueCookie.set("userInfo", results, 0);
      if (formData.remeberMe)
        VueCookie.set("loginInfo", loginInfo, "14d");
      else if (VueCookie.isKey("loginInfo")) VueCookie.remove("loginInfo");
      setTimeout(()=>{
        router.push(globalRouter.adminRouter.ADMIN_HOME);
      }, 500);
    }
  });
}

</script>

<style scoped>
.login-body {
  width: 100%;
  height: calc(100vh);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  background-image: url(../../assets/login-bg.jpg);
}

.login-panel {
  float: right;
  width: 350px;
  height: 250px;
  margin-top: 300px;
  margin-right: 200px;
  padding: 35px;
  background: rgba(255, 255, 255, 0.8);
  border-radius: 5px;
  box-shadow: 2px 2px 10px #fff;
}

.login-title {
  font-size: 25px;
  text-align: center;
  margin-bottom: 30px;
}

</style>