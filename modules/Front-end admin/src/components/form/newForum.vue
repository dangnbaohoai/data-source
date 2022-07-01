<template>
  <div class="">
    <h1 class="text-left text-white text-3xl">Add new Forum</h1>
    <form class="place-items-start">
      <div class="text-xl grid grid-rows-6 grid-flow-col gap-4 w-3/4">
        <div class="flex place-items-center">
          <p class="text-left text-white">Forum ID:</p>
          <div class="flex-grow" />
          <input
            type="text"
            name=""
            class="form text-white"
            v-model="FormData.forumId"
            :disabled="isDisabled"
            required
          />
        </div>

        <div class="flex place-items-center">
          <p class="text-left text-white">Forum Name:</p>
          <div class="flex-grow" />
          <input
            type="text"
            name=""
            class="form"
            v-model="FormData.nameForum"
            required
          />
        </div>
        <div class="flex place-items-center">
          <p class="text-left text-white">Description:</p>
          <div class="flex-grow" />
          <input
            type="text"
            name=""
            class="form"
            v-model="FormData.description"
          />
        </div>
        <div class="place-items-center row-span-3 col-start-2 col-end-3">
          <div class="flex">
            <p class="text-left text-white place-self-center">Pick a censor:</p>
            <div class="flex-grow" />
            <div>
              <!-- <p>{{ FormData.censerId }}</p> -->
            </div>
          </div>

          <div class="h-44 overflow-auto bg-gray-900 my-2 rounded-lg">
            <table
              class="
                border-collapse
                table-fixed
                border border-green-800 border-dashed
                bg-gray-900
                text-white
                h-10
              "
            >
              <thead class="sticky">
                <th class="px-2 border border-green-600">ID</th>
                <th class="w-full border border-green-600">Name</th>
                <th class="w-1/5 border border-green-600"></th>
              </thead>
              <tbody class="">
                <tr
                  class="
                    border border-green-600 border-dashed
                    hover:bg-gray-800
                    cursor-pointer
                  "
                  v-for="Censor in Censors"
                  :key="Censor.userId"
                  :class="[
                    FormData.censerId == Censor.userId ? 'bg-gray-700' : '',
                  ]"
                >
                  <td @click="setCensor(Censor)">
                    {{ Censor.userId }}
                  </td>
                  <td @click="setCensor(Censor)" class="text-left pl-2">
                    {{ Censor.fullName }}
                  </td>
                  <td
                    class="hover:bg-green-500 grid justify-items-stretch h-full"
                  >
                    <svg
                      class="h-6 w-6 text-white"
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
                      <polyline points="16 4 20 4 20 8" />
                      <line x1="14" y1="10" x2="20" y2="4" />
                      <polyline points="8 20 4 20 4 16" />
                      <line x1="4" y1="20" x2="10" y2="14" />
                      <polyline points="16 20 20 20 20 16" />
                      <line x1="14" y1="14" x2="20" y2="20" />
                      <polyline points="8 4 4 4 4 8" />
                      <line x1="4" y1="4" x2="10" y2="10" />
                    </svg>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <!-- Place cover picture  -->
        <label
          class="
            bg-gray-800
            col-start-1 col-end-2
            row-start-4 row-end-7
            border-2 border-green-600 border-dashed
            rounded-lg
            grid
            place-items-center
            hover:bg-gray-700
            cursor-pointer
            mr-10
          "
          @click="onUpload"
          for="fileupload"
        >
          <input
            id="fileupload"
            type="file"
            @change="previewImage"
            accept="image/*"
          />
          <div v-if="pictureURL == null" class="grid place-items-center">
            <svg
              class="h-16 w-16 text-green-500"
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
              <circle cx="12" cy="13" r="3" />
              <path
                d="M5 7h1a2 2 0 0 0 2 -2a1 1 0 0 1 1 -1h2m9 7v7a2 2 0 0 1 -2 2h-14a2 2 0 0 1 -2 -2v-9a2 2 0 0 1 2 -2"
              />
              <line x1="15" y1="6" x2="21" y2="6" />
              <line x1="18" y1="3" x2="18" y2="9" />
            </svg>
            <p>Cover image</p>
            <progress id="progress" :value="uploadValue" max="100"></progress>
          </div>
          <img
            class="preview rounded-md h-52 w-full"
            :src="pictureURL"
            v-if="pictureURL !== null"
          />
        </label>
        <div
          class="
            bg-gray-800
            col-start-2 col-end-3
            row-start-4 row-end-7
            border-2 border-green-600 border-collapse
            rounded-lg
            grid
            place-items-start
            hover:bg-gray-700
            cursor-pointer
            p-4
            text-white text-left
          "
          v-if="censor !== null"
        >
          <div class="flex">
            <img
              class="h-40 w-32 bg-gray-400 rounded-lg mr-4"
              :src="censor.avatar"
            />
            <div>
              <h1>{{ censor.Name }}</h1>
              <p>{{ censor.userName }}</p>
              <p>{{ censor.email }}</p>
              <p>{{ censor.dateOfBirth }}</p>
              <p>{{ censor.addressOfUser }}</p>
            </div>
          </div>
          <!-- <p class="-my-10">email@email.com</p> -->
        </div>
      </div>
      <div class="w-1/3 grid place-items-start">
        <warning
          v-if="warn.isWarn"
          :msg="warn.msg"
          @turnAlertOff="turnAlertOff"
        />
        <button
          v-if="isEdit"
          class="
            bg-gray-700
            text-white
            font-bold
            py-2
            rounded
            hover:bg-gray-600
            focus:bg-gray-500
            justify-start
            mt-2
            px-16
          "
          type="button"
          @click="updateForum"
        >
          Update
        </button>
        <button
          v-else
          class="
            bg-gray-700
            text-white
            font-bold
            py-2
            px-4
            w-auto
            rounded
            hover:bg-gray-600
            focus:bg-gray-500
            justify-start
            mt-2
          "
          type="button"
          @click="onSubmit"
        >
          Create New Forum
        </button>
      </div>
    </form>
  </div>
</template>
<script>
import { getfreeCensor } from "../../logic/api";
import { CreateForum } from "../../logic/ForumAPI";
import { getSingleForum, updateForum } from "../../logic/ForumAPI";
import {
  getStorage,
  ref,
  uploadBytesResumable,
  getDownloadURL,
} from "firebase/storage";
import warning from "../smallmodal/Warn.vue";
export default {
  components: {
    warning,
  },
  data() {
    return {
      FormData: {
        forumId: "",
        censerId: "",
        censerName: "",
        nameForum: "",
        description: "",
        coverImage: "",
      },
      isDisabled: "true",
      Censors: {},
      imageData: null,
      pictureURL: null,
      uploadValue: 0,
      censor: null,
      warn: {
        isWarn: false,
        msg: "none",
      },
      isEdit: false,
    };
  },
  props: {
    id: String,
  },
  async created() {
    if (this.id == null) {
      this.Censors = await getfreeCensor();
      console.log(this.Censors);
    } else {
      this.isEdit = true;
      const a = await getSingleForum(this.id);
      if (a == "Error: Request failed with status code 500") {
        this.$router.push({ name: "NotFound" });
      } else {
        this.FormData.nameForum = a.nameForum;
        this.FormData.description = a.description;
        this.FormData.coverImage = a.coverImage;
        this.FormData.forumId = a.forumId;
        this.FormData.censerId = a.censerId;
        this.FormData.censerName = a.censerName;
        console.log(this.FormData);
        this.pictureURL = a.coverImage;
        this.Censors.push = { userId: a.censerId, fullName: a.censerName };
      }
    }
  },
  methods: {
    async getCensor() {
      this.Censors = await getfreeCensor();
    },
    setCensor(censor) {
      this.FormData.censerId = censor.userId;
      this.FormData.censerName = censor.fullName;
      this.censor = censor;
      // console.log(this.censor);
    },
    previewImage(event) {
      this.uploadValue = 0;
      this.pictureURL = null;
      this.imageData = event.target.files[0];
      // console.log(event.target.files[0]);
      this.onUpload();
    },

    onUpload() {
      if (this.FormData.nameForum !== "") {
        const metadata = {
          contentType: "image/jpeg",
        };

        const storage = getStorage();
        const storageRef = ref(
          storage,
          `/cover_image_forum/${this.FormData.nameForum}/coverimg`
        );
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
              this.pictureURL = downloadURL;
              this.FormData.coverImage = downloadURL;
            });
          }
        );
      } else {
        this.turnAlertOff("Please fill the forum name first");
      }
    },
    turnAlertOff(msg) {
      this.warn.isWarn = !this.warn.isWarn;
      this.warn.msg = msg;
    },
    async onSubmit() {
      const msg = await CreateForum(this.FormData);
      if (msg.message == "create success") {
        this.$root.addnoti({
          group: "top",
          title: "Create success",
        });
        this.$router.push({ path: "/forumManagement" });
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: "Error",
          text: msg.message,
        });
      }
    },
    async updateForum() {
      const msg = await updateForum(this.FormData);
      if (msg.message == "update success!!!") {
        this.$root.addnoti({
          group: "top",
          title: "Update success",
          text: msg.message,
        });
        this.$router.push({ path: "/forumManagement" });
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: "Error",
          text: msg.message,
        });
      }
    },
  },
};
</script>

<style scoped>
.form {
  width: 8cm;
  height: 10mm;
  border-radius: 0.25rem;
  border-bottom-right-radius: 0.25rem;
  border-bottom-left-radius: 0.25rem;
  border-block-color: rga();
  padding-left: 2mm;
  margin-right: 10mm;
  margin-top: 2mm;
}
select > option {
  color: black;
}
select > option:hover {
  color: white;
}
#progress {
  height: 1mm;
}
#fileupload {
  display: none;
}
img {
  object-fit: cover;
}
</style>
