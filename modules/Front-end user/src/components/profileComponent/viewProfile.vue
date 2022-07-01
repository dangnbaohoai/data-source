<template>
  <div class="min-h-screen" v-if="avatar != ''">
    <div class="grid justify-items-center">
      <img
        class="rounded-full border-8 border-white h-40 w-40 mt-20"
        :src="avatar"
        v-if="avatar != ''"
      />

      <h1 class="username font-semibold">{{ username }}</h1>

      <router-link
        :to="{ path: '/Messenger/' + id }"
        class="
          py-3
          px-8
          bg-red-
          flex
          items-center
          bg-gray-600
          text-white
          m-1
          text-2xl
          font-bold
          rounded-xl
        "
        v-if="id"
        :disabled="!isActive"
        ><svg
          class="h-7 w-7 mr-2"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <line x1="22" y1="2" x2="11" y2="13" />
          <polygon points="22 2 15 22 11 13 2 9 22 2" />
        </svg>
        Contact</router-link
      >
      <div class="grid grid-cols-1 gap-1 xl:grid-cols-2 w-auto">
        <div
          class="text-justify flex font-sans font-medium text-lg"
          v-for="data in datas"
          v-bind:key="data.field"
        >
          <div class="py-2 xl:px-4 bg-white mr-1 w-80">
            {{ data.field }}
          </div>
          <div
            class="
              py-2
              px-2
              xl:px-4
              bg-white
              w-full
              rounded-tr-xl
              transition
              duration-150
              ease-in
              transform
              hover:scale-125
              hover:text-white
              hover:translate-x-12
              hover:bg-gray-600
            "
          >
            {{ data.value }}
          </div>
        </div>
      </div>
      <div
        class="my-4 bg-gray-600 py-2 px-4 cursor-pointer text-lg text-white"
        @click="setEdit"
        v-if="!isOther"
      >
        Edit profile
      </div>
    </div>
  </div>
  <div class="min-h-screen animate-pulse" v-else>
    <div class="grid justify-items-center">
      <div
        class="rounded-full border-8 border-white bg-gray-400 h-40 w-40 mt-20"
      ></div>
      <div class="h-10 w-44 bg-gray-300 -my-2 mb-4"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
      <div class="h-10 my-1 w-full md:w-1/3 bg-gray-300"></div>
    </div>
  </div>
</template>

<script>
import { createRoomChat } from "../../logic/chatAPI";
// import Date from "../smallComponent/time.vue";
export default {
  // components: { Date },
  props: {
    username: String,
    avatar: String,
    datas: Object,
    isOther: {
      type: Boolean,
      default: false,
    },
    id: Number,
  },
  data() {
    return {
      isActive: true,
    };
  },
  methods: {
    setEdit() {
      this.$emit("edit");
    },
    async createRoomChat() {
      this.isActive = false;
      console.log(await createRoomChat({ userId: this.id }));
      this.isActive = true;
    },
    addnoti() {
      this.$root.test({
        group: "top",
        title: "Cảnh báo",
        text: "Nội dung đã xóa",
      });
    },
  },
};
</script>

<style></style>
