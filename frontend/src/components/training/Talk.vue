<template>
  <v-row style="margin-top: 100px;">
    <!-- left side -->
    <v-col class="youtubeContainer" cols="12" lg="8">
      <div class="d-flex justify-center youtube pa-5">
        <youtube
          @playing="playing"
          @ended="ended"
          :video-id="url"
          ref="youtube"
          :player-vars="playerVars"
          flex
          fit-parent
        ></youtube>
        <div
          class="middle"
          :style="{
            backgroundColor: path === '/talk' ? '#1C1C1C' : 'lightgoldenrodyellow',
            opacity: screen === false ? 0 : 1.0
          }"
        >
          <div class="eng hoverTitle pa-5">Press Replay If you want retry!</div>
        </div>
      </div>
      <div class="d-flex justify-space-around mt-5">
        <v-btn v-if="this.current !== 0" @click="previous" icon>
          <v-icon color="white" style="font-size: 40px;">
            mdi-chevron-left
          </v-icon>
        </v-btn>
        <span v-else></span>
        <v-btn @click="playVideo" class="stickypink" icon>
          <v-icon v-show="!screen" style="font-size: 45px; margin:0.2em">
            mdi-play
          </v-icon>
          <v-icon v-show="screen" style="font-size: 45px; margin:0.2em">
            mdi-replay
          </v-icon>
          <span
            v-show="!screen"
            class="eng stickypink"
            :class="{ note: notemode }"
            style="font-size: 2em;"
            >PLAY</span
          >
          <span
            v-show="screen"
            class="eng stickypink"
            :class="{ note: notemode }"
            style="font-size: 2em;"
            >REPLAY</span
          >
        </v-btn>
        <v-btn v-if="this.current !== this.video.length - 1" @click="next" icon>
          <v-icon color="white" style="font-size: 40px;">
            mdi-chevron-right
          </v-icon>
        </v-btn>
        <span v-else></span>
      </div>
      <div class="myTitle d-flex justify-space-around my-5" :class="{ note: notemode }">
        {{ answer }}
      </div>
      <div class="myTitle d-flex justify-space-around my-5" :class="{ note: notemode }">
        {{ answerEng }}
      </div>
    </v-col>
    <!-- right side -->
    <v-col cols="12" lg="4">
      <v-row class="d-flex justify-center pa-5">
        <v-col cols="12">
          <div>
            <Record @child-event="receiveText" />
          </div>
          <!-- 나의 발음 -->
          <h2
            class="myTitle my-5"
            id="result"
            :class="{ note: notemode }"
            :style="{ 'border-color': notemode ? 'black' : 'white' }"
          >
            <!-- {{ speechText }} -->
          </h2>
          <div class="d-flex justify-space-around">
            <v-btn
              class="mb-5 eng stickypink"
              text
              icon
              style="font-size:20px;"
              @click="insertNote"
            >
              <v-icon class="mr-3" large>mdi-clipboard-edit-outline</v-icon>
              Add to Note
            </v-btn>
            <!-- <div class="speech-bubble">
              Focus on the marked area<br />and try to pronounce it :)
            </div> -->
          </div>

          <div class="py-5 dictionary" v-for="(item, index) in dict" :key="index">
            <h4 class="kor stickypink" style="display:inline">
              {{ item.kor }}
            </h4>
            <h5 class="eng note" style="display: inline">(eng) {{ item.eng }}</h5>
            <div class="eng note">
              {{ item.dfn }}
            </div>
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
  props: ['notemode', 'noteitem'],
  async created() {
    if (!this.notemode) {
      await this.getData();
      this.answer = this.video[0].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      this.answerEng = this.video[0].eng.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
    } else {
      this.id = this.noteitem.videoid;
      await http.get('/search/video/', { params: { id: this.id } }).then((res) => {
        this.url = res.data.url;
      });
      this.video.push({
        starttime: this.noteitem.starttime,
        endtime: this.noteitem.endtime,
        kor: this.noteitem.content,
        eng: this.noteitem.engcontent,
        subtitleid: this.noteitem.subtitleid,
        engsubtitleid: this.noteitem.engsubtitleid,
      });
      this.answer = this.video[0].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      this.answerEng = this.video[0].eng.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
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
      path: this.$route.path,
      screen: false,
      id: this.$route.query.index,
      speechText: '',
      url: '',
      current: 0,
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
      answerEng: '',
      answerTrim: '',
      romaza: '',
      dict: [],
    };
  },
  methods: {
    playing() {
      this.screen = false;
    },
    ended() {
      this.screen = true;
    },
    play() {
      const start = this.timer(this.video[this.current].starttime);
      const end = this.timer(this.video[this.current].endtime);
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
      if (this.current > 0) {
        this.current -= 1;
      }
    },
    next() {
      if (this.current < this.video.length - 1) {
        this.current += 1;
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
      const { subtitleid } = this.video[this.current];
      await http.get('/subtitle/dict', { params: { subtitleid } }).then((res) => {
        console.log(res);
        for (let i = 0; i < res.data.length; i += 1) {
          if (res.data[i].dfn !== 'Cannot find the meaning') {
            this.dict.push(res.data[i]);
          }
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
            engsubtitleid: res.data.English[i].id,
          });
        }
      });
    },
    insertNote() {
      const fd = new FormData();
      fd.append('email', this.$store.state.email);
      fd.append('subtitleid', this.video[this.current].subtitleid);
      fd.append('engsubtitleid', this.video[this.current].engsubtitleid);
      fd.append('type', 0);
      fd.append('videoid', this.id);
      http.post('/note/insert/', fd).then(() => {});
      this.$swal.fire({
        icon: 'success',
        title: '<span style="color:white">Saved</span>',
        timer: 2000,
        background: '#1C1C1C',
        backdrop: 'rgba(0,0,0,0.89)',
      });
    },
  },
  watch: {
    current() {
      this.answer = '';
      this.dict = '';
      this.answer = this.video[this.current].kor.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
      this.answerEng = this.video[this.current].eng.replace(/[&/\\#,+\-()$~%.'":*?!<>{}]/g, ' ');
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
      container.appendChild(document.createElement('br'));
      container.appendChild(document.createElement('br'));

      container = document.createElement('font');
      text = document.createTextNode(`[ ${this.romaza} ]`);
      if (this.notemode) {
        container.style.color = 'black';
      } else {
        container.style.color = 'white';
      }
      container.appendChild(text);
      pos.appendChild(container);
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
  padding: 10px;
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
  margin-top: -20px;
  margin-left: -10px;
}

.note {
  color: black;
}
.dictionary {
  background: lightgoldenrodyellow;
  padding-left: 5%;
}
.stickypink {
  color: rgb(233, 103, 131) !important;
}
.youtube{
  position: relative;
}
.middle {
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  width: 100%;
  opacity: 0;
  transition: 0.5s ease;
  position: absolute;
  /* background-color: rgba(0, 0, 0, 1); */
}

.hoverTitle {
  color: steelblue;
  font-size: 25px;
  position: absolute;
  text-align: center;
  width:100%;
  bottom:0;
}

</style>
