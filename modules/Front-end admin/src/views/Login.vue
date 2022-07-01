<template>
  <div class="h-auto">
    <div class="bg-stock2 h-screen pt-40">
      <div
        class="
          flex
          bg-white
          rounded-lg
          shadow-lg
          overflow-hidden
          mx-auto
          max-w-sm
          lg:max-w-4xl
        "
      >
        <div
          class="hidden lg:block lg:w-1/2 bg-sclogo bg-cover bg-clip-padding"
        ></div>
        <!-- Left / right -->
        <div class="relative w-full p-8 lg:w-1/2">
          <div v-if="!isforgot">
            <h2 class="text-2xl font-semibold text-gray-700 text-center">
              {{ UserName }}
            </h2>
            <p class="text-xl text-gray-600 text-center">Welcome back! Boss</p>
            <div class="h-10"></div>
            <div class="mt-4 flex items-center justify-between">
              <span class="border-b w-1/5 lg:w-1/4"></span>
              <a
                href="#"
                class="text-xs text-center text-gray-500 uppercase"
              ></a>
              <span class="border-b w-1/5 lg:w-1/4"></span>
            </div>
            <form @submit.prevent="login">
              <div class="mt-4">
                <label
                  class="text-left block text-gray-700 text-sm font-bold mb-2"
                  >Username:</label
                >
                <input
                  v-model="username"
                  class="
                    bg-gray-200
                    text-gray-700
                    focus:outline-none focus:shadow-outline
                    rounded
                    py-2
                    px-4
                    block
                    w-full
                    appearance-none
                    border-b-2
                    focus:border-blue-400
                  "
                  type="username"
                  ref="input1"
                  required
                />
              </div>
              <div class="mt-4">
                <div class="flex justify-between">
                  <label class="block text-gray-700 text-sm font-bold mb-2"
                    >Password:</label
                  >
                  <h1
                    @click="imforgot"
                    href="#"
                    class="text-xs text-gray-500 cursor-pointer hover:underline"
                  >
                    Forget Password?
                  </h1>
                </div>
                <div class="passwordzone flex">
                  <input
                    v-model="password"
                    class="
                      bg-gray-200
                      text-gray-700
                      focus:outline-none focus:shadow-outline
                      border-b-2
                      rounded-l
                      py-2
                      px-4
                      block
                      w-full
                      appearance-none
                      focus:border-blue-400
                      flex-1
                    "
                    @keyup.enter="login"
                    :type="ispassword"
                    ref="input2"
                    required
                    :focus="true"
                  />
                  <!-- Show pass -->
                  <button
                    class="
                      bg-gray-200
                      rounded-r
                      inline-block
                      align-baseline
                      hover:bg-gray-300
                      cursor-pointer
                    "
                    @click="showpass"
                    type="button"
                  >
                    <svg
                      class="h-5 w-5 text-gray-700 mx-2"
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
                      <circle cx="12" cy="12" r="3" />
                      <line x1="12" y1="21" x2="12" y2="21.01" />
                      <line x1="3" y1="9" x2="3" y2="9.01" />
                      <line x1="21" y1="9" x2="21" y2="9.01" />
                      <path d="M8 20.1a9 9 0 0 1 -5 -7.1" />
                      <path d="M16 20.1a9 9 0 0 0 5 -7.1" />
                      <path d="M6.2 5a9 9 0 0 1 11.4 0" />
                    </svg>
                  </button>
                </div>
              </div>
              <div class="mt-4">
                <button
                  class="
                    bg-gray-700
                    text-white
                    font-bold
                    py-2
                    px-4
                    w-full
                    rounded
                    hover:bg-gray-600
                  "
                  type="submit"
                >
                  Login
                </button>
              </div>
            </form>
            <div class="h-12" v-if="!isAlert"></div>
            <div class="warn flex align-middle mt-4" v-if="isAlert">
              <svg
                class="h-7 w-7 text-red-700"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              >
                <polygon
                  points="7.86 2 16.14 2 22 7.86 22 16.14 16.14 22 7.86 22 2 16.14 2 7.86 7.86 2"
                />
                <line x1="12" y1="8" x2="12" y2="12" />
                <line x1="12" y1="16" x2="12.01" y2="16" />
              </svg>
              <div class="bg-red-700 flex-1 text-left pt-0.5">
                <span class="text-white pl-2">{{ errors }}</span>
              </div>
              <svg
                class="
                  h-7
                  w-7
                  text-white
                  bg-red-700
                  hover:bg-red-50 hover:text-red-700
                "
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
                @click="Alert"
              >
                <rect x="3" y="3" width="18" height="18" rx="2" ry="2" />
                <line x1="9" y1="9" x2="15" y2="15" />
                <line x1="15" y1="9" x2="9" y2="15" />
              </svg>
            </div>
          </div>

          <forgotpassword
            v-if="isforgot"
            @notforgotanymore="imforgot"
            @loading="loading"
          />
          <div
            class="loading absolute bottom-0 left-0 w-full h-full"
            v-if="isloading"
          >
            <div class="mt-32 lds-ellipsis">
              <div></div>
              <div></div>
              <div></div>
              <div></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { login } from "../logic/api.js";
import forgotpassword from "@/components/login/forgotPassword.vue";
export default {
  components: {
    forgotpassword,
  },
  data() {
    return {
      success: [],
      errors: "",
      username: "",
      password: "",
      ispassword: "password",
      isloading: false,
      isforgot: false,
      isAlert: false,
    };
  },
  methods: {
    async login() {
      if (this.username === "" || this.password === "") {
        this.errors = "Please fill all the field";
        this.Alert();
        this.$refs.input1.$el.focus();
        this.$refs.input2.$el.focus();
      } else {
        this.loading();
        const res = await login(this.username, this.password);
        console.log(typeof res);
        if (res.status == 0) {
          this.loading();
          this.Alert();
          this.errors = "Wrong username of password";
        } else {
          this.errors = res.message;

          this.$router.go();
        }
      }
    },
    autologin() {
      this.username = "hieunguyen";
      this.password = "O4P600NW";
      this.login();
    },
    imforgot() {
      this.isforgot = !this.isforgot;
    },
    Alert() {
      this.isAlert = !this.isAlert;
    },
    loading() {
      this.isloading = !this.isloading;
    },
    showpass() {
      if (this.ispassword == "password") {
        this.ispassword = "text";
      } else {
        this.ispassword = "password";
      }
    },
  },
  // lấy dữ liệu khi component được tạo thành công
};
</script>

<style>
.loading {
  background-color: rgba(253, 253, 253, 0.876);
}
.lds-ellipsis {
  display: inline-block;
  position: relative;
  width: 80px;
  height: 80px;
}
.lds-ellipsis div {
  position: absolute;
  top: 33px;
  width: 13px;
  height: 13px;
  border-radius: 50%;
  background: rgb(75, 85, 99);
  animation-timing-function: cubic-bezier(0, 1, 1, 0);
}
.lds-ellipsis div:nth-child(1) {
  left: 8px;
  animation: lds-ellipsis1 0.6s infinite;
}
.lds-ellipsis div:nth-child(2) {
  left: 8px;
  animation: lds-ellipsis2 0.6s infinite;
}
.lds-ellipsis div:nth-child(3) {
  left: 32px;
  animation: lds-ellipsis2 0.6s infinite;
}
.lds-ellipsis div:nth-child(4) {
  left: 56px;
  animation: lds-ellipsis3 0.6s infinite;
}
@keyframes lds-ellipsis1 {
  0% {
    transform: scale(0);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes lds-ellipsis3 {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(0);
  }
}
@keyframes lds-ellipsis2 {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(24px, 0);
  }
}
</style>
