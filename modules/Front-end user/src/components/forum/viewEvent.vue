<template>
  <CreateContent
    v-if="isCreateContent"
    @close="createcontent"
    :forumId="forumId"
  />
  <div class="w-full grid justify-items-center">
    <div
      v-if="forumId"
      class="
        overflow-hidden
        rounded-lg
        shadow-lg
        text-left
        my-4
        divide-y divide-dashed
        px-4
        flex
        py-2
        bg-white
        hover:bg-gray-200
        w-full
        max-w-2xl
        cursor-pointer
      "
      @click="createcontent"
    >
      <img
        class="inline-block object-cover w-10 h-10 rounded-full mx-0.5"
        :src="avatar"
        alt="Profile image"
      />
      <div class="h-3 p-2 w-max text-center">Thêm bài viết mới</div>
    </div>
    <!-- Phần nội dung -->
    <infinite-scroll
      @infinite-scroll="loadDataFromServer"
      :message="message"
      :noResult="noResult"
      class="my-10 w-full grid justify-items-center"
    >
      <div
        class="
          w-full
          max-w-2xl
          overflow-hidden
          rounded-lg
          shadow-lg
          text-left
          block
          my-4
          divide-y divide-dashed
        "
        v-for="content in viewcontents"
        :key="content.postId"
      >
        <!-- Để content tại đây -->
        <postView :content="content" @remove="deleteContent" />
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
        v-if="noResult"
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
      <div class="h-44"></div>
    </infinite-scroll>
  </div>
</template>

<script>
import InfiniteScroll from "infinite-loading-vue3";
import CreateContent from "@/components/editor/CreateContent.vue";
import postView from "../postComponent/postFirstView.vue";
import { getEvent } from "../../logic/forumAPI";
import { location } from "../../logic/handlingdata";
export default {
  props: {
    forumId: Number,
    Data: Object,
  },
  components: {
    postView,
    CreateContent,
    InfiniteScroll,
  },
  watch: {},
  async mounted() {
    await this.loadDataFromServer();
  },
  data() {
    return {
      isCreateContent: false,
      avatar: localStorage.getItem("avartar"),
      viewcontents: [],
      page: 1,
      noResult: false,
      message: "",
      isdone: true,
    };
  },
  methods: {
    createcontent() {
      this.isCreateContent = !this.isCreateContent;
    },
    test() {
      console.log(this.contents);
    },
    async loadDataFromServer() {
      if (this.isdone && !this.noResult) {
        this.isdone = false;
        const temp = await getEvent(this.forumId, this.page);
        // 2 sec delay

        console.log(temp);
        if (temp.data.length) {
          this.viewcontents.push(...temp.data);
          this.page++;
        } else {
          this.noResult = true;
          this.message = "No more post";
        }
        this.isdone = true;
      }
    },
    deleteContent(content) {
      this.viewcontents.splice(location(content, this.viewcontents), 1);
    },
  },
};
</script>

<style>
.button-54 {
  font-family: "Open Sans", sans-serif;
  font-size: 16px;
  letter-spacing: 2px;
  text-decoration: none;
  text-transform: uppercase;
  color: #000;
  cursor: pointer;
  border: 3px solid;
  padding: 0.25em 0.5em;
  box-shadow: 1px 1px 0px 0px, 2px 2px 0px 0px, 3px 3px 0px 0px, 4px 4px 0px 0px,
    5px 5px 0px 0px;
  user-select: none;
  -webkit-user-select: none;
  touch-action: manipulation;
}

.button-54:active {
  box-shadow: 0px 0px 0px 0px;
  top: 5px;
  left: 5px;
}
.ProseMirror {
  position: inherit;
}
@media (min-width: 768px) {
  .button-54 {
    margin: 3mm;
    padding: 0.25em 0.75em;
  }
}
::selection {
  background: #fff5b8;
  display: block;
}
</style>
