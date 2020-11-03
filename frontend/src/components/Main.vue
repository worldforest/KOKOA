<template>
  <v-container>
    <Channels />
    <span>main</span>
    <Popular/>
    <Test />
  </v-container>
</template>

<script>
import Channels from './Channels.vue';
import Popular from '@/components/list/popular.vue';
import Test from '@/components/list/test.vue';

export default {
  name: 'Main',
  components: {
    Channels,
    Popular,
    Test,
  },
  data() {
    return {

    };
  },
};
</script>
