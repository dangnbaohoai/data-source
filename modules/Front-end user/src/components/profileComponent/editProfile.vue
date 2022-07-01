<template>
  <div class="min-h-screen">
    <div class="grid justify-items-center">
      <div
        v-bind:style="{ 'background-image': 'url(' + backurl + ')' }"
        class="cover parent h-56 xl:w-1/2 2xl:w-1/2 w-full rounded-b-xl"
      ></div>
      <img
        class="rounded-full border-8 border-white h-40 w-40 -mt-20"
        :src="pictureURL == null ? avatar : pictureURL"
      />
      <label
        class="
          grid
          place-items-center
          rounded-full
          border-8 border-white
          h-40
          w-40
          -mt-40
          hover:border-green-500
          bg-opacity-20
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
          class="h-10 w-10 text-green-500"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path
            d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"
          />
          <circle cx="12" cy="13" r="4" />
        </svg>
      </label>
      <h1 class="username font-semibold">{{ username }}</h1>
      <div class="grid grid-cols-1 gap-1 xl:grid-cols-2">
        <div
          class="text-justify flex font-sans font-medium w-auto"
          v-for="data in datas"
          v-bind:key="data.field"
        >
          <div class="py-2 xl:px-4 bg-white mr-1 w-52">
            {{ data.field }}
          </div>
          <input
            class="
              py-2
              px-2
              xl:px-4
              w-full
              rounded-tr-xl
              bg-gray-600
              text-white text-lg
              focus:bg-gray-500
              border-white
              mr-2
            "
            v-model="data.value"
            :readonly="!data.edit"
          />
        </div>
      </div>
      <div class="flex">
        <div
          class="
            my-4
            bg-gray-600
            py-1
            px-4
            cursor-pointer
            text-lg text-white
            mr-4
            rounded-md
          "
          @click="cancel"
        >
          Hủy
        </div>
        <div
          class="
            my-4
            bg-gray-600
            py-1
            px-4
            cursor-pointer
            text-lg text-white
            rounded-md
          "
          @click="saveEdit"
        >
          Lưu
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { updateUser } from "@/logic/api.js";
import {
  getStorage,
  ref,
  uploadBytesResumable,
  getDownloadURL,
} from "firebase/storage";
export default {
  props: {
    username: String,
    avatar: String,
    datas: Object,
  },
  data() {
    return {
      imageData: null,
      pictureURL: null,
      uploadValue: 0,
    };
  },
  methods: {
    async saveEdit() {
      let pic = "";
      if (this.pictureURL == null) {
        pic = this.avatar;
      } else {
        pic = this.pictureURL;
      }
      console.log("Puc URL" + pic);
      const up = {
        dateOfBirth: this.datas[3].value,
        gender: this.datas[5].value,
        addressOfUser: this.datas[7].value,
        numberPhone: this.datas[8].value,
        avatar: pic,
      };
      console.log(up);
      const res = await updateUser(up);
      console.log(typeof res);
      if (res === 200) this.cancel();
      //   this.$router.push();
    },
    cancel() {
      this.$emit("cancel");
    },
    previewImage(event) {
      this.uploadValue = 0;
      this.pictureURL = null;
      this.imageData = event.target.files[0];
      console.log(event.target.files[0]);
      this.onUpload();
    },
    onUpload() {
      const storage = getStorage();
      const storageRef = ref(storage, `/avatar_user/${this.username}/coverimg`);
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
            // console.log("File available at", downloadURL);
            this.pictureURL = downloadURL;
          });
        }
      );
    },
  },
};
</script>

<style>
.avatar :hover {
  background-color: rgba(0, 0, 0, 0.651);
}
</style>
