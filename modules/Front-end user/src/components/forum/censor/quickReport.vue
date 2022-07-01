<template>
  <div class="sticky mx-16">
    <table class="text-left divide-y-2">
      <tr class="text-center">
        <th colspan="3" class="border-2">
          <p class="text-lg font-bold">📢 Quick report</p>
        </th>
      </tr>
      <tr>
        <th><p>Number of Member</p></th>

        <th>
          <p>{{ data.numberMembers }}</p>
        </th>
        <th>
          <svg
            class="h-8 w-8 text-gray-500 hover:bg-gray-400"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            stroke-width="2"
            stroke="currentColor"
            fill="none"
            stroke-linecap="round"
            stroke-linejoin="round"
            @click="goMember"
          >
            <path stroke="none" d="M0 0h24v24H0z" />
            <circle cx="5" cy="9" r="1" />
            <circle cx="5" cy="15" r="1" />
            <circle cx="12" cy="9" r="1" />
            <circle cx="12" cy="15" r="1" />
            <circle cx="19" cy="9" r="1" />
            <circle cx="19" cy="15" r="1" />
          </svg>
        </th>
      </tr>
      <tr>
        <th><p>Mumber of waiting member</p></th>
        <th>
          <p>{{ data.numberMembersNotCensor }}</p>
        </th>
        <th>
          <svg
            class="h-8 w-8 text-gray-500 hover:bg-gray-400"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            stroke-width="2"
            stroke="currentColor"
            fill="none"
            stroke-linecap="round"
            stroke-linejoin="round"
            @click="goMemberCensor"
          >
            <path stroke="none" d="M0 0h24v24H0z" />
            <circle cx="5" cy="9" r="1" />
            <circle cx="5" cy="15" r="1" />
            <circle cx="12" cy="9" r="1" />
            <circle cx="12" cy="15" r="1" />
            <circle cx="19" cy="9" r="1" />
            <circle cx="19" cy="15" r="1" />
          </svg>
        </th>
      </tr>
      <tr>
        <th><p>Mumber of Posts</p></th>
        <th>
          <p>{{ data.numberPosts }}</p>
        </th>
        <th>
          <svg
            class="h-8 w-8 text-gray-500 hover:bg-gray-400"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            stroke-width="2"
            stroke="currentColor"
            fill="none"
            stroke-linecap="round"
            stroke-linejoin="round"
            @click="goMainfeed"
          >
            <path stroke="none" d="M0 0h24v24H0z" />
            <circle cx="5" cy="9" r="1" />
            <circle cx="5" cy="15" r="1" />
            <circle cx="12" cy="9" r="1" />
            <circle cx="12" cy="15" r="1" />
            <circle cx="19" cy="9" r="1" />
            <circle cx="19" cy="15" r="1" />
          </svg>
        </th>
      </tr>
      <tr>
        <th><p>Mumber of posts waiting</p></th>
        <th>
          <p>{{ data.numberPostsNotCensor }}</p>
        </th>
        <th @click="goPostCensor">
          <svg
            class="h-8 w-8 text-gray-500 hover:bg-gray-400"
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
            <circle cx="5" cy="9" r="1" />
            <circle cx="5" cy="15" r="1" />
            <circle cx="12" cy="9" r="1" />
            <circle cx="12" cy="15" r="1" />
            <circle cx="19" cy="9" r="1" />
            <circle cx="19" cy="15" r="1" />
          </svg>
        </th>
      </tr>
      <tr>
        <th><p>Reported comment</p></th>
        <th>
          <p>{{ data.numberReportComments }}</p>
        </th>
        <th @click="goCMTReport">
          <svg
            class="h-8 w-8 text-gray-500 hover:bg-gray-400"
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
            <circle cx="5" cy="9" r="1" />
            <circle cx="5" cy="15" r="1" />
            <circle cx="12" cy="9" r="1" />
            <circle cx="12" cy="15" r="1" />
            <circle cx="19" cy="9" r="1" />
            <circle cx="19" cy="15" r="1" />
          </svg>
        </th>
      </tr>
      <tr>
        <th><p>Reported post</p></th>
        <th>
          <p>{{ data.numberReportPost }}</p>
        </th>
        <th @click="goPostReport">
          <svg
            class="h-8 w-8 text-gray-500 hover:bg-gray-400"
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
            <circle cx="5" cy="9" r="1" />
            <circle cx="5" cy="15" r="1" />
            <circle cx="12" cy="9" r="1" />
            <circle cx="12" cy="15" r="1" />
            <circle cx="19" cy="9" r="1" />
            <circle cx="19" cy="15" r="1" />
          </svg>
        </th>
      </tr>
    </table>
  </div>
  <!-- <ComfirmModel :msg="msg" /> -->
  <!-- numberReportComments: 0 numberReportPost: 6 -->
</template>

<script>
export default {
  props: {
    data: {
      type: Object,
      required: true,
    },
    forumId: {
      type: String,
      required: true,
    },
    isloading: {
      type: Boolean,
      default: true,
    },
  },
  // watch: {
  //   forumId: function () {
  //     this.isloading = false;
  //   },
  // },
  data() {
    return {
      controls: [
        { header: "Dợi kiểm", value: 34 },
        { header: "Số bài viết", value: 34 },
        { header: "Báo cáo", value: 34 },
      ],
    };
  },
  methods: {
    goPostCensor() {
      this.$router.push({
        path: `/forum/${Number.parseInt(this.forumId)}/postCensorship`,
      });
    },
    goMemberCensor() {
      this.$router.push({ path: `/forum/${this.forumId}/memberCensorship` });
    },
    goMember() {
      this.$router.push({ path: `/forum/${this.forumId}/nembers` });
    },
    goMainfeed() {
      this.$router.push({ path: `/forum/${this.forumId}` });
    },
    goPostReport() {
      this.$router.push({ path: `/forum/${this.forumId}/postReport` });
    },
    goCMTReport() {
      this.$router.push({ path: `/forum/${this.forumId}/commentReport` });
    },
  },
};
</script>

<style>
th {
  padding-inline: 2mm;
}
</style>
