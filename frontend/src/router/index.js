import Vue from 'vue';
import VueRouter from 'vue-router';
import Write from '@/components/training/Write.vue';
import Talk from '@/components/training/Talk.vue';
import Home from '@/views/Home.vue';
import Login from '@/components/user/Login.vue';
import VueYoutube from 'vue-youtube';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';

Vue.use(VueYoutube);
Vue.use(VueRouter);
Vue.use(VueSweetalert2);
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/Login',
    name: 'Login',
    component: Login,
  },
  {
    path: '/write',
    component: Write,
    name: 'Write',
    props: true,
  },
  {
    path: '/talk',
    component: Talk,
    name: 'Talk',
    props: true,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
