<template>
  <div class="upcoming-contents ma-5">
    <VueSlickCarousel v-bind="settings" v-if="items.length">
      <img
        v-for="(item, index) in items"
        :src="'http://img.youtube.com/vi/' + item.url + '/0.jpg'"
        :key="index"
        @click="goTraining(item)"
        class="pa-1"
      />
    </VueSlickCarousel>
  </div>
</template>

<script>
import VueSlickCarousel from 'vue-slick-carousel';
import 'vue-slick-carousel/dist/vue-slick-carousel.css';
import 'vue-slick-carousel/dist/vue-slick-carousel-theme.css';
import { mapGetters } from 'vuex';
import http from '../../util/http-common';

export default {
  name: 'Review',
  components: { VueSlickCarousel },
  data() {
    return {
      items: [],
    };
  },
  computed: {
    ...mapGetters(['email']),
  },
  async created() {
    this.items = [];
    await http.get('/log/load', { params: { email: this.email } })
      .then((res) => {
        console.log(res);
      });
  },
};
</script>

<style></style>
