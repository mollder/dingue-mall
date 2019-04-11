import Vue from 'vue';
import Router from 'vue-router';
import Login from '../components/Login';
import Layout from '../components/Layout';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/main',
      name: Login,
      component: Login,
    },
    {
      path: '/',
      name: 'Layout',
      component: Layout,
    }
  ],
});
