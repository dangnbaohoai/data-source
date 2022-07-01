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
import { getuser } from "../logic/api.js";
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
    const res = await getuser();
    console.log("GET complete");
    this.data = [
      { field: "Tên đăng nhập", value: res.userName, edit: false },
      { field: "MSSV", value: res.maSo, edit: false },
      {
        field: "Họ và tên",
        value: res.fullName,
        edit: false,
      },
      {
        field: "🎂 Ngày sinh",
        value: res.dateOfBirth,
        edit: true,
      },
      {
        field: "📧 Email",
        value: res.email,
        edit: true,
      },
      {
        field: "♂️ Giới tính",
        value: res.gender,
        edit: true,
      },
      { field: "📅 Ngày tham gia", value: res.createDate, edit: false },
      { field: "🏠 Địa chỉ", value: res.addressOfUser, edit: true },
      { field: "📞 Số điện thoại", value: res.numberPhone, edit: true },
      { field: "💼 Vai trò", value: res.position, edit: false },
    ];
    this.avatar = res.avatar;
    this.username = res.userName + " - " + res.userId;
  },
  methods: {
    turnEdit() {
      console.log(" - > Turn EDIT");
      this.isview = !this.isview;
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
.username {
  font-size: 10mm;
  font-family: "Gill Bahnschrift", "Gill Sans MT", Calibri, "Trebuchet MS",
    sans-serif;
}
</style>
