<template>
  <div class="h-12"></div>
  <div class="flex">
    <div class="left w-1/4 hidden xl:block"></div>

    <div class="grid justify-items-center flex-grow">
      <div
        v-if="isloading"
        class="
          cover
          parent
          h-56
          w-full
          rounded-b-xl
          bg-gray-400
          animate-pulse
          block
        "
      ></div>
      <img
        :src="header.coverImage"
        v-if="!isloading"
        class="cover parent h-56 w-full rounded-b-xl"
      />
      <div class="w-full grid justify-items-start">
        <h1
          class="
            forumnamme
            font-semibold
            items-start
            -m-7
            text-opacity-100
            ml-10
          "
        >
          {{ header.nameForum }}
        </h1>
      </div>
      <div class="flex my-2 xl:w-full" v-if="!header.isCensor">
        <div class="flex-grow mx-4"></div>

        <button
          class="
            bg-red-600
            text-white
            border-4
            font-bold
            py-2
            px-7
            rounded
            hover:bg-white hover:border-red-400 hover:text-red-400
            flex
            group
          "
          v-if="isJoined"
          @click="leaveForum"
        >
          <svg
            class="h-6 w-6 text-white mr-3 group-hover:text-red-400"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
            <polyline points="16 17 21 12 16 7" />
            <line x1="21" y1="12" x2="9" y2="12" />
          </svg>
          <p>Leave Forum</p>
        </button>
        <button
          class="
            bg-green-600
            text-white
            font-bold
            py-2
            px-7
            rounded
            hover:bg-gray-600
            flex
          "
          v-else
          @click="joinforum"
        >
          <svg
            class="h-6 w-6 text-white mr-3"
            viewBox="0 0 24 24"
            fill="none"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          >
            <path d="M16 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2" />
            <circle cx="8.5" cy="7" r="4" />
            <line x1="20" y1="8" x2="20" y2="14" />
            <line x1="23" y1="11" x2="17" y2="11" />
          </svg>
          <p>Join Forum</p>
        </button>
      </div>
      <div class="flex my-2" v-if="header.message != null">
        <div
          class="
            flex-grow
            bg-red-400
            text-white
            font-bold
            py-2
            px-10
            rounded-l-md
            text-left
          "
        >
          {{ header.message }}
        </div>
        <button
          class="
            group
            bg-red-400
            text-white
            font-bold
            px-4
            rounded-r-md
            hover:bg-white
            border-2 border-red-400
            justify-items-center
          "
          @click="header.message = null"
        >
          <svg
            class="h-8 w-8 text-white group-hover:text-red-700"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M6 18L18 6M6 6l12 12"
            />
          </svg>
        </button>
      </div>
      <div
        class="mt-10 grid justify-items-center w-full"
        v-if="isCensored && !isloading"
      >
        <div class="flex">
          <router-link
            class="
              bg-gray-100
              px-4
              py-2
              mr-1
              rounded-t-xl
              hover:border-blue-300
              border-b-4 border-gray-400
              flex-auto
            "
            v-for="funtion in funtions"
            :key="funtion.value"
            :to="{ path: /forum/ + forumId + funtion.router }"
            :isuserisCensor="isuserisCensor"
          >
            {{ funtion.value }}
          </router-link>
        </div>
        <router-view
          :forumId="forumId"
          class="text-left"
          :contents="contents"
          :isCensor="isuserisCensor"
        />
      </div>
      <Loading v-if="isloading" class="xl:w-3/5" />
    </div>
    <div class="right w-1/4 hidden xl:block">
      <QuickReport
        :data="quickreportData"
        :forumId="forumId"
        :isloading="isloading"
        class="top-60"
        v-if="header.isCensor"
      />
    </div>
  </div>
</template>

<script>
import { viewForumJoin, joinForum, leaveForum } from "../logic/forumAPI";
import QuickReport from "../components/forum/censor/quickReport.vue";
import Loading from "../components/loading/MainfeedLoading.vue";
export default {
  props: {
    forumId: Number,
  },
  watch: {
    async forumId() {
      this.isloading = true;
      console.log("------------------------DetailForum");
      this.header = await viewForumJoin(this.forumId);
      document.title = this.header.nameForum;
      this.isloading = false;
      console.log("this.header");
      console.log(this.header);
      this.contents = this.header.listPostResponse;
      // For Censor quick report
      this.quickreportData.numberMembers = this.header.numberMembers;
      this.quickreportData.numberMembersNotCensor =
        this.header.numberMembersNotCensor;
      this.quickreportData.numberPosts = this.header.numberPosts;
      this.quickreportData.numberPostsNotCensor =
        this.header.numberPostsNotCensor;
      this.quickreportData.numberReportComments =
        this.header.numberReportComments;
      this.quickreportData.numberReportPost = this.header.numberReportPost;
      console.log(this.quickreportData);
      //-----------------------
      if (this.header.message != "user have not been censored") {
        this.isCensored = false;
      }
      if (this.header.message == null) {
        this.isCensored = true;
        this.isJoined = true;
      }

      if (this.header.message == "no forum found")
        this.$router.push({ path: "/404" });
      // coverImage nameForum description message listResult
      this.isloading = false;
    },
  },
  async created() {
    console.log("------------------------DetailForum");
    this.header = await viewForumJoin(this.forumId);
    document.title = this.header.nameForum;
    if (isNaN(parseInt(this.forumId))) {
      this.$router.push({ path: "/404" });
    }
    this.isloading = false;
    console.log("this.header");
    console.log(this.header);
    this.contents = this.header.listPostResponse;
    // For Censor quick report
    this.quickreportData.numberMembers = this.header.numberMembers;
    this.quickreportData.numberMembersNotCensor =
      this.header.numberMembersNotCensor;
    this.quickreportData.numberPosts = this.header.numberPosts;
    this.quickreportData.numberPostsNotCensor =
      this.header.numberPostsNotCensor;
    this.quickreportData.numberReportComments =
      this.header.numberReportComments;
    this.quickreportData.numberReportPost = this.header.numberReportPost;
    if (
      this.header.numberReportPost +
        this.header.numberReportComments +
        this.header.numberPostsNotCensor +
        this.header.numberMembers !=
      0
    )
      this.isuserisCensor = true;
    console.log(this.quickreportData);
    //-----------------------
    if (this.header.message != "user have not been censored") {
      this.isCensored = false;
    }
    if (this.header.message == null) {
      this.isCensored = true;
      this.isJoined = true;
    }

    if (this.header.message == "no forum found")
      this.$router.push({ path: "/404" });
    // coverImage nameForum description message listResult
    this.isloading = false;
  },
  components: { QuickReport, Loading },
  data() {
    return {
      header: {},
      backurl:
        "https://static5.depositphotos.com/1006844/519/i/600/depositphotos_5196564-stock-photo-beautiful-young-woman-kissing-a.jpg",
      informations: {},
      funtions: [
        { value: "Main forum", router: "/" },
        { value: "Events", router: "/events" },
        { value: "Members", router: "/nembers" },
      ],
      isCensored: false,
      isJoined: false,
      quickreportData: {},
      contents: [],
      isloading: true,
      isuserisCensor: false,
      type: "",
    };
  },
  methods: {
    async joinforum() {
      const a = (await joinForum(this.forumId)).message;
      if (a == "join forum success") {
        this.$root.addnoti({
          group: "top",
          title: "Request to join forum successfull",
        });
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: a,
          text: "",
        });
      }
    },
    async leaveForum() {
      const a = await leaveForum(this.forumId);
      if (a == "leave Forum success") {
        this.$root.addnoti({
          group: "top",
          title: "Leave room successfull",
        });
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: a,
          text: "",
        });
      }
    },
  },
};
</script>

<style>
.cover {
  object-fit: cover;
  object-position: px;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 50% 50%;
  /* background-image: linear-gradient(to bottom, rgba(255, 0, 0, 0)); */
}
.forumnamme {
  font-size: 10mm;
  font-family: "Gill Bahnschrift", "Gill Sans MT", Calibri, "Trebuchet MS",
    sans-serif;
  text-shadow: 2px 0 0 #fff, -2px 0 0 #fff, 0 2px 0 #fff, 0 -2px 0 #fff,
    1px 1px #fff, -1px -1px 0 #fff, 1px -1px 0 #fff, -1px 1px 0 #fff;
}
</style>
