<template>
  <div>
    <h1>받아쓰기 페이지</h1>
    <h2>name: {{ $route.params.index }}</h2>
    <iframe v-bind="player"></iframe>
    <div>
      <b-button v-for="(item, index) in video" :key="index" @click="play(index)">{{
        item.eng
      }}</b-button>
    </div>
  </div>
</template>
<script>
export default {
  name: 'Write',
  props: {
    index: {
      type: String,
      default: '',
    },
  },
  created() {

  },
  mounted() {},
  data() {
    return {
      player: '',
      url: 'https://www.youtube.com/embed/mLx7D98zP_A',
      origin: '?enablejsapi=1&origin=*',
      video: [
        {
          starttime: '00:00:00,000',
          endtime: '00:00:01,831',
          eng: '[National treasure zombie beast 2PM]',
          kor: '[국보급 짐승 좀비 2PM]',
        },
        {
          starttime: '00:00:01,832',
          endtime: '00:00:04,034',
          eng: '"Chansung lifts people up"',
          kor: '"찬성은 사람을 들어"',
        },
        {
          starttime: '00:00:04,035',
          endtime: '00:00:06,406',
          eng: '"Taecyeon is mad"',
          kor: '"화가 잔뜩 난 택연"',
        },
      ],
    };
  },
  methods: {
    play(index) {
      console.log(index);
      console.log(this.player);
      // player.seekTo(00:00:00,000,true);
    },
  },
};
</script>
<style></style>
