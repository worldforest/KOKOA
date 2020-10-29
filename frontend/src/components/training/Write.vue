<template>
  <div>
    <h1>받아쓰기 페이지</h1>
    <h2>name: {{ $route.params.index }}</h2>
    <youtube :video-id="url" ref="youtube" :player-vars="playerVars" @ended="onEnded"></youtube>
    <button @click="playVideo">play</button>
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
  created() {},
  mounted() {},
  data() {
    return {
      url: 'mLx7D98zP_A',
      origin: '?enablejsapi=1&origin=*',
      replay: '',
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
      playerVars: {
        autoplay: 1,
        controls: 0,
        loop: 1,
      },
    };
  },
  computed: {
    player() {
      return this.$refs.youtube.player;
    },
  },
  methods: {
    async play(index) {
      const start = this.timer(this.video[index].starttime);
      const end = this.timer(this.video[index].endtime);
      this.replay = index;
      await this.player.loadVideoById({
        videoId: this.url,
        startSeconds: start,
        endSeconds: end,
        suggestedQuality: 'default',
      });
    },
    async playVideo() {
      await this.player.playVideo();
    },
    timer(input) {
      const hms = input;
      const a = hms.split(':');
      const s = a[2].split(',');
      const ms = Number(a[0] * 60 * 60) + Number(a[1] * 60) + Number(s[0]) + Number(s[1] / 1000);
      return ms;
    },
    onEnded() {
      this.play(this.replay);
    },
  },
};
</script>
<style></style>
