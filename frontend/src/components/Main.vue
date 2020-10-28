<template>
  <v-container>
    <span>main</span>
    <Popular/>
  </v-container>
</template>

<script>
import Popular from '@/components/list/popular.vue';

export default {
  name: 'Main',
  components: {
    Popular,
  },
  data() {
  },
};
</script>
