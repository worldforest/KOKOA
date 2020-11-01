<template>
  <v-app id="inspire">
    <v-navigation-drawer stateless v-model="drawer" app>
      <v-sheet color="grey lighten-4" class="pa-4">
        <v-btn v-if="!isLogin"  @click="getAuth()">
           <img src="@/assets/google.png" alt="구글로그인버튼" style="width:30px" />
          login
        </v-btn>

        <div v-else>
        <v-avatar class="mb-4" color="grey darken-1" size="64"></v-avatar>

        <div>john@vuetifyjs.com</div>
        </div>
      </v-sheet>

      <v-divider></v-divider>

      <v-list>
        <v-list-item v-for="[icon, text] in links" :key="icon" link>
          <v-list-item-icon>
            <v-icon>{{ icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ text }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app>
      <v-app-bar-nav-icon @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title>KOKOA</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn icon>
        <v-icon>mdi-magnify</v-icon>
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
    };
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
  },
};
</script>
