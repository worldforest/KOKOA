<template>
  <div>
    <h1>받아쓰기 페이지</h1>
    <h2>name: {{ $route.params.index }}</h2>
  </div>
</template>
<script>
export default {
  name: 'Write',
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
