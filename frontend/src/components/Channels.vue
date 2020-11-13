<template>
  <div>
    <h2
      class="eng"
      style="color:#fff;
      font-size: calc(40px + 2.5vw);
      display: flex;
      justify-content: center;"
    >
      Choose Your Artist
    </h2>
    <span class="blinking">
      <div class="example-3d" @mouseenter="hover = true" @mouseleave="hover = false">
        <swiper class="swiper" :options="swiperOption" ref="swiperRef">
          <swiper-slide
            v-for="(item, index) in items"
            :key="index"
            :style="{ 'background-image': 'url(' + require(`@/assets${item.img}`) + ')' }"
            @click.native="enter(item.id)"
            class="swiperImg"
          >
            <div class="middle">
              <div class="text">{{ item.title }}</div>
            </div>
          </swiper-slide>
          <div class="swiper-button-prev" slot="button-prev"></div>
          <div class="swiper-button-next" slot="button-next"></div>
        </swiper>
      </div>
    </span>
    <v-flex d-flex>
      <v-layout wrap>
        <v-flex xs12 sm6 md4 lg3 xl2 v-for="(data, index) in items" :key="index">
          <v-card class="scale ma-3" @click="enter(data.id)">
            <v-img aspect-ratio="1" :src="require(`@/assets` + data.img)" />
            <v-card-title class="eng temp justify-center">{{ data.title }}</v-card-title>
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
          rotate: -20,
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
.scale {
  transform: scale(1);
  transition: all 0.3s ease-in-out;
}
.temp {
  background-color: rgba(0, 0, 0, 0.89);
  padding: 1;
}
.scale:hover {
  transform: scale(1.02);
  z-index: 1;
  animation: blinking 2s alternate infinite;
}
.example-3d {
  width: 100%;
  height: 50vh;
  margin: 50px 0;
}

.swiper {
  height: auto;
  width: auto;
  --swiper-theme-color: #fdb165;
  .swiper-slide {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 300px;
    height: 50vh;
    text-align: center;
    font-weight: bold;
    background-color: #fdb165;
    background-position: center;
    background-size: cover;
  }
}
.swiper-button-prev {
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
.swiper-button-next {
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
.blink {
  animation: blinking 2s alternate infinite;
}
@keyframes blinking {
  from {
    box-shadow: 0 0 5px 5px rgb(250, 222, 166);
  }
  50% {
    box-shadow: 0 0 5px 5px #fdb165;
  }
  to {
    box-shadow: 0 0 5px 5px rgb(245, 236, 218);
  }
}
.swiperImg{
  opacity: 1;
  // display: block;
  // width: 100%;
  // height: auto;
  // transition: .5s ease;
  backface-visibility: hidden;
}

.middle {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  height: 100%;
  width: 100%;
  opacity: 0;
  transition: .5s ease;
}

.swiperImg:hover .middle{
  background-color: rgba(0, 0, 0, 0.5);
  opacity: 1;
}
.text {
  color: white;
  font-size: 20px;
  // position: relative;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  text-align: center;
}
</style>
