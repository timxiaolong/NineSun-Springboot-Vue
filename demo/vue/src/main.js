import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import 'bootstrap/dist/js/bootstrap.min.js'
import 'bootstrap/dist/css/bootstrap.min.css'

const app = createApp(App)
createApp(App).use(router).mount('#app')