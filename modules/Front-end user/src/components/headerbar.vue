<template>
  <header
    class="
      fixed
      top-0
      z-40
      lg:z-50
      w-full
      max-w-8xl
      mx-auto
      flex
      h-12
      bg-gradient-to-r
      from-gray-100
      via-gray-300
      shadow-lg
      py-1
    "
  >
    <!-- sticky top-0 z-40 lg:z-50 w-full max-w-8xl mx-auto flex h-12 bg-white -->
    <!-- header sticky top-0 flex py-1 shadow-lg max-h-12 w-full -->
    <div class="px-3 ml-2 flex rounded max-h-10">
      <img src="@/assets/logo.png" alt="" width="40" height="50" />
      <router-link
        class="logo xl:py-2 px-5 text-gray-900 font-black text-sm"
        to="/"
        >School Connect</router-link
      >
    </div>
    <div
      class="
        flex-grow
        placeholder-gray-500
        hidden
        md:block md:mx-10
        lg:mx-20
        xl:mx-50
        2xl:mx-80
        px-10
        max-h-10
      "
      @mouseover="isSearch = true"
      @mouseleave="isSearch = false"
    >
      <div class="flex shadow-xl">
        <input
          class="
            w-full
            h-10
            flex-1
            px-4
            rounded-l-lg
            focus:outline-none
            bg-white
          "
          placeholder="Search forum or user"
          v-model="search"
        />
        <svg
          class="h-10 w-10 p-2 text-green- bg-white rounded-r-lg"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
          />
        </svg>
      </div>

      <div v-if="isSearch && search" class="border-2 divide-y divide-dashed">
        <div class="py-3 px-4 w-full bg-white flex" v-if="awaitingSearch">
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
          <p class="ml-5 mt-2 animate-pulse">Searching ...</p>
        </div>
        <p
          class="bg-gray-50 py-1 px-4 w-full flex text-sm\"
          v-if="!awaitingSearch"
        >
          Forum
        </p>
        <router-link
          class="bg-white py-3 px-4 w-full flex hover:bg-gray-50"
          v-for="forum in Result.forums"
          :key="forum.forumId"
          :to="{ path: '/forum/' + forum.forumId }"
        >
          <img class="w-10 h-10 rounded-full" :src="forum.coverImage" />
          <p class="pt-1 px-4 font-bold text-lg">{{ forum.nameForum }}</p>
        </router-link>
        <p
          class="bg-gray-50 py-1 px-4 w-full flex text-sm"
          v-if="!awaitingSearch"
        >
          User
        </p>
        <router-link
          class="bg-white py-3 px-4 w-full flex hover:bg-gray-50"
          v-for="user in Result.users"
          :key="user.userId"
          :to="{ path: '/profile/' + user.userId }"
        >
          <img
            class="w-11 h-11 rounded-full border-2 border-dashed"
            :src="user.avatar"
          />
          <div class="w-full h-full flex">
            <p class="pt-1 px-4 mt-1 font-bold text-lg flex-1 text-left">
              {{ user.fullName }}
            </p>
            <p
              class="py-1 px-4 rounded-xl mt-1 font-bold"
              :class="[
                user.position == 'teacher' ? 'bg-yellow-500' : 'bg-green-300',
              ]"
            >
              {{ user.position }}
            </p>
          </div>
        </router-link>
      </div>
    </div>

    <div class="flex-grow md:hidden"></div>

    <div class="flex justify-center h-screen px-2 py-1">
      <div class="relative my-1">
        <button
          @click="messDrop"
          divclass="relative z-10 block rounded-md bg-white p-2 focus:outline-none"
        >
          <svg
            class="h-5 w-5 text-black"
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
              d="M4 21v-13a3 3 0 0 1 3 -3h10a3 3 0 0 1 3 3v6a3 3 0 0 1 -3 3h-9l-4 4"
            />
            <line x1="8" y1="9" x2="16" y2="9" />
            <line x1="8" y1="13" x2="14" y2="13" />
          </svg>
          <span
            v-if="notify.Message > 0"
            class="
              absolute
              top-0
              right-0
              inline-flex
              items-center
              justify-center
              px-2
              py-1
              text-xs
              font-bold
              leading-none
              text-red-100
              transform
              translate-x-1/2
              -translate-y-1/2
              bg-red-600
              rounded-full
            "
            >{{ notify.Message }}</span
          >
        </button>

        <div x-show="dropdownOpen" @click="messageDrop = false"></div>

        <div
          v-if="messageDrop"
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
          @click.self="messageDrop = false"
        >
          <div
            v-if="messageDrop"
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
            <messengerlist />
          </div>
        </div>
      </div>
    </div>
    <!------------------------------------------- Vùng thông báo -->
    <div class="flex justify-center h-screen px-2">
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
          <span
            v-if="notify.nnotify > 0"
            class="
              absolute
              top-0
              right-0
              inline-flex
              items-center
              justify-center
              px-2
              py-1
              text-xs
              font-bold
              leading-none
              text-red-100
              transform
              translate-x-1/2
              -translate-y-1/2
              bg-red-600
              rounded-full
            "
            >{{ notify.nnotify }}</span
          >
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
            <notifylist />
          </div>
        </div>
      </div>
    </div>
    <!--  ------------------------------------------------------------------User tag -->
    <div
      class="
        mx-4
        flex
        rounded
        hover:border-gray-200
        text-green-900
        hover:bg-gray-200
        textcentre
        order-last
      "
      @mouseover="userDrop = true"
      @mouseleave="userDrop = fasle"
    >
      <div class="sm:flex items-center hidden">
        <img
          class="inline-block object-cover w-10 h-10 rounded-full mx-0.5"
          :src="user.avatar"
          alt="Profile image"
        />
        <a class="inline-block py-2 px-4 xl:block 2xl:block" href="#">{{
          user.username
        }}</a>
      </div>
      <div class="sm:hidden items-center flex">
        <svg
          class="h-8 w-8 text-gray-500"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M3 4h13M3 8h9m-9 4h9m5-4v12m0 0l-4-4m4 4l4-4"
          />
        </svg>
      </div>

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
          sm:w-72
          w-full
        "
      >
        <!-- Tài khoản -->
        <div class="py-2">
          <div class="flex items-center p-2 sm:hidden">
            <img
              class="inline-block object-cover w-14 h-14 rounded-full mx-0.5"
              :src="user.avatar"
              alt="Profile image"
            />
            <a
              class="
                inline-block
                py-2
                px-4
                xl:block
                2xl:block
                text-2xl
                font-bold
              "
              href="#"
              >{{ user.username }}</a
            >
          </div>
          <div class="block p-2 lg:hidden">
            <leftfeed />
          </div>
          <router-link
            to="/myprofile"
            class="flex items-center px-3 py-1 border-b hover:bg-gray-100"
          >
            <svg
              class="h-10 w-10 md:h-6 md:w-6 text-blue-500"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M5.121 17.804A13.937 13.937 0 0112 16c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0zm6 2a9 9 0 11-18 0 9 9 0 0118 0z"
              />
            </svg>

            <p class="text-gray-600 text-xl md:text-sm pl-2">Profile</p>
          </router-link>
          <!-- Cài đặt tài khoản -->
          <details
            class="flex items-center px-3 py-1 border-b hover:bg-gray-100"
          >
            <summary class="flex">
              <svg
                class="h-10 w-10 md:h-6 md:w-6 text-blue-500"
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

              <p class="text-gray-600 text-xl md:text-sm pl-2">Setting</p>
            </summary>
            <router-link to="/Account/ChangePassword" class="hover:bg-gray-300"
              >Change password</router-link
            >
          </details>
          <!-- Đăng xuất -->
          <a
            href="#"
            class="flex items-center px-3 py-1 border-b hover:bg-gray-100"
            @click="logout"
          >
            <svg
              class="h-10 w-10 md:h-6 md:w-6 text-red-500"
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

            <p class="text-gray-600 text-xl md:text-sm pl-2">Log-out</p>
          </a>
        </div>
      </div>
    </div>
    <!-- ------------------- Thong bao -->
  </header>
  <!-- <button @click="test">test</button> -->
</template>
<script>
import leftfeed from "./homepage/LeftFeeds.vue";
import { header } from "../logic/startup.js";
import { search, autoNotify } from "../logic/api";
import { mapGetters } from "vuex";
import notifylist from "./header/notifylist.vue";
import messengerlist from "./header/messengerlist.vue";
export default {
  computed: {
    ...mapGetters(["userID"]),
  },
  components: { notifylist, leftfeed, messengerlist },
  data() {
    return {
      search: "",
      notiDrop: false,
      userDrop: false,
      messageDrop: false,
      user: {},
      isSearch: false,
      awaitingSearch: false,
      Result: {
        users: [],
        forums: [],
      },
      notify: {
        nnotify: 0,
        Message: 0,
      },
    };
  },
  watch: {
    search: async function () {
      console.log("---------------- Waching ---------------");
      if (!this.awaitingSearch) {
        setTimeout(() => {
          this.getAnswer();
        }, 2000); // 2 sec delay
      }
      this.Result = { user: [], forums: [] };
      this.awaitingSearch = true;
      console.log("Waitting...");
    },
  },
  async created() {
    this.user = await header();
    this.test();
  },
  methods: {
    logout() {
      document.cookie = "username=" + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
      document.cookie = "token=" + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
      document.cookie = "userID=" + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
      localStorage.clear();
      this.$router.go();
    },
    gotoProfile() {
      this.$router.push({ path: "profile" });
    },
    async getAnswer() {
      const a = await search(this.search);
      console.log("======================");
      this.Result.users = a.data.listResult1;
      this.Result.forums = a.data.listResult2;
      // console.log(a.data);
      this.awaitingSearch = false;
    },
    async test() {
      const res = await autoNotify();
      if (res.status == 200) {
        this.notify.notify = res.data.numberNotifyNotSeen;
        this.notify.Message = res.data.numberMessageNotSeen;
      }
      setInterval(async () => {
        const res = await autoNotify();
        if (res.status == 200) {
          console.log(res);
          this.notify.nnotify = res.data.numberNotifyNotSeen;
          this.notify.Message = res.data.numberMessageNotSeen;
        }
      }, 8000);
    },
    messDrop() {
      this.messageDrop = !this.messageDrop;
      this.notify.Message = 0;
    },
  },
};
</script>
<style>
.logo {
  font-family: Bahnschrift;
  font-size: 5mm;
}
</style>
