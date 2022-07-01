<template>
  <div class="h-12"></div>
  <div class="max-h-full bg-white flex overflow-hidden" style="height: 850px">
    <div class="hidden sm:block w-max">
      <div
        class="h-24 border-b-4 lg:flex items-center shadow-lg hidden min-w-max"
      >
        <SearchRoom @searching="searching" @issearching="issearch" />
      </div>
      <div class="message-box grid">
        <div
          class="
            my-1
            h-20
            flex
            items-center
            pr-3
            shadow-lg
            transition
            duration-500
            ease-in-out
            group
            transform
            bg-green-200
            hover:bg-gray-200 hover:-translate-y-1 hover:scale-110
            w-max
          "
          v-for="room in search"
          :key="room.roomId"
          @click="viewRoom(room)"
          :class="[
            room.roomId == currentRoomroomID ? 'bg-blue-200' : 'bg-white',
          ]"
        >
          <div
            class="w-2 h-full mr-1 blur-md"
            :class="[
              room.roomId == currentRoomroomID ? 'bg-blue-500' : 'bg-white',
            ]"
          ></div>
          <img
            class="h-14 w-14 rounded-full object-cover"
            :src="room.userResponse1.avatar"
          />
          <div class="text-left ml-4 hidden lg:block w-60">
            <strong>{{ room.userResponse1.fullName }}</strong>
            <!-- <span>{{ room.modifiedDate }}</span> -->
            <span class="text-xs"> <Date :date="room.modifiedDate" /></span>
            <p
              :class="[
                (room.statusNewMessage == 'unseen' || true) &&
                room.userRecipient == 'username'
                  ? 'font-semibold text-blue-500'
                  : '',
              ]"
            >
              {{ room.messageNew }}
            </p>
          </div>
        </div>
        <div
          v-if="issearching"
          class="
            my-1
            h-20
            flex
            items-center
            pr-3
            shadow-lg
            transition
            duration-500
            ease-in-out
            group
            transform
            bg-green-200
            hover:bg-gray-200 hover:-translate-y-1 hover:scale-110
            w-full
            animate-pulse
            px-2
          "
        >
          <div class="h-14 w-14 rounded-full bg-green-400"></div>
          <div class="px-2">
            <div class="w-32 bg-green-300 h-6"></div>
            <div class="w-44 bg-green-300 h-6 mt-1"></div>
          </div>
        </div>
        <div v-if="search == null">
          <div
            class="
              my-1
              h-20
              flex
              items-center
              pr-3
              shadow-lg
              transition
              duration-500
              ease-in-out
              group
              transform
              hover:bg-gray-200 hover:-translate-y-1 hover:scale-110
              w-max
            "
            v-for="room in roomChat.data"
            :key="room.roomId"
            @click="viewRoom(room)"
            :class="[
              room.roomId == currentRoomroomID ? 'bg-blue-200' : 'bg-white',
            ]"
          >
            <div
              class="w-2 h-full mr-1 blur-md"
              :class="[
                room.roomId == currentRoomroomID ? 'bg-blue-500' : 'bg-white',
              ]"
            ></div>
            <img
              class="h-14 w-14 rounded-full object-cover"
              :src="room.userResponse1.avatar"
            />
            <div class="text-left ml-4 hidden lg:block w-60">
              <strong>{{ room.userResponse1.fullName }}</strong>
              <!-- <span>{{ room.modifiedDate }}</span> -->
              <span class="text-xs"> <Date :date="room.modifiedDate" /></span>
              <p
                :class="[
                  (room.statusNewMessage == 'unseen' || true) &&
                  room.userRecipient == 'username'
                    ? 'font-semibold text-blue-500'
                    : '',
                ]"
              >
                {{ room.messageNew }}
              </p>
            </div>
          </div>
        </div>
        <button
          class="w-full flex items-center hover:bg-gray-100 shadow-lg pt-1"
        >
          <div
            class="
              text-gray-500
              h-14
              w-14
              flex
              items-center
              justify-items-center
            "
          >
            <svg
              class="h-9 w-9 ml-5"
              :class="[isLoading ? 'animate-spin' : '']"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15"
              />
            </svg>
          </div>

          <strong class="hidden xl:block ml-3">Reload</strong>
        </button>
      </div>
    </div>
    <ChatRoom v-if="isActive" :roomInfo="currentRoom" ref="chatroom" />
  </div>
</template>

<script>
import ChatRoom from "../components/Messenger/chatRoom.vue";
import SearchRoom from "../components/Messenger/searchRoomChat.vue";
import Date from "../components/smallComponent/time.vue";
import { viewRooms, createRoomChat } from "../logic/chatAPI";
import { getcookie } from "../logic/getcookie";
export default {
  components: { ChatRoom, SearchRoom, Date },
  props: {
    userID: Number,
  },
  data() {
    return {
      roomChat: [],
      viewRoomChatPage: 1,
      isLoading: false,
      currentRoom: null,
      currentRoomroomID: 0,
      isActive: false,
      username: "",
      search: null,
      isSearching: false,
    };
  },
  watch: {
    currentRoom() {
      this.currentRoomroomID = this.currentRoom.roomId;
      console.log("=Current room ID");
      console.log(this.currentRoom.roomId);
    },
  },
  async created() {
    document.title = "School connect Messenger";
    this.roomChat = await viewRooms(this.viewRoomChatPage);
    if (this.userID) {
      this.createRoom();
    }
    this.username = getcookie("username");
  },
  methods: {
    async createRoom() {
      const res = await createRoomChat({ userId: this.userID });
      console.log(res);
      this.viewRoom(res);
    },
    viewRoom(room) {
      this.search = null;
      if (room == this.currentRoom) {
        this.currentRoom = null;
        this.isActive = false;
      } else {
        this.currentRoom = room;
        this.isActive = false;

        this.isActive = true;
      }
    },
    searching(data) {
      this.search = data;
      console.log(this.search);
    },
    issearch(search) {
      this.issearching = search;
      console.log("================" + this.issearching);
    },
  },
  beforeRouteLeave: function (to, from, next) {
    console.log("In beforeRouteLeave of AnotherComponent");
    this.$refs.chatroom.prepareToExit();
    next();
  },
};
</script>

<style></style>
