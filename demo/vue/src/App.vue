
<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>
<script>
import index from '@/pages/users/index.vue'
export default {
  name: 'app',
  components:{
    index
  }
}
</script>
<style scoped>

</style>
