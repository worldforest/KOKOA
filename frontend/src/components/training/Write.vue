<template>
  <div>
    <div class="d-flex justify-center mt-3">
      <youtube
        :video-id="url"
        ref="youtube"
        :player-vars="playerVars"
        flex
        @ended="onEnded"
      ></youtube>
    </div>
    <div class="d-flex justify-space-around my-5">
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <b-button class="mx-5" variant="success" @click="playVideo">play</b-button>
      <b-button variant="danger" @click="stopVideo">stop</b-button>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>
    <div>
      <div class="row d-flex justify-center ma-5">
        <div class="col-8">
          <h3 class="d-flex justify-center">Answer</h3>
          <draggable
            class="row wrap fill-height align-center sortable-list"
            :list="checklist"
            group="people"
          >
            <div class="list-group-item" v-for="(element, index) in checklist" :key="element.name">
              {{ element.name }} {{ index }}
            </div>
          </draggable>
        </div>

        <div class="col-8">
          <h3 class="d-flex justify-center">Choice</h3>
          <draggable
            class="row wrap fill-height align-center sortable-list"
            :list="choicelist"
            group="people"
          >
            <div class="list-group-item" v-for="(element, index) in choicelist" :key="element.name">
              {{ element.name }} {{ index }}
            </div>
          </draggable>
        </div>
      </div>
    </div>
    <b-button v-for="(item, index) in video" :key="index" @click="play(index)">{{
      item.eng
    }}</b-button>
  </div>
</template>
<script>
import draggable from 'vuedraggable';

export default {
  name: 'Write',
  components: {
    draggable,
  },
  props: {
    index: {
      type: String,
      default: '',
    },
  },
  created() {
    this.answer = [];
    this.choicelist = [];
    const list = this.video[0].kor.split(' ');
    for (let i = 0; i < list.length; i += 1) {
      this.answer.push({ name: list[i], id: i });
      this.choicelist.push({ name: list[i], id: i });
    }
    this.choicelist = this.shuffle(this.choicelist);
    this.checklist = [];
  },
  mounted() {},
  data() {
    return {
      url: 'mLx7D98zP_A',
      replay: 0,
      video: [
        {
          starttime: '00:00:00,001',
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
        modestbranding: 1,
        autoplay: 1,
        controls: 0,
        loop: 1,
        fs: 0,
        rel: 1,
        showinfo: 0,
        playlist: '',
      },
      checklist: [
        { name: 'John', id: 1 },
        { name: 'Joao', id: 2 },
        { name: 'Jean', id: 3 },
        { name: 'Gerard', id: 4 },
      ],
      choicelist: [
        { name: 'Juan', id: 5 },
        { name: 'Edgard', id: 6 },
        { name: 'Johnson', id: 7 },
      ],
      answer: [
        { name: 'Jean', id: 3 },
        { name: 'Gerard', id: 4 },
      ],
      fail: false,
    };
  },
  computed: {
    player() {
      return this.$refs.youtube.player;
    },
  },
  watch: {
    checklist() {
      console.log(this.checklist);
      console.log(this.choicelist);
      console.log(this.answer);
      let tmp = true;
      if (this.checklist.length === 0 || this.checklist.length !== this.answer.length) {
        tmp = false;
      }
      for (let i = 0; i < this.checklist.length; i += 1) {
        if (this.answer[i].id !== this.checklist[i].id) {
          tmp = false;
          break;
        }
      }
      console.log(tmp);
      this.fail = tmp;
    },
  },
  methods: {
    play() {
      console.log(this.replay);
      const start = this.timer(this.video[this.replay].starttime);
      const end = this.timer(this.video[this.replay].endtime);
      console.log(start, end);
      this.player.loadVideoById({
        videoId: this.url,
        startSeconds: start,
        endSeconds: end,
        suggestedQuality: 'default',
      });
      console.log(this.fail);
      if (this.fail === true) {
        this.replay += 1;
        this.fail = false;
        this.choicelist = [];
      }
    },
    playVideo() {
      this.play();
    },
    async stopVideo() {
      await this.player.pauseVideo();
    },
    timer(input) {
      const hms = input;
      const a = hms.split(':');
      const s = a[2].split(',');
      const ms = Number(a[0] * 60 * 60) + Number(a[1] * 60) + Number(s[0]) + Number(s[1] / 1000);
      return ms;
    },
    onEnded() {
      this.play();
    },
    shuffle(array) {
      let currentIndex = array.length;
      let temporaryValue;
      let randomIndex;
      const tmp = array;
      // While there remain elements to shuffle...
      while (currentIndex !== 0) {
      //   // Pick a remaining element...
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;
        //   // And swap it with the current element.
        temporaryValue = tmp[currentIndex];
        tmp[currentIndex] = tmp[randomIndex];
        tmp[randomIndex] = temporaryValue;
      }
      return tmp;
    },
  },
};
</script>
<style>
iframe {
  width: 60%;
  height: 50vh;
  /* max-width: 1000px; Also helpful. Optional. */
}
</style>
