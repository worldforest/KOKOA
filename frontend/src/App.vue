<template>
  <v-app id="create">
    <!-- KOKOA LOGO -->
      <div id="logo" @click="goHome">KOKOA
        <v-btn v-if="!isLogin" @click="getAuth()">
          <img src="@/assets/google.png" alt="구글로그인버튼" style="width:30px" />
          login
        </v-btn>
      </div>
      <!-- circular menu -->
      <quick-menu
      class="circular"
      :menu-count=count :icon-class=icons :menu-url-list=list
      :background-color=backgroundColor :color=color :position=position
      @process=print></quick-menu>
      <!-- :is-open-new-tab=getIsOpenNewTab @process=print -->
    <v-main>
      <router-view />
    </v-main>

  </v-app>
</template>

<script>
// import { RadialMenu, RadialMenuItem } from 'vue-radial-menu';
import quickMenu from 'vue-quick-menu';

export default {
  name: 'App',
  components: {
    // RadialMenu,
    // RadialMenuItem,
    quickMenu,
  },
  data() {
    return {
      isLogin: false,
      drawer: false,
      links: [
        ['mdi-inbox-arrow-down', '복습'],
        ['mdi-send', '오답노트'],
        ['mdi-delete', 'Trash'],
        ['mdi-alert-octagon', 'Spam'],
      ],
      direction: 'bottom',
      fab: false,
      fling: false,
      hover: true,
      tabs: null,
      top: true,
      right: true,
      bottom: false,
      left: false,
      transition: 'slide-x',
      //
      count: 4,
      icons: ['fa fa-github', 'fa fa-comment', 'fa fa-code', 'fa fa-envelope'],
      list: [{ isLink: false }, { isLink: true, url: '/doo' }, { isLink: true, url: '/foo' }, { isLink: false }],
      backgroundColor: 'rgb(180, 91, 180)',
      color: '#ffffff',
      position: 'top-right',
      isOpenNewTab: false,
    };
  },
  computed: {
    activeFab() {
      switch (this.tabs) {
        case 'one': return { class: 'purple', icon: 'account_circle' };
        case 'two': return { class: 'red', icon: 'edit' };
        case 'three': return { class: 'green', icon: 'keyboard_arrow_up' };
        default: return {};
      }
    },
  },
  watch: {
    top(val) {
      this.bottom = !val;
    },
    right(val) {
      this.left = !val;
    },
    bottom(val) {
      this.top = !val;
    },
    left(val) {
      this.right = !val;
    },
  },
  methods: {
    getAuth() {
      this.$gAuth
        .getAuthCode()
        .then((authCode) => {
          this.$store.dispatch('googleLogin', authCode);
        })
        .then((response) => {
          console.log(response);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    goHome() {
      this.$router.push('/');
    },
    handleClick(item) {
      this.lastClicked = item;
    },
  },
};
</script>
<style>
  .circular {
    position: fixed;
    z-index: 999;
  }
  /* #create .v-btn--floating {
    position: relative;
  } */

  #create {
    background-color: rgb(29, 25, 29)
  }

  #logo {
    position: fixed;
    z-index: 999;
    color: rgb(180, 91, 180)
  }
</style>
