<template>
  <div
    class="
      min-w-screen
      h-screen
      bg-gray-50
      flex
      items-center
      justify-center
      px-5
      py-5
    "
  >
    <div class="grid">
      <h1 class="text-2xl pb-2 font-bold">Change password</h1>
      <div
        class="w-full mx-auto rounded-lg bg-white p-5 text-gray-800 shadow-2xl"
        style="max-width: 500px"
      >
        <div class="relative mb-2">
          <input
            :type="ispassword ? 'password' : 'text'"
            id="password"
            v-model="oldPass"
            class="
              w-full
              pl-3
              pr-10
              py-2
              border-2 border-gray-200
              rounded-md
              focus:outline-none focus:border-green-500
              transition-colors
              my-2
            "
            placeholder="Current Password"
          />

          <input
            :type="ispassword ? 'password' : 'text'"
            id="password"
            class="
              w-full
              pl-3
              pr-10
              py-2
              border-2 border-gray-200
              rounded-md
              focus:outline-none
              transition-colors
              my-2
            "
            v-model="newPass"
            placeholder="New Password"
            @input="checkStrength()"
          />
          <password-meter :password="newPass" @score="onScore" />
          <strong>{{ score.strength }}</strong>
          <input
            :type="ispassword ? 'password' : 'text'"
            id="password"
            v-model="confirm"
            class="
              w-full
              pl-3
              pr-10
              py-2
              border-2 border-gray-200
              rounded-md
              focus:outline-none
              transition-colors
              my-2
            "
            :class="[
              isPair
                ? 'focus:border-green-500'
                : 'focus:border-red-500 border-red-500',
            ]"
            placeholder="Confirm password"
          />
          <strong>{{ msg }}</strong>
          <button
            class="w-full py-2 bg-green-500 rounded-lg opacity-90"
            @click="changePass"
          >
            <strong> {{ button }}</strong>
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import PasswordMeter from "vue-simple-password-meter";
import { changepassword } from "../../logic/api";
export default {
  data() {
    return {
      ispassword: true,
      isPair: true,
      oldPass: "",
      newPass: "",
      confirm: "",
      msg: "",
      score: {
        strength: "",
        score: 0,
      },
      button: "Change Password",
    };
  },
  components: {
    PasswordMeter,
  },
  watch: {
    confirm() {
      if (this.confirm == this.newPass) {
        this.isPair = true;
      } else {
        this.isPair = false;
      }
    },
  },
  methods: {
    async changePass() {
      if (this.score.score < 3) {
        this.isPair = false;
        this.msg = "Your password is not safe.";
      } else {
        if (this.confirm == this.newPass) {
          this.isPair = true;
        } else {
          this.isPair = false;
          this.msg = "Two value passwords doesn't match";
        }
      }
      if (this.isPair) {
        const res = await changepassword({
          currentPassword: this.oldPass,
          newPassword: this.confirm,
        });
        console.log(res);
        if (res.data.message == "yes") {
          this.button = "Password changed";
        }
      }
    },
    onScore(payload) {
      console.log(payload.score); // from 0 to 4
      console.log(payload.strength); // one of : 'risky', 'guessable', 'weak', 'safe' , 'secure'
      this.score = payload;
    },
    showpass() {
      this.ispassword = !this.ispassword;
    },
  },
};
</script>

<style></style>
