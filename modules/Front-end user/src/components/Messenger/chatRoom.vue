<template>
  <div class="w-full">
    <div
      class="
        chatheader
        h-24
        w-full
        mx-1
        bg-white
        flex
        items-center
        px-10
        border-b-2
        shadow-lg
      "
    >
      <img
        class="h-16 w-16 bg-gray-500 rounded-full"
        :src="roomInfo.userResponse1.avatar"
      />
      <div class="ml-6 text-left text-lg">
        <strong> {{ roomInfo.userResponse1.fullName }}</strong>
        <div class="flex items-center">
          <div
            class="w-2 h-2 rounded-full animate-pulse"
            :class="[
              roomInfo.statusRoom == 'is_active'
                ? 'bg-green-700 animate-pulse'
                : 'bg-red-700',
            ]"
          ></div>
          <span>{{ roomInfo.statusRoom }}</span>
        </div>
        <!-- <p>flex justify-end rounded-bl-xl justify-start rounded-br-xl</p> -->
      </div>
    </div>
    <!-- Chat zone -->
    <div
      v-if="blocked"
      class="
        scroll
        text-xl
        px-11
        overflow-y-auto
        relative
        mx-1
        grid
        items-center
        text-center
      "
    >
      <div class="h-56 text-2xl"></div>
      <strong>This room has been locked</strong>
    </div>
    <div
      class="scroll grid text-xl text-white px-11 overflow-y-auto relative mx-1"
      id="messagesbox"
      style="height: 90%"
      v-else
    >
      <!-- Signal message-->
      <ul>
        <li>
          <div
            class="my-2 max-h-full flex"
            :class="[
              msg.userSend.userId == UserID ? 'justify-end' : 'justify-start',
            ]"
            v-for="msg in MSGdata"
            :key="msg.messageId"
          >
            <div class="grid">
              <div
                class="flex items-center"
                :class="[
                  msg.userSend.userId == UserID
                    ? 'justify-self-end'
                    : 'justify-self-start',
                ]"
              >
                <ActionPanel
                  v-if="msg.userSend.userId == UserID"
                  :userId="msg.userSend.userId"
                  :chat="msg"
                  :roomchatid="roomInfo.roomId"
                  @deleteMSG="deleteMSG(msg)"
                />
                <div
                  class="py-2 px-7 rounded-t-xl"
                  :class="[
                    msg.userSend.userId == UserID
                      ? 'rounded-bl-xl bg-gray-600'
                      : 'rounded-br-xl bg-gray-800',
                  ]"
                >
                  <p class="break-words text-left">{{ msg.message }}</p>
                  <p class="font-extralight text-sm text-right text-gray-400">
                    <Date :date="msg.createDate" />
                  </p>
                </div>
                <ActionPanel
                  v-if="msg.userSend.userId != UserID"
                  :userId="msg.userSend.userId"
                  :chat="msg"
                  :roomchatid="roomInfo.roomId"
                  @deleteMSG="deleteMSG(msg)"
                />
              </div>
              <img
                :class="[
                  msg.userSend.userId == UserID
                    ? 'justify-self-end'
                    : 'justify-self-start',
                ]"
                class="
                  h-56
                  m-1
                  bg-gray-200
                  rounded-lg
                  grid
                  place-items-start
                  object-cover
                  border-4
                  hover:border-gray-500
                "
                :src="msg.imageMessage"
                v-if="msg.imageMessage != ''"
              />
            </div>
          </div>
          <div class="h-20"></div>
        </li>
      </ul>
    </div>

    <div
      class="
        chatwrite
        w-full
        h-20
        shadow-xl
        flex
        items-center
        justify-center
        bg-gradient-to-tl
        from-white
      "
    >
      <div class="px-28 bg-red-200"></div>

      <div class="flex items-center py-2 xl:px-80 rounded-lg w-full">
        <img
          class="h-24 w-24 m-1 bg-gray-200 rounded-lg grid place-items-start"
          :src="listImages"
          v-if="listImages != ''"
        />
        <input
          name=""
          id=""
          type="text"
          class="
            ml-11
            resize-none
            rounded-lg
            border-l-2 border-t-2 border-b-2
            hover:border-gray-500
            text-xl text-gray-800
            h-12
            w-full
            px-5
            shadow-2xl
            z-20
            focus-within:outline-none
            focus:border-green-500
          "
          v-model="textValue"
          placeholder="Place your message here"
          @keypress.enter="sendMsg"
        />
        <label
          class="pr-3 pl-6 bg-gray-500 h-12 rounded-lg -mx-3 shadow-2xl z-10"
          for="fileupload"
        >
          <input
            id="fileupload"
            type="file"
            @change="previewImage"
            accept="image/*"
            class="hidden"
          />
          <svg
            class="h-8 w-8 text-white mt-2"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <rect x="3" y="3" width="18" height="18" rx="2" ry="2" />
            <circle cx="8.5" cy="8.5" r="1.5" />
            <polyline points="21 15 16 10 5 21" />
          </svg>
        </label>
        <button
          class="pr-4 pl-6 bg-green-700 h-12 rounded-lg -mx-1 shadow-2xl z-0"
          @click="sendMsg"
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
            <line x1="10" y1="14" x2="21" y2="3" />
            <path
              d="M21 3L14.5 21a.55 .55 0 0 1 -1 0L10 14L3 10.5a.55 .55 0 0 1 0 -1L21 3"
            />
          </svg>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import _ from "lodash";
import { getcookie } from "../../logic/getcookie";
import {
  viewMessagesInRoom,
  sendMSGwAPI,
  autoLoad,
  deleteMessage,
} from "../../logic/chatAPI";
import { location } from "../../logic/handlingdata";
import ActionPanel from "./newAction.vue";
import Date from "../smallComponent/time.vue";
// import BaseProgress from "../smallComponent/BaseProgress.vue";
// import { getcookie } from "../../logic/getcookie";
import {
  getStorage,
  ref,
  uploadBytesResumable,
  getDownloadURL,
} from "firebase/storage";
export default {
  components: { ActionPanel, Date },
  props: {
    roomInfo: Object,
  },
  data() {
    return {
      UserID: 0,
      textValue: "",
      page: 1,
      MSGdata: [],
      lastseenMsg: 0,
      autoload: null,
      isWaitting: true,
      process: 10,
      imageData: null,
      pictureURL: null,
      uploadValue: 0,
      listImages: "",
      blocked: true,
    };
  },
  async created() {
    await this.getMessage();
    if (this.roomInfo.statusRoom == "is_active") {
      this.blocked = false;
    }
    this.UserID = getcookie("userID");
    this.getlast();
  },
  watch: {
    async roomInfo() {
      if (this.roomInfo.statusRoom == "is_active") {
        this.blocked = false;
      } else {
        this.blocked = true;
      }
      this.MSGdata = [];
      this.listImages = "";
      console.log("Chuyển trang");
      await this.getMessage();
      // this.getlast();
    },
  },
  methods: {
    async sendMsg() {
      if (this.textValue != "") {
        let data = {
          imageMessage: this.listImages,
          message: this.textValue,
          roomChatId: this.roomInfo.roomId,
        };

        const res = await sendMSGwAPI(data);
        this.scrollToBottom();
        console.log(res.data);
        this.MSGdata.push(res.data);
        this.listImages = "";
      }

      this.textValue = "";
    },
    scrollToBottom() {
      setTimeout(() => {
        const container = this.$el.querySelector("#messagesbox");
        container.scrollTop = container.scrollHeight;
      }, 100);
    },
    scrolltoTop() {
      setTimeout(() => {
        const container = this.$el.querySelector("#messagesbox");
        container.scrollTop = 10;
      }, 100);
    },
    async getlast() {
      this.autoload = setInterval(async () => {
        // Lấy msgid cuối dùng để gửi.
        console.log(this.MSGdata[this.MSGdata.length - 1]);
        const res = await autoLoad(
          this.roomInfo.roomId,
          this.MSGdata[this.MSGdata.length - 1].messageId
        );
        console.log(
          "----Auto load----------------------------------------" +
            res.data.message
        );

        if (res.data.message != "no new message") {
          this.MSGdata = this.MSGdata.concat(res.data.listResult2);
          this.MSGdata = _.orderBy(this.MSGdata, "messageId", "esc");
          console.log(this.MSGdata);
          this.scrollToBottom();
        }
        // console.log(res.data.listResult2);
      }, 15000);
    },
    async getMessage() {
      const temp = await viewMessagesInRoom(this.roomInfo.roomId, this.page);
      this.page++;
      this.MSGdata = temp;
      this.MSGdata = _.orderBy(this.MSGdata, "messageId", "esc");
    },
    prepareToExit: function () {
      clearInterval(this.autoload);
    },
    processstart() {
      this.process = this.process + 10;
      if (this.process == 60) this.color = "blue";
      if (this.process == 90) this.color = "green";
    },
    previewImage(event) {
      if (this.listImages.length <= 15) {
        this.uploadValue = 0;
        this.pictureURL = null;
        this.imageData = event.target.files[0];
        console.log(event.target.files[0]);
        this.onUpload();
      }
    },
    onUpload() {
      const storage = getStorage();
      const r = (Math.random() + 1).toString(36).substring(7);
      const storageRef = ref(storage, `/chat/${this.userID}/${r}`);
      const metadata = {
        contentType: "image/jpeg",
      };

      const uploadTask = uploadBytesResumable(
        storageRef,
        this.imageData,
        metadata
      );
      uploadTask.on(
        "state_changed",
        (snapshot) => {
          const progress =
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
          this.uploadValue = progress;
          console.log("Upload is " + progress + "% done");
          switch (snapshot.state) {
            case "paused":
              console.log("Upload is paused");
              break;
            case "running":
              console.log("Upload is running");
              break;
          }
        },
        (error) => {
          console.error(error);
        },
        () => {
          getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
            console.log("File available at", downloadURL);
            this.listImages = downloadURL;
            // this.$emit("update", this.listImages);
          });
        }
      );
    },
    deleteImg() {
      this.listImages = null;
    },
    async deleteMSG(content) {
      const res = (await deleteMessage(content.messageId)).data;
      if (res.message == "delete successfully") {
        this.$root.addnoti({
          group: "top",
          title: "Delete success",
          text: "Your message delete successfully",
        });
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: "Error",
          text: res.message,
        });
      }
      this.MSGdata.splice(location(content, this.MSGdata), 1);
    },
  },
};
</script>

<style>
#messagesbox {
  scroll-behavior: smooth;
}
.chatwrite {
  position: fixed;
  bottom: 0;
  left: 0;
}
.chatheader {
  position: sticky;
  top: 10;
}
</style>
