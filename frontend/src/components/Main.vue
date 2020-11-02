<template>
  <v-container>
    <span>main</span>
<<<<<<< HEAD
    <Popular />
=======
    <Popular/>
    <Test />
>>>>>>> feature/apitest
  </v-container>
</template>

<script>
<<<<<<< HEAD
import Popular from './list/Popular.vue';
=======
import Popular from '@/components/list/popular.vue';
import Test from '@/components/list/test.vue';
>>>>>>> feature/apitest

export default {
  name: 'Main',
  components: {
    Popular,
    Test,
  },
  data() {
    return {

    };
  },
};
</script>
