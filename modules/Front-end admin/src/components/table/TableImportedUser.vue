<template>
  <div class="items-start justify-center">
    <div class="col-span-12">
      <div class="overflow-auto lg:overflow-visible">
        <div
          class="
            text-left
            px-4
            py-1
            font-bold
            text-4xl text-white
            max-w-lg
            my-5
            flex
            items-center
          "
        >
          <svg
            class="h-10 w-10 text-white mx-4"
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
          <h1>Imported list</h1>
        </div>
        <table class="table text-gray-400 border-separate space-y-6 xl:text-lg">
          <thead class="bg-gray-800 text-gray-100">
            <tr>
              <th class="px-4 py-2">code</th>
              <th class="px-4 py-2">fullName</th>
              <th class="px-4 py-2 text-left">userName</th>
              <th class="px-4 py-2 text-left">email</th>
              <th class="px-4 py-2">dateOfBirth</th>
              <th class="px-4 py-2 text-center">gender</th>
              <th class="px-4 py-2 text-center">position</th>
              <th class="px-4 py-2 text-left">addressOfUser</th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="bg-gray-700 hover:bg-gray-600 text-white"
              v-for="data in sumitedData"
              v-bind:key="data.code"
            >
              <td class="p-3">{{ data.code }}</td>
              <td class="p-3">{{ data.fullName }}</td>
              <td class="p-3">{{ data.userName }}</td>
              <td class="p-3">{{ data.email }}</td>
              <td class="p-3 font-bold">{{ data.dateOfBirth }}</td>
              <td class="p-3 font-bold text-left">{{ data.gender }}</td>

              <td class="p-3">
                <span
                  class="text-gray-50 rounded-md px-2"
                  v-bind:class="{
                    'bg-green-400':
                      data.position == 'student' || data.position == 'Student',
                    'bg-red-400':
                      data.position == 'teacher' || data.position == 'Teacher',
                  }"
                  >{{ data.position }}</span
                >
              </td>
              <td class="p-3 font-bold">{{ data.addressOfUser }}</td>
            </tr>
            <tr class="p-3" v-if="sumitedData == null">
              <td colspan="8">
                <strong
                  >Seem like no account was created, please check your data
                  !</strong
                >
              </td>
            </tr>
            <td class="p-3"></td>
          </tbody>
        </table>
        <!-- LOADING .............. -->
      </div>
    </div>
  </div>
</template>

<script>
// import data from "./data.json";
export default {
  props: {
    sumitedData: Object,
  },
  // data() {
  //   return {
  //     datas: data,
  //   };
  // },
  watch: {},
  created() {
    console.log(this.sumitedData);
  },
  methods: {},
};
</script>
