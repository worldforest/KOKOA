<template>
  <v-container>
    <span>main</span>
    <Popular/>
    <Test />
  </v-container>
</template>

<script>
import Popular from '@/components/list/popular.vue';
import Test from '@/components/list/test.vue';

export default {
  name: 'Main',
  components: {
    Popular,
    Test,
  },
  data() {
  },
};
</script>
