<template>
<div class="artist">
    <div>{{name}}</div>
    <Popular />
</div>
</template>

<script>
import Popular from './list/Popular.vue';

export default {
  name: 'Artist',
  components: {
    Popular,
  },
  data() {
    return {
      name: this.$route.query.name,
    };
  },
};
</script>

<style>

</style>
