
import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
//import react from '@vitejs/plugin-react'
// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  server: {
    proxy: {
      '/test': {
        target: 'http://localhost:8848',
        changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
        secure: false,
      }
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  }
})

/**
module.exports = {
 devServer: {
   host: 'localhost',
   port: 5173,
   proxy: {
     '/test': {
       target: 'http://localhost:8848',
       changeOrigin: true, // 如果接口跨域，需要进行这个参数配置
     }
   }
 }
}
  */
