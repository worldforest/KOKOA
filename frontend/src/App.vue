<template>
  <v-app id="create">
    <!-- KOKOA LOGO -->
    <div id="logo" @click="goHome">
      KOKOA
    </div>
    <v-btn v-show="!isLogin" @click="getAuth()">
        <img src="@/assets/google.png" alt="구글로그인버튼" style="width:30px" />
        login
      </v-btn>
    <!-- circular menu -->
    <quick-menu
      class="circular"
      :menu-count="count"
      :icon-class="icons"
      :menu-url-list="list"
      :background-color="backgroundColor"
      :color="color"
      :position="position"
    ></quick-menu>
    <!-- :is-open-new-tab=getIsOpenNewTab @process=print -->
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import quickMenu from 'vue-quick-menu';
import { mapState, mapActions } from 'vuex';

export default {
  name: 'App',
  components: {
    quickMenu,
  },
  data() {
    return {
      email: '',
      drawer: false,
      // circular menu settings
      count: 3,
      icons: ['fas fa-book', 'fas fa-history', 'fas fa-sign-out-alt'],
      list: [
        { isLink: true, url: '/note' },
        { isLink: true, url: '/review' },
        { isLink: false },
      ],
      backgroundColor: 'rgb(255, 127, 0)',
      color: '#ffffff',
      position: 'top-right',
      isOpenNewTab: false,
    };
  },
  computed: {
    ...mapState(['isLogin']),
  },
  watch: {
    isLogin() {
      console.log('Dfdf');
    },
  },
  methods: {
    ...mapActions(['login']),
    async getAuth() {
      await this.$gAuth.signIn()
        .then((data) => {
          this.email = data.vt.bu; // 이메일
          this.$store.dispatch('login', this.email);
        })
        .catch((error) => {
          console.log(error);
        });
    },
    goHome() {
      this.$router.push('/').catch((error) => {
        if (error.name !== 'NavigationDuplicated') {
          throw error;
        }
      });
    },
    handleClick(item) {
      this.lastClicked = item;
    },
  },
};
</script>
<style>
#create{
  background-color: rgba(0, 0, 0, 0.89);
}
.circular {
  position: fixed;
  z-index: 999;
}

#logo {
  position: fixed;
  z-index: 999;
  color: rgb(255, 127, 0);
}
</style>
