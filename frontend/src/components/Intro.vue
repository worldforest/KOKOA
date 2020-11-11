<template>
  <div>
    <!-- <v-btn @click='$refs.fullpage.api.moveTo(1, 1)' class='top-button'>👆</v-btn> -->
    <full-page :options="options" id="fullpage" ref="fullpage">
      <div class="section s0">
        <h3>Learn Korean</h3>
        <h3>with K-pop Artist</h3>
      </div>
      <div class="section s1">
        <h3>Select Your Artist</h3>
      </div>
      <div class="section s2">
        <div class="slide">
          <h3>Speaking</h3>
          <h3 style="font-size: 50px; margin-top:20px;">Check your pronunciation</h3>
        </div>
      </div>
      <div class="section s3">
        <div class="slide">
          <h3>Listening</h3>
          <h3>&</h3>
          <h3>Writing</h3>
          <h3 style="font-size: 50px;">Matching Sentences</h3>
        </div>
      </div>
      <div class="section s4">
        <h3>Let's go to learn</h3>
        <h3>with K-pop Artist</h3>
        <button
          style="margin:30px; color:white; font-size:30px; position: relative;" @click="getAuth()">
          <img src="@/assets/google.png" alt="구글로그인버튼" style="width:30px" />
          Join with Google
        </button>
      </div>
    </full-page>

    <ul class="actions">
      <li @click="$refs.fullpage.api.silentMoveTo(5, 5)" class="actions-button">go to learn 📚</li>
      <!-- <li @click="$refs.fullpage.api.moveTo(1, 1)" class='top-button'>👆</li> -->
    </ul>
    <ul @click="$refs.fullpage.api.moveTo(1, 1)" class='top-button'>👆</ul>
  </div>
</template>
<script>
import Vue from 'vue';
import 'fullpage.js/vendors/scrolloverflow'; // Optional. When using scrollOverflow:true
import VueFullPage from 'vue-fullpage.js';
import { mapActions } from 'vuex';

Vue.use(VueFullPage);

export default {
  name: 'Intro',
  data() {
    return {
      options: {
        afterLoad: this.afterLoad,
        navigation: false,
      },
    };
  },
  methods: {
    ...mapActions(['login']),
    async getAuth() {
      await this.$gAuth
        .signIn()
        .then((data) => {
          this.email = data.vt.bu; // 이메일
          this.$store.dispatch('login', this.email);
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>

<style scoped>
@charset 'ISO-8859-1';
/* Custom
 * --------------------------------------- */
body {
  font-family: arial, helvetica;
}
.section {
  position: relative;
  text-align: center;
}
.s0 {
  background: url(https://www.c-mw.net/wp-content/uploads/2017/06/Seoul-City-Landscape-CNEW-1600x1000.jpeg);
  background-size: cover;
  position: relative;
}
.s0::before {
  content: "";
  opacity: 0.5;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-color: #000;
}
.section h3 {
  position: relative;
  font-size: 6rem;
}
.s1 {
  background: url(https://static-assets.hallyulife.com/kpop-girl-group-reputation-may-2020.jpg);
  background-size: cover;
  position: relative;
}
.s1::before {
  content: "";
  opacity: 0.5;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-color: #000;
}
.s2 {
  background: url(https://ieltsprofi.com/blog/wp-content/uploads/2020/08/speaking_part1-scaled-6.jpg);
  background-size: cover;
  position: relative;
}
.s2::before {
  content: "";
  opacity: 0.5;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-color: #000;
}
.s3 {
  background: url(https://d2wldp4wpenmih.cloudfront.net/wp-content/uploads/2018/11/effective-listening-skills.jpg);
  background-size: cover;
  position: relative;
}
.s3::before {
  content: "";
  opacity: 0.5;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-color: #000;
}
.s4{
  background-image: url(https://thewritelife.com/wp-content/uploads/2019/08/How-to-format-a-book.jpg);
  background-size: cover;
  position: relative;
}
.s4::before{
  content: "";
  opacity: 0.5;
  position: absolute;
  top: 0px;
  left: 0px;
  right: 0px;
  bottom: 0px;
  background-color: #000;
}
#section-1 h2 {
  color: #fff;
  font-size: 10em;
  font-weight: 900;
}
#section-1 h1 {
  font-size: 2em;
  font-weight: 100;
  -webkit-font-smoothing: antialiased;
  -moz-font-smoothing: antialiased;
  margin: 1.5em auto 1em auto;
  color: #35495e;
  padding-right: 30px;
  padding-left: 30px;
}
#section-1 li {
  display: inline-block;
  margin: 1.25em 0.3em;
}
.section-1-button {
  padding: 0.93em 1.87em;
  background: #35495e;
  border-radius: 5px;
  display: block;
  color: #fff;
}
h3 {
  font-size: 5em;
  text-align: center;
  color: #fff;
  font-weight: bold;
}
/* Actions buttons
 * --------------------------------------- */
.actions {
  position: fixed;
  bottom: 2%;
  margin: 0 auto;
  z-index: 99;
  left: 0;
  right: 0;
  text-align: center;
}
.actions li {
  display: inline;
  margin: 0.1em 0.1em;
  font-size: 2.5em;
  font-family: "Lobster", cursive;
}
.actions-button {
  padding: 0.23em 1.07em;
  background: rgba(245, 128, 11, 0.47);
  border-radius: 5px;
  display: block;
  color: #fff;
  cursor: pointer;
}
.top-button{
  font-size: 5vw;
  position: fixed;
  top: 2%;
  right: 20px;
  cursor: pointer;
}
</style>
