<template>
  <div class="rid-rows-2 grid-flow-col gap-4">
    <h1 class="text-left text-white py-2 text-3xl">
      {{ FormData.userId ? "Edit user information" : "Add new user" }}
    </h1>
    <form @submit.prevent="onNewUser">
      <table class="text-xl">
        <tr class="">
          <td>
            <p class="text-left text-white">Full name:</p>
          </td>
          <td>
            <input
              type="text"
              name=""
              class="form"
              v-model="FormData.fullName"
              required
            />
          </td>
          <td>
            <p class="text-left text-white">User name:</p>
          </td>
          <td>
            <input
              type="text"
              name=""
              class="form"
              v-model="FormData.userName"
              :disabled="this.FormData.userId"
              required
            />
          </td>
        </tr>
        <tr class="">
          <td>
            <p class="text-left text-white">Number phone:</p>
          </td>
          <td>
            <input
              type="text"
              name=""
              class="form"
              v-model="FormData.numberPhone"
            />
          </td>
          <td>
            <p class="text-left text-white">Student ID:</p>
          </td>
          <td>
            <input
              type="number"
              name=""
              class="form"
              v-model="FormData.code"
              maxlength="4"
              size="4"
              required
            />
          </td>
        </tr>
        <tr class="">
          <td>
            <p class="text-left text-white">Day of birth:</p>
          </td>
          <td>
            <input
              type="date"
              name=""
              class="form font-sans"
              lang="en-us"
              v-model="FormData.dateOfBirth"
              required
            />
          </td>
          <td>
            <p class="text-left text-white">Address:</p>
          </td>
          <td>
            <input
              type="text"
              name=""
              class="form"
              v-model="FormData.addressOfUser"
            />
          </td>
        </tr>
        <tr class="">
          <td>
            <p class="text-left text-white">Position:</p>
          </td>
          <td>
            <select
              name="cars"
              id="cars"
              class="form font-sans"
              v-model="FormData.position"
              required
            >
              <option value="Student">Student</option>
              <option value="Teacher">Teacher</option>
            </select>
          </td>
          <td>
            <p class="text-left text-white">Email:</p>
          </td>
          <td>
            <input type="text" name="" class="form" v-model="FormData.email" />
          </td>
        </tr>
        <tr>
          <td>
            <p class="text-left text-white">Gender:</p>
          </td>
          <td class="flex">
            <div
              class="m-2 px-3 py-1 text-white rounded-lg"
              :style="{
                backgroundColor: FormData.gender.includes('nam')
                  ? ' #1F2937'
                  : '',
              }"
            >
              <input
                type="radio"
                name="gender"
                value="nam"
                v-model="FormData.gender"
                required
              />
              Male
            </div>
            <div
              class="my-2 px-3 py-1 text-white rounded-lg"
              :style="{
                backgroundColor: FormData.gender.includes('nu')
                  ? ' #1F2937'
                  : '',
              }"
            >
              <input
                type="radio"
                name="gender"
                value="nu"
                v-model="FormData.gender"
                required
              />
              Female<br />
            </div>
          </td>
          <td>
            <p class="text-left text-white">Roles:</p>
          </td>
          <td>
            <div class="flex text-base">
              <button
                class="mr-2 px-4 py-0.5 rounded-lg text-white text-opacity-100"
                v-for="tab in setroles"
                :key="tab"
                :class="[
                  FormData.roles.includes(tab) ? 'bg-green-400' : 'bg-gray-700',
                ]"
                @click="addRoles(tab)"
                type="button"
              >
                {{ tab }}
              </button>
            </div>
          </td>
        </tr>
        <tr>
          <td>
            <button
              class="
                bg-gray-700
                text-white
                font-bold
                py-2
                px-4
                w-40
                rounded
                hover:bg-gray-600
              "
              v-if="FormData.userId"
              type="submit"
            >
              Edit user
            </button>
            <button
              class="
                bg-gray-700
                text-white
                font-bold
                py-2
                px-4
                w-40
                rounded
                hover:bg-gray-600
              "
              v-else
              type="submit"
            >
              New user
            </button>
          </td>
          <td>
            <!-- <button
              class="
                bg-gray-700
                text-white
                font-bold
                py-2
                px-4
                rounded
                hover:bg-gray-600
                flex
                mx-5
              "
              v-if="!FormData.userId"
              type="submit"
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
              Import by Excel
            </button> -->
          </td>
        </tr>
      </table>
    </form>
  </div>
</template>

<script>
import { signUp, updateUser } from "@/logic/api";
export default {
  props: {
    data: Object,
  },
  mounted() {
    if (this.data != null) {
      console.log(this.data);
      this.FormData = {
        code: this.data.code,
        userName: this.data.userName,
        fullName: this.data.fullName,
        email: this.data.email,
        dateOfBirth: this.data.dateOfBirth,
        gender: this.data.gender,
        addressOfUser: this.data.addressOfUser,
        numberPhone: this.data.numberPhone,
        position: this.data.position,
        roles: this.data.roles,
        avatar: this.data.avatar,
        userId: this.data.userId,
      };
    }
  },
  data() {
    return {
      FormData: {
        code: "",
        userName: "",
        fullName: "",
        email: "",
        dateOfBirth: "",
        gender: "nam",
        addressOfUser: "",
        numberPhone: "",
        position: "",
        roles: ["CENSOR"],
        avatar:
          "https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/avatar_mac_dinh.png?alt=media&token=cffd2f64-f96a-453c-848e-a7a6d042ea85",
      },
      setroles: ["CENSOR", "USER", "ADMIN"],
    };
  },
  methods: {
    async onNewUser() {
      console.log(this.FormData);
      let res = null;
      if (this.FormData.userId) {
        res = await updateUser(this.FormData);
      } else {
        res = await signUp(this.FormData);
      }

      if (res.message == "update success!!! email: sent success") {
        this.$root.addnoti({
          group: "top",
          title: "successfull",
          text: res.message,
        });
        this.FormData = {
          code: "",
          userName: "",
          fullName: "",
          email: "",
          dateOfBirth: "",
          gender: "nam",
          addressOfUser: "",
          numberPhone: "",
          position: "",
          roles: ["CENSOR"],
          avatar:
            "https://firebasestorage.googleapis.com/v0/b/school-conection.appspot.com/o/avatar_mac_dinh.png?alt=media&token=cffd2f64-f96a-453c-848e-a7a6d042ea85",
        };
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: "Failure",
          text: res.message,
        });
      }
    },
    addRoles(tab) {
      if (!this.FormData.roles.includes(tab)) {
        this.FormData.roles.push(tab);
        console.log(this.FormData.roles);
      } else {
        this.FormData.roles.splice(location(tab, this.FormData.roles), 1);
        console.log(this.FormData.roles);
      }
    },
  },
};
function location(a, b) {
  for (let i = 0; i < a.length; i++) {
    if (b[i] == a) return i;
  }
}
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
</style>
