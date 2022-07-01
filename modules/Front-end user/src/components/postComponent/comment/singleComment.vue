<template>
  <div class="flex items-start w-full" v-if="!isEdit">
    <div class="flex-1 leading-relaxed text-left">
      <strong>{{ comment.userName }}</strong>
      <span class="text-xs text-gray-400">{{ comment.createDate }}</span>
      <p class="text-sm text-left">{{ editcontent }}</p>
    </div>
    <ActionPanel
      :forumId="forumId"
      :comment="comment"
      @turnEdit="Cancel"
      @deletecmt="deletecmt"
      :key="postId"
      :postId="postId"
    />
  </div>
  <form
    class="flex my-4 text-left w-full"
    v-on:submit.prevent="editComment"
    v-else
  >
    <div class="ml-3 rounded-lg w-full px-2 py-1">
      <p class="text-xl font-bold text-gray-500">{{ comment.userName }}</p>
      <textarea
        class="
          bg-gray-100
          rounded
          leading-normal
          resize-y
          w-full
          py-2
          px-3
          font-medium
          overflow-y-visible
          placeholder-gray-700
          border-dashed border-2
          focus:outline-white focus:bg-white
        "
        name="body"
        :class="[isPosting ? 'animate-pulse' : '']"
        placeholder="Type Your Comment"
        required
        v-model="editcontent"
      ></textarea>
      <div class="flex">
        <div class="flex-grow"></div>
        <button
          class="flex border-2 rounded-md px-4 hover:bg-green-100"
          @click="Cancel"
        >
          <span class="font-semibold">Cancel</span>
        </button>
        <button
          class="flex border-2 rounded-md px-4 hover:bg-green-100"
          type="submit"
        >
          <svg
            class="h-6 w-6 text-green-500"
            :class="[isPosting ? 'animate-bounce' : '']"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            stroke-width="2"
            stroke="currentColor"
            fill="none"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path stroke="none" d="M0 0h24v24H0z" />
            <line x1="10" y1="14" x2="21" y2="3" />
            <path
              d="M21 3L14.5 21a.55 .55 0 0 1 -1 0L10 14L3 10.5a.55 .55 0 0 1 0 -1L21 3"
            /></svg
          ><span class="font-semibold">Edit</span>
        </button>
      </div>
    </div>
  </form>
</template>

<script>
// {-commentId-
//   "content": "string",
//   "imageComment": "string",
//   "postId": 0,
//   "replyId": 0,
//   "timeComment": "2021-12-18T01:16:38.405Z"
// }
import ActionPanel from "./actionPanel.vue";
import { updateComment } from "../../../logic/postAPI";
export default {
  components: { ActionPanel },
  emits: ["deletecmt"],
  props: {
    comment: Object,
    forumId: String,
    postId: String,
  },
  data() {
    return {
      editcontent: "",
      isEdit: false,
      isPosting: false,
    };
  },
  created() {
    this.editcontent = this.comment.content;
  },
  methods: {
    async editComment() {
      this.isPosting = true;
      const a = {
        content: this.editcontent,
        postId: this.comment.postId,
        imageComment: "",
        replyId: this.comment.replyId,
        timeComment: new Date().toISOString(),
      };

      const res = await updateComment(this.comment.commentId, a);
      console.log(res);
      this.isPosting = false;
      if (res.content == this.editcontent) {
        this.$root.addnoti({
          group: "top",
          title: "Upadate successfull",
          text: "",
        });
        this.isEdit = !this.isEdit;
      }
    },
    Cancel() {
      this.isEdit = !this.isEdit;
    },
    deletecmt() {
      this.$emit("deletecmt");
    },
  },
};
</script>

<style></style>
