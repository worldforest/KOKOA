<template>
  <v-row style="margin-top:100px; margin-bottom: 100px;" @click="closeOverlay">
    <v-overlay
      :z-index="zIndex"
      :value="overlay"
      style="margin-top: 80px;"
      class="overlay">
      <p style="padding-left: 25%; font-size:30px;">
        1. Click Play Button <br>
        2. Drag word into Answer Box <br>
        3. Hint will help you :) <br>
        4. Click '>' Button If you want the next sentence <br>
      </p>
      <img src = "@/assets/tutorialwrite1.gif" class="gif-write">
    </v-overlay>
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
            backgroundColor: path === '/write' ? '#1C1C1C' : 'lightgoldenrodyellow',
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
        <v-btn @click="playVideo" color="rgb(73, 178, 134)" icon>
          <v-icon v-show="!screen" style="font-size: 45px; margin:0.2em">
            mdi-play
          </v-icon>
          <v-icon v-show="screen" style="font-size: 45px; margin:0.2em">
            mdi-replay
          </v-icon>
          <span v-show="!screen" class="eng" :class="{ note: notemode }" style="font-size: 2em;"
            >PLAY</span
          >
          <span v-show="screen" class="eng" :class="{ note: notemode }" style="font-size: 2em;"
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
    </v-col>
    <v-col cols="12" lg="4">
      <v-row class="d-flex justify-center px-5">
        <v-col cols="12" class="ma-0">
          <div class="answerDiv">
            <h3 class="eng writeTitle mr-5" :class="{ note: notemode }" style="margin-bottom: 1vw;">
              Answer
            </h3>
            <v-btn icon color="rgb(73, 178, 134)" @click="reset"
              ><v-icon class="restart" style="font-size: 2em;">mdi-replay</v-icon>
              <span class="eng" :class="{ note: notemode }" style="font-size: 1em;">RESET</span>
            </v-btn>
          </div>

          <draggable
            class="drag row wrap justify-center sortable-list"
            :style="{ 'border-color': notemode ? 'black' : 'white' }"
            :list="checklist"
            group="people"
          >
            <div class="kor wordblock" v-for="element in checklist" :key="element.name">
              {{ element.name }}
            </div>
          </draggable>
        </v-col>

        <v-col cols="12" class="ma-5">
          <h3
            class="eng writeTitle"
            :class="{ note: notemode }"
            style="margin-bottom: 1vw; display:inline"
          >
            Choice
          </h3>
          <div class="ml-10 mb-3 eng" style="display:inline">
            <v-btn icon @click="showhint = !showhint"
              ><v-icon class="mr-2" color="rgb(73, 178, 134)">fas fa-smile-wink</v-icon>
              <span class="eng" :class="{ note: notemode }" style="font-size: 1em;">HINT</span>
            </v-btn>
            <div v-show="showhint" class="speech-bubble my-3">{{ answerEng }}</div>
          </div>
          <draggable
            class="row wrap align-center justify-center sortable-list"
            :list="choicelist"
            group="people"
          >
            <div class="kor wordblock" v-for="element in choicelist" :key="element.name">
              {{ element.name }}
            </div>
          </draggable>
        </v-col>
        <v-btn icon class="question-btn" @click="question">
          <v-icon class="mr-2" color="rgb(73, 178, 134)"
          style="font-size:55px;">fa fa-question-circle</v-icon>
        </v-btn>
      </v-row>
    </v-col>
  </v-row>
</template>
<script>
import draggable from 'vuedraggable';
import http from '../../util/http-common';

export default {
  name: 'Write',
  components: {
    draggable,
  },
  props: ['notemode', 'noteitem'],
  async created() {
    this.answer = [];
    this.choicelist = [];
    this.answerEng = '';

    if (!this.notemode) {
      await this.getData();
    } else {
      this.id = this.noteitem.videoid;
      this.video = [];
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
    }
    const list = this.video[0].kor.replace(/[&/\\#,+()$~%.'":*?!<>{}]/g, '').split(' ');
    for (let i = 0; i < list.length; i += 1) {
      if (list[i].trim().length > 0) {
        this.answer.push({ name: list[i].trim(), id: i });
        this.choicelist.push({ name: list[i].trim(), id: i });
      }
    }
    this.choicelist = this.shuffle(this.choicelist);
    this.checklist = [];
    this.answerEng = this.video[0].eng.replace(/[&/\\#,+()$~%.'":*?!<>{}]/g, '');
  },
  data() {
    return {
      screen: false,
      id: this.$route.query.index,
      url: '',
      path: this.$route.path,
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
      checklist: [],
      choicelist: [],
      answer: [],
      fail: false,
      wrongAnswer: {
        icon: 'error',
        title: '<span style="color:white">Oops...</span>',
        html: '<span style="color:white">Something is Wrong!!</span>',
        timer: 5000,
        showDenyButton: true,
        confirmButtonText: 'Answer',
        denyButtonText: 'Retry',
        background: '#1C1C1C',
        backdrop: 'rgba(0,0,0,0.89)',
      },
      timerInterval: '',
      correctAnswer: {
        icon: 'success',
        title: 'Good Job!',
        text: "Let's go next sentence",
        timer: 1000,
        willOpen: () => {
          this.$swal.showLoading();
          this.timerInterval = setInterval(() => {}, 100);
        },
      },
      answerEng: '',
      overlay: true,
      zIndex: 10,
      flag: false,
      showhint: false,
    };
  },
  computed: {
    player() {
      return this.$refs.youtube.player;
    },
  },
  watch: {
    checklist() {
      let tmp = true;
      if (this.checklist.length === 0 || this.checklist.length !== this.answer.length) {
        tmp = false;
        return;
      }
      const n = this;
      for (let i = 0; i < this.checklist.length; i += 1) {
        if (this.answer[i].id !== this.checklist[i].id) {
          tmp = false;
          this.$swal.fire(n.wrongAnswer).then((result) => {
            if (result.isConfirmed) {
              this.$swal
                .fire({
                  title: `Answer is \n ${n.video[this.current].kor}`,
                  icon: 'info',
                  showDenyButton: true,
                  denyButtonText: 'Add to Note',
                })
                .then((res) => {
                  if (res.isDenied) {
                    this.insertNote();
                    this.$swal.fire('Saved!', '', 'success');
                  }
                });
            }
          });
          break;
        }
      }
      this.fail = tmp;
      if (this.fail === true) {
        this.$swal.fire(n.correctAnswer).then(() => {
          this.current += 1;
        });
      }
    },
    current() {
      this.showhint = false;
      this.answerEng = this.video[this.current].eng;
      this.fail = false;
      this.answer = [];
      this.choicelist = [];
      const list = this.video[this.current].kor.split(' ');
      for (let i = 0; i < list.length; i += 1) {
        if (list[i].trim().length > 0) {
          this.answer.push({ name: list[i].trim(), id: i });
          this.choicelist.push({ name: list[i].trim(), id: i });
        }
      }
      this.choicelist = this.shuffle(this.choicelist);
      this.checklist = [];
      this.play();
    },
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
    shuffle(array) {
      let currentIndex = array.length;
      let temporaryValue;
      let randomIndex;
      const tmp = array;
      while (currentIndex !== 0) {
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex -= 1;
        temporaryValue = tmp[currentIndex];
        tmp[currentIndex] = tmp[randomIndex];
        tmp[randomIndex] = temporaryValue;
      }
      return tmp;
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
    reset() {
      this.fail = false;
      this.answer = [];
      this.choicelist = [];
      const list = this.video[this.current].kor
        .replace(/[&/\\#,+()$~%.'":*?!<>{}]/g, '')
        .split(' ');
      for (let i = 0; i < list.length; i += 1) {
        this.answer.push({ name: list[i], id: i });
        this.choicelist.push({ name: list[i], id: i });
      }
      this.choicelist = this.shuffle(this.choicelist);
      this.checklist = [];
    },
    async getData() {
      this.video = [];
      await http.get('/search/video/', { params: { id: this.id } }).then((res) => {
        this.url = res.data.url;
        for (let i = 0; i < res.data.Korean.length; i += 1) {
          this.video.push({
            starttime: res.data.Korean[i].starttime,
            endtime: res.data.Korean[i].endtime,
            eng: res.data.English[i].content.replace(/[&/\\#,+()$~%.'":*?!<>{}]/g, ''),
            kor: res.data.Korean[i].content.replace(/[&/\\#,+()$~%.'":*?!<>{}]/g, ''),
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
      fd.append('type', 1);
      fd.append('videoid', this.id);
      http.post('/note/insert/', fd).then(() => {});
    },
    closeOverlay() {
      const OVERLAY = document.querySelector('.overlay');
      OVERLAY.style.opacity = 0;
      this.overlay = false;
      if (this.flag) {
        this.overlay = !this.overlay;
        OVERLAY.style.opacity = 1.0;
        this.flag = false;
      }
    },
    question() {
      this.flag = true;
    },
  },
};
</script>
<style scoped lang="scss">
$stickygreen: rgb(73, 178, 134);
$stickypink: rgb(233, 103, 131);

.drag {
  border: 1px solid white;
  padding: 1em;
}
.answerDiv {
  display: relative;
}
.answerDiv h3 {
  display: inline-block;
  margin: auto;
}
.writeTitle {
  color: white;
  /* display: flex; */
  align-content: center;
  justify-items: center;
  font-size: 3em;
}
.restart {
  height: 2vw;
  width: auto;
}
.wordblock {
  font-size: 1.5em;
  background-color: $stickygreen;
  padding: 1vw;
  border: 50%;
  margin: 0.5vw;
  border-radius: 30px;
}
.note {
  color: black;
}
.gif-write{
  width:55%;
  height:55vh;
  margin:0px auto;
  display:block;
  z-index:11;
}
.overlay{
  background-color:rgba(0, 0, 0, 0.702);
}
.speech-bubble {
  position: relative;
  background: $stickypink;
  opacity: 0.95;
  border-radius: 0.4em;
  padding: 10px;
}

.speech-bubble:after {
  content: "";
  position: absolute;
  left: 50%;
  top: 0;
  width: 0;
  height: 0;
  border: 20px solid transparent;
  border-bottom-color: $stickypink;
  opacity: 0.95;
  border-top: 0;
  border-left: 0;
  margin-left: -10px;
  margin-top: -20px;
}
.youtube {
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
  // background-color: rgba(0, 0, 0, 1);
}

.hoverTitle {
  color: steelblue;
  font-size: 25px;
  position: absolute;
  text-align: center;
  width: 100%;
  bottom: 0;
}
.question-btn {
  position: absolute;
  bottom: 8px;
  right: 10%;
}
</style>
