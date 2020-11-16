<template>
  <v-app id="create">
    <div id="logo" @click="goHome" v-if="isLogin" :class="{ 'navbar--hidden': !showLogo }">
      <img src="@/assets/kokoa1.png">
    </div>
    <quick-menu v-show="isLogin"
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
    <footer style="margin: 30px 0 50px; font-family: 'Xanh Mono', monospace;">
      <h3 class="" style="font-size: 15px;">Copyright © KOKOA 2020</h3>
      <div>
        <h3 class="" style="margin-top:30px; font-size: 15px;">Daejeon SSAFY 3 Lastproject</h3>
        <h3 class="" style="font-size: 15px;">
          We dream of a world where everyone who loves K-pop understands Korean.</h3>
      </div>
    </footer>
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
  mounted() {
    const SUBTAGS = document.querySelectorAll('a[target=_self]');
    const QUICKMENU = document.querySelector('.quick-menu');
    for (let i = 0; i < SUBTAGS.length; i += 1) {
      SUBTAGS[i].onclick = function () {
        QUICKMENU.classList.remove('active');
      };
    }
    window.addEventListener('scroll', this.onScroll);
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.onScroll);
  },
  data() {
    return {
      showLogo: true,
      lastScrollPosition: 0,
      email: '',
      drawer: false,
      // circular menu settings
      count: 4,
      icons: ['fas fa-home', 'fas fa-book', 'fas fa-history', 'fas fa-sign-out-alt'],
      list: [
        { isLink: true, url: '/' },
        { isLink: true, url: '/note' },
        { isLink: true, url: '/history' },
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
    onScroll() {
      const currentScrollPosition = window.pageYOffset || document.documentElement.scrollTop;
      if (currentScrollPosition < 0) {
        return;
      }
      if (Math.abs(currentScrollPosition - this.lastScrollPosition) < 60) {
        return;
      }
      this.showLogo = currentScrollPosition < this.lastScrollPosition;
      this.lastScrollPosition = currentScrollPosition;
    },
  },
};
</script>
<style>
.kor{
  font-family: 'Do Hyeon', sans-serif;
}
.eng{
  font-family: 'Merriweather', serif;
  color: white;
}
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
  transform: translate3d(0, 0, 0);
  transition: 0.1s all ease-out;
}
#logo img{
  z-index: 999;
  margin: 20px 0 0 30px;
  height: calc(50px + 1vw);;
  width: auto;
}

#logo.navbar--hidden{
  box-shadow: none;
  transform: translate3d(0, -100%, 0);
}

@import url('https://fonts.googleapis.com/css2?family=Xanh+Mono&display=swap');
footer{
  padding-left:80px;
  font-family: 'Xanh Mono', monospace;
  color: wheat;
  font-size: 15px;
}
.slick-slider{
  padding: inherit;
}
.slick-prev:before {
  font-family: 'swiper-icons' !important;
  content: 'prev' !important;
  color: #FDB165 !important;
  font-size: 35px !important;
}
.slick-next:before {
  font-family: 'swiper-icons' !important;
  content: 'next' !important;
  color: #FDB165 !important;
  font-size: 35px !important;
}
figure {
  padding: 5px;
  display: table;
}

figure img {
  display: block;
  width: 100%;
}

figcaption {
  caption-side: bottom;
  font-size: 1.5em;
  padding: 0 5px 5px;
}
</style>
