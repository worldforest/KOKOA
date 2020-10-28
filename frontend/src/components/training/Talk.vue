<template>
  <div>
    <h1>말하기 페이지</h1>
    <h2>name: {{ $route.params.index }}</h2>
  </div>
</template>
<script>
export default {
  name: 'Talk',
  props: {
    index: {
      type: String,
      default: '',
    },
  },
  mounted() {},
  data() {
    return {};
  },
};
</script>
<style></style>
