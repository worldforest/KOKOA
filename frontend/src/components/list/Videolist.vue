<template>
  <section>
    <b-container fluid>
      <b-row>
        <b-col sm="12" class="overflow-hidden">
          <div class="d-flex align-items-center justify-content-between">
            <h4 class="main-title">{{option===false ? "Speaking":"Dictation"}}</h4>
          </div>
          <div class="upcoming-contens ma-5">
            <VueSlickCarousel v-bind="settings">
              <img
                v-for="(item, index) in items"
                :src="'http://img.youtube.com/vi/' + item.url + '/0.jpg'"
                :key="index"
                @click="showModal(index)"
                class="pa-1"
              />
              <b-modal v-model="modalShow" centered>
                <p class="my-4">{{ selected }}</p>
                <b-button @click="goWrite">Writing</b-button>
                <b-button @click="goTalk">Speaking</b-button>
              </b-modal>
            </VueSlickCarousel>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </section>
</template>
<script>
// import axios from 'axios';
import VueSlickCarousel from 'vue-slick-carousel';
import 'vue-slick-carousel/dist/vue-slick-carousel.css';
import 'vue-slick-carousel/dist/vue-slick-carousel-theme.css';
import http from '../../util/http-common';
import channelList from '../core/channelList.json';

export default {
  name: 'Popular',
  components: { VueSlickCarousel },
  mounted() {},
  data() {
    return {
      items: [],
      groupid: this.$route.query.groupid,
      teams: channelList,
      team: '',
      page: 0,
      option: false,
      settings: {
        arrows: true,
        infinite: true,
        // focusOnSelect: true,
        speed: 3000,
        slidesToShow: 3,
        slidesToScroll: 3,
        rows: 2,
      },
      settings2: {
        arrows: true,
        infinite: true,
        // variableWidth: true,
        adaptiveHeight: true,
        speed: 3000,
        slidesToShow: 10,
        slidesToScroll: 10,
      },
      selected: '',
      modalShow: false,
    };
  },
  async created() {
    this.team = this.teams[this.groupid - 1].title;
    this.items = [];
    const pages = await this.getTotalPages(this.team);
    await this.getList(pages, this.team, this.items);
  },
  methods: {
    showModal(index) {
      this.selected = index;
      this.modalShow = true;
    },
    goWrite() {
      this.$router.push({ name: 'Write', params: { index: String(this.selected) } });
    },
    goTalk() {
      this.$router.push({ name: 'Talk', params: { index: String(this.selected) } });
    },
    getTotalPages(team) {
      return new Promise((resolve) => {
        http.get(`/search/idol/0?groupname=${team}`).then((res) => {
          resolve(res.data.totalPages);
        });
      });
    },
    async getList(page, team, items) {
      for (let i = 0; i < page; i += 1) {
        // eslint-disable-next-line no-await-in-loop
        await http.get(`/search/idol/${i}?groupname=${team}`).then((res) => res.data.content.forEach((element) => {
          items.push(element);
        }));
      }
    },
  },
};
</script>
<style>
.slick-prev:before {
  color: red !important;
  background-color: #eee;
}
.slick-next:before {
  color: red !important;
  background-color: #eee;
}
.choice img {
  height: 100%;
}
</style>
