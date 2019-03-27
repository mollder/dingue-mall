import Vue from 'vue';
import Router from 'vue-router';
import Login from '../components/Login';
import Redirect from '../components/Redirect';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: Login,
      component: Login,
    },
    {
      path: '/success',
      name: 'Redirect',
      component: Redirect,
    }
  ],
});
