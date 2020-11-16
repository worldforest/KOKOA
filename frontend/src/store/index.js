import Vue from 'vue';
import Vuex from 'vuex';
import http from '@/util/http-common';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    email: '',
    isLogin: false,
    overlayWrite: true,
    overlayTalk: true,
  },
  mutations: {
    setLogOut(state) {
      state.email = null;
      state.isLogin = false;
      state.overlayWrite = true;
      state.overlayTalk = true;
      localStorage.removeItem('email');
    },
    setLogIn(state, data) {
      state.isLogin = true;
      state.email = data;
    },
    setOverlayWrite(state) {
      state.overlayWrite = false;
    },
    setOverlayTalk(state) {
      state.overlayTalk = false;
    },
  },
  getters: {
    email(state) {
      return state.email;
    },
    overlayTalk(state) {
      return state.overlayTalk;
    },
    overlayWrite(state) {
      return state.overlayWrite;
    },
  },
  actions: {
    // eslint-disable-next-line no-unused-vars
    async login({ commit }, email) {
      await http
        .get('/user/join/', { params: { email } })
        .then(() => {
          localStorage.setItem('email', email);
          this.commit('setLogIn', email);
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
      } else {
        commit('setLogOut');
      }
    },
    logout({ commit }) {
      commit('setLogout');
    },
    overlayTalk({ commit }) {
      commit('setOverlayTalk');
    },
    overlayWrite({ commit }) {
      commit('setOverlayWrite');
    },
  },
  modules: {},
});
