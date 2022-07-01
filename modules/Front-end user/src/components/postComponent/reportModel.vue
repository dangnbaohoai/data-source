<template>
  <div
    class="
      min-w-screen
      h-screen
      animated
      fadeIn
      faster
      fixed
      left-0
      top-0
      flex
      justify-center
      items-center
      inset-0
      z-50
      outline-none
      focus:outline-none
      bg-no-repeat bg-center bg-cover bg-gray-500 bg-opacity-0
    "
    @click.self="cancel"
    id="modal-id"
  >
    <div class="absolute bg-black opacity-80 inset-0 z-0"></div>
    <div
      class="
        w-full
        max-w-lg
        p-5
        relative
        mx-auto
        my-auto
        rounded-xl
        shadow-lg
        bg-white
        opacity-100
      "
    >
      <!--content-->
      <div class="">
        <!--body-->
        <div class="text-center p-5 flex-auto justify-center">
          <svg
            class="w-16 h-16 flex items-center text-yellow-700 mx-auto"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M11 5.882V19.24a1.76 1.76 0 01-3.417.592l-2.147-6.15M18 13a3 3 0 100-6M5.436 13.683A4.001 4.001 0 017 6h1.832c4.1 0 7.625-1.234 9.168-3v14c-1.543-1.766-5.067-3-9.168-3H7a3.988 3.988 0 01-1.564-.317z"
            />
          </svg>

          <p class="text-xl font-bold py-4 text-yellow-500">REPORT</p>
          <p class="text-lg px-8 font-bold">Choice the reason below:</p>
          <div class="grid">
            <button
              class="px-0.5 py-2 text-xl font-medium"
              v-for="reason in reasons"
              :key="reason.id"
              @click="choiceReason(reason.value)"
              :class="[
                reportForm.reason == reason.value
                  ? 'border-2 border-yellow-400'
                  : '',
              ]"
            >
              <p>{{ reason.value }}</p>
            </button>
          </div>
        </div>
        <!--footer-->
        <div class="flex p-3 mt-2 text-center space-x-4 md:block">
          <button
            class="
              mb-2
              md:mb-0
              bg-white
              px-5
              py-2
              text-sm
              shadow-sm
              font-medium
              tracking-wider
              border
              text-gray-600
              rounded-full
              hover:shadow-lg hover:bg-gray-100
            "
            @click="cancel"
          >
            Cancel
          </button>
          <button
            class="
              bg-red-500
              border border-red-500
              py-2
              px-5
              text-sm
              shadow-sm
              font-medium
              text-white
              rounded-full
              hover:shadow-lg hover:bg-red-600
            "
            @click="sendReport"
          >
            Send report
          </button>
        </div>
        <div v-if="msg" class="bg-red-500 text-white text-center py-0.5 flex">
          <p class="flex-grow">{{ msg }}</p>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { ReportPost } from "../../logic/postAPI";
export default {
  name: "reportModel",
  props: {
    forumId: Number,
    postId: Number,
  },
  data: () => ({
    reasons: [
      {
        id: 1,
        value: "Toxic content",
      },
      {
        id: 2,
        value: "Sensitive content",
      },
      {
        id: 3,
        value: "Personal insult",
      },
      {
        id: 4,
        value: "Hearsay",
      },
      {
        id: 5,
        value: "Hating",
      },
    ],
    targetReason: "",
    reportForm: {
      forumId: 0,
      postId: 0,
      reason: "",
    },
    msg: null,
  }),
  methods: {
    cancel() {
      this.$emit("close");
    },
    choiceReason(R) {
      this.reportForm.reason = R;
    },
    async sendReport() {
      this.reportForm.forumId = Number.parseInt(this.forumId);
      this.reportForm.postId = Number.parseInt(this.postId);
      console.log(this.reportForm);
      this.msg = await ReportPost(this.reportForm);
      if (this.msg.message == "report successfully") {
        this.$root.test({
          group: "top",
          title: "Report success",
          text: "Your report has been sended to censor",
        });
        this.cancel();
      }
    },
  },
};
</script>
