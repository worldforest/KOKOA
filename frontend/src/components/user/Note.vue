<template>
  <div>
    <div
      class="bookmarks bookmark-top"
      @click="type = 0;"
      :class="{ expandbmk: type === 0, basic: type !== 0 }"
    >
      Speaking
    </div>
    <div
      class="bookmarks bookmark-bottom"
      @click="type = 1;
      "
      :class="{ expandbmk: type === 1, basic: type !== 1 }"
    >
      Writing
    </div>
    <div class="paper">
      <div class="lines">
        <div class="text">
          <!-- contenteditable spellcheck="false"-->
          <h2>Note - Review your incorrect sentences.</h2>
          <br />
          <!-- 문장 목록 -->
          <div v-if="type === 0">
            <h3>[Speaking]</h3>
            <div v-for="(item, index) in speechnote" :key="index" style="">
              <div
                class="sentences"
                @click="expanded === index ? (expanded = -1) : setToTop(index)"
                :id="'sentence' + index"
              >
                ({{ index + 1 }}) {{ item.content }}
              </div>
              <div v-if="expanded === index">
                <Talk
                :type="'note'"
                :noteitem="item"
                style="background: lightgoldenrodyellow; z-index:2" />
              </div>
            </div>
          </div>
          <!-- writing 문장 목록 -->
          <div v-else>
            <h3>[Writing]</h3>
            <div v-for="(item, index) in writenote" :key="index" style="">
              <div
                class="sentences"
                @click="expanded === index ? (expanded = -1) : setToTop(index)"
                :id="'sentence' + index"
              >
                ({{ index + 1 }}) {{ item.content }}
              </div>
              <div v-if="expanded === index">
                <Write
                style="background: lightgoldenrodyellow; z-index:2" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="holes hole-top"></div>
      <div class="holes hole-middle"></div>
      <div class="holes hole-bottom"></div>
    </div>
  </div>
</template>

<script>
import http from '../../util/http-common';
import Talk from '../training/Talk.vue';
import Write from '../training/Write.vue';

export default {
  name: 'Note',
  components: {
    Talk,
    Write,
  },
  data: () => ({
    email: '',
    singleExpand: true,
    itemsPerPage: 4,
    speechnote: [],
    writenote: [],
    expanded: -1,
    type: 0,
  }),
  async created() {
    this.email = this.$store.state.email;
    await this.getData();
    // this.answer = this.video[0].kor.replace(/[&/\\#,+()$~%.'":*?!<>{}]/g, ' ');
  },
  methods: {
    setToTop(index) {
      const elmnt = document.getElementById(`sentence${index}`);
      elmnt.scrollIntoView(true);
      this.expanded = index;
    },
    async getData() {
      this.speechnote = [];
      this.writenote = [];
      await http.get('/note/load/', { params: { email: this.email } }).then((res) => {
        for (let i = 0; i < res.data.speechnote[0].length; i += 1) {
          this.speechnote.push({
            id: res.data.speechnote[0][i].id,
            subtitleid: res.data.speechnote[0][i].subtitleid,
            videoid: res.data.speechnote[0][i].videoid,
            content: res.data.speechnote_sub[0][i].content,
            starttime: res.data.speechnote_sub[0][i].starttime,
            endtime: res.data.speechnote_sub[0][i].endtime,
          });
        }

        for (let i = 0; i < res.data.writenote[0].length; i += 1) {
          this.writenote.push({
            id: res.data.writenote[0][i].id,
            subtitleid: res.data.writenote[0][i].subtitleid,
            videoid: res.data.writenote[0][i].videoid,
            content: res.data.writenote_sub[0][i].content,
            starttime: res.data.writenote_sub[0][i].starttime,
            endtime: res.data.writenote_sub[0][i].endtime,
          });
        }
      });
    },
  },
};
</script>

<style>
@import url(https://fonts.googleapis.com/css?family=Indie+Flower);
.paper {
  position: absolute;
  width: 90%;
  height: 90%;
  background: lightgoldenrodyellow;
  margin: -350px -25% 30px;
  left: 30%;
  top: 50%;
  box-shadow: 0px 0px 5px 0px #888;
  overflow: hidden;
}
.paper::before {
  content: "";
  position: absolute;
  left: 45px;
  height: 100%;
  width: 2px;
  background: rgba(255, 0, 0, 0.4);
}
.lines {
  margin-top: 40px;
  height: calc(100% - 40px);
  width: 100%;
  background-image: repeating-linear-gradient(
    lightgoldenrodyellow 0px,
    lightgoldenrodyellow 24px,
    steelblue 25px
  );
}
.text {
  position: absolute;
  top: 65px;
  left: 55px;
  bottom: 10px;
  right: 10px;
  line-height: 25px;
  font-family: "Indie Flower";
  outline: none;
  overflow: auto;
}
.holes {
  position: absolute;
  left: 10px;
  height: 25px;
  width: 25px;
  background: rgb(250, 250, 247);
  border-radius: 50%;
  box-shadow: inset 0px 0px 2px 0px #888;
}
.hole-top {
  top: 10%;
}
.hole-middle {
  top: 50%;
}
.hole-bottom {
  bottom: 10%;
}
.sentences {
  cursor: pointer;
  font-size: 20px;
  margin: 10px;
}
.bookmarks {
  position: absolute;
  height: 128px;
  box-shadow: inset 0px 0px 2px 0px #888;
  cursor: pointer;
  writing-mode: vertical-lr;
  text-align: center;
}
.bookmark-top {
  top: 7%;
  background: rgb(233, 103, 131);
}
.bookmark-bottom {
  top: 25%;
  background: rgb(85, 219, 163);
}
.expandbmk {
  width: 7vw;
  left: 1%;
  font-size: 2vw;
  padding-left: 0.5vw;
}
.basic {
  width: 3vw;
  left: 2%;
  font-size: 1.6vw;
  padding-left: 0.3vw;
}
</style>
