<template>
  <div class="items-start justify-center">
    <div class="col-span-12">
      <div class="overflow-auto lg:overflow-visible">
        <div class="text-left px-4 py-1 font-bold text-4xl text-white max-w-md">
          <h1>Forum Management</h1>
        </div>
        <div class="flex">
          <table
            class="
              table
              text-gray-400
              border-separate
              space-y-6
              xl:text-lg
              my-5
            "
          >
            <thead class="bg-gray-800 text-gray-500">
              <tr class="">
                <th class="px-4 py-2">ID</th>
                <th class="px-4 py-2">Cover</th>
                <th class="px-4 py-2">Name</th>
                <th class="px-4 py-2 text-left">Description</th>
                <th class="px-4 py-2">Censor</th>
                <th class="px-4 py-2 text-center">Create date</th>
                <th class="px-4 py-2 text-left">Members</th>
                <th class="px-4 py-2 text-left">Posts</th>
                <th class="px-4 py-2 text-left">
                  <p class="bg-red-500 px-1 rounded-md text-white">Report</p>
                </th>
                <th class="px-4 py-2 text-left">Create By</th>
                <th class="px-2 py-2 text-left">Action</th>
              </tr>
            </thead>
            <tbody>
              <tr
                class="bg-gray-700 hover:bg-gray-600"
                v-for="data in datas"
                v-bind:key="data.forumId"
              >
                <td class="p-3">{{ data.forumId }}</td>
                <td>
                  <img
                    class="rounded-xl h-12 w-16 object-cover m-2"
                    :src="data.coverImage"
                    alt="unsplash image"
                  />
                </td>
                <td class="p-3">
                  <div class="flex">
                    <div class="ml-3 text-left">
                      <h1 class="">{{ data.nameForum }}</h1>
                    </div>
                  </div>
                </td>
                <td class="p-3">{{ data.description }}</td>
                <td class="p-3">{{ data.censerName }}</td>
                <td class="p-3 font-bold">{{ data.createDate }}</td>
                <td class="p-3 font-bold">{{ data.numberMembers }}</td>
                <td class="p-3 font-bold">{{ data.numberPosts }}</td>
                <td class="p-3 font-bold">
                  {{ data.numberReportComments + data.numberReportPost }}
                </td>
                <td class="p-3 font-bold">{{ data.createBy }}</td>
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
                <td class="p-3 flex items-center">
                  <router-link
                    :to="'/forumManagement/edit/' + data.forumId"
                    class="text-gray-400 hover:text-gray-100 mr-2"
                  >
                    <ion-icon name="settings-outline"></ion-icon>
                  </router-link>
                  <a href="#" class="text-gray-400 hover:text-gray-100 ml-2">
                    <ion-icon
                      name="trash-outline"
                      @click="deleteForumc(data)"
                    ></ion-icon>
                  </a>
                </td>
              </tr>
              <tr class="bg-gray-700 animate-pulse" v-if="isloading">
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
              </tr>
              <tr class="bg-gray-700 animate-pulse" v-if="isloading">
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
              </tr>
              <tr class="bg-gray-700 animate-pulse" v-if="isloading">
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
              </tr>
              <tr class="bg-gray-700 animate-pulse" v-if="isloading">
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
                <td class="h-16 b"></td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- LOADING .............. -->
      </div>
    </div>
    <div>
      <Pagination
        :currentPage="currentPage"
        :pages="data"
        :totalPages="page"
        @pagechanged="pagechanged"
      />
    </div>
  </div>
</template>

<script>
import Pagination from "../smallmodal/Pagination.vue";
import { getForum, getSingleForum, deleteForum } from "../../logic/ForumAPI";
import { location } from "../../logic/handlingdata";
export default {
  components: { Pagination },
  data() {
    return {
      datas: [],
      isloading: true,
      page: 50,
      currentPage: 1,
      data: [{ name: 1 }, { name: 2 }, { name: 3 }, { name: 4 }, { name: 5 }],
    };
  },
  watch: {
    currentPage: function () {
      let data = [];
      console.log(this.page);
      if (this.page < 5) {
        for (let i = 1; i <= this.page; i++) {
          data.push({ name: i });
        }
        this.data = data;
        console.log("this.page < 5");
      } else if (this.currentPage > 2 && this.currentPage < this.page) {
        for (let i = this.currentPage - 3; i < this.currentPage + 2; i++) {
          data.push({ name: i });
        }
        console.log("this.page > 5");
        this.data = data;
      }
    },
    page: function () {
      let data = [];
      console.log(this.page);
      if (this.page < 5) {
        for (let i = 1; i <= this.page; i++) {
          data.push({ name: i });
        }
        this.data = data;
      } else if (this.currentPage > 2 && this.currentPage < this.page) {
        for (let i = this.currentPage - 3; i < this.currentPage + 2; i++) {
          data.push({ name: i });
        }
        this.data = data;
      }
    },
  },
  async created() {
    const a = await getForum(this.currentPage);
    this.datas = a.listResult;
    this.page = a.totalPage;
    this.isloading = false;
    console.log("DATA table:");
    this.isloading = false;
    console.log(this.datas);
  },
  methods: {
    async deleteForumc(frm) {
      if (confirm("Delete this Account ?")) {
        const res = await deleteForum(frm.forumId);
        if (res.message == "yes") {
          this.$root.addnoti({
            group: "top",
            title: "Delete success",
            text: "User has been deleted",
          });
          this.datas.splice(location(frm, this.datas), 1);
        } else {
          this.$root.addnoti({
            group: "bottom",
            title: "Error",
            text: res.message,
          });
        }
      }
    },
    async getForumdata(id) {
      console.log("Get data -->" + id);
      console.log(await getSingleForum(id));
    },
    async pagechanged(page) {
      this.isloading = true;
      this.currentPage = page;
      this.datas = [];
      const a = await getForum(page);
      this.datas = a.listResult;
      this.isloading = false;
    },
  },
};
</script>
<style></style>
