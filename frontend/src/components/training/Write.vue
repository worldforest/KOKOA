<template>
  <v-row style="margin-top:100px; margin-bottom: 100px;">
    <v-col class="youtubeContainer" cols="12" lg="8">
      <div class="d-flex justify-center mt-3 youtube">
        <youtube :video-id="url" ref="youtube" :player-vars="playerVars" flex fit-parent></youtube>
      </div>
      <div class="d-flex justify-space-around my-5">
        <v-btn v-if="this.current !== 0" @click="previous" icon>
          <v-icon color="white" style="font-size: 40px;">
            mdi-chevron-left
          </v-icon>
        </v-btn>
        <span v-else></span>
        <v-btn @click="playVideo" color="rgb(73, 178, 134)" icon>
          <v-icon style="font-size: 45px; margin:0.2em">
            mdi-play
          </v-icon>
          <span class="eng" :class="{ note: notemode }" style="font-size: 2em;">PLAY</span>
        </v-btn>
        <v-btn v-if="this.current !== this.video.length - 1" @click="next" icon>
          <v-icon color="white" style="font-size: 40px;">
            mdi-chevron-right
          </v-icon>
        </v-btn>
        <span v-else></span>
      </div>
    </v-col>
    <v-col class="testContainer" cols="12" lg="4">
      <v-row class="d-flex justify-center ma-5">
        <v-col cols="12" class="ma-5">
          <div class="answerDiv">
            <h3 class="eng writeTitle mr-5" :class="{ note: notemode }" style="margin-bottom: 1vw;">
              Answer
            </h3>
            <v-btn icon color="rgb(73, 178, 134)" @click="reset"
              ><v-icon class="restart" style="font-size: 2em;">mdi-replay</v-icon></v-btn
            >
            <span class="eng" :class="{ note: notemode }" style="font-size: 1em;">RESET</span>
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
          <h3 class="eng writeTitle" :class="{ note: notemode }" style="margin-bottom: 1vw;">
            Choice
          </h3>
          <draggable
            class="row wrap align-center justify-center sortable-list"
            :list="choicelist"
            group="people"
          >
            <div class="kor wordblock" v-for="element in choicelist" :key="element.name">
              {{ element.name }}
            </div>
          </draggable>
          <div class="d-flex justify-space-around my-5 eng" :class="{ note: notemode }">
            {{ answerEng }}
          </div>
        </v-col>
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
      this.answer.push({ name: list[i], id: i });
      this.choicelist.push({ name: list[i], id: i });
    }
    this.choicelist = this.shuffle(this.choicelist);
    this.checklist = [];
    this.answerEng = this.video[0].eng.replace(/[&/\\#,+()$~%.'":*?!<>{}]/g, '');
  },
  data() {
    return {
      id: this.$route.query.index,
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
      checklist: [],
      choicelist: [],
      answer: [],
      fail: false,
      wrongAnswer: {
        icon: 'error',
        title: 'Oops...',
        text: 'Something is Wrong!!',
        // timer: 2000,
        showDenyButton: true,
        confirmButtonText: 'Retry',
        denyButtonText: 'Answer and Save',
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
            if (result.isDenied) {
              this.$swal.fire('Answer is', n.video[this.current].kor, 'error');
              this.insertNote();
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
      this.answerEng = this.video[this.current].eng;
      this.fail = false;
      this.answer = [];
      this.choicelist = [];
      const list = this.video[this.current].kor.split(' ');
      for (let i = 0; i < list.length; i += 1) {
        this.answer.push({ name: list[i], id: i });
        this.choicelist.push({ name: list[i], id: i });
      }
      this.choicelist = this.shuffle(this.choicelist);
      this.checklist = [];
      this.play();
    },
  },
  methods: {
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
  },
};
</script>
<style scoped>
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
  background-color: rgb(73, 178, 134);
  padding: 1vw;
  border: 50%;
  margin: 0.5vw;
  border-radius: 30px;
}
.note {
  color: black;
}

</style>
