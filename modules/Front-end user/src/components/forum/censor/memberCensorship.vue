<template>
  <div
    class="bg-white xl:flex px-5 py-2 shadow-lg rounded-md w-full my-3"
    v-for="data in datas"
    :key="data.code"
  >
    <div class="flex-grow">
      <div class="flex">
        <img
          class="
            h-16
            w-16
            mr-5
            bg-gray-600
            rounded-full
            border-2 border-dashed border-gray-400
          "
          :src="data.avatar"
        />
        <div class="grid justify-items-start">
          <h1 class="text-xl font-semibold">
            {{ data.fullName }}
          </h1>
          <div class="xl:flex">
            <div class="mx-2 text-left">
              <p>Email: {{ data.email }}</p>
              <p>StudentID:{{ data.code }}</p>
            </div>
            <div class="mx-2 text-left">
              <p>Address: {{ data.addressOfUser }}</p>
              <p>Gender:{{ data.gender }}</p>
            </div>
            <div class="mx-2 text-left">
              <p>Phone number: {{ data.numberPhone }}</p>
              <p>Birth:{{ data.dateOfBirth }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="h-full">
      <button
        class="
          flex
          w-full
          justify-items-center
          items-center
          border-2 border-green-400
          rounded-lg
          px-4
          group
          hover:bg-green-400
        "
        @click="accept(data)"
      >
        <svg
          class="h-8 w-8 text-green-500 group-hover:text-white"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M5 13l4 4L19 7"
          />
        </svg>
        <p class="group-hover:text-white">Accept</p>
      </button>
      <button
        class="
          mt-2
          w-full
          flex
          justify-items-center
          items-center
          border-2 border-red-400
          group
          hover:bg-red-400
          rounded-lg
          px-4
        "
        @click="decline(data)"
      >
        <svg
          class="h-8 w-8 text-red-500 mr-1 group-hover:text-white"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
          />
        </svg>
        <p class="group-hover:text-white">Decline</p>
      </button>
    </div>
  </div>
</template>

<script>
import {
  memberNotCensored,
  censorMember,
  deleteMemberWaiting,
} from "../../../logic/forumAPI";
export default {
  props: {
    content: {
      type: Object,
    },
    forumId: {
      type: Number,
    },
  },
  created() {
    this.memberNotCensored();
  },
  data() {
    return {
      datas: [],
      page: 1,
    };
  },
  methods: {
    async accept(id) {
      console.log("======================censor");
      const res = await censorMember(this.forumId, id.userId);
      console.log(res);
      if (res.data.message == "censorship successful") {
        this.$root.test({
          group: "top",
          title: "Censor success",
          text: "",
        });
        this.datas.splice(location(id, this.datas), 1);
      }
    },
    async decline(id) {
      const res = await deleteMemberWaiting(this.forumId, id.userId);
      console.log(res);
      if (res.data.message == "delete member successful") {
        this.$root.test({
          group: "top",
          title: "Delete success",
          text: "",
        });
        this.datas.data.splice(location(id, this.datas.data), 1);
      }
    },
    async memberNotCensored() {
      const res = await memberNotCensored(this.forumId, this.page);
      console.log(res.data);
      this.datas = res.data;
      // console.log("============this.datas");
      // console.log(this.datas);
      if (res.data.message == "not censor") {
        this.$router.push({ path: "404" });
      }
      this.page++;
    },
  },
};
function location(a, b) {
  console.log("loaction" + a + b);
  for (let i = 0; i < b.length; i++) {
    console.log(i);
    if (b[i] == a) return i;
  }
}
</script>

<style></style>
