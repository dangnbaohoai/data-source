<template>
  <reportModal v-if="isReport" @close="report" />
  <div class="flex" @mouseover="isDropon = true" @mouseleave="dropoff">
    <div
      class="flex transition duration-300 ease-in-out origin-center transform"
    >
      <div class="rounded-full text-white -mx-10" v-if="!isOwner">
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
        v-if="isOwner"
        :class="!isDropon ? 'option' : 'showup'"
        @click="deleteMs"
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
    </div>
    <div class="rounded-full text-black -mx-10" v-if="isOwner">
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
  <!-- <ComfirmModel
    :msg="msg"
    ref="confirmDialogue"
    v-if="isConfirm"
    @close="close"
  /> -->
  <!-- ------------------------------------------------------------------  -->
  <reportModel
    :forumId="ForumID"
    :roomChatId="roomChatId"
    v-if="isReport"
    @close="report"
  />
</template>

<script>
import { getcookie } from "../../logic/getcookie";
import reportModal from "./reportMessage.vue";
export default {
  components: { reportModal },
  setup() {},
  props: {
    roomChatId: {
      type: Number,
      required: true,
    },
    userId: {
      type: Number,
      required: true,
    },
    ForumID: {
      type: Number,
      required: true,
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
    this.isOwner = true;
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
      if (getcookie("userID") == this.userId) this.isReport = !this.isReport;
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
    deleteMs() {},
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
.p-confirm-popup {
  /* display: -webkit-box; */
  display: -ms-flexbox;
  display: grid;
  height: 100px;

  border-radius: 0.375rem;
  max-height: 90%;
  -webkit-transform: scale(1);
  transform: scale(1);
  background-color: #ffffff;
  font-size: 5mm;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1),
    0 4px 6px -2px rgba(0, 0, 0, 0.05);
  font-weight: 500;
}
.p-confirm-popup-message {
  padding-inline: 20px;
  background-color: #fee2e2;
  font-size: 1.25rem;
  line-height: 1.75rem;
}
.p-confirm-popup-content {
  font-size: 1.25rem;
  line-height: 1.75rem;
}
.p-button.p-button-text {
  background-color: transparent;
  color: #a5b4fc;
  font-weight: 500;
  border-color: transparent;
}
.p-dialog .p-dialog-footer {
  border-top: 0 none;
  background: #22272d;
  padding: 0 1.5rem 1.5rem 1.5rem;
  text-align: right;
  border-bottom-right-radius: 6px;
  border-bottom-left-radius: 6px;
}
.p-button {
  padding: 2px 20px;
  border-radius: 0.5rem;
}
.p-confirm-popup-reject {
  color: black;
}
.p-confirm-popup-accept {
  background-color: #ef4444;
  color: white;
  font-weight: 500;
}
</style>
