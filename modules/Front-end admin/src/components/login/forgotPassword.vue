<template>
  <div class="h-96 place-items-end">
    <div class="h-10"></div>
    <h1 class="text-2xl text-black text-opacity-100">FORGOT PASSWORD</h1>
    <form>
      <div class="mt-4">
        <label class="text-left block text-gray-700 text-sm font-bold mb-2"
          >Username</label
        >
        <input
          v-model="username"
          class="
            bg-gray-200
            text-gray-700
            focus:outline-none focus:shadow-outline
            border border-gray-300
            rounded
            py-2
            px-4
            block
            w-full
            appearance-none
          "
          type="username"
          required
        />
      </div>
      <div class="mt-4">
        <label class="text-left block text-gray-700 text-sm font-bold mb-2"
          >Email Address</label
        >
        <input
          v-model="email"
          class="
            bg-gray-200
            text-gray-700
            focus:outline-none focus:shadow-outline
            border border-gray-300
            rounded
            py-2
            px-4
            block
            w-full
            appearance-none
          "
          type="email"
          required
        />
      </div>
      <div class="text-red-500">{{ message }}</div>
      <div class="mt-4">
        <button
          class="
            bg-gray-700
            text-white
            font-bold
            py-2
            px-4
            w-full
            rounded
            hover:bg-gray-600
          "
          @click="sendEnail"
          type="button"
        >
          Reset password
        </button>
      </div>
    </form>
    <div class="mt-4 flex items-center justify-between">
      <span class="border-b w-1/5 md:w-1/4"></span>
      <h1 class="cursor-pointer hover:underline" @click="notforgotanymore">
        Return to login
      </h1>
      <span class="border-b w-1/5 md:w-1/4"></span>
    </div>
  </div>
</template>

<script>
import { resetaccount } from "../../logic/api.js";
export default {
  data() {
    return {
      username: "hieunguyen",
      email: "hnguyentrung20@gmail.com",
      message: "",
    };
  },
  methods: {
    async sendEnail() {
      if (this.username === "" || this.email === "") {
        this.message = "Please fill all the field";
      } else {
        this.loading();
        this.message = await resetaccount({
          username: this.username,
          email: this.email,
        });
        this.loading();
        console.log("DONE");
      }
    },
    notforgotanymore() {
      console.log("notforgotanymore");
      this.$emit("notforgotanymore");
    },
    loading() {
      console.log("is loading....");
      this.$emit("loading");
    },
  },
};
</script>

<style></style>
