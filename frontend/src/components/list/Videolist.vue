<template>
  <section>
    <b-container fluid>
      <b-row>
        <b-col sm="12" class="overflow-hidden">
          <div class="eng toggle-btn">
            <form class="tabber">
              <label for="t1">Speaking</label>
              <input id="t1" type="radio" value="Speaking" v-model="choice"/>
              <label for="t2">Dictation</label>
              <input id="t2" type="radio" value="Dictation" v-model="choice"/>
              <div class="blob"></div>
            </form>
          </div>
          <div class="upcoming-contents ma-5">
            <VueSlickCarousel v-bind="settings" v-if="items.length">
              <img
                v-for="(item, index) in items"
                :src="'http://img.youtube.com/vi/' + item.url + '/0.jpg'"
                :key="index"
                @click="goTraining(item)"
                class="pa-1"
                :label=item.title
              />
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
import { mapGetters } from 'vuex';
import http from '../../util/http-common';
import channelList from '../core/channelList.json';

export default {
  name: 'Videolist',
  components: { VueSlickCarousel },
  props: ['filters'],
  data() {
    return {
      items: [],
      choice: 'Speaking',
      groupid: this.$route.query.groupid,
      teams: channelList,
      team: '',
      page: 0,
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
      modalShow: false,
      timerInterval: '',
      correctAnswer: {
        title: 'Loading....',
        html: 'I will close in <b></b> milliseconds.',
        timer: 500,
        timerProgressBar: true,
        willOpen: () => {
          this.$swal.showLoading();
          this.timerInterval = setInterval(() => {
            const content = this.$swal.getContent();
            if (content) {
              const b = content.querySelector('b');
              if (b) {
                b.textContent = this.$swal.getTimerLeft();
              }
            }
          }, 100);
        },
        onClose: () => {
          clearInterval(this.timerInterval);
        },
      },
    };
  },
  computed: {
    ...mapGetters(['email']),
  },
  async created() {
    this.team = this.teams[this.groupid - 1].title;
    this.items = [];
    const pages = await this.getTotalPages(this.team);
    await this.getTotal(pages, this.team, this.items);
  },
  watch: {
    async filters() {
      // 나중에 여기서 if문에 따라서 멘트를 바꿔야할듯?
      // pages === 0 이면 해당 멤버 영상 X -> 모든 멤버를 불러옵니다.
      //  pages !== 0 이면 해당 멤버 영상 O -> {{member}} 영상을 불러옵니다.
      this.$swal.fire(this.correctAnswer);
      if (this.filters === this.team) {
        this.getStart();
      } else {
        this.items = [];
        const pages = await this.getMemberPages(this.filters);
        if (pages !== 0) {
          await this.getMember(pages, this.filters, this.items);
        } else {
          await this.getStart();
        }
      }
    },

  },
  methods: {
    async goTraining(item) {
      await http.get('/log/insert', { params: { videoid: Number(item.id), email: this.email, groupid: Number(this.groupid) } });
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
    async getStart() {
      this.items = [];
      const pages = await this.getTotalPages(this.team);
      await this.getTotal(pages, this.team, this.items);
    },
    getTotalPages(team) {
      return new Promise((resolve) => {
        http.get(`/search/idol/0?groupname=${team}`).then((res) => {
          resolve(res.data.totalPages);
        });
      });
    },
    getMemberPages(data) {
      return new Promise((resolve) => {
        http.get(`/search/member/0?membername=${data}`).then((res) => {
          resolve(res.data.totalPages);
        });
      });
    },
    async getTotal(page, team, items) {
      for (let i = 0; i < page; i += 1) {
        // eslint-disable-next-line no-await-in-loop
        await http.get(`/search/idol/${i}?groupname=${team}`).then((res) => res.data.content.forEach((element) => {
          items.push(element);
        }));
      }
    },
    async getMember(page, name, items) {
      for (let i = 0; i < page; i += 1) {
        // eslint-disable-next-line no-await-in-loop
        await http.get(`/search/member/${i}?membername=${name}`).then((res) => res.data.content.forEach((element) => {
          items.push(element);
        }));
      }
    },
  },
};
</script>
<style lang="scss">
.upcoming-contents::after{
  color: #FDB165;
  // background-color: #FDB165;
}
.choice img {
  height: 100%;
}
.toggle-btn {
  left: 40%;
  margin-top: calc(10px + .5vw);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: calc(20px + 0.8vw);
  -webkit-font-smoothing: antialiased;
  text-rendering: optimizeLegibility;
}
svg {
  display: none;
}
.tabber {
  color: white;
  width: 500px;
  // height: 80%;
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
    // filter: blur(.25rem);
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
      animation-name: pulse;
      animation-duration: 1.1s;
      animation-iteration-count: infinite;
      animation-direction: alternate;
    }
    &:before {
      left: 0;
      animation-delay:2s;
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

@keyframes pulse {
  0%,
  50% {
    transform: scaleX(0.5);
  }
  25%,
  75% {
    transform: scaleX(0.2);
  }
}
</style>
