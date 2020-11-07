<template>
  <div>
    <h2 style="color:#fff;
  font-size:5vw;
  display: flex;
  justify-content: center;">Choose Your Artist</h2>
    <div class="example-3d" @mouseenter="hover = true" @mouseleave="hover = false">
      <swiper class="swiper" :options="swiperOption" ref="swiperRef">
        <swiper-slide
          v-for="(item, index) in items"
          :key="index"
          :style="{ 'background-image': 'url(' + require(`@/assets${item.img}`) + ')' }"
          @click.native="enter(item.id)"
        ></swiper-slide>
        <div class="swiper-button-prev" slot="button-prev"></div>
        <div class="swiper-button-next" slot="button-next"></div>
      </swiper>
    </div>
    <v-flex d-flex>
      <v-layout wrap>
        <v-flex xs12 sm6 md4 lg3 xl2 v-for="(data, index) in items" :key="index">
          <v-card class="scale ma-3" @click="enter(data.id)">
            <v-img aspect-ratio="1" :src="require(`@/assets` + data.img)" />
            <v-card-title class="temp justify-center">{{ data.title }}</v-card-title>
          </v-card>
        </v-flex>
      </v-layout>
    </v-flex>
  </div>
</template>

<script>
import { Swiper, SwiperSlide } from 'vue-awesome-swiper';
import 'swiper/css/swiper.css';
import channelList from './core/channelList.json';

export default {
  name: 'Channels',
  components: {
    Swiper,
    SwiperSlide,
  },
  data() {
    return {
      hover: false,
      swiperRef: '',
      items: channelList,
      swiperOption: {
        autoHeight: true,
        effect: 'coverflow',
        spaceBetween: 30,
        loop: true,
        centeredSlides: true,
        slidesPerView: 2,
        autoplay: {
          delay: 2500,
          disableOnInteraction: true,
        },
        coverflowEffect: {
          rotate: 60,
          stretch: 2,
          depth: 200,
          modifier: 2,
          slideShadows: true,
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev',
        },
      },
    };
  },
  methods: {
    enter(id) {
      this.$router.push({ name: 'Artist', query: { groupid: String(id) } });
    },
  },
  watch: {
    hover() {
      if (this.hover === true) {
        this.$refs.swiperRef.swiperInstance.autoplay.stop();
      } else {
        this.$refs.swiperRef.swiperInstance.autoplay.start();
      }
    },
  },
};
</script>

<style lang="scss" scoped>
* {
  font-family: 'Merriweather', serif;
}
.scale {
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}
.temp{
  background-color: rgba(0, 0, 0, 0.89);
  padding: 1;
  color: white;
}

.scale:hover {
  transform: scale(1.1);
  z-index: 1;
}
.example-3d {
  width: 100%;
  height: 50vh;
  margin: 50px 0;
}

.swiper {
  height: auto;
  width: auto;
  --swiper-theme-color: #FDB165;
  .swiper-slide {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 300px;
    height: 50vh;
    text-align: center;
    font-weight: bold;
    background-color: #FDB165;
    background-position: center;
    background-size: cover;
  }
}
</style>
