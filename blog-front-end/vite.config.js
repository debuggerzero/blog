import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

export default defineConfig({
  plugins: [vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
  server: {
    hmr: true,
    port: 3001,
    proxy:{
      '/api':{
        target:"http://localhost:8081/",
        changeOrigin:true,
        rewrite:(path) => path.replace(/^\/api/, "/api")//重写路径,替换/api
      }
    }
  },
})
