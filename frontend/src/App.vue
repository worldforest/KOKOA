<template>
  <v-app id="create">
    <div id="logo" @click="goHome" v-if="isLogin">
      <img src="@/assets/kokoa1.png">
    </div>
    <quick-menu v-if="isLogin"
      class="circular"
      :menu-count="count"
      :icon-class="icons"
      :menu-url-list="list"
      :background-color="backgroundColor"
      :color="color"
      :position="position"
    ></quick-menu>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
import quickMenu from 'vue-quick-menu';
import { mapState } from 'vuex';

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
        { isLink: true, url: '/logout' },
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
  methods: {
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
  height: 10px;
  width: 10px;
  position: fixed;
  z-index: 999;
}
#logo{
  position: fixed;
  z-index: 999;
}
#logo img{
  /* position: absolute; */
  z-index: 999;
  margin: 8px 0 0 15px;
  height: calc(40px + 1vw);;
  width: auto;
}
* {
  font-family: 'Merriweather', serif;
}
</style>
