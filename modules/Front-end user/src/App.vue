<template>
  <Login v-if="isNLogin" />

  <div v-else class="grid">
    <Header />
    <div class="bg-gray-100">
      <router-view />
      <div class="footer text-xs font-bold"></div>
      <Notify ref="noti" />
    </div>
  </div>
</template>

<script>
import Login from "@/views/Login.vue";
import Header from "@/components/headerbar.vue";
import Notify from "./views/notify.vue";
// import { notify } from "notiwind";
export default {
  components: {
    Header,
    Login,
    Notify,
  },
  props: {
    isNotLogin: Boolean,
  },
  setup(props) {
    console.log(props);
    var isNLogin;
    let a = getCookie("username");

    if (a === "") {
      isNLogin = true;
    } else isNLogin = false;
    console.log("self.isNotLogin 2=" + isNLogin);
    return { isNLogin };
  },
  methods: {
    test(data) {
      this.$refs.noti.addnoti(data);
    },
    addnoti(data) {
      this.$refs.noti.addnoti(data);
    },
  },
};
function getCookie(cname) {
  let name = cname + "=";
  let decodedCookie = decodeURIComponent(document.cookie);
  let ca = decodedCookie.split(";");
  for (let i = 0; i < ca.length; i++) {
    let c = ca[i];
    while (c.charAt(0) == " ") {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return "";
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  background-color: #f5f3f4;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
/* width */
::-webkit-scrollbar {
  width: 16px;
  height: 16px;
}

/* Track */
::-webkit-scrollbar-track {
  border-radius: 100vh;
  background: rgba(20, 16, 16, 0);
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: #cbd5e0;
  /* border-radius: 100vh; */
  border: 3px solid #edf2f7;
}

/* Handle on hover */
::-webkit-scrollbar-thumb:hover {
  background: #a0aec0;
}
.footer {
  position: fixed;
  bottom: 5px;
  left: 0;
  width: fit-content;
}
</style>
