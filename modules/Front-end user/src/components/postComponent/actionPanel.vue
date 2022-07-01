<template>
  <div class="flex h-6" @mouseover="isDropon = true" @mouseleave="dropoff">
    <div
      class="flex transition duration-300 ease-in-out origin-center transform"
    >
      <ConfirmPopup></ConfirmPopup>
      <button
        class="flex align-middle mx-2 px-2 rounded-sm hover:bg-gray-200"
        :class="!isDropon ? 'option' : 'showup'"
        @click="deletePost"
        v-if="isOwner || !isuserisCensor"
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
    :forumId="ForumID"
    :postId="PostID"
    v-if="isReport"
    @close="report"
  />
</template>

<script>
import { deletePost } from "../../logic/postAPI";
import reportModel from "./reportModel.vue";
import { getcookie } from "../../logic/getcookie";
// import ConfirmDialog from "primevue/confirmdialog";
// import ConfirmationService from "primevue/confirmationservice";
// import { useConfirm } from "primevue/useconfirm";
import ConfirmPopup from "primevue/confirmpopup";
export default {
  components: { reportModel, ConfirmPopup },
  emits: ["remove", "edit"],
  setup() {},
  props: {
    PostID: {
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
    isuserisCensor: Boolean,
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
    if (getcookie("userID") == this.userId) {
      this.isOwner = true;
    }
  },
  methods: {
    dropoff() {
      setTimeout(() => {
        this.isDropon = false;
      }, 2000);
    },
    editPost() {
      this.$emit("edit");
    },
    report() {
      this.isReport = !this.isReport;
    },
    async deletePost(event) {
      await this.$confirm.require({
        target: event.currentTarget,
        message: "Are you sure you want to delete this post?",
        icon: "pi pi-exclamation-triangle",
        accept: async () => {
          const res = await deletePost(this.ForumID, this.PostID);
          console.log(res);
          if (res == "delete success" || res == "ok") {
            console.log("It work" + this.PostID);
            this.$root.test({
              group: "top",
              title: "Delete success",
              text: "Your post has been deleted",
            });
            this.$emit("remove");
          } else {
            this.$root.test({
              group: "bottom",
              title: "Error",
              text: res,
            });
          }
        },
        reject: () => {
          console.log("It also work" + this.PostID);
        },
      });
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
