<template>
  <view-profile
    v-if="isview"
    :datas="data"
    :username="username"
    :avatar="avatar"
    @edit="turnEdit"
  />
  <edit-profile
    v-if="!isview"
    :datas="data"
    :username="username"
    :avatar="avatar"
    @cancel="turnEdit"
  />
</template>

<script>
import viewProfile from "@/components/profileComponent/viewProfile.vue";
import editProfile from "@/components/profileComponent/editProfile.vue";
import axios from "axios";
import { getcookie } from "../logic/getcookie";
const base = "https://school-connection.herokuapp.com";
export default {
  name: "Home",
  components: {
    viewProfile,
    editProfile,
  },
  data() {
    return {
      username: "",
      avatar: "",
      data: {},
      isview: true,
    };
  },
  async created() {
    document.title = "Profile";
    await this.onUpdate();
  },
  methods: {
    async turnEdit() {
      console.log(" - > Turn EDIT");
      if (this.isview == false) {
        await this.onUpdate();
      }
      this.isview = !this.isview;
    },
    async onUpdate() {
      const res = await getuser();
      localStorage.setItem("avartar", res.avatar);
      localStorage.setItem("username", res.userName);
      document.title = res.fullName;
      this.data = [
        { field: "User name:", value: res.userName, edit: false },
        { field: "ID", value: res.code, edit: false },
        {
          field: "Full name",
          value: res.fullName,
          edit: false,
        },
        {
          field: "🎂 Day of Birth",
          value: res.dateOfBirth,
          edit: true,
        },
        {
          field: "📧 Email",
          value: res.email,
          edit: true,
        },
        {
          field: "♂️ Gender",
          value: res.gender,
          edit: true,
        },
        { field: "📅 Join Date", value: res.createDate, edit: false },
        { field: "🏠 Address", value: res.addressOfUser, edit: true },
        { field: "📞 Phone number", value: res.numberPhone, edit: true },
        { field: "💼 Position", value: res.position, edit: false },
      ];
      this.avatar = res.avatar;
      this.username = res.userName + " - " + res.userId;
    },
  },
};

async function getuser() {
  const url = "/api/user/seeProfile";
  const token = getcookie("token");
  let headers = {
    Authorization: "Bearer " + token,
  };

  try {
    const res = await axios
      .get(url, { headers }, { baseURL: base })
      .then((res) => res.data);
    console.log("res:");
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}
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
.username {
  font-size: 10mm;
  font-family: "Gill Bahnschrift", "Gill Sans MT", Calibri, "Trebuchet MS",
    sans-serif;
}
</style>
