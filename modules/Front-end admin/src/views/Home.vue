<template>
  <div class="">
    <div class="flex text-3xl font-light text-white my-4">
      <div
        class="
          h-20
          w-min
          flex-grow
          mx-32
          border-b-2
          flex
          items-center
          shadow-2xl
          bg-gray-800
          rounded-xl
        "
      >
        <svg
          class="h-10 w-10 text-white flex-grow"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
          <circle cx="9" cy="7" r="4" />
          <path d="M23 21v-2a4 4 0 0 0-3-3.87" />
          <path d="M16 3.13a4 4 0 0 1 0 7.75" />
        </svg>
        <div class="px-4 flex-grow">
          <p class="text-xl">Total number of user</p>
          <p class="font-semibold">{{ result.numberUser }}</p>
        </div>
      </div>
      <div
        class="
          h-20
          w-min
          flex-grow
          mx-10
          border-b-2
          flex
          items-center
          shadow-2xl
          bg-gray-800
          rounded-xl
        "
      >
        <svg
          class="h-10 w-10 text-white flex-grow"
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
          <rect x="4" y="4" width="16" height="6" rx="2" />
          <rect x="4" y="14" width="16" height="6" rx="2" />
        </svg>
        <div class="px-4 flex-grow">
          <p class="text-xl">Total number of Post</p>
          <p class="font-semibold">{{ result.numberPosts }}</p>
        </div>
      </div>
      <div
        class="
          h-20
          w-min
          flex-grow
          mx-32
          border-b-2
          flex
          items-center
          shadow-2xl
          bg-gray-800
          rounded-xl
        "
      >
        <svg
          class="h-10 w-10 text-white flex-grow"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z"
          />
        </svg>

        <div class="px-4 flex-grow">
          <p class="text-xl">Total number of forum</p>
          <p class="font-semibold">{{ result.numberForum }}</p>
        </div>
      </div>
    </div>
    <div class="grid text-white" v-if="isdone">
      <Chart class="ml-52 p-4 border-2 w-2/3 self-center" :data="chartdata" />
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { getcookie } from "../logic/getcookie.js";
import Chart from "../components/PlanetChart.vue";
export default {
  name: "Dashboard",
  components: { Chart },
  data() {
    return {
      result: {
        numberUser: 0,
        numberPosts: 0,
        numberForum: 0,
      },
      chartdata: {},
      isdone: false,
    };
  },

  async created() {
    let headers = {
      Authorization: "Bearer " + getcookie("token"),
      "header-name": "https://school-connection.herokuapp.com",
    };
    await axios.get("/api/admin/home/status", { headers }).then((res) => {
      this.result = res.data;
      console.log(res);
      this.chartdata = dataBlockChat(res.data.dataBlockChat);
    });
    this.isdone = true;
  },
};
function dataBlockChat(input) {
  let data = {
    labels: [
      "January",
      "February",
      "March",
      "April",
      "May",
      "June",
      "July",
      "August",
      "September",
      "October",
      "November",
      "December",
    ],

    datasets: [
      {
        label: "Blocked chat each month",
        data: [],
        backgroundColor: "rgba(54,73,93,.5)",
        borderColor: "#36495d",
        borderWidth: 3,
      },
    ],
  };
  for (let i = 0; i < input.length; i++) {
    console.log("input" + i);
    data.datasets[0].data.push(input[i].numbers);
  }
  console.log("input");
  console.log(input);
  console.log(data);
  return data;
}
</script>
