<template>
  <div class="flex items-center py-2">
    <router-link
      :to="{ path: '/ReportandBlocking/blocked' }"
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
      >Blocked room chat</router-link
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
                <td colspan="7" v-if="datas.length == 0" class="text-2xl px-4">
                  Enjoy the good time with no drama :)
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
                  <a href="#" class="text-gray-400 hover:text-gray-100 ml-2">
                    <ion-icon
                      class="hover:text-gray-400"
                      name="trash-outline"
                      @click="cancelRoomReport(data.reportId)"
                    ></ion-icon>
                  </a>
                  <a href="#" class="text-gray-400 hover:text-gray-100 ml-2">
                    <ion-icon
                      name="checkmark-outline"
                      @click="blockRoom(data.reportId)"
                    ></ion-icon>
                  </a>
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
import { getReport, cancelRoomReport, blockRoom } from "../../logic/reportAPI";
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
    const a = await getReport(this.page);
    console.log(a);
    this.datas = a.listResult;
    this.isloading = false;
    console.log("DATA table:");
    this.isloading = false;
    console.log(this.datas);
  },
  methods: {
    async blockRoom(id) {
      console.log("DELETE -->" + id);
      const res = await blockRoom(id);
      if (res.message == "cancel successfully") {
        this.$root.addnoti({
          group: "top",
          title: "Cancel success",
          text: "Your post has been deleted",
        });
      } else {
        this.$root.addnoti({
          group: "top",
          title: "Failure",
          text: res.message,
        });
      }
    },
    async cancelRoomReport(id) {
      console.log("Get data -->" + id);
      const res = await cancelRoomReport(id);
      if (res.message == "cancel successfully") {
        this.$root.addnoti({
          group: "top",
          title: "Cancel success",
          text: "Your post has been deleted",
        });
      } else {
        this.$root.addnoti({
          group: "top",
          title: "Failure",
          text: res.message,
        });
      }
    },
  },
};
</script>
