<template>
  <div class="h-14"></div>

  <censor />
  1111111111111 2222222222222
  <div
    class="
      min-w-screen min-h-screen
      bg-gray-800
      flex
      items-center
      justify-center
      px-5
      py-5
    "
  >
    <div
      class="w-full mx-auto rounded-lg bg-white shadow p-5 text-gray-800"
      style="max-width: 500px"
      x-data="app()"
      x-init="generatePassword()"
    >
      <div class="relative mb-2">
        <input
          :type="showPasswordField ? 'password' : 'text'"
          id="password"
          v-model="currentpassword"
          class="
            w-full
            pl-3
            pr-10
            py-2
            border-2 border-gray-200
            rounded-md
            focus:outline-none focus:border-indigo-500
            transition-colors
            my-2
          "
          placeholder="Current Password"
        />

        <input
          :type="showPasswordField ? 'password' : 'text'"
          id="password"
          class="
            w-full
            pl-3
            pr-10
            py-2
            border-2 border-gray-200
            rounded-md
            focus:outline-none focus:border-indigo-500
            transition-colors
            my-2
          "
          placeholder="New Password"
          @input="checkStrength()"
        />
        <input
          :type="showPasswordField ? 'password' : 'text'"
          id="password"
          x-model="password"
          class="
            w-full
            pl-3
            pr-10
            py-2
            border-2 border-gray-200
            rounded-md
            focus:outline-none focus:border-indigo-500
            transition-colors
            my-2
          "
          placeholder="Confirm password"
        />
      </div>
      <div class="flex -mx-1">
        <template x-for="(v,i) in 5">
          <div class="w-1/5 px-1">
            <div
              class="h-2 rounded-xl transition-colors"
              :class="
                i < passwordScore
                  ? passwordScore <= 2
                    ? 'bg-red-400'
                    : passwordScore <= 4
                    ? 'bg-yellow-400'
                    : 'bg-green-500'
                  : 'bg-gray-200'
              "
            ></div>
          </div>
        </template>
      </div>
    </div>
  </div>
  <luot />
  <button @click="test" class="h-11 bg-red-300">Loadhome</button>

  <div class="parent row overflow-x-auto flex">
    <div class="flex">
      <section class="one"></section>
      <section class="two"></section>
      <section class="three"></section>
      <section class="four"></section>
    </div>
  </div>
</template>
<script>
import censor from "../components/forum/censor/memberCensorship.vue";
import luot from "../components/testLuot.vue";
// import { notify } from "notiwind";
export default {
  data() {
    return {
      showPasswordField: true,
      passwordScore: 5,
      currentpassword: "",
      newpassword: "sd",
      password: "",
      show: false,
      contents: [],
      number: 15,
    };
  },
  setup() {
    // console.log(this.contents);
  },
  components: { censor, luot },
  watch: {
    password: function () {
      // if (!this.password) return (this.passwordScore = 0);
      // this.passwordScore = zxcvbn(this.password).score + 1;
      // console.log(zxcvbn(this.password).score);
    },
  },
  methods: {
    test() {
      let data = {
        group: "top",
        title: "Cảnh báo",
        text: "Nội dung đã xóa",
      };
      this.$root.test(data);
    },
    onScroll({ target: { scrollTop, clientHeight, scrollHeight } }) {
      if (scrollTop + clientHeight >= scrollHeight) {
        console.log("sdsđ");
      }
    },
  },
  mounted() {
    this.scroll();
  },
  scroll() {
    window.onscroll = () => {
      let bottomOfWindow =
        Math.max(
          window.pageYOffset,
          document.documentElement.scrollTop,
          document.body.scrollTop
        ) +
          window.innerHeight ===
        document.documentElement.offsetHeight;

      if (bottomOfWindow) {
        console.log("vvvvvvvvvvvvvvvvvvv");
        const a = 2 + 3;
        console.log(a);
      }
    };
  },
};
</script>
<style scoped>
.parent {
  height: 100px;
  width: 100px;
  scroll-snap-type: mandatory;
  scroll-snap-points-x: repeat(100vh);
  scroll-snap-type: x mandatory;
}

section {
  height: 100px;
  width: 100px;
  scroll-snap-align: start;
  position: relative;
}

.one {
  background-color: red;
}

.two {
  background-color: blue;
}

.three {
  background-color: grey;
}

.four {
  background-color: green;
}
</style>
