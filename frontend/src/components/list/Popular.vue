<template>
  <section>
    <b-container fluid>
      <b-row>
        <b-col sm="12" class="overflow-hidden">
          <div class="d-flex align-items-center justify-content-between">
            <h4 class="main-title">인기별</h4>
          </div>
          <div class="upcoming-contens ma-5">
            <VueSlickCarousel v-bind="settings">
              <img
                v-for="(item, index) in items"
                :src="item.url"
                :key="index"
                @click="showModal(index)"
              />
              <b-modal v-model="modalShow" centered>
                <p class="my-4">{{ selected }}</p>
                <b-button @click="goWrite">받아쓰기</b-button>
                <b-button @click="goTalk">말하기</b-button>
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

export default {
  name: 'Popular',
  components: { VueSlickCarousel },
  mounted() {},
  data() {
    return {
      items: [
        {
          url: 'http://img.youtube.com/vi/WotZgTcbHRA/0.jpg',
        },
        {
          url: 'http://img.youtube.com/vi/WotZgTcbHRA/0.jpg',
        },
        {
          url: 'http://img.youtube.com/vi/WotZgTcbHRA/0.jpg',
        },
        {
          url: 'http://img.youtube.com/vi/WotZgTcbHRA/0.jpg',
        },
        {
          url: 'http://img.youtube.com/vi/WotZgTcbHRA/0.jpg',
        },
        {
          url: 'http://img.youtube.com/vi/WotZgTcbHRA/0.jpg',
        },
      ],
      settings: {
        arrows: true,
        infinite: true,
        // focusOnSelect: true,
        speed: 2000,
        slidesToShow: 3,
        slidesToScroll: 3,
      },
      selected: '',
      modalShow: false,
    };
  },
  methods: {
    showModal(index) {
      this.selected = index;
      this.modalShow = true;
    },
    goWrite() {
      this.$router.push({ name: 'write', params: { index: this.selected } });
    },
    goTalk() {
      this.$router.push({ name: 'talk', params: { index: this.selected } });
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
</style>
