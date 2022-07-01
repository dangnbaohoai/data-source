<template>
  <div
    class="
      min-w-screen
      h-screen
      animated
      fadeIn
      faster
      fixed
      left-0
      top-0
      flex
      justify-center
      items-start
      inset-0
      z-50
      outline-none
      focus:outline-none
      bg-no-repeat bg-center bg-cover
    "
    @click.self="close"
    style="background-color: rgba(255, 255, 255, 0.7)"
    id="modal-id"
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
      <h2 class="text-lg font-bold text-center py-2 bg-gray-100">
        Tạo bài viết mới
      </h2>
      <tiptap :msg="msg" @posting="post" />
      <div v-if="isEvent">
        Ngày bắt đầu :
        <input type="date" id="birthdaytime" name="birthdaytime" /><br />
        Ngày kết thúc:
        <input type="date" id="birthdaytime" name="birthdaytime" />
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
          Bài đăng
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
          Bài đăng
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
          Sự kiện
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
          Sự kiện
        </div>
      </div>
      <div aria-label="Page navigation" class="py-2">
        <div class="px-4 flex justify-end">
          <button
            class="
              bg-blue-500
              hover:bg-blue-700
              text-white
              font-bold
              py-2
              px-4
              mx-2
              rounded-full
            "
            @click="close"
          >
            Thoát
          </button>

          <button
            class="
              bg-blue-500
              hover:bg-blue-700
              text-white
              font-bold
              py-2
              px-4
              rounded-full
            "
            @click="msg = !msg"
          >
            Đăng bài
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Tiptap from "./editor/Tiptap.vue";
export default {
  components: {
    Tiptap,
  },
  data() {
    return {
      isEvent: 0,
      msg: false,
      showpost: {
        isEvent: false,
        content: "",
        startdate: 21 - 11 - 2000,
      },
    };
  },
  methods: {
    thatAevent() {
      this.isEvent = !this.isEvent;
    },
    close() {
      this.$emit("close");
    },
    post(content) {
      console.log(content);
    },
  },
};
</script>
