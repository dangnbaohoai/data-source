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
          <h1>Account Management</h1>
        </div>
        <table class="table text-gray-400 border-separate space-y-6 xl:text-lg">
          <thead class="bg-gray-800 text-gray-100">
            <tr>
              <th class="px-4 py-2">ID</th>
              <th class="px-4 py-2">Name</th>
              <th class="px-4 py-2 text-left">Code</th>
              <th class="px-4 py-2">Gender</th>
              <th class="px-4 py-2 text-center">Email</th>
              <th class="px-4 py-2 text-left">Address</th>
              <th class="px-4 py-2 text-left">Numer</th>
              <th class="px-4 py-2 text-left">Position</th>
              <th class="px-4 py-2 text-left">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr
              class="bg-gray-700 hover:bg-gray-600 text-white"
              v-for="data in datas"
              v-bind:key="data.userId"
            >
              <td class="p-3">{{ data.userId }}</td>
              <td class="p-3">
                <div class="flex align-items-center">
                  <img
                    class="
                      rounded-full
                      h-12
                      w-12
                      object-cover
                      transform
                      hover:scale-200
                    "
                    :src="data.avatar"
                    alt="unsplash image"
                  />
                  <div class="ml-3 text-left">
                    <div class="">{{ data.fullName }}</div>
                    <div class="text-gray-500">usn:{{ data.userName }}</div>
                  </div>
                </div>
              </td>
              <td class="p-3">{{ data.userId }}</td>
              <td class="p-3">{{ data.gender }}</td>
              <td class="p-3 font-bold">{{ data.email }}</td>
              <td class="p-3 font-bold text-left">{{ data.addressOfUser }}</td>
              <td class="p-3 font-bold">{{ data.numberPhone }}</td>
              <td class="p-3">
                <span
                  class="text-gray-50 rounded-md px-2"
                  v-bind:class="[
                    data.position == 'student' || data.position == 'Student'
                      ? 'bg-green-400'
                      : 'bg-red-400',
                  ]"
                  >{{ data.position }}</span
                >
              </td>
              <td class="p-3">
                <button
                  class="text-gray-400 hover:text-gray-100 mr-2"
                  @click="forEdit(data)"
                >
                  <ion-icon name="settings-outline"></ion-icon>
                </button>
                <a href="#" class="text-gray-400 hover:text-gray-100 ml-2">
                  <ion-icon
                    name="trash-outline"
                    @click="deleteAcc(data)"
                  ></ion-icon>
                </a>
              </td>
            </tr>
            <tr class="bg-gray-700 animate-pulse" v-if="isloading">
              <td class="h-16 w-20"></td>
              <td class="h-16 w-60"></td>
              <td class="h-16 b"></td>
              <td class="h-16 b"></td>
              <td class="h-16 w-72"></td>

              <td class="h-16 w-32"></td>
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
            </tr>
          </tbody>
        </table>
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
import { getacc, deleteacc } from "../../logic/UserAPI";
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
      // console.log(this.page);
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
    const a = await getacc(1);
    this.datas = a.listResult;
    this.isloading = false;
    this.page = a.totalPage;
    // if(this.page <5 )
    // {

    // }
  },
  methods: {
    async deleteAcc(id) {
      if (confirm("Delete this Account ?")) {
        const res = await deleteacc(id.userId);
        if (res.message == "yes") {
          this.$root.addnoti({
            group: "top",
            title: "Delete success",
            text: "User has been deleted",
          });
          this.datas.splice(location(id, this.datas), 1);
        } else {
          this.$root.addnoti({
            group: "bottom",
            title: "Error",
            text: res.message,
          });
        }
      }
    },
    async pagechanged(page) {
      this.isloading = true;
      this.currentPage = page;
      this.datas = [];
      const a = await getacc(page);
      this.datas = a.listResult;
      this.isloading = false;
    },
    forEdit(data) {
      this.$emit("forEdit", data);
    },
  },
};
</script>
