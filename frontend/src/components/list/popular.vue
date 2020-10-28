<template>
  <section>
    <b-container fluid>
      <b-row>
        <b-col sm="12" class="overflow-hidden">
          <div class="d-flex align-items-center justify-content-between">
            <h4 class="main-title">인기별</h4>
          </div>
          <div class="upcoming-contens">
            <VueSlickCarousel v-bind="settings">
              <img v-for="item in items" :src="item.url" :key="item.url"/>
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
        {
          url: 'http://img.youtube.com/vi/WotZgTcbHRA/0.jpg',
        },
      ],
      settings: {
        dots: true,
        focusOnSelect: true,
        infinite: true,
        speed: 500,
        slidesToShow: 3,
        slidesToScroll: 3,
        touchThreshold: 5,
      },
    };
  },
  methods: {
    modal(val) {
      console.log(val);
    },
  },
};
</script>
