<template>
  <reportModal
    v-if="isReport"
    @close="report"
    :messageReport="chat.messageId"
    :roomChatId="roomchatid"
    :usernameReport="chat.userSend.userId"
  />
  <div class="flex" @mouseover="isDropon = true" @mouseleave="dropoff">
    <div
      class="flex transition duration-300 ease-in-out origin-center transform"
    >
      <div class="rounded-full text-black -mx-10 mr-1" v-if="!isOwner">
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
      <button
        class="
          ml-14
          flex
          transition
          duration-300
          ease-in-out
          bg-gray-800
          px-2
          py-0.5
          rounded-xl
        "
        v-if="!isOwner"
        :class="!isDropon ? 'option' : 'showup'"
        @click="report"
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
      <ConfirmPopup />
      <button
        class="
          ml-14
          flex
          transition
          duration-300
          ease-in-out
          bg-gray-800
          px-2
          rounded-xl
          items-center
        "
        v-if="isOwner"
        :class="!isDropon ? 'option' : 'showup'"
        @click="deleteMs"
      >
        <svg
          class="h-6 w-6 text-red-500"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <polyline points="3 6 5 6 21 6" />
          <path
            d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"
          />
          <line x1="10" y1="11" x2="10" y2="17" />
          <line x1="14" y1="11" x2="14" y2="17" />
        </svg>
        <p>Delete</p>
      </button>
      <div class="rounded-full text-black -mx-6 ml-4" v-if="isOwner">
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
</template>

<script>
import { getcookie } from "../../logic/getcookie";
import reportModal from "./reportMessage.vue";
import ConfirmPopup from "primevue/confirmpopup";
export default {
  components: { reportModal, ConfirmPopup },
  emits: ["deleteMSG"],
  props: {
    chat: {
      type: Object,
      required: true,
    },
    userId: {
      type: Number,
      required: true,
    },
    roomchatid: Number,
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
    console.log(this.chat);
    if (getcookie("userID") == this.userId) this.isOwner = true;
  },
  methods: {
    dropoff() {
      setTimeout(() => {
        this.isDropon = false;
      }, 2000);
    },
    async editPost() {
      if (confirm("Press a button!")) {
        console.log("EDIT" + this.roomChatId);
      }
    },
    report() {
      this.isReport = !this.isReport;
    },
    // async deletePost(event) {
    //   await this.$confirm.require({
    //     target: event.currentTarget,
    //     message: "Are you sure you want to delete this post?",
    //     icon: "pi pi-exclamation-triangle",
    //     accept: async () => {
    //       const res = await deletePost(this.ForumID, this.roomChatId);
    //       console.log(res);
    //       if (res == "delete success") {
    //         console.log("It work" + this.roomChatId);
    //       }
    //     },
    //     reject: () => {
    //       console.log("It also work" + this.roomChatId);
    //     },
    //   });
    // },
    async deleteMs() {
      await this.$confirm.require({
        target: event.currentTarget,
        message: "Are you sure you want to delete this message?",
        icon: "pi pi-exclamation-triangle",
        accept: async () => {
          this.$emit("deleteMSG");
        },
        reject: () => {},
      });
    },
  },
};
</script>

<style lang="css"></style>
