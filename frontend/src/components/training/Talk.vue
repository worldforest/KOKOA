<template>
  <v-row style="margin-top: 100px;">
    <!-- left side -->
    <v-col class="youtubeContainer" cols="12" lg="8">
      <div class="d-flex justify-center mt-3">
        <youtube :video-id="url" ref="youtube" :player-vars="playerVars" flex fit-parent></youtube>
      </div>
      <div class="d-flex justify-space-around my-5">
        <v-btn @click="previous" icon>
          <v-icon color="white">
            mdi-arrow-left
          </v-icon>
        </v-btn>
        <b-button class="mx-5" variant="success" @click="playVideo">play</b-button>
        <v-btn @click="next" icon>
          <v-icon color="white">
            mdi-arrow-right
          </v-icon>
        </v-btn>
      </div>
      <div class="myTitle d-flex justify-space-around my-5" :class="{ note: type === 'note' }">
        {{ answer }}
      </div>
    </v-col>
    <!-- right side -->
    <v-col class="testContainer" cols="12" lg="4">
      <v-row class="d-flex justify-center ma-5">
        <v-col cols="12">
          <div>
            <Record @child-event="receiveText" />
          </div>
          <!-- 나의 발음 -->
          <h2 class="myTitle my-5" id="result" :class="{ note: type === 'note' }">
            <!-- {{ speechText }} -->
          </h2>
          <div class="d-flex justify-space-around">
            <v-btn class="ma-2" text icon color="purple lighten-2" @click="insertNote">
              <v-icon>mdi-clipboard-edit-outline</v-icon>
              Add to<br />Note
            </v-btn>
            <div class="speech-bubble">
              Focus on the marked area<br />and try to pronounce it :)
            </div>
          </div>
          <h4 class="myTitle d-flex justify-space-around" :class="{ note: type === 'note' }">
            {{ romaza }}
          </h4>
          <div class="py-5" style="background:purple" v-for="(item, index) in dict" :key="index">
            {{ item.kor + " " + item.eng + " " + item.dfn }}
          </div>
        </v-col>
      </v-row>
    </v-col>
  </v-row>
</template>
<script>
import Record from './Record.vue';
import http from '../../util/http-common';

export default {
  name: 'Talk',
  components: {
    Record,
  },
  props: ['type', 'noteitem'],
  async created() {
    if (this.type !== 'note') {
      await this.getData();
      this.answer = this.video[0].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
    } else {
      this.id = this.noteitem.videoid;
      await http.get('/search/video/', { params: { id: this.id } }).then((res) => {
        this.url = res.data.url;
      });
      this.video.push({
        starttime: this.noteitem.starttime,
        endtime: this.noteitem.endtime,
        kor: this.noteitem.content,
        subtitleid: this.noteitem.subtitleid,
      });
      this.answer = this.video[0].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
    }

    const start = this.timer(this.video[0].starttime);
    const end = this.timer(this.video[0].endtime);
    this.player.loadVideoById({
      videoId: this.url,
      startSeconds: start,
      endSeconds: end,
      suggestedQuality: 'default',
    });
  },
  computed: {
    player() {
      return this.$refs.youtube.player;
    },
  },
  data() {
    return {
      id: this.$route.query.index,
      speechText: '',
      url: '',
      replay: 0,
      video: [],
      playerVars: {
        modestbranding: 1,
        controls: 0,
        loop: 1,
        fs: 0,
        rel: 1,
        showinfo: 0,
        playlist: '',
        cc_load_policy: 0,
      },
      answer: '',
      answerTrim: '',
      romaza: '',
      dict: [],
    };
  },
  methods: {
    play() {
      const start = this.timer(this.video[this.replay].starttime);
      const end = this.timer(this.video[this.replay].endtime);
      this.player.loadVideoById({
        videoId: this.url,
        startSeconds: start,
        endSeconds: end,
        suggestedQuality: 'default',
      });
    },
    playVideo() {
      this.play();
    },
    timer(input) {
      const hms = input.replace(/'/g, '');
      const a = hms.split(':');
      const s = a[2].split(',');
      let ms = Number(a[0] * 60 * 60) + Number(a[1] * 60) + Number(s[0]) + Number(s[1] / 1000);
      if (ms === 0) {
        ms += 0.001;
      }
      return ms;
    },
    previous() {
      if (this.replay > 0) {
        this.replay -= 1;
      }
    },
    next() {
      if (this.replay < this.video.length - 1) {
        this.replay += 1;
      }
    },
    async receiveText(text) {
      this.dict = [];
      const tmp = this.answer;
      this.answerTrim = tmp
        .replace(/[&/\\#,+\-()$~%@.'":*?!<>{}[]]/g, ' ')
        .replace('[', '')
        .replace(']', '');
      await http.get('/subtitle/roma', { params: { word: this.answerTrim } }).then((res) => {
        this.romaza = res.data;
      });
      this.speechText = text.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      const { subtitleid } = this.video[this.replay];
      await http.get('/subtitle/dict', { params: { subtitleid } }).then((res) => {
        for (let i = 0; i < res.data.length; i += 1) {
          this.dict.push(res.data[i]);
        }
      });
    },
    async getData() {
      this.video = [];
      await http.get('/search/video/', { params: { id: this.id } }).then((res) => {
        this.url = res.data.url;
        for (let i = 0; i < res.data.Korean.length; i += 1) {
          this.video.push({
            starttime: res.data.Korean[i].starttime,
            endtime: res.data.Korean[i].endtime,
            eng: res.data.English[i].content,
            kor: res.data.Korean[i].content,
            subtitleid: res.data.Korean[i].id,
          });
        }
      });
    },
    insertNote() {
      http
        .post('/note/insert/', {
          params: {
            email: this.$store.state.email,
            subtitleid: this.video[this.replay].subtitleid,
            type: 0,
            videoid: this.id,
          },
        })
        .then((res) => {
          console.log(res);
        });
    },
  },
  watch: {
    replay() {
      this.answer = '';
      this.answer = this.video[this.replay].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, '');
      this.play();
    },
    speechText() {
      const answerTrimTrim = this.answerTrim.replaceAll(' ', '');
      const speechTextTrim = this.speechText.replaceAll(' ', '');
      const pos = document.getElementById('result');
      pos.innerHTML = '';
      let text;
      let container;
      let i;
      for (i = 0; i < answerTrimTrim.length; i += 1) {
        container = document.createElement('font');
        if (speechTextTrim.length > i) {
          if (speechTextTrim.charAt(i) === answerTrimTrim.charAt(i)) {
            text = document.createTextNode(speechTextTrim.charAt(i));
            container.style.color = 'blue';
          } else {
            text = document.createTextNode(speechTextTrim.charAt(i));
            container.style.color = 'red';
          }
        } else {
          text = document.createTextNode(speechTextTrim.charAt(i));
          container.style.color = 'red';
          container.appendChild(text);
          pos.appendChild(container);
          break;
        }
        container.appendChild(text);
        pos.appendChild(container);
      }
      const left = speechTextTrim.substr(i);
      for (i = 0; i < left.length; i += 1) {
        container = document.createElement('font');
        text = document.createTextNode(left.charAt(i));
        container.style.color = 'red';
        container.appendChild(text);
        pos.appendChild(container);
      }
    },
  },
};
</script>
<style scoped>
font {
  padding: 0;
}
.myTitle {
  color: white;
  font-family: "Do Hyeon", sans-serif;
  font-size: 1.5em;
}
.myTitle * {
  font-family: "Do Hyeon", sans-serif;
  font-size: 1.5em;
}
.speech-bubble {
  position: relative;
  background: #cfcdce;
  border-radius: 0.4em;
}

.speech-bubble:after {
  content: "";
  position: absolute;
  left: 0;
  top: 50%;
  width: 0;
  height: 0;
  border: 10px solid transparent;
  border-right-color: #cfcdce;
  border-left: 0;
  margin-top: -10px;
  margin-left: -10px;
}

.note {
  color: black;
}
</style>
