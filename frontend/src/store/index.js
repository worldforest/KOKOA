import Vue from 'vue';
import Vuex from 'vuex';
import http from '@/util/http-common';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    email: '',
    isLogin: false,
  },
  mutations: {
    setLogOut(state) {
      state.email = null;
      state.isLogin = false;
      localStorage.removeItem('email');
    },
    setLogIn(state, data) {
      state.isLogin = true;
      state.email = data;
    },
  },
  actions: {
    // eslint-disable-next-line no-unused-vars
    login({ commit }, email) {
      http
        .get('/user/join/', { params: { email } })
        .then(() => {
          localStorage.setItem('email', email);
          this.commit('setLogIn', email);
          console.log('로그인 성공');
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getUserInfo({ commit }) {
      // 로컬 스토리지에 저장된 토큰 불러오기
      const email = localStorage.getItem('email');
      if (email !== null) {
        commit('setLogIn', email);
        console.log('리로드 성공');
      } else {
        commit('setLogOut');
        console.log('리로드실패');
      }
    },
  },
  modules: {},
});
