<template>
  <input
    type="text"
    name=""
    id=""
    class="
      pl-5
      block
      w-4/5
      h-10
      rounded-full
      bg-gray-100
      outline-none
      focus:text-gray-700
      z-10
    "
    placeholder="Seaching message"
    v-model="search"
  />
  <button
    class="
      pl-9
      pr-1
      w-20
      h-10
      rounded-full
      bg-gray-200
      hover:bg-gray-300
      outline-none
      focus:text-gray-700
      -mx-9
      z-0
      border-2
      flex
      items-center
    "
    @click="search = ''"
  >
    <svg
      class="h-6 w-6 text-red-500 pl-2"
      fill="none"
      viewBox="0 0 24 24"
      stroke="currentColor"
    >
      <path
        stroke-linecap="round"
        stroke-linejoin="round"
        stroke-width="2"
        d="M6 18L18 6M6 6l12 12"
      />
    </svg>
  </button>
</template>

<script>
import { searchRoom } from "../../logic/chatAPI";
export default {
  data() {
    return {
      search: "",
      awaitingSearch: false,
      Result: [],
    };
  },
  emits: ["issearching", "searching"],
  watch: {
    search: async function () {
      console.log("---------------- Waching ---------------");
      if (this.search == "") {
        console.log("----------------search=''-");
        this.$emit("searching", null);
      }
      if (!this.awaitingSearch) {
        setTimeout(() => {
          this.getAnswer();
        }, 2000); // 2 sec delay
      }
      this.awaitingSearch = true;
      this.Result = [];

      console.log("Waitting...");
    },
    Result: function () {
      this.$emit("searching", this.Result);
    },
    awaitingSearch: function () {
      this.$emit("issearching", this.awaitingSearch);
    },
  },
  methods: {
    async getAnswer() {
      console.log("===========tìm kiếm===========");
      const a = await searchRoom(this.search, 1);

      this.Result = a.data;
      // console.log(a.data);
      this.awaitingSearch = false;
    },
  },
};
</script>

<style></style>
