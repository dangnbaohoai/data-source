<template>
  <div class="">
    <canvas id="planet-chart"></canvas>
  </div>
</template>

<script>
import { Chart } from "chart.js";
import planetChartData from "./planet-data";

export default {
  name: "PlanetChart",
  props: {
    data: Object,
  },
  data() {
    return {
      planetChartData: planetChartData,
    };
  },
  mounted() {
    this.planetChartData.data = this.data;
    this.planetChartData.data.datasets.push({
      label: "Fake data",
      data: [0, 111, 1, 2, 79, 82, 27, 14],
      backgroundColor: "rgba(120, 167, 183, 1)",
      borderColor: "#36495d",
      borderWidth: 3,
    });
    const ctx = document.getElementById("planet-chart");
    new Chart(ctx, this.planetChartData);
    console.log(this.planetChartData);
  },
};
</script>
