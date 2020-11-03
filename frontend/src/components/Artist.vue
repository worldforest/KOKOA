<template>
<div class="artist">
  <!-- header -->
  <div class="header mr-auto"
  :style="{'background-image': 'url('+ require(`@/assets/img/${team}/${team}.jpg`) + ')',}">
    <div>{{name}}</div>
  </div>
  <!-- 멤버 -->
  <v-row justify="space-around" class="member">
    <div class="" v-for="member in team" :key="member">
      <img :src="require(`@/assets/img/${team}/${team}.jpg`)" alt="">
      <div>{{member}}</div>
    </div>
  </v-row>
  <!-- 영상 리스트 -->
  <Popular />
</div>
</template>

<script>
import Popular from './list/Popular.vue';

export default {
  name: 'Artist',
  components: {
    Popular,
  },
  data() {
    return {
      name: this.$route.query.name,
      team: '',
    };
  },
  created() {
    console.log(this.$route.query.name);
    this.team = this.$route.query.name;
  },
};
</script>

<style>
.teamImg {
  height: 350px;
  width: auto;
}

.header {
  background-color: #e7d0e2; /* Grey background */
  /* padding: 50px; */
  margin-bottom: 30px;
  height: 500px;
  /* background-size: 80% 80%; */
  background-size: contain;
  background-position: center;
  background-repeat:no-repeat;
  border: 2px solid;
  color: rgb(180, 91, 180);
  resize: both;
  text-align: center;
  font-size: 60px;
  font-weight: bold;
}

.member {
  text-align: center;
}

.member img {
  border-radius: 50%;
  width: 100px;
  height: 100px;
}
</style>
