<template>
  <div class="w-full">
    <div class="w-full">
      <div class="Prose px-6 py-4 bg-white">
        <div class="user textcentre flex mb-5 w-full">
          <img
            class="
              inline
              object-cover
              w-14
              h-14
              mr-5
              rounded-full
              -ml-2
              border-2 border-dashed
            "
            :src="content.avatar"
            alt="Profile image"
          />
          <div>
            <h4 class="text-xl font-semibold tracking-tight text-gray-800">
              {{ content.createBy }}
            </h4>
            <p
              class="text-xs mr-2 hover:underline cursor-pointer"
              @click="push"
            >
              {{ content.createDate }}
            </p>
          </div>
          <div class="w-96"></div>
        </div>
        <div class="flex">
          <p>{{ content.dateOfEvent }}</p>
          <p>{{ content.addressOfEvent }}</p>
        </div>
        <div>
          <p
            class="leading-normal text-gray-700 text-left"
            v-html="content.content"
          ></p>
        </div>
        <link-Preview :url="firstLink" v-if="isShowLP" />
      </div>
    </div>
    <VuePictureSwipe class="w-full" :items="ImgItems" v-if="ImgItems != null" />
    <!-- ---------------------------- Censor button -->
    <div class="likezone px-4 py-2 bg-white xl:flex">
      <button
        class="
          flex
          w-full
          justify-items-center
          items-center
          border-2 border-green-400
          rounded-lg
          px-4
          py-2
          group
          hover:bg-green-400
          m-2
        "
        @click="accept"
      >
        <svg
          class="h-8 w-8 text-green-500 group-hover:text-white"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M5 13l4 4L19 7"
          />
        </svg>
        <p class="group-hover:text-white">Accept</p>
      </button>
      <button
        class="
          w-full
          flex
          justify-items-center
          items-center
          border-2 border-red-400
          group
          hover:bg-red-400
          rounded-lg
          px-4
          py-2
          m-2
          h-full
        "
        @click="Decline"
      >
        <svg
          class="h-8 w-8 text-red-500 mr-1 group-hover:text-white"
          fill="none"
          viewBox="0 0 24 24"
          stroke="currentColor"
        >
          <path
            stroke-linecap="round"
            stroke-linejoin="round"
            stroke-width="2"
            d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z"
          />
        </svg>
        <p class="group-hover:text-white">Decline</p>
      </button>
    </div>
  </div>

  <!-- <Alert :indeterminate="true" /> -->
</template>

<script>
import linkPreview from "../../postComponent/linkPreview.vue";
import VuePictureSwipe from "vue-picture-swipe";
// import Alert from "../smallComponent/alert.vue";
import { sensorPost, deleteWaittoCensorPost } from "../../../logic/forumAPI";
export default {
  components: { linkPreview, VuePictureSwipe },
  props: {
    content: {
      type: Object,
    },
    forumId: {
      type: Number,
    },
  },
  setup(props) {
    var urlR = /\bhttps?::\/\/\S+|\bwww?.\S+/;
    var URL = props.content.content.match(urlR);
    let ImgItems = ImageList(props.content.linkImage, props.content.createBy);
    if (URL) {
      const firstLink = URL[0];
      const isShowLP = true;
      return {
        firstLink,
        isShowLP,
        ImgItems,
      };
    }
    return {
      isShowLP: false,
    };
  },
  methods: {
    viewPost() {
      this.$emit("showUp");
    },
    push() {
      this.$router.push({ name: "post", params: { id: this.content.ID } });
    },
    async accept() {
      const a = await sensorPost(this.forumId, this.content.postId);

      if (a.data.message == "ok") {
        this.$root.test({
          group: "top",
          title: "Censor success",
          text: "",
        });
        this.$emit("deletep", this.content);
      } else {
        this.$root.test({
          group: "bottom",
          title: a.data.message,
          text: "",
        });
      }
    },
    async Decline() {
      const a = await deleteWaittoCensorPost(this.forumId, this.content.postId);
      console.log(a);
      if (a.data.message == "ok") {
        this.$root.test({
          group: "top",
          title: "Post has been refuse",
          text: "",
        });
        this.$emit("deletep", this.content);
      } else {
        this.$root.test({
          group: "bottom",
          title: a.data.message,
          text: "",
        });
      }
    },
  },
};
function ImageList(imgs, caption) {
  let ImgResult = [];
  for (let i = 0; i < imgs.length; i++) {
    ImgResult.push({
      src: imgs[i],
      thumbnail: imgs[i],
      w: 900,
      h: 600,
      title: "Photo of " + caption,
    });
  }
  console.log("------------ chức năng nhập ảnh--");
  console.log(ImgResult);
  return ImgResult;
}
</script>

<style></style>
