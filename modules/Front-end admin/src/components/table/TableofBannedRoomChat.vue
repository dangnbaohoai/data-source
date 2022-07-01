<template>
  <div class="flex items-center py-2">
    <router-link
      :to="{ path: '/ReportandBlocking' }"
      class="
        px-4
        py-2
        bg-gray-800
        rounded-md
        flex
        content-center
        border-2
        text-white
        hover:bg-white hover:text-black hover:border-gray-200
      "
    >
      Turn to repored chat room</router-link
    >
  </div>
  <div class="items-start justify-center">
    <div class="col-span-12">
      <div class="overflow-auto lg:overflow-visible">
        <div class="flex">
          <table
            class="table text-gray-400 border-separate space-y-6 xl:text-lg"
            v-if="!isloading"
          >
            <thead class="bg-gray-700 text-gray-500 rounded-t-lg">
              <tr>
                <th class="p-3">ID</th>
                <th class="p-3 text-left">User sent report</th>
                <th class="p-3 text-left">User reported</th>
                <th class="p-3 text-left">Position</th>
                <th class="p-3 text-left px-36">Message content</th>
                <th class="p-3 text-center">Reason</th>
                <th class="p-3 text-center">Create date</th>
                <th class="p-3 text-left">Action</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td
                  colspan="8"
                  v-if="datas.length == 0"
                  class="text-2xl px-4 bg-gray-500 text-white"
                >
                  No data
                </td>
              </tr>
              <tr
                class="bg-gray-800 hover:bg-gray-700"
                v-for="data in datas"
                v-bind:key="data.forumId"
              >
                <td class="p-3">{{ data.roomChat.roomId }}</td>
                <td class="items-center px-6">
                  <div class="flex items-center">
                    <img
                      class="rounded-xl h-12 w-12 object-cover my-3 mr-4"
                      :src="data.user.avatar"
                      alt="unsplash image"
                    />
                    <strong>{{ data.user.fullName }}</strong>
                  </div>
                </td>
                <!-- User sent -->
                <td class="flex items-center px-6">
                  <img
                    class="rounded-xl h-12 w-12 object-cover my-3 mr-4"
                    :src="data.messageIsReported.userSend.avatar"
                    alt="unsplash image"
                  />
                  <strong>{{
                    data.messageIsReported.userSend.fullName
                  }}</strong>
                </td>

                <td class="p-3">
                  {{ data.messageIsReported.userSend.position }}
                </td>
                <!-- Noij dung -->
                <td class="p-3 bg-gray-700 text-left">
                  {{ data.messageIsReported.message }}
                </td>
                <td class="p-3">
                  <strong>{{ data.reason }}D</strong>
                </td>
                <td class="p-3">{{ data.messageIsReported.modifiedDate }}</td>
                <!-- <td class="p-3">
                <span
                  class="text-gray-50 rounded-md px-2"
                  v-bind:class="{
                    'bg-green-400': data.position == 'student',
                    'bg-red-400': data.position == 'teacher',
                  }"
                  >{{ data.position }}</span
                >
              </td> -->
                <td class="p-3">
                  <svg
                    class="
                      h-8
                      w-8
                      text-white
                      hover:text-gray-200
                      cursor-pointer
                    "
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                    @click="unblock(data.reportId)"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M8 11V7a4 4 0 118 0m-4 8v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2z"
                    />
                  </svg>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- LOADING .............. -->
        <div
          class="w-auto bg-gray-800 animate-pulse h-24 flex my-10 rounded-lg"
          v-if="isloading"
        >
          <div class="my-4 flex rounded-lg">
            <div
              class="bg-gray-700 h-full animate-pulse w-40 mx-10 rounded-lg"
            ></div>
            <div></div>
            <div
              class="bg-gray-700 h-full animate-pulse w-10 mx-10 rounded-lg"
            ></div>
            <div></div>
            <div
              class="bg-gray-700 h-full animate-pulse w-10 mx-10 rounded-lg"
            ></div>
            <div></div>
            <div
              class="bg-gray-700 h-full animate-pulse w-10 mx-10 rounded-lg"
            ></div>
            <div
              class="bg-gray-700 h-full animate-pulse w-10 mx-10 rounded-lg"
            ></div>

            <div
              class="bg-gray-700 h-full animate-pulse w-10 mx-10 rounded-lg"
            ></div>
            <div></div>
          </div>
        </div>
      </div>
    </div>
    <div><Pagination /></div>
  </div>
</template>

<script>
import Pagination from "../smallmodal/Pagination.vue";
import { viewReportBlock, unblock } from "../../logic/reportAPI";
export default {
  components: { Pagination },
  data() {
    return {
      datas: [{}],
      isloading: true,
      page: 1,
    };
  },
  async created() {
    const a = await viewReportBlock(this.page);
    console.log(a);
    this.datas = a.listResult;
    this.isloading = false;
    console.log("DATA table:");
    this.isloading = false;
    console.log(this.datas);
  },
  methods: {
    async unblock(id) {
      console.log("DELETE -->" + id);
      const res = await unblock(id);
      if (res.message == "Unblock Room chat successfully") {
        this.$root.addnoti({
          group: "top",
          title: "Cancel success",
          text: res.message,
        });
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: "Failure",
          text: res.message,
        });
      }
    },
  },
};
</script>
