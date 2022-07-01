<template>
  <div
    class="
      header
      sticky
      top-0
      flex
      py-1
      shadow-lg
      max-h-12
      w-full
      bg-gray-900
      text-white
      border-b-2 border-gray-700
    "
  >
    <div class="px-3 ml-2 flex rounded max-h-10">
      <img src="@/assets/logo.png" alt="" width="40" height="50" />
      <router-link class="logo xl:py-2 px-5 font-black text-sm" to="/"
        >School Connect</router-link
      >
    </div>
    <div class="flex-grow"></div>
    <!------------------------------------------- Vùng thông báo -->
    <div class="flex justify-center h-screen px-2 py-1">
      <div class="relative my-2">
        <button
          @click="notiDrop = !notiDrop"
          divclass="relative z-10 block rounded-md bg-white p-2 focus:outline-none"
        >
          <svg
            class="h-5 w-5 text-gray-800"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 20 20"
            fill="currentColor"
          >
            <path
              d="M10 2a6 6 0 00-6 6v3.586l-.707.707A1 1 0 004 14h12a1 1 0 00.707-1.707L16 11.586V8a6 6 0 00-6-6zM10 18a3 3 0 01-3-3h6a3 3 0 01-3 3z"
            />
          </svg>
        </button>

        <div x-show="dropdownOpen" @click="notiDrop = false"></div>

        <div
          v-if="notiDrop"
          class="
            min-w-screen
            h-screen
            animated
            fadeIn
            faster
            fixed
            left-0
            top-0
            block
            justify-center
            items-start
            inset-0
            z-50
          "
          @click.self="notiDrop = false"
        >
          <div
            v-if="notiDrop"
            class="
              absolute
              right-0
              mt-12
              bg-white
              rounded-md
              shadow-lg
              overflow-hidden
              z-20
            "
            style="width: 20rem"
          >
            <div class="py-2">
              <!-- Sẽ đặt dữ liệu tại đây -->
              <a
                href="#"
                class="
                  flex
                  items-center
                  px-4
                  py-3
                  border-b
                  hover:bg-gray-100
                  -mx-2
                "
              >
                <img
                  class="h-8 w-8 rounded-full object-cover mx-1"
                  src="https://images.unsplash.com/photo-1494790108377-be9c29b29330?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=334&q=80"
                  alt="avatar"
                />
                <p class="text-gray-600 text-sm mx-2">
                  <span class="font-bold" href="#">Huyền</span> đã trả lời
                  <span class="font-bold text-blue-500" href="#"
                    >bình luận</span
                  >
                  của bạn. 2p
                </p>
              </a>
              <!-- Kết thúc vùng đặt dữ liệu -->
            </div>
            <a
              href="#"
              class="block bg-black text-white text-center font-bold py-2"
              >Xem toàn bộ thông báo</a
            >
          </div>
        </div>
      </div>
    </div>
    <!--  ------------------------------------------------------------------User tag -->
    <div
      class="
        mr-3
        flex
        border-b-2 border-white
        rounded
        hover:border-gray-200
        text-white
        hover:bg-gray-600
        textcentre
        order-last
      "
      @mouseover="userDrop = true"
      @mouseleave="userDrop = fasle"
    >
      <img
        class="inline-block object-cover w-10 h-10 rounded-full mx-0.5"
        :src="user.avatar"
        alt="Profile image"
      />
      <a class="inline-block py-2 px-4 xl:block 2xl:block" href="#">{{
        user.username
      }}</a>
      <div
        v-if="userDrop"
        class="
          absolute
          right-0
          mt-10
          bg-white
          rounded-md
          shadow-lg
          overflow-hidden
          z-10
        "
        style="width: 15rem"
      >
        <!-- Tài khoản -->
        <div class="py-2">
          <a
            href="http://localhost:8080/profile/22"
            class="flex items-center px-3 py-1 border-b hover:bg-gray-100"
          >
            <svg
              class="h-6 w-6 text-blue-500"
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
              <path
                d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 0 0 2.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 0 0 1.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 0 0 -1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 0 0 -2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 0 0 -2.573 -1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 0 0 -1.065 -2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 0 0 1.066 -2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"
              />
              <circle cx="12" cy="12" r="3" />
            </svg>

            <p class="text-gray-600 text-sm pl-2">Setting</p>
          </a>
          <!-- Đăng xuất -->
          <a
            href="#"
            class="flex items-center px-3 py-1 border-b hover:bg-gray-100"
            @click="logout"
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
                d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1"
              />
            </svg>

            <p class="text-gray-600 text-sm pl-2">Log-out</p>
          </a>
        </div>
      </div>
    </div>
    <!-- ------------------- Thong bao -->
  </div>
</template>
<script>
import { header } from "../logic/startup.js";
export default {
  data() {
    return {
      notiDrop: false,
      userDrop: false,
      messageDrop: false,
      user: {},
    };
  },
  async created() {
    this.user = await header();
  },
  methods: {
    logout() {
      document.cookie = "username=";
      document.cookie = "token=";
      document.cookie = "userID=";
      document.cookie = "name=";
      this.$router.go();
    },
    gotoProfile() {
      this.$router.push({ path: "profile" });
    },
  },
};
</script>
<style>
.logo {
  font-family: Bahnschrift;
  font-size: 5mm;
}
.header {
  background-color: #f5f3f4;
}
</style>
