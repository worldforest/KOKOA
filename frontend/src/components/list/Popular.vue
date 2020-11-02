<template>
  <section>
    <b-container fluid>
      <b-row>
        <b-col sm="12" class="overflow-hidden">
          <div class="d-flex align-items-center justify-content-between">
            <h4 class="main-title">Your K-Pop Star</h4>
          </div>
          <VueSlickCarousel class="choice" v-bind="settings2">
            <img
              v-for="(star, index) in stars"
              :src="require(`@/assets/`+star.group)"
              :key="index"
              class="pa-1"
            />
          </VueSlickCarousel>
          <div class="d-flex align-items-center justify-content-between">
            <h4 class="main-title">Favorite</h4>
          </div>
          <div class="upcoming-contens ma-5">
            <VueSlickCarousel v-bind="settings">
              <img
                v-for="(item, index) in items"
                :src="item.url"
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
      stars: [
        {
          group: 'iu.jpg',
          member: {
            0: 'iu.jpg',
          },
        },
        {
          group: 'redvelvet.jpg',
          member: {
            0: 'irene.jpg',
            1: 'seulki.jpg',
            2: 'wendy.jpg',
            3: 'joy.jpg',
            4: 'yeri.jpg',
          },
        },
        {
          group: 'iu.jpg',
          member: {
            0: 'iu.jpg',
          },
        },
        {
          group: 'redvelvet.jpg',
          member: {
            0: 'irene.jpg',
            1: 'seulki.jpg',
            2: 'wendy.jpg',
            3: 'joy.jpg',
            4: 'yeri.jpg',
          },
        },
        {
          group: 'redvelvet.jpg',
          member: {
            0: 'irene.jpg',
            1: 'seulki.jpg',
            2: 'wendy.jpg',
            3: 'joy.jpg',
            4: 'yeri.jpg',
          },
        },
      ],
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
    // getImg(index) {
    //   const img = this.stars[index];
    //   return `../assets/${img.group}`;
    // },
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
   height:100%;
}
</style>
