<template>
  <div class="home">
    <Intro v-if="!isLogin" />
    <Main v-else />
  </div>
</template>

<script>
// @ is an alias to /src
import Main from '@/components/Main.vue';
import Intro from '@/components/Intro.vue';
import { mapState } from 'vuex';

export default {
  name: 'Home',
  components: {
    Main,
    Intro,
  },
  created() {
    console.log(this.isLogin);
  },
  computed: {
    ...mapState(['isLogin']),
  },
  watch: {
    isLogin() {
      console.log('Dfdf');
    },
  },
};
</script>
