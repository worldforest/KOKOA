<template>
  <div>
    <div class="d-flex justify-center mt-3">
      <youtube
        :video-id="url"
        ref="youtube"
        :player-vars="playerVars"
        @ended="onEnded"
        flex
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
      <!-- <youtube
        :video-id="url"
        ref="youtube"
        :player-vars="playerVars"
        @ended="onEnded"
        flex
      ></youtube> -->

    <div>
      <draggable
        v-model="rows"
        class="row wrap fill-height align-center sortable-list"
        style="background: grey;"
      >
        <v-flex
          v-for="row in rows"
          :key="row.index"
          class="sortable"
          xs12
          my-2
          style="background: red"
        >
          <draggable
            :list="row.items"
            :group="{ name: 'row' }"
            class="row wrap justify-space-around"
          >
            <v-flex v-for="item in row.items" :key="item.title" xs4 pa-3 class="row-v">
              <v-card style="height: 100px;">{{ item.title }}</v-card>
            </v-flex>
          </draggable>
        </v-flex>
      </draggable>
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
  created() {},
  mounted() {},
  data() {
    return {
      url: 'mLx7D98zP_A',
      // origin: '?enablejsapi=1&origin=*',
      replay: 0,
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
        modestbranding: 1,
        autoplay: 1,
        controls: 0,
        loop: 1,
        fs: 0,
        rel: 1,
        showinfo: 0,
        playlist: '',
      },
      enabled: true,
      rows: [
        {
          index: 1,
          items: [
            {
              title: 'item 1',
            },
          ],
        },
        {
          index: 2,
          items: [
            {
              title: 'item 2',
            },
            {
              title: 'item 3',
            },
          ],
        },
      ],
    };
  },
  computed: {
    player() {
      return this.$refs.youtube.player;
    },
  },
  methods: {
    async play() {
      const start = this.timer(this.video[this.replay].starttime);
      const end = this.timer(this.video[this.replay].endtime);
      await this.player.loadVideoById({
        videoId: this.url,
        startSeconds: start,
        endSeconds: end,
        suggestedQuality: 'default',
      });
      this.replay += 1;
    },
    async playVideo() {
      await this.play();
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
      this.play(this.replay);
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
