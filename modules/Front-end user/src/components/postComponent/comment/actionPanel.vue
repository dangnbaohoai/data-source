<template>
  <div class="flex h-6" @mouseover="isDropon = true" @mouseleave="dropoff">
    <div
      class="flex transition duration-300 ease-in-out origin-center transform"
    >
      <button
        class="flex align-middle mx-2 px-2 rounded-sm hover:bg-gray-200"
        :class="!isDropon ? 'option' : 'showup'"
        @click="deletePost"
        v-if="isOwner"
      >
        <svg
          class="h-6 w-6 text-red-500"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
          />
        </svg>
        <p>Delete</p>
      </button>
      <button
        class="
          flex
          mx-2
          px-2
          rounded-sm
          hover:bg-gray-200
          transition
          duration-300
          ease-in-out
        "
        :class="!isDropon ? 'option' : 'showup'"
        @click="report"
        v-if="!isOwner"
      >
        <svg
          class="h-6 w-6 text-yellow-500"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M11 5.882V19.24a1.76 1.76 0 01-3.417.592l-2.147-6.15M18 13a3 3 0 100-6M5.436 13.683A4.001 4.001 0 017 6h1.832c4.1 0 7.625-1.234 9.168-3v14c-1.543-1.766-5.067-3-9.168-3H7a3.988 3.988 0 01-1.564-.317z"
          />
        </svg>
        <p>Report</p>
      </button>
      <button
        class="
          flex
          mx-2
          px-2
          rounded-sm
          hover:bg-gray-200
          transition
          duration-300
          ease-in-out
        "
        :class="!isDropon ? 'option' : 'showup'"
        @click="editPost"
        v-if="isOwner"
      >
        <svg
          class="h-6 w-6 text-gray-500"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path
            d="M11 4H4a2 2 0 0 0-2 2v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2v-7"
          />
          <path d="M18.5 2.5a2.121 2.121 0 0 1 3 3L12 15l-4 1 1-4 9.5-9.5z" />
        </svg>
        <p>Edit</p>
      </button>
      <div class="rounded-full">
        <svg
          fill="currentColor"
          viewBox="0 0 20 20"
          class="flex-shrink-0 w-5 h-5 ml-1"
        >
          <path
            :class="{ 'rotate-180': isDropon }"
            class="transition duration-300 ease-in-out origin-center transform"
            fill-rule="evenodd"
            d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
            clip-rule="evenodd"
          ></path>
        </svg>
      </div>
    </div>
  </div>
  <!-- <ComfirmModel
    :msg="msg"
    ref="confirmDialogue"
    v-if="isConfirm"
    @close="close"
  /> -->
  <!-- ------------------------------------------------------------------  -->
  <reportModel
    :forumId="forumId"
    :commentId="comment.commentId"
    v-if="isReport"
    @close="report"
    :postId="postId"
  />
</template>

<script>
// import { deleteComment } from "../../../logic/postAPI";
import reportModel from "./reportModel.vue";
import { getcookie } from "../../../logic/getcookie";
export default {
  components: { reportModel },
  emits: ["turnEdit", "deletecmt"],
  props: {
    comment: {
      type: Object,
      required: true,
    },
    forumId: {
      type: String,
    },
    postId: {
      type: Number,
    },
  },
  data() {
    return {
      isDropon: false,
      msg: null,
      isConfirm: false,
      isReport: false,
      isOwner: false,
    };
  },
  created() {
    if (getcookie("userID") == this.comment.userId) {
      this.isOwner = true;
    }
  },
  methods: {
    dropoff() {
      setTimeout(() => {
        this.isDropon = false;
      }, 2000);
    },
    deletePost() {
      if (confirm("Are you to delete this post?")) {
        // await deleteComment(this.commentId, this.ForumID);
        this.$emit("deletecmt");
      }
    },
    async editPost() {
      this.$emit("turnEdit");
    },
    report() {
      this.isReport = !this.isReport;
    },
  },
};
</script>

<style lang="css">
.menu {
  display: none;
}

.header:hover .menu {
  display: block;
}
.option {
  visibility: hidden;
  transform: scale(0);
  transition: 0.5s;
}
.showup {
  visibility: visible;
  transform: scale(1);
  transition: 0.5s;
}
</style>
