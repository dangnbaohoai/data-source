<template>
  <div class="grid w-full">
    <details
      open
      class="
        w-96
        p-2
        content-end
        justify-self-end
        mr-10
        text-left
        border-2
        shadow-md
        transition
        ease-out
        duration-700
      "
    >
      <summary
        class="justify-center flex flex-nowrape bg-gray-100"
        :class="[isLoading ? 'animate-pulse' : '']"
      >
        <svg
          class="h-8 w-8 text-green-600"
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
          <rect x="4" y="5" width="16" height="16" rx="2" />
          <line x1="16" y1="3" x2="16" y2="7" />
          <line x1="8" y1="3" x2="8" y2="7" />
          <line x1="4" y1="11" x2="20" y2="11" />
          <line x1="11" y1="15" x2="12" y2="15" />
          <line x1="12" y1="15" x2="12" y2="18" />
        </svg>
        <h1 class="text-center text-xl font-bold mt-1">
          {{ data.length }} Event at
          <input
            type="month"
            id="start"
            name="start"
            min="2018-03"
            v-model="date"
            class="bg-gray-100"
          />
        </h1>
      </summary>
      <div
        v-for="event in data"
        :key="event.postId"
        class="py-2 px-2 bg-white my-2 rounded-lg shadow-lg flex font-light"
      >
        <details class="grid w-full divide-y-2 divide-dashed">
          <summary class="flex text-lg w-full px-2 cursor-pointer">
            <h2 class="flex-1">{{ event.nameOfEvent }}</h2>
            <div class="flex-grow"></div>
            <p>{{ event.dateOfEvent }}</p>
          </summary>
          <p>Location: {{ event.addressOfEvent }}</p>
          <p>Organizer: {{ event.userName }}</p>
          <router-link
            :to="{ path: '/forum/' + event.forumId + '/post/' + event.postId }"
          ></router-link>
        </details>
      </div>
      <div v-if="data == []"><strong>No event in current time</strong></div>
    </details>
  </div>
</template>

<script>
import { viewEventJoin } from "../../logic/postAPI";
export default {
  data() {
    return {
      data: [],
      month: 0,
      year: 2021,
      date: "2021-01",
      isLoading: true,
    };
  },
  watch: {
    async date() {
      this.isLoading = true;
      this.year = parseInt(this.date.substring(0, 4));
      console.log("----------------------" + this.date);
      this.month = parseInt(this.date.substring(5, 7));
      console.log("----------------++++" + this.month + "//" + this.year);
      this.data = await viewEventJoin(this.month, this.year);
      this.data = this.data.data;
      this.isLoading = false;
    },
  },
  async created() {
    const today = new Date();
    this.month = today.getMonth() + 1;
    this.year = today.getFullYear();
    console.log(
      "-------------------------- from today :" + this.month + this.year
    );
    if (this.month < 10) {
      this.date = this.year + "-0" + this.month;
    }
    {
      this.date = this.year + "-" + this.month;
    }
    // this.data = await viewEventJoin(this.month, this.year);

    this.isLoading = false;
  },
};
</script>

<style>
input[type="date"]::-webkit-inner-spin-button,
input[type="date"]::-webkit-calendar-picker-indicator {
  display: none;
  -webkit-appearance: none;
}
</style>
