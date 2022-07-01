<template>
  <div class="xl:mx-24">
    <div class="flex mt-12 items-center">
      <button
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
        @click="addUser"
        v-if="isaddUser"
      >
        <svg
          class="h-8 w-8 text-white"
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
          <path d="M9 11l-4 4l4 4m-4 -4h11a4 4 0 0 0 0 -8h-1" />
        </svg>
        <p class="ml-1 self-center my-1">Back to view user</p>
      </button>
      <button
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
        @click="addUser"
        v-else
      >
        <svg
          class="h-8 w-8 self-center"
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
          <rect x="4" y="4" width="16" height="16" rx="2" />
          <line x1="9" y1="12" x2="15" y2="12" />
          <line x1="12" y1="9" x2="12" y2="15" />
        </svg>
        <p class="ml-1 self-center my-1">new account</p>
      </button>
      <button
        class="
          bg-gray-700
          text-white
          font-bold
          py-2
          px-4
          rounded
          hover:bg-gray-600
          flex
          items-center
          mx-5
          border-2
        "
        @click="showSubmit = !showSubmit"
      >
        <svg
          class="h-7 w-7 text-white mr-3"
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
          <rect x="3" y="4" width="18" height="4" rx="2" />
          <path d="M5 8v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2 -2v-10" />
          <line x1="10" y1="12" x2="14" y2="12" />
        </svg>
        Import from Excel
      </button>
      <div v-if="showSubmit" class="flex items-center">
        <input
          type="file"
          class="form-control"
          :class="{ ' is-invalid': error.message }"
          id="input-file-import"
          name="file_import"
          ref="import_file"
          @change="onFileChange"
        />
        <button
          class="
            bg-gray-700
            text-white
            font-bold
            py-2
            px-4
            rounded
            hover:bg-gray-600
            flex
            items-center
            mx-5
            border-2
          "
          @click="submitFile"
        >
          <svg
            class="h-8 w-8 text-white mr-2"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <polyline
              points="16 16 12 12 8 16"
              :class="[submiting ? 'animate-bounce' : '']"
            />
            <line
              x1="12"
              y1="12"
              x2="12"
              y2="21"
              :class="[submiting ? 'animate-bounce' : '']"
            />
            <path d="M20.39 18.39A5 5 0 0 0 18 9h-1.26A8 8 0 1 0 3 16.3" />
            <polyline
              points="16 16 12 12 8 16"
              :class="[submiting ? 'animate-bounce' : '']"
            />
          </svg>
          {{ submiting ? "Submiting ..." : "Submit" }}
        </button>
      </div>

      <button
        class="
          bg-gray-700
          text-white
          font-bold
          py-2
          px-4
          rounded
          hover:bg-gray-600
          flex
          items-center
          border-2
        "
        @click="downloadFile"
      >
        <svg
          class="h-7 w-7 text-white mr-2"
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
          <rect x="4" y="4" width="16" height="16" rx="2" />
          <line x1="4" y1="12" x2="20" y2="12" />
          <line x1="12" y1="4" x2="12" y2="20" />
        </svg>
        Export to Excel
      </button>

      <!-- <input
        type="text"
        class="ml-4 w-60 bg-gray-800 rounded-xl text-white px-4"
        placeholder="Searching user"
      /> -->
      <div class="w-10"></div>
    </div>
    <router-view
      :data="dataforedit"
      @forEdit="addData"
      :sumitedData="sumitedData"
    />
  </div>
</template>

<script>
// import axios from "axios";
import { getExcel, submitFile } from "../logic/UserAPI";
export default {
  data() {
    return {
      isaddUser: false,
      dataforedit: [],
      file: "",
      error: {},
      import_file: "",
      showSubmit: false,
      submiting: false,
      sumitedData: null,
    };
  },
  created() {
    if (this.$route.path == "/accountmanagement") {
      this.isaddUser = false;
    } else {
      this.isaddUser = true;
    }
  },
  components: {},
  methods: {
    onFileChange(e) {
      this.import_file = e.target.files[0];
    },
    async submitFile() {
      this.submiting = true;
      if (this.import_file) {
        console.log(this.import_file);
        const a = await submitFile(this.import_file);
        console.log(a);
        if (a.status == 200) {
          this.$root.addnoti({
            group: "top",
            title: "Upload success",
            text: "",
          });
          this.sumitedData = a.data;
          this.$router.push({ path: "/accountmanagement/imported" });
        } else {
          this.$root.addnoti({
            group: "bottom",
            title: "Error",
            text: a.status,
          });
        }
      }

      this.submiting = false;
    },
    downloadFile() {
      getExcel();
    },
    addUser() {
      if (this.$route.path == "/accountmanagement") {
        this.$router.push({ path: "/accountmanagement/new" });
        this.isaddUser = true;
      } else {
        this.$router.push({ path: "/accountmanagement" });
        this.isaddUser = false;
        this.dataforedit = null;
      }
    },
    addData(data) {
      this.dataforedit = data;
      this.addUser();
    },
  },
};
</script>

<style></style>
