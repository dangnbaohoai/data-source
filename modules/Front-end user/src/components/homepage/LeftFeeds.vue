<template>
  <div class="w-full align-middle">
    <div class="sticky top-20 divide-y">
      <h1
        class="
          w-full
          py-2
          px-4
          mb-2
          inline-flex
          align-middle
          font-sans
          text-base
          font-bold
        "
        @click="consoleList"
      >
        Forum list :
      </h1>
      <router-link
        class="
          w-full
          py-2
          px-4
          mb-2
          hover:shadow hover:bg-gray-200
          inline-flex
          align-middle
          font-sans
          text-base
          font-bold
          text-left
        "
        v-for="forum in forums"
        :key="forum.forumId"
        :to="{ path: '/forum/' + forum.forumId }"
      >
        {{ forum.nameForum }}
      </router-link>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  created() {
    this.forums = this.listJoinedForum;
  },
  computed: {
    ...mapGetters(["listJoinedForum"]),
    ...mapGetters(["userID"]),
  },
  data() {
    return {
      forums: [],
    }; //end return
  },
  watch: {
    listJoinedForum: function () {
      this.forums = this.listJoinedForum;
    },
  },
  methods: {
    consoleList() {
      console.log(this.$store.state.listJoinedForum);
      this.forums = this.$store.state.listJoinedForum;
      console.log(this.forums[0]);
    },
  },
};
</script>
