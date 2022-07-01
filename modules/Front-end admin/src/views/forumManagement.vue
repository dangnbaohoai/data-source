<template>
  <div class="xl:mx-24">
    <div class="flex mt-12">
      <button
        class="
          px-4
          py-2
          bg-gray-800
          rounded-md
          flex
          content-center
          border-2
          text-white
          hover:bg-white hover:text-black hover:border-gray-200
        "
        @click="addForum"
        v-if="isaddUser"
      >
        <svg
          class="h-8 w-8 text-white"
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
          <path d="M9 11l-4 4l4 4m-4 -4h11a4 4 0 0 0 0 -8h-1" />
        </svg>
        <p class="ml-1 self-center my-1">Back to view forum</p>
      </button>
      <button
        class="
          px-4
          py-2
          bg-gray-800
          rounded-md
          flex
          content-center
          border-2
          text-white
          hover:bg-white hover:text-black hover:border-gray-200
        "
        @click="addForum"
        v-else
      >
        <svg
          class="h-8 w-8 self-center"
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
          <rect x="4" y="4" width="16" height="16" rx="2" />
          <line x1="9" y1="12" x2="15" y2="12" />
          <line x1="12" y1="9" x2="12" y2="15" />
        </svg>
        <p class="ml-1 self-center my-1">New Forum</p>
      </button>

      <button
        class="
          bg-gray-700
          text-white
          font-bold
          py-2
          px-4
          rounded
          hover:bg-gray-600
          flex
          items-center
          border-2
          mx-5
        "
        @click="downloadFile"
      >
        <svg
          class="h-7 w-7 text-white"
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
          <rect x="4" y="4" width="16" height="16" rx="2" />
          <line x1="4" y1="12" x2="20" y2="12" />
          <line x1="12" y1="4" x2="12" y2="20" />
        </svg>
        Export to Excel
      </button>
      <!-- <input
        type="text"
        class="ml-4 w-60 bg-gray-800 rounded-xl text-white px-4"
        placeholder="Searching user"
      /> -->
      <div class="w-10"></div>
    </div>
    <router-view />
  </div>
</template>

<script>
import { getExcel } from "../logic/ForumAPI";
export default {
  data() {
    return {
      isaddUser: false,
    };
  },
  created() {
    if (this.$route.path == "/forumManagement") {
      this.isaddUser = false;
    } else {
      this.isaddUser = true;
    }
  },
  components: {},
  methods: {
    addForum() {
      if (this.$route.path == "/forumManagement") {
        this.$router.push({ path: "/forumManagement/new" });
        this.isaddUser = true;
      } else {
        this.$router.push({ path: "/forumManagement" });
        this.isaddUser = false;
      }
    },
    downloadFile() {
      getExcel();
    },
  },
};
</script>

<style></style>
