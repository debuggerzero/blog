import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from "./router/index.js"
import "./assets/icon/iconfont.css"
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import Request from './utils/Request'


const app = createApp(App);
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(router);
app.config.globalProperties.Request = Request;

app.mount('#app');