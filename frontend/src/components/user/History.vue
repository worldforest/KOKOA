<template>
  <div class="px-10" style="margin-top:100px; margin-bottom: 100px;">
    <h1 class="eng">Last-visited Artist</h1>
    <v-layout wrap>
      <v-flex xs12 sm6 md4 lg3 xl2 v-for="(data, index) in groups" :key="index">
        <v-card class="scale ma-3" @click="enter(data.id)">
          <v-img aspect-ratio="1" :src="require(`@/assets` + data.img)" />
          <v-card-title class="eng temp justify-center">{{ data.title }}</v-card-title>
        </v-card>
      </v-flex>
    </v-layout>
    <h1 class="eng mt-10">Last-watched Videos</h1>
    <div class="eng toggle-btn">
      <form class="tabber">
        <label for="t1">Speaking</label>
        <input id="t1" type="radio" value="Speaking" v-model="choice" />
        <label for="t2">Writing</label>
        <input id="t2" type="radio" value="Dictation" v-model="choice" />
        <div class="blob"></div>
      </form>
    </div>
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
      choice: 'Speaking',
      items: [],
      groups: [],
      infos: [],
      tmp: channelList,
      settings: {
        arrows: true,
        infinite: true,
        speed: 3000,
        slidesToShow: 3,
        slidesToScroll: 3,
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
      for (let i = 0; i < res.data.video[0].length; i += 1) {
        this.items.push(res.data.video[0][i]);
      }
      for (let i = 0; i < res.data.video[0].length; i += 1) {
        for (let j = 0; j < this.tmp.length; j += 1) {
          let str = res.data.group[0][i].replace(/ /g, '_');
          str = str.replace(/'/g, '');
          if (str === this.tmp[j].groupname) {
            this.groups.push(this.tmp[j]);
          }
          str = '';
        }
      }
    });
  },
  methods: {
    async goTraining(item) {
      await http.get('/log/insert', {
        params: { videoid: Number(item.id), email: this.email, groupid: Number(item.groupid) },
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
      this.$router.push({ name: 'Artist', query: { groupid: String(id) } });
    },
  },
};
</script>

<style lang="scss" scoped>
.temp {
  background-color: rgba(0, 0, 0, 0.89);
  padding: 1;
}
.toggle-btn {
  left: 40%;
  margin-top: calc(10px + 0.5vw);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: calc(20px + 0.8vw);
  -webkit-font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
}
.tabber {
  color: white;
  width: 500px;
  position: relative;
  display: flex;
  align-items: center;
  justify-content: center;
  label {
    width: 50%;
    user-select: none;
    padding: 0.2rem;
    text-align: center;
    cursor: pointer;
    will-change: transform;
    transform: translateZ(0.5px);
    z-index: 1;
    transition: transform 90ms ease-in-out, filter 90ms ease-in-out;
    &:hover {
      transform: scale(1.15);
      filter: blur(0px);
    }
  }
  input[type="radio"] {
    display: none;
    // static
    &#t1 ~ .blob {
      transform-origin: right center;
    }
    &#t2 ~ .blob {
      transform-origin: left center;
    }
    // animated
    &#t1:checked {
      ~ .blob {
        background: rgb(233, 103, 131);
        animation-name: stretchyRev;
      }
    }
    &#t2:checked {
      ~ .blob {
        background-color: rgb(73, 178, 134);
        animation-name: stretchy;
      }
    }
  }

  .blob {
    top: 0;
    left: 0;
    width: 50%;
    height: 100%;
    position: absolute;
    z-index: 0;
    border-radius: 2rem;
    animation-duration: 0.2s;
    animation-direction: forwards;
    animation-iteration-count: 1;
    animation-fill-mode: forwards;
    transition: transform 300ms ease, background 300ms ease;
    filter: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" version="1.1"><defs><filter id="goo"><feGaussianBlur in="SourceGraphic" stdDeviation="10" result="blur" /><feColorMatrix in="blur" mode="matrix" values="1 0 0 0 0  0 1 0 0 0  0 0 1 0 0  0 0 0 19 -9" result="goo" /><feComposite in="SourceGraphic" in2="goo" operator="atop"/></filter></defs></svg>#goo');
    &:before,
    &:after {
      display: block;
      content: "";
      position: absolute;
      top: 0;
      background-color: inherit;
      height: 100%;
      width: 50%;
      border-radius: 100%;
      transform: scale(1.15);
      transition: transform 1s;
      animation-duration: 1.1s;
      animation-iteration-count: infinite;
      animation-direction: alternate;
    }
    &:before {
      left: 0;
      animation-delay: 2s;
    }
    &:after {
      right: 0;
    }
  }
}

@keyframes stretchy {
  0% {
    transform: translateX(0) scaleX(1.5);
  }
  50% {
    transform: translateX(0) scaleX(2);
  }
  100% {
    transform: translateX(100%) scaleX(1);
  }
}

@keyframes stretchyRev {
  0% {
    transform: translateX(100%) scaleX(1);
  }
  50% {
    transform: translateX(0) scaleX(2);
  }
  100% {
    transform: translateX(0) scaleX(1);
  }
}
</style>
