<template>
  <div v-for="data in datas" :key="data.postId">
    <contentWait
      :content="data"
      :forumId="forumId"
      @deletep="deletep"
      class="w-full"
    />
  </div>
  <div
    class="
      w-full
      h-36
      flex
      items-center
      rounded-lg
      text-4xl
      font-bold
      text-center
      not-italic
    "
    v-if="datas == ''"
  >
    <h1
      class="
        w-full
        text-center
        bg-clip-text
        text-transparent
        bg-gradient-to-r
        from-green-400
        to-blue-500
      "
    >
      No more data available
    </h1>
  </div>
  <div class="h-96"></div>
</template>

<script>
import { contentsWaitToCensor } from "../../../logic/forumAPI";
import contentWait from "./contentWait.vue";
import { location } from "../../../logic/handlingdata";
export default {
  props: {
    forumId: {
      type: Number,
    },
  },

  data() {
    return {
      datas: [],
    };
  },
  async created() {
    const z = await contentsWaitToCensor(this.forumId);
    this.datas = z.data.listPostResponse;
    console.log(this.datas);
  },
  components: {
    contentWait,
  },
  methods: {
    deletep(content) {
      console.log("====================================" + content);
      console.log(content);
      this.datas.splice(location(content, this.datas), 1);
    },
  },
};
</script>

<style></style>
