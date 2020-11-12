<template>
  <div class="px-10" style="margin-top:100px; margin-bottom: 100px;">
    <h1 class="eng">Last-visited Artist</h1>
    <v-layout wrap>
      <v-flex xs12 sm6 md4 lg3 xl2 v-for="(data, index) in groups" :key="index">
        <v-card class="scale ma-3" @click="enter(data.id)">
          <v-img aspect-ratio="1" :src="require(`@/assets` + data.img)" />
          <v-card-title class="temp justify-center">{{ data.title }}</v-card-title>
        </v-card>
      </v-flex>
    </v-layout>
    <h1 class="eng mt-10">Last-watched Videos</h1>
    <div class="upcoming-contents ma-5">
      <VueSlickCarousel v-bind="settings" v-if="items.length">
        <figure v-for="(item, index) in items" :key="index">
          <img
            :src="'https://img.youtube.com/vi/' + item.url + '/0.jpg'"
            @click="goTraining(item)"
            class="pa-1"
            :label="item.title"
          />
          <figcaption class="eng">{{ item.title }}</figcaption>
        </figure>
      </VueSlickCarousel>
    </div>
  </div>
</template>

<script>
import VueSlickCarousel from 'vue-slick-carousel';
import 'vue-slick-carousel/dist/vue-slick-carousel.css';
import 'vue-slick-carousel/dist/vue-slick-carousel-theme.css';
import { mapGetters } from 'vuex';
import http from '../../util/http-common';
import channelList from '../core/channelList.json';

export default {
  name: 'Review',
  components: { VueSlickCarousel },
  data() {
    return {
      items: [],
      groups: [],
      infos: [],
      tmp: channelList,
      settings: {
        // lazyload: 'ondemand',
        // fade: true,
        arrows: true,
        infinite: true,
        speed: 3000,
        slidesToShow: 3,
        slidesToScroll: 3,
        // rows: 2,
        responsive: [
          {
            breakpoint: 1440,
            settings: {
              slidesToShow: 2,
              slidesToScroll: 2,
              initialSlide: 2,
            },
          },
          {
            breakpoint: 960,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1,
            },
          },
        ],
      },
    };
  },
  computed: {
    ...mapGetters(['email']),
  },
  async created() {
    this.items = [];
    await http.get('/log/load', { params: { email: this.email } }).then((res) => {
      console.log(res);
      for (let i = 0; i < res.data.video[0].length; i += 1) {
        this.items.push(res.data.video[0][i]);
      }
      for (let i = 0; i < res.data.video[0].length; i += 1) {
        for (let j = 0; j < this.tmp.length; j += 1) {
          if (res.data.group[0][i] === this.tmp[j].groupname) {
            this.groups.push(this.tmp[j]);
          }
        }
      }
    });
  },
  methods: {
    async goTraining(item) {
      await http.get('/log/insert', {
        params: { videoid: Number(item.id), email: this.email, groupid: Number(this.groupid) },
      });
      if (this.choice === 'Speaking') {
        this.goTalk(item.id);
      } else {
        this.goWrite(item.id);
      }
    },
    goWrite(index) {
      this.$router.push({ name: 'Write', query: { index: String(index) } });
    },
    goTalk(index) {
      this.$router.push({ name: 'Talk', query: { index: String(index) } });
    },
    enter(id) {
      // let j;
      // for (let i = 0; i < this.tmp.length; i += 1) {
      //   if (this.tmp[i].title === id) {
      //     j = this.tmp[i];
      //     break;
      //   }
      // }
      this.$router.push({ name: 'Artist', query: { groupid: String(id) } });
    },
  },
};
</script>

<style scoped></style>
