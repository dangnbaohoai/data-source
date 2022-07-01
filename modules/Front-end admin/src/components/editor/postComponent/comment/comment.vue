<template>
  <div class="bg-white p-5 mt-2 rounded-lg">
    <!--Tao Comment -->
    <form class="flex my-4" v-on:submit.prevent="sendComment">
      <img
        class="mt-2 rounded-full w-8 h-8 sm:w-10 sm:h-10 avatar"
        :src="userData.avatar"
        alt=""
      />
      <div class="ml-3 rounded-lg w-full px-2 py-1">
        <p class="text-xl text-left font-bold text-gray-500">
          {{ userData.username }}
        </p>
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
            class="
              flex
              border-2
              px-4
              py-0.5
              text-green-600
              hover:bg-green-500 hover:text-white
              rounded-lg
            "
            type="submit"
          >
            <svg
              class="h-6 w-6"
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
            ><span class="font-semibold">Comment</span>
          </button>
        </div>
      </div>
    </form>
    <!-- Khu vực hiển thị bình luận -->
    <div
      class="flex my-1"
      v-for="comment in commentData"
      :key="comment.commentId"
    >
      <div class="flex-shrink-0 mr-3">
        <img
          class="mt-2 rounded-full w-8 h-8 sm:w-10 sm:h-10 avatar"
          :src="comment.avatarOfUser"
          alt=""
        />
      </div>
      <div
        class="
          flex-1
          border
          rounded-lg
          px-4
          py-2
          sm:px-6 sm:py-4
          leading-relaxed
        "
      >
        <!-- <div class="flex items-center">
          <strong class="text-xl">{{ comment.userName }}</strong>
          <span class="ml-2 text-xs text-gray-400">{{
            comment.createDate
          }}</span>
          <div class="flex-grow"></div>
          <actionpanel :comment="comment" :forumId="forumId" />
        </div>

        <p class="text-sm text-left">
          {{ comment.content }}
        </p> -->
        <singleComment
          :comment="comment"
          :forumId="forumId"
          @deletecmt="deletecmt(comment)"
          :postId="postId"
        />
        <commentchild
          :userData="userData"
          :commentId="comment.commentId"
          :postId="postId"
          :numberReplyCount="comment.numberReplyCount"
          :forumId="forumId"
        />
      </div>
    </div>
    <div
      class="
        text-sm text-gray-500
        font-semibold
        cursor-pointer
        flex
        items-center
      "
      @click="getMoreData"
    >
      <svg
        fill="none"
        class="w-10 h-10 animate-spin text-green-500"
        viewBox="0 0 32 32"
        xmlns="http://www.w3.org/2000/svg"
        v-if="isLoading"
      >
        <path
          clip-rule="evenodd"
          d="M15.165 8.53a.5.5 0 01-.404.58A7 7 0 1023 16a.5.5 0 011 0 8 8 0 11-9.416-7.874.5.5 0 01.58.404z"
          fill="currentColor"
          fill-rule="evenodd"
        />
      </svg>
      <span>Get more comment</span>
    </div>
  </div>
</template>

<script>
import $ from "jquery";
import { header } from "../../../logic/startup";
import { viewCommentInPost, CommentPost } from "../../../logic/postAPI";
import { location } from "../../../logic/handlingdata";
import commentchild from "../comment/commentChild.vue";
// import actionpanel from "./actionPanel.vue";
import singleComment from "./singleComment.vue";
export default {
  components: { commentchild, singleComment },
  props: {
    data: Object,
    postId: String,
    forumId: String,
  },
  async created() {
    await this.getMoreData();
  },
  data() {
    return {
      userData: {},
      commentpage: 1,
      commentData: [],
      commentSendData: {
        content: "",
        imageComment: "",
        postId: 0,
        timeComment: "",
      },
      isPosting: false,
      isLoading: false,
    };
  },
  methods: {
    async sendComment() {
      this.isPosting = true;
      this.commentSendData.postId = this.postId;
      this.commentSendData.timeComment = new Date().toJSON();
      const res = await CommentPost(this.commentSendData);
      console.log(res);
      this.commentSendData.content = "";
      this.commentData.unshift(res);
      console.log(this.commentData.data);
      this.isPosting = false;
    },
    async getMoreData() {
      this.isLoading = true;
      this.userData = await header();
      const temp = await viewCommentInPost(this.postId, this.commentpage);
      this.commentData.push(...temp.data);
      console.log("commentData");
      console.log(this.commentData);
      this.commentpage++;
      this.isLoading = false;
    },
    deletecmt(cmt) {
      this.commentData.splice(location(cmt, this.commentData), 1);
    },
  },
};
$("#container").on("keyup", "textarea", function () {
  $(this).height(0);
  $(this).height(this.scrollHeight);
});
$("#container").find("textarea").keyup();
</script>

<style>
input {
  background-color: transparent;
  border: 0px solid;
  color: rgb(34, 27, 27);
}
textarea:focus,
input:focus {
  outline: none;
}
.avatar {
  object-fit: cover;
}
</style>
