<template>
  <v-app id="create">
     <v-speed-dial
      v-model="fab"
      :top="top"
      :bottom="bottom"
      :right="right"
      :left="left"
      :direction="direction"
      :open-on-hover="hover"
      :transition="transition"
    >
      <template v-slot:activator>
        <v-btn
          v-model="fab"
          color="blue darken-2"
          dark
          fab
        >
          <v-icon v-if="fab">
            mdi-close
          </v-icon>
          <v-icon v-else>
            mdi-account-circle
          </v-icon>
        </v-btn>
      </template>
      <v-btn
        fab
        dark
        small
        color="green"
      >
        <v-icon>mdi-pencil</v-icon>
      </v-btn>
      <v-btn
        fab
        dark
        small
        color="indigo"
      >
        <v-icon>mdi-plus</v-icon>
      </v-btn>
      <v-btn
        fab
        dark
        small
        color="red"
      >
        <v-icon>mdi-delete</v-icon>
      </v-btn>
    </v-speed-dial>
    <v-app-bar flat dense app>
      <v-toolbar-title black @click="goHome">KOKOA</v-toolbar-title>
      <div class="navigation">
        <v-btn text>
          오답노트
        </v-btn>
        <v-btn text>
          라이크
        </v-btn>
        <v-btn icon>
          <v-icon>mdi-heart</v-icon>
        </v-btn>
        <v-btn icon>
          <v-icon>mdi-dots-vertical</v-icon>
        </v-btn>
      </div>
      <v-spacer></v-spacer>
      <v-btn v-if="!isLogin" @click="getAuth()">
        <img src="@/assets/google.png" alt="구글로그인버튼" style="width:30px" />
        login
      </v-btn>
      <v-btn v-else>
        logout
      </v-btn>
    </v-app-bar>
    <v-main>
      <router-view />
    </v-main>

  </v-app>
</template>

<script>
export default {
  name: 'App',
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
  },
};
</script>
<style>
  #create .v-speed-dial {
    position: fixed;
    z-index: 999;
  }
  #create .v-btn--floating {
    position: relative;
  }
</style>
