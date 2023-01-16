import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from "./router/index.js"
import "./assets/icon/iconfont.css"
import 'element-plus/dist/index.css'

import Request from './utils/Request'

const app = createApp(App);
app.use(router);
app.config.globalProperties.Request = Request;
app.mount('#app');
