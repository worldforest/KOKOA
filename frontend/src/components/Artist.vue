<template>
  <div class="artist"
  :style="{ 'background-image': 'url(' + require(`@/assets/img/${team}/${team}.jpg`) + ')' }">
    <!-- header -->
    <div class="header">
      <div class="group">{{ team }}</div>
    </div>
    <!-- 멤버 -->
    <div class="member">
      <v-row justify="space-around" class="member">
        <div class="list" v-for="(member,index) in members" :key="index">
          <img :class="{selected: isActive==index}"
            :src="require(`@/assets/img/${team}/${member.name}.jpg`)"
            alt=""
            @click="changeSelected(index)"
          />
          <div class="">{{ member.name }}</div>
        </div>
      </v-row>
    </div>
    <!-- 영상 리스트 -->
    <Videolist :filters="filters"/>
  </div>
</template>

<script>
import Videolist from './list/Videolist.vue';
import channelList from './core/channelList.json';
import memberList from './core/memberList.json';

export default {
  name: 'Artist',
  components: {
    Videolist,
  },
  data() {
    return {
      groupid: this.$route.query.groupid,
      team: '',
      title: '',
      teams: channelList,
      stars: memberList,
      members: [],
      filters: '',
      isActive: '',
    };
  },
  created() {
    this.team = this.teams[this.groupid - 1].groupname;
    this.title = this.teams[this.groupid - 1].title;
    this.filters = this.team;
    this.members = [];
    this.members.push({ name: this.title });
    for (let i = 0; i < this.stars.length; i += 1) {
      if (String(this.stars[i].groupid) === this.groupid) {
        this.members.push(this.stars[i]);
      }
    }
    if (this.members.length === 2) {
      this.members.pop();
    }
  },
  methods: {
    changeFilter(name) {
      this.filters = name;
    },
    changeSelected(index) {
      this.isActive = (index === this.isActive ? '' : index);
      this.filters = this.members[index].name;
    },
  },
};
</script>

<style>
.artist {
  background-size: cover;
  position: relative;
  background-color: rgba(0, 0, 0, 0.333);
}
.artist::before{
  content: "";
  opacity: 0.7;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-color: #000;
}
.header {
  height: 250px;
  resize: both;
  /* font setting */
  position: relative;
  display: flex;
  align-content: center;
  justify-content: center;
}

.group{
  font-size: calc(60px + 5vw);
  font-family: 'Bangers', cursive;
  color: rgb(255, 127, 0);
  position: absolute;
  bottom: 0px;
  font-family: 'Lobster', cursive;
}

.member {
  position: relative;
  margin-top: 2vw;
  margin-bottom: 0.4vw;
  justify-content: center;
  text-align: center;
  font-size: 1.5vw;
  justify-items: center;
  color: white;
}

.member img {
  justify-content: center;
  margin: 0;
  padding: 0;
  border-radius: 50%;
  width: calc(60px + 1.5vw);
  height: calc(60px + 1.5vw);
}
.selected {
  border: 3px solid rgb(255, 127, 0);
}

</style>
