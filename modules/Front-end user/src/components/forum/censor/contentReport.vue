<template>
  <div class="w-full mb-8 overflow-hidden rounded-lg shadow-lg">
    <div class="w-full overflow-x-auto">
      <h1 class="text-3xl px-4 font-bold">Reported post</h1>
      <Alert :msgs="almsg" :color="alcolor" />
      <table class="w-full">
        <thead>
          <tr
            class="
              text-md
              font-semibold
              tracking-wide
              text-left text-gray-900
              bg-gray-100
              uppercase
            "
          >
            <th class="px-4 py-3">ID</th>
            <th class="px-4 py-3">Reporter</th>
            <th class="px-4 py-3">Date</th>
            <th class="px-4 py-3">Reason</th>
            <th class="px-4 py-3">To</th>
            <th class="pl-4 py-3">Action</th>
          </tr>
        </thead>
        <tbody class="bg-white">
          <tr class="text-gray-700" v-for="data in datas" :key="data.reportId">
            <td class="px-4 py-3 text-ms font-semibold border">
              {{ data.reportId }}
            </td>
            <td class="px-4 py-3 border">
              <div class="flex items-center text-sm">
                <div class="relative w-8 h-8 mr-3 rounded-full md:block">
                  <img
                    class="object-cover w-full h-full rounded-full"
                    :src="data.user.avatar"
                    alt=""
                    loading="lazy"
                  />
                  <div
                    class="absolute inset-0 rounded-full shadow-inner"
                    aria-hidden="true"
                  ></div>
                </div>
                <div>
                  <p class="font-semibold text-black">
                    {{ data.user.fullName }}
                  </p>
                  <p class="text-xs text-gray-600">{{ data.user.position }}</p>
                </div>
              </div>
            </td>
            <td class="px-4 py-3 text-ms font-semibold border">
              {{ data.createDate }}
            </td>
            <td class="px-4 py-3 text-base border">
              <span
                class="
                  px-1
                  py-1
                  font-semibold
                  leading-tight
                  text-red-600
                  rounded-sm
                "
              >
                {{ data.reason }}
              </span>
            </td>
            <td class="px-4 py-3 border">
              <p class="font-semibold text-black">
                {{ data.post.createBy }}
              </p>
            </td>
            <!--Xem tin - bo qua - xoa tin -->
            <td class="px-2 py-4 text-sm border flex items-center">
              <router-link
                class="hover:bg-gray-100 border- px-1"
                title="go to the post"
                :to="{
                  path:
                    '/Forum/' + data.post.forumId + '/post/' + data.post.postId,
                }"
                target="_blank"
              >
                <svg
                  class="h-5 w-5 text-gray-500"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <polyline points="15 3 21 3 21 9" />
                  <polyline points="9 21 3 21 3 15" />
                  <line x1="21" y1="3" x2="14" y2="10" />
                  <line x1="3" y1="21" x2="10" y2="14" />
                </svg>
              </router-link>
              <button
                @click="cancelPostReport(data)"
                class="hover:bg-gray-100 border-0 px-1"
                title="cancel the report (dismiss)"
              >
                <svg
                  class="h-5 w-5 text-blue-500"
                  viewBox="0 0 24 24"
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                >
                  <polyline points="3 6 5 6 21 6" />
                  <path
                    d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"
                  />
                  <line x1="10" y1="11" x2="10" y2="17" />
                  <line x1="14" y1="11" x2="14" y2="17" />
                </svg>
              </button>
              <button
                @click="deleteReport(data)"
                class="hover:bg-gray-100 border-0 px-1"
                title="delete post"
              >
                <svg
                  class="h-8 w-8 text-green-500"
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
                  <path d="M5 12l5 5l10 -10" />
                </svg>
              </button>
              <ConfirmPopup></ConfirmPopup>
              <button
                @click="kickUser(data)"
                class="hover:bg-gray-100 border-0 px-1"
                title="Kick user"
              >
                <svg
                  class="h-7 w-7 text-red-500"
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
                  <circle cx="9" cy="7" r="4" />
                  <path d="M3 21v-2a4 4 0 0 1 4 -4h4a4 4 0 0 1 4 4v2" />
                  <path d="M17 9l4 4m0 -4l-4 4" />
                </svg>
              </button>
            </td>
          </tr>
          <tr
            class="text-center hover:bg-gray-200 cursor-pointer"
            @click="getReportData"
          >
            <td colspan="6" class="text-center">Next page</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import {
  getPostReport,
  DeleteReport,
  cancelPostReport,
} from "../../../logic/forumAPI";
import { timeconvert, location } from "../../../logic/handlingdata";
import Alert from "../../smallComponent/alert.vue";
import ConfirmPopup from "primevue/confirmpopup";
import { deleteMemberOutOfForum } from "../../../logic/forumAPI";
export default {
  props: {
    forumId: String,
    postId: String,
  },
  data() {
    return {
      datas: null,
      almsg: "",
      alcolor: "green",
      page: 1,
    };
  },
  components: {
    Alert,
    ConfirmPopup,
  },
  async created() {
    await this.getReportData();
  },
  methods: {
    push(forumId, postId) {
      console.log(forumId + postId);
      this.$router.push({
        path: "/Forum/" + forumId + "/post/" + postId,
      });
    },
    async deleteReport(id) {
      const res = await DeleteReport(id.reportId, this.forumId);
      if (res.data.message == "delete successfully") {
        this.$root.addnoti({
          group: "top",
          title: "Delete successfully",
          text: "",
        });
        this.datas.splice(location(id, this.datas), 1);
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: res.status,
          text: "",
        });
      }
    },
    async cancelPostReport(id) {
      const res = await cancelPostReport(id.reportId, this.forumId);
      if (res.data.message == "cancel successfully") {
        this.$root.addnoti({
          group: "top",
          title: "Cancel successfully",
          text: "",
        });
      } else {
        this.$root.addnoti({
          group: "top",
          title: res.data.message,
          text: "",
        });
      }
      this.datas.splice(location(id, this.datas), 1);
    },
    alert(msg, color) {
      this.almsg = msg;
      this.alcolor = color;
    },
    async getReportData() {
      this.datas = await getPostReport(
        Number.parseInt(this.forumId),
        this.page
      );
      console.log(this.datas);
      for (let i = 0; i < this.datas.length; i++) {
        console.log(this.datas[i].createDate);
        this.datas[i].createDate = timeconvert(this.datas[i].createDate);
      }
      this.page++;
    },
    async kickUser(data) {
      console.log(data);
      await this.$confirm.require({
        target: event.currentTarget,
        message: "Are you sure you want to kick this user?",
        icon: "pi pi-exclamation-triangle",
        accept: async () => {
          await this.deleteReport(data);
          const res = await (
            await deleteMemberOutOfForum(this.forumId, data.post.userId)
          ).data;
          if (res.message == "delete member successful") {
            this.$root.addnoti({
              group: "top",
              title: "Delete success",
              text: "The user has been kicked out of the forum",
            });
          } else {
            this.$root.addnoti({
              group: "bottom",
              title: "Failure",
              text: res.message,
            });
          }
        },
        reject: () => {
          console.log("It also work" + this.PostID);
        },
      });
    },
  },
};
</script>

<style></style>
