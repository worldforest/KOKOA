<template>
  <div class="paper">
    <div class="lines">
      <div class="text">
        <!-- contenteditable spellcheck="false"-->
        <h2>Note - Review your incorrect sentences.</h2>
        <br />
        <!-- 문장 목록 -->
        <div>
          <h3>[Speaking]</h3>
          <div v-for="(item, index) in items" :key="index">
            <div
              @click="expanded === index ? (expanded = -1) : setToTop(index)"
              :id="'sentence' + index"
            >
              {{ index }} : {{ item.name }}
              <v-space />
              <v-chip class="ma-2" color="pink">
                팀명
              </v-chip>
              <v-chip class="ma-2" color="orange">
                멤버명
              </v-chip>
            </div>
            <div v-if="expanded === index">
              <Talk style="background: lightgoldenrodyellow" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="holes hole-top"></div>
    <div class="holes hole-middle"></div>
    <div class="holes hole-bottom"></div>
  </div>
</template>

<script>
import Talk from '../training/Talk.vue';

export default {
  name: 'Note',
  components: {
    Talk,
  },
  data: () => ({
    singleExpand: true,
    itemsPerPage: 4,
    items: [
      {
        name: '찬성은 사람을 들어',
      },
      {
        name: 'Ice cream sandwich',
      },
      {
        name: 'Eclair',
      },
      {
        name: 'Cupcake',
      },
    ],
    expanded: -1,
  }),
  methods: {
    setToTop(index) {
      const elmnt = document.getElementById(`sentence${index}`);
      elmnt.scrollIntoView();
      this.expanded = index;
    },
  },
};
</script>

<style>
@import url(https://fonts.googleapis.com/css?family=Indie+Flower);
/* body {
  margin: 0;
  padding: 0;
  background: lightgoldenrodyellow;
} */
.paper {
  position: absolute;
  /* height: 550px;
  width: 450px; */
  width: 90%;
  height: 100%;
  background: rgba(255, 255, 255, 0.9);
  margin: -350px -25% 30px;
  left: 30%;
  top: 50%;
  box-shadow: 0px 0px 5px 0px #888;
}
.paper::before {
  content: "";
  position: absolute;
  left: 45px;
  height: 100%;
  width: 2px;
  background: rgba(255, 0, 0, 0.4);
}
.lines {
  margin-top: 40px;
  height: calc(100% - 40px);
  width: 100%;
  background-image: repeating-linear-gradient(white 0px, white 24px, steelblue 25px);
}
.text {
  position: absolute;
  top: 65px;
  left: 55px;
  bottom: 10px;
  right: 10px;
  line-height: 25px;
  font-family: "Indie Flower";
  overflow: auto;
  outline: none;
}
.holes {
  position: absolute;
  left: 10px;
  height: 25px;
  width: 25px;
  background: lightgoldenrodyellow;
  border-radius: 50%;
  box-shadow: inset 0px 0px 2px 0px #888;
}
.hole-top {
  top: 10%;
}
.hole-middle {
  top: 50%;
}
.hole-bottom {
  bottom: 10%;
}
</style>
