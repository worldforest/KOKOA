<template>
  <div>
    <span @click="clickRCBtn(audioFlag)" style="width:64px; height:64px;">
      <vue-record-audio  @result="onResult" mode="press" />
    </span>
    <audio style="width:70%; margin-left:7%;" :src="audioSrc" controls="controls"></audio>
    <!-- <p v-for="word in transcription">{{ word }}</p> -->
    <!-- <h1>발음 : {{ finalTranscript }}</h1> -->
  </div>
</template>
<script>
import Vue from 'vue';
import VueRecord from '@codekraft-studio/vue-record';

Vue.use(VueRecord);

window.SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

const recognition = new window.SpeechRecognition();
recognition.continuous = true;
recognition.interimResults = true;

export default {
  name: 'Record',
  data() {
    return {
      btn: true,
      btnStop: true,
      isRecognizing: false,
      ignoreEndProcess: false,
      finalTranscript: '',
      myRecord: true,
      audioSrc: '',
      audioFlag: false,
    };
  },
  mounted() {
    const ref = this;
    /**
    * 음성 인식 시작 처리
    */
    recognition.onstart = function () {
      // console.log('onstart');
      this.isRecognizing = true;
      // $btnMic.className = 'on';
    };

    /**
    * 음성 인식 종료 처리
    */
    recognition.onend = function () {
      // console.log('onend');
      this.isRecognizing = false;

      if (this.ignoreEndProcess) {
        return false;
      }
      // DO end process
      // $btnMic.className = 'off';
      if (!this.finalTranscript) {
        // console.log('empty finalTranscript');
        return false;
      }
      return true;
    };

    /**
     * 음성 인식 결과 처리
     */
    recognition.onresult = function (event) {
      // console.log('onresult', event);
      // const interimTranscript = '';
      // if (typeof event.results === 'undefined') {
      //   recognition.onend = null;
      //   recognition.stop();
      //   return;
      // }

      for (let i = event.resultIndex; i < event.results.length; i += 1) {
        let transcript = '';
        transcript = event.results[i][0].transcript;

        if (event.results[i].isFinal) {
          ref.finalTranscript = transcript;
          ref.$emit('child-event', transcript);
        } else {
          // this.interimTranscript += transcript;
        }
        transcript = '';
      }
      // this.finalTranscript = capitalize(this.finalTranscript);
      // final_span.innerHTML = linebreak(finalTranscript);
      // interim_span.innerHTML = linebreak(interimTranscript);
      // console.log('finalTranscript', this.finalTranscript);
      // console.log('interimTranscript', this.interimTranscript);
      // fireCommand(interimTranscript);
    };

    /**
     * 음성 인식 에러 처리
     */
    recognition.onerror = function (event) {
      // console.log('onerror', event);
      if (event.error.match(/no-speech|audio-capture|not-allowed/)) {
        this.ignoreEndProcess = true;
      }
      // $btnMic.className = 'off';
    };
  },
  computed: {},
  methods: {
    clickRCBtn(flag) {
      if (!flag) {
        this.start();
        this.audioFlag = true;
      } else {
        this.stopRecording();
        this.audioFlag = false;
      }
    },
    start() {
      if (this.isRecognizing) {
        recognition.stop();
        return;
      }
      recognition.lang = 'ko-KR';
      recognition.start();
      // this.ignoreEndProcess = false;
      // this.finalTranscript = '';
      // final_span.innerHTML = '';
      // interim_span.innerHTML = '';
      //
    },
    stopRecording() {
      recognition.stop();
    },
    onResult(data) {
      // console.log('The blob data:', data);
      // console.log('Downloadable audio', window.URL.createObjectURL(data));
      this.audioSrc = window.URL.createObjectURL(data);
    },

  },
};
</script>
<style>
  #result {
  margin-bottom: 20px;
  padding: 10px 20px;
  height: 150px;
  border: solid 1px #000;
  border-radius: 6px;
  }
  #result span {
    line-height: 1.5;
  }

  #btn-mic {
    text-align: center;
    min-width: 160px;
  }
  #btn-mic span {
    display: inline-block;
    margin: 1px 0 0 5px;
    width: 10px;
    height: 10px;
    border: solid 1px #fff;
    background: #bbb;
    border-radius: 50%;
  }
  #btn-mic.on span {
    background: green;
  }
  #icon-music {
    display: none;
    position: absolute;
    top: 62px;
    right: 70px;
    font-size: 80px;
  }
  ul li {
    font-size: 1em;
  }
  .visible {
    display: block !important;
  }
</style>
