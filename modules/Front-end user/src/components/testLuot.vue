<template>
  <div>
    <infinite-scroll
      @infinite-scroll="loadDataFromServer"
      :message="message"
      :noResult="noResult"
      class="my-5"
    >
      <div>
        <div
          v-for="repo in trendingRepos"
          :key="repo"
          style="margin-bottom: 20px"
        >
          <div>{{ repo }}</div>
        </div>
      </div>
    </infinite-scroll>
  </div>
</template>

<script>
import InfiniteScroll from "infinite-loading-vue3";
// import axios from "axios";

export default {
  name: "TrendingRepos",

  components: {
    InfiniteScroll,
  },

  data() {
    return {
      trendingRepos: [],
      page: 1,
      noResult: false,
      message: "",
      lan: 0,
    };
  },

  methods: {
    async loadDataFromServer() {
      this.lan++;
      console.log(this.lan);
      try {
        const result = [];
        await setTimeout(() => {
          for (
            let i = this.trendingRepos.length + 1;
            i < this.trendingRepos.length + 30;
            i++
          ) {
            result.push(i);
          }
        }, 2000);

        if (result.length != 100) {
          this.trendingRepos.push(...result);
          this.page++;
        } else {
          this.noResult = true;
          this.message = "No result found";
        }
      } catch (err) {
        this.noResult = true;
        this.message = "Error loading data";
      }
    },
  },

  mounted() {
    this.loadDataFromServer();
  },
};
</script>
