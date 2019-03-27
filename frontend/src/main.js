import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'

import router from './router';

import BootstrapVue from 'bootstrap-vue'

Vue.use(BootstrapVue);
Vue.prototype.$http = axios;

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

new Vue({
  el: '#app',
  router,
  render: h => h(App)
})
