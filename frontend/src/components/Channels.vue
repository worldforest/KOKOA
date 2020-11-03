<template>
  <div>
    <h2>Choose Your Star(가제)</h2>
    <v-flex d-flex>
      <v-layout wrap>
        <v-flex xs12 sm6 md4 lg3 xl2 v-for="(data, index) in items" :key="index">
          <v-card class="ma-3 " @click="enter(data.id)">
            <v-img class="scale" aspect-ratio="1" :src="require(`@/assets` + data.img)" />
            <v-card-title class="justify-center">{{ data.groupname }}</v-card-title>
          </v-card>
        </v-flex>
      </v-layout>
    </v-flex>
  </div>
</template>

<script>
import channelList from './core/channelList.json';

export default {
  name: 'Channels',
  data() {
    return {
      items: channelList,
      loader: null,
      loading: false,
    };
  },
  methods: {
    enter(id) {
      this.$router.push({ name: 'Artist', query: { groupid: String(id) } });
    },
  },
};
</script>

<style>
.scale {
  transform: scale(1);
  /* -webkit-transform: scale(1);
  -moz-transform: scale(1);
  -ms-transform: scale(1);
  -o-transform: scale(1); */
  transition: all 0.3s ease-in-out;
}
.scale:hover {
  transform: scale(1.1);
  z-index:1
  /* -webkit-transform: scale(1.2);
  -moz-transform: scale(1.2);
  -ms-transform: scale(1.2);
  -o-transform: scale(1.2); */
}
</style>
