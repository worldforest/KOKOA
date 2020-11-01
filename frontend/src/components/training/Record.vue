<template>
  <span>
    <v-btn v-show="btn" @click="start" block round color="primary" dark>
    <v-icon left>mic</v-icon> Recording</v-btn>
    <v-btn v-show="btnStop" @click.native="stopRecording" block round color="error" dark>
    <v-icon left>stop</v-icon> Stop</v-btn>
    <!-- <p v-for="word in transcription">{{ word }}</p> -->
    <!-- <h1>발음 : {{ finalTranscript }}</h1> -->
  </span>
</template>
<script>
window.SpeechRecognition = window.SpeechRecognition || window.webkitSpeechRecognition;

const recognition = new window.SpeechRecognition();
recognition.continuous = true;
recognition.interimResults = true;

export default {
  name: 'Record',
  mounted() {
    let ref = this;
    /**
    * 음성 인식 시작 처리
    */
    recognition.onstart = function () {
      console.log('onstart');
      this.isRecognizing = true;
      // $btnMic.className = 'on';
    };

    /**
    * 음성 인식 종료 처리
    */
    recognition.onend = function () {
      console.log('onend');
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
      if (typeof event.results === 'undefined') {
        recognition.onend = null;
        recognition.stop();
        return;
      }

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
      console.log('onerror', event);

      if (event.error.match(/no-speech|audio-capture|not-allowed/)) {
        this.ignoreEndProcess = true;
      }

      // $btnMic.className = 'off';
    };
  },
  computed: {},
  data() {
    return {
      btn: true,
      btnStop: true,
      isRecognizing: false,
      ignoreEndProcess: false,
      finalTranscript: '',
    };
  },
  methods: {
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
    },
    stopRecording() {
      recognition.stop();
    }

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

  .red {
    background: red;
  }
  .blue {
    background: blue;
    color: #fff !important;
  }
  .green {
    background: green;
  }
  .yellow {
    background: yellow;
  }
  .orange {
    background: orange;
  }
  .grey {
    background: grey;
  }
  .gold {
    background: gold;
  }
  .white {
    background: white;
  }
  .black {
    background: black;
    color: #fff !important;
  }
  .visible {
    display: block !important;
  }
</style>
