<template>
  <div class="flex overflow-x-auto">
    <div v-if="listImages != [{}]" class="flex">
      <div class="relative" v-for="Image in listImages" :key="Image">
        <img
          class="h-24 w-24 m-1 bg-gray-200 rounded-lg grid place-items-start"
          :src="Image"
        />
        <div
          class="
            h-24
            w-24
            m-1
            hover:bg-gray-100 hover:bg-opacity-75
            rounded-sm
            grid
            absolute
            bottom-0
            left-0
            content-center
            justify-center
            group
            cursor-pointer
          "
          @click="deleteImg(Image)"
        >
          <svg
            class="
              h-8
              w-8
              text-black text-opacity-0
              group-hover:text-opacity-100
            "
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
            />
          </svg>
        </div>
      </div>
    </div>

    <label
      class="
        min-w-min
        w-16
        m-1
        py-2
        bg-gray-200
        hover:bg-gray-300
        rounded-lg
        grid
        place-items-center
        cursor-pointer
      "
      for="fileupload"
    >
      <input
        id="fileupload"
        type="file"
        @change="previewImage"
        accept="image/*"
        class="hidden"
      />
      <svg
        class="h-8 w-8 text-black"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z"
        />
      </svg>
    </label>
  </div>
  <BaseProgress :color="color" :percentage="uploadValue" :rounded="false" />
</template>

<script>
import BaseProgress from "../smallComponent/BaseProgress.vue";
import { getcookie } from "../../logic/getcookie";
import {
  getStorage,
  ref,
  uploadBytesResumable,
  getDownloadURL,
} from "firebase/storage";
export default {
  components: {
    BaseProgress,
  },
  props: {
    importIMG: Object,
  },

  created() {
    if (this.importIMG) this.listImages = this.importIMG;
  },
  data() {
    return {
      process: 10,
      imageData: null,
      pictureURL: null,
      uploadValue: 0,
      listImages: [],
      userID: getcookie("userID"),
    };
  },
  computed: {
    color: function () {
      if (this.uploadValue <= 40) return "red";
      if (this.uploadValue <= 70) return "blue";
      if (this.uploadValue <= 100) return "green";
      return "black";
    },
  },
  // watch: {
  //   listImages: function () {
  //     console.log("---------------- Waching ---------------");

  //   },
  // },
  methods: {
    processstart() {
      this.process = this.process + 10;
      if (this.process == 60) this.color = "blue";
      if (this.process == 90) this.color = "green";
    },
    previewImage(event) {
      if (this.listImages.length <= 15) {
        this.uploadValue = 0;
        this.pictureURL = null;
        this.imageData = event.target.files[0];
        console.log(event.target.files[0]);
        this.onUpload();
      }
    },
    onUpload() {
      const storage = getStorage();
      const r = (Math.random() + 1).toString(36).substring(7);
      const storageRef = ref(storage, `/post_image/${this.userID}/${r}`);
      const metadata = {
        contentType: "image/jpeg",
      };

      const uploadTask = uploadBytesResumable(
        storageRef,
        this.imageData,
        metadata
      );
      uploadTask.on(
        "state_changed",
        (snapshot) => {
          const progress =
            (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
          this.uploadValue = progress;
          console.log("Upload is " + progress + "% done");
          switch (snapshot.state) {
            case "paused":
              console.log("Upload is paused");
              break;
            case "running":
              console.log("Upload is running");
              break;
          }
        },
        (error) => {
          console.error(error);
        },
        () => {
          getDownloadURL(uploadTask.snapshot.ref).then((downloadURL) => {
            console.log("File available at", downloadURL);
            this.listImages.push(downloadURL);
            this.$emit("update", this.listImages);
          });
        }
      );
    },
    deleteImg(url) {
      console.log(url);
      for (var i = 0; i < this.listImages.length; i++) {
        this.listImages.splice(this.listImages.indexOf(url), 1);
        this.$emit("update", this.listImages);
      }
    },
  },
};
</script>

<style></style>
