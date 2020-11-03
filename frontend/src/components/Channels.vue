<template>
  <div>
    <h2>Choose Your Star(가제)</h2>
    <v-flex d-flex>
      <v-layout wrap>
        <v-flex xs12 sm6 md4 lg3 xl2 v-for="(data, index) in items" :key="index">
          <v-card class="ma-3" @click="enter(data.groupname)">
            <v-img  aspect-ratio="1" :src="require(`@/assets` + data.img)" alt="" />
            <v-card-title class="justify-center">{{data.groupname}}</v-card-title>
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
    };
  },
  methods: {
    enter(name) {
      this.$router.push({ name: 'Artist', query: { name: String(name) } });
    },
  },
};
</script>

<style>
.img{
    overflow: scroll;
}
</style>
