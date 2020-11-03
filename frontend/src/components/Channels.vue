<template>
  <div>
    <h2>Choose Your Star(가제)</h2>
    <v-flex d-flex>
      <v-layout wrap>
        <v-flex sm4 md3 lg2 v-for="(data, index) in items" :key="index">
          <v-card @click="enter(data.groupname)">
            <v-img height="220" :src="require(`@/assets` + data.img)" alt="" />
            <v-card-text>
              <v-row class="justify-center mx-0">
                  <p class="font-weight-black" >{{data.groupname}}</p>
              </v-row>
            </v-card-text>
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

<style></style>
