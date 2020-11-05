<template>
  <v-container>
    <Channels />
    <span>main</span>
    <Popular/>
    <Test />
  </v-container>
</template>

<script>
import Popular from '@/components/list/Popular.vue';
import Test from '@/components/list/test.vue';
import Channels from './Channels.vue';

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
