<template>
  <details class="mt-4">
    <summary class="flex items-center" @click="getCMT">
      <!-- <div class="flex -space-x-2 mr-2">
              <img
                class="rounded-full w-6 h-6 border border-white avatar"
                src="https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=100&h=100&q=80"
                alt=""
              />
              <img
                class="rounded-full w-6 h-6 border border-white avatar"
                src="https://images.unsplash.com/photo-1513956589380-bad6acb9b9d4?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=100&h=100&q=80"
                alt=""
              />
            </div> -->
      <div class="text-sm text-gray-500 font-semibold">
        {{ numberReplyCount }} Replies | Reply
      </div>
      <div class="flex-shrink-0 mr-3"></div>
    </summary>
    <div
      class="
        text-sm text-gray-500
        font-semibold
        cursor-pointer
        flex
        items-center
      "
      v-if="isLoading"
    >
      <svg
        fill="none"
        class="w-10 h-10 animate-spin text-green-500"
        viewBox="0 0 32 32"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          clip-rule="evenodd"
          d="M15.165 8.53a.5.5 0 01-.404.58A7 7 0 1023 16a.5.5 0 011 0 8 8 0 11-9.416-7.874.5.5 0 01.58.404z"
          fill="currentColor"
          fill-rule="evenodd"
        />
      </svg>
      <span>Loading</span>
    </div>
    <!-- Hiển th -->
    <div
      v-for="comment in commentData"
      :key="comment.commentId"
      class="flex my-2"
    >
      <img
        class="mt-2 rounded-full w-8 h-8 sm:w-10 sm:h-10 border-2 avatar mr-3"
        :src="comment.avatarOfUser"
        alt=""
      />
      <signalComment
        :comment="comment"
        :forumId="forumId"
        @deletecmt="deletecmt(comment)"
      />
    </div>
    <!-- Gửi comment -->
    <form class="flex my-4" v-on:submit.prevent="sendComment">
      <img
        class="mt-2 rounded-full w-8 h-8 sm:w-10 sm:h-10 border-2 avatar"
        :src="userData.avatar"
        alt=""
      />
      <div class="ml-3 rounded-lg w-full px-2 py-1">
        <p class="text-xl font-bold text-gray-500">{{ userData.username }}</p>
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
          v-model="commentSendData.content"
        ></textarea>
        <div class="flex">
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
            ><span class="font-semibold">Reply</span>
          </button>
        </div>
      </div>
    </form>
  </details>
</template>

<script>
import { CommentPost, viewReplyComment } from "../../../logic/postAPI";
import signalComment from "./singleComment.vue";
import { location } from "../../../logic/handlingdata";
export default {
  props: {
    userData: Object,
    commentId: Number,
    postId: Number,
    numberReplyCount: Number,
    forumId: String,
  },
  components: { signalComment },
  created() {},
  data() {
    return {
      commentpage: 1,
      commentData: [],
      commentSendData: {
        content: "",
        imageComment: "",
        postId: 0,
        replyId: 0,
        timeComment: "",
      },
      isPosting: false,
      isLoading: true,
      isNotLoaded: true,
    };
  },
  methods: {
    async sendComment() {
      this.isPosting = true;
      this.commentSendData.postId = this.postId;
      this.commentSendData.timeComment = new Date().toJSON();
      this.commentSendData.replyId = this.commentId;
      const res = await CommentPost(this.commentSendData);
      console.log(res);
      this.commentData.push(res);
      this.commentSendData.content = "";
      this.isPosting = false;
    },
    async getCMT() {
      if (this.numberReplyCount > 0 && this.isNotLoaded) {
        this.commentData = (
          await viewReplyComment(this.postId, this.commentId)
        ).data;
        this.isNotLoaded = false;
      }

      this.isLoading = false;
    },
    deletecmt(content) {
      this.commentData.splice(location(content, this.commentData), 1);
    },
  },
};
</script>

<style></style>
