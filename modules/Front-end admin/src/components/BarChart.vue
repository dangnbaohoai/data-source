<template>
  <div class="grid place-items-center">
    <div id="a" class="w-1/2 h-min bg-white">
      <PlanetChart />
    </div>
  </div>
</template>

<script>
import PlanetChart from "./PlanetChart.vue";

export default {
  name: "App",
  components: {
    PlanetChart,
  },
};
</script>
<style scoped></style>
