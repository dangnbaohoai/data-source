<template>
  <CreateContent
    v-if="isCreateContent"
    @close="createcontent"
    :forumId="forumId"
    :content="contentforedit"
  />
  <div class="my-4"></div>
  <div v-if="content != null" class="max-w-2xl w-full shadow-2xl">
    <postfirstview
      :content="content.data"
      class=""
      :postId="postId"
      @remove="deleteContent"
      @edit="editcontent"
    />
    <comment :postId="postId" :forumId="forumId" />
  </div>
</template>

<script>
import postfirstview from "../components/postComponent/postFirstView.vue";
import comment from "./postComponent/comment/comment.vue";
import CreateContent from "@/components/editor/CreateContent.vue";
import { detailPost } from "../logic/postAPI";
export default {
  props: {
    forumId: String,
    postId: String,
  },
  data() {
    return {
      content: null,
      isCreateContent: false,
      contentforedit: null,
    };
  },
  async created() {
    this.content = await detailPost(
      Number.parseInt(this.forumId),
      Number.parseInt(this.postId)
    );
    console.log("-------------------------------detail content");
    console.log(this.content);
  },
  components: {
    postfirstview,
    comment,
    CreateContent,
  },
  methods: {
    editcontent(edit) {
      this.contentforedit = edit;
      this.isCreateContent = true;
    },
    deleteContent() {
      this.$router.push({
        path: "/Forum/" + this.forumId,
      });
    },
    createcontent() {
      this.isCreateContent = !this.isCreateContent;
      this.contentforedit = null;
    },
  },
};
</script>

<style></style>
