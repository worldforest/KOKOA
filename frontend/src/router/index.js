import Vue from 'vue';
import VueRouter from 'vue-router';
import Write from '@/components/training/Write.vue';
import Talk from '@/components/training/Talk.vue';
import Home from '@/views/Home.vue';
import Artist from '@/components/Artist.vue';
import Note from '@/components/user/Note.vue';
import Review from '@/components/user/Review.vue';
import VueYoutube from 'vue-youtube';
import VueSweetalert2 from 'vue-sweetalert2';
import 'sweetalert2/dist/sweetalert2.min.css';
import ToggleButton from 'vue-js-toggle-button';
import VueAwesomeSwiper from 'vue-awesome-swiper';
import 'swiper/css/swiper.css';
import GAuth from 'vue-google-oauth2';
// eslint-disable-next-line import/extensions
import store from '@/store/index.js';

Vue.use(GAuth, {
  clientId: '925119046285-8pdsf0gb5d88g0t18ibhudioaodptq4h.apps.googleusercontent.com',
  scope: 'profile email',
});
Vue.use(VueAwesomeSwiper);
Vue.use(ToggleButton);
Vue.use(VueYoutube);
Vue.use(VueRouter);
Vue.use(VueSweetalert2);

const onlyAuthUser = (to, from, next) => {
  // 로그인 된 유저만 접속 가능
  if (store.state.isLogin === true || localStorage.getItem('email') !== null) {
    next();
  } else {
    alert('Login please!!');
    next('/');
  }
};
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
  },
  {
    path: '/Artist',
    name: 'Artist',
    component: Artist,
    beforeEnter: onlyAuthUser,
  },
  {
    path: '/write',
    component: Write,
    name: 'Write',
    props: true,
    beforeEnter: onlyAuthUser,
  },
  {
    path: '/talk',
    component: Talk,
    name: 'Talk',
    props: true,
    beforeEnter: onlyAuthUser,
  },
  {
    path: '/note',
    component: Note,
    name: 'Note',
    props: true,
    beforeEnter: onlyAuthUser,
  },
  {
    path: '/review',
    component: Review,
    name: 'Review',
    props: true,
    beforeEnter: onlyAuthUser,
  },
  {
    path: '/logout',
    beforeEnter: (to, from, next) => {
      store.state.email = null;
      store.state.isLogin = false;
      localStorage.clear();
      next('/');
    },
  }
  
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes,
});

export default router;
