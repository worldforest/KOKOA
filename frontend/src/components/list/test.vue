<template>
    <section>
        <h1>123</h1>

        <input type="file" id="audio-file" accept="audio/*" @change="setAudio">
    </section>
</template>
<script>
import axios from 'axios';

export default {
  name: 'Test',
  data() {
    return {

    };
  },
  methods: {
    setAudio(event) {
      const uploadSound = event.target.files[0];
      console.log(uploadSound);
      const frm = new FormData();
      frm.append('file', uploadSound);
      axios.post('http://localhost:8080/sst',
        frm).then((data) => {
        console.log(data);
      }).catch(() => {
        console.log('fail');
      });
    },
  },
};
</script>
