<template>
  <div class="bg-gray-100 text-left">
    <div class="p-4 w-full bg-gray-200 animate-pulse" v-if="isLoading">
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
    </div>
    <router-link
      class="py-2 hover:bg-gray-50"
      v-for="noti in data.data"
      :key="noti.notifyId"
      @click="read(noti.notifyId)"
      :class="[noti.statusMessage == 'read' ? 'bg-gray-100' : 'bg-gray-200']"
      :to="{
        path: '/forum/' + noti.forumResponse.forumId + '/post/' + noti.postId,
      }"
    >
      <!-- Sẽ đặt dữ liệu tại đây -->
      <a href="#" class="flex items-center px-4 py-3 border-b -mx-2">
        <img
          class="h-8 w-8 rounded-lg object-cover mx-1"
          :src="noti.forumResponse.coverImage"
          alt="avatar"
        />
        <p class="text-gray-600 text-sm mx-2">
          <span class="font-bold" href="#">{{ noti.message }}</span>
        </p>
      </a>
      <!-- Kết thúc vùng đặt dữ liệu -->
    </router-link>
  </div>
</template>

<script>
import { getNotify, readNotify } from "../../logic/api";
export default {
  data() {
    return {
      isLoading: true,
      data: [],
    };
  },
  async created() {
    this.data = await getNotify(1);
    console.log(this.data);
    this.isLoading = false;
  },
  methods: {
    async read(id) {
      await readNotify(id);
    },
  },
};
</script>

<style></style>
