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
      class="py-2 hover:bg-gray-50 flex items-center"
      v-for="room in data"
      :key="room.roomId"
      :class="[
        (room.statusNewMessage == 'unseen' ||
          room.statusNewMessage == 'unread') &&
        room.userRecipient == username
          ? 'bg-gray-300'
          : '',
      ]"
      :to="{
        path: '/Messenger/' + room.userResponse1.userId,
      }"
    >
      <!-- Sẽ đặt dữ liệu tại đây -->
      <img
        class="h-10 w-10 rounded-full object-cover m-2"
        :src="room.userResponse1.avatar"
      />
      <div class="text-left ml-4 lg:block w-60 grid">
        <strong>{{ room.userResponse1.fullName }}</strong
        ><spann class="text-xs font-semibold"
          ><Time :date="room.modifiedDate"
        /></spann>
        <!-- <span>{{ room.modifiedDate }}</span> -->
        <p>{{ room.messageNew }}</p>
      </div>
    </router-link>
  </div>
</template>

<script>
import { viewRooms } from "../../logic/chatAPI";
import { getcookie } from "../../logic/getcookie";
import Time from "../smallComponent/time.vue";
export default {
  components: { Time },
  data() {
    return {
      data: [],
      page: 1,
      isLoading: true,
      username: "",
    };
  },
  async created() {
    this.data = (await viewRooms(this.page)).data;
    console.log(this.data);
    this.isLoading = false;
    this.username = getcookie("username");
  },
};
</script>

<style></style>
