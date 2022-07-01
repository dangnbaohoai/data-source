<template>
  <form
    class="
      min-w-screen
      h-screen
      animated
      fadeIn
      faster
      fixed
      left-0
      top-0
      grid
      justify-center
      items-start
      inset-0
      z-50
      outline-none
      focus:outline-none
      bg-no-repeat bg-center bg-cover
    "
    style="background-color: rgba(255, 255, 255, 0.7)"
    id="modal-id"
    @submit.prevent="msg = !msg"
  >
    <div
      class="
        max-w-2xl
        overflow-hidden
        rounded-lg
        shadow-lg
        text-left
        block
        my-10
        2xl:my-10
        divide-y
        bg-gray-50
      "
    >
      <div class="flex">
        <h2 class="text-lg font-bold text-center py-2 bg-gray-100 flex-grow">
          Create new post
        </h2>

        <div
          class="
            w-12
            bg-red-500
            hover:bg-red-400
            focus:bg-green-500
            group-hover:border-gray-700
            cursor-pointer
            flex
            items-center
          "
          @click="close"
        >
          <svg
            class="h-6 w-6 text-white mt-0.5 ml-3"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <polygon
              points="7.86 2 16.14 2 22 7.86 22 16.14 16.14 22 7.86 22 2 16.14 2 7.86 7.86 2"
            />
            <line x1="15" y1="9" x2="9" y2="15" />
            <line x1="9" y1="9" x2="15" y2="15" />
          </svg>
        </div>
      </div>
      <!-- <select name="cars" id="cars" v-bind="showpost.forumIdForUser">
        <option
          v-for="forum in listJoinedForum"
          :value="forum.forumId"
          :key="forum.forumId"
        >
          {{ forum.nameForum }}
        </option>
      </select> -->
      <div class="title flex py-2 mx-2" v-if="isEvent">
        <p class="text-xl mr-4">Event title:</p>
        <input
          class="truncate flex-grow bg-gray-50 border-b-2 text-xl"
          type="text"
          name=""
          id=""
          v-model="showpost.nameEvent"
          required
        />
      </div>
      <tiptap
        :msg="msg"
        @posting="post"
        :class="[contentnull ? 'border-2 border-red-500' : '']"
        :content="showpost.content"
      />
      <div v-if="isEvent">
        <div class="flex h-6">
          <p>Date :</p>
          <input
            type="date"
            name=""
            v-model="showpost.dateOfEvent"
            required
          /><br />
          <p class="mx-4">Time:</p>
          <input
            type="time"
            id="birthdaytime"
            name=""
            v-model="showpost.time"
          />
        </div>
        <div class="flex mt-2 h-6">
          <p class="mr-4">Address of event:</p>
          <input
            class="truncate flex-grow bg-gray-50 border-b-2 text-xl"
            type="text"
            name=""
            id=""
            v-model="showpost.addressOfEvent"
            required
          />
        </div>
      </div>
      <div class="flex justify-items-center m-2 text-center rounded-lg pt-2">
        <div
          class="
            text-center
            w-max
            text-white
            px-4
            mr-2
            bg-red-700
            border-4 border-white
            hover:bg-red-300 hover:border-red-800
            rounded-lg
          "
          v-if="!isEvent"
        >
          Post
        </div>
        <div
          class="
            text-center
            w-max
            text-black
            px-4
            mr-2
            bg-white
            border-4
            hover:bg-gray-600 hover:text-white
            rounded-lg
          "
          v-on:click="thatAevent"
          v-if="isEvent"
        >
          Post
        </div>
        <div
          class="
            text-right
            w-max
            text-white
            px-4
            mx-2
            bg-red-700
            border-4 border-white
            hover:bg-red-300 hover:border-red-800
            rounded-lg
          "
          v-if="isEvent"
        >
          Event
        </div>
        <!-- neu day la event -->

        <div
          class="
            text-right
            w-max
            text-black
            px-4
            mx-2
            bg-white
            border-4
            hover:bg-gray-600 hover:text-white
            rounded-lg
          "
          v-if="!isEvent"
          v-on:click="thatAevent"
        >
          Event
        </div>
      </div>
      <uploadImage @update="updateImg" :importIMG="showpost.linkImage" />
      <Alert :msgs="alertMsg" :color="alertColor" @close="alertMsg = null" />
      <div aria-label="Page navigation" class="py-2">
        <div class="px-4 flex justify-end">
          <button
            v-if="isEdit"
            class="
              bg-green-500
              hover:bg-blue-700
              text-white
              font-bold
              py-2
              px-4
              rounded-2xl
              hover:shadow-none
              shadow-lg
            "
            type="submit"
          >
            Edit post
          </button>
          <button
            v-else
            class="
              bg-green-500
              hover:bg-blue-700
              text-white
              font-bold
              py-2
              px-4
              rounded-2xl
              hover:shadow-none
              shadow-lg
            "
            type="submit"
          >
            Submit
          </button>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
import Tiptap from "./Tiptap.vue";
import uploadImage from "./uploadInmage.vue";
import Alert from "../smallComponent/alert.vue";
import { Post, updatePost } from "../../logic/postAPI";
// import { mapGetters } from "vuex";
export default {
  // computed: {
  //   ...mapGetters(["listJoinedForum"]),
  // },
  components: {
    Tiptap,
    uploadImage,
    Alert,
  },
  props: {
    forumId: Number,
    content: Object,
  },
  data() {
    return {
      isEvent: false,
      msg: false,
      alertMsg: "",
      alertColor: "green",
      showpost: {
        content: "",
        dateOfEvent: "2018-07-22",
        time: {},
        forumIdForUser: 0,
        linkImage: [],
      },
      contentnull: false,
      isEdit: false,
    };
  },
  created() {
    this.showpost.forumIdForUser = this.forumId;
    if (this.content != null && this.content != "") {
      this.showpost = this.content;
      if (this.content.nameOfEvent) {
        this.showpost.nameEvent = this.content.nameOfEvent;
        this.isEvent = true;
      }
      this.showpost.forumIdForUser = this.forumId;
      this.showpost.forumId = [];
      this.isEdit = true;
    }
    console.log("================this.showpost");
    console.log(this.showpost);
  },
  methods: {
    thatAevent() {
      this.isEvent = !this.isEvent;
    },
    close() {
      this.$emit("close");
    },
    async post(content) {
      if (!this.isEdit) {
        if (content == "" || content == null || content == "<p></p>") {
          this.contentnull = true;
          this.alertMsg = "please say something";
        } else {
          this.showpost.content = content;
          console.log(this.showpost);
          const res = await Post(this.showpost);
          if (res.message == "created success") {
            this.$root.test({
              group: "top",
              title: res.message,
              text: "",
            });
            this.close();
          }
        }
      } else {
        if (content == "" || content == null || content == "<p></p>") {
          this.contentnull = true;
          this.alertMsg = "please say something";
        } else {
          this.showpost.content = content;
          console.log("this is edit");
          const res = await updatePost(this.showpost);
          if (res.message == "update success") {
            this.$root.test({
              group: "top",
              title: res.message,
              text: "",
            });
            this.close();
          } else {
            this.$root.test({
              group: "bottom",
              title: "ERROR !",
              text: res.message,
            });
          }
        }
      }
    },
    updateImg(img) {
      this.showpost.linkImage = img;
    },
    test() {
      this.alert("test", "green");
    },
    alert(alertMsg, alertColor) {
      this.alertMsg = alertMsg;
      this.alertColor = alertColor;
    },
  },
};
</script>
