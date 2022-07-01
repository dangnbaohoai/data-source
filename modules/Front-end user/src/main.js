import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./index.css";
import axios from "axios";
import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";
import PrimeVue from "primevue/config";
import Dialog from "primevue/dialog";
import Notifications from "notiwind";
axios.defaults.headers.get["header-name"] =
  "https://school-connection.herokuapp.com";
import Vuex from "vuex";
import ConfirmationService from "primevue/confirmationservice";

const firebaseConfig = {
  apiKey: "AIzaSyCAM_WNaO_xks997GCsWmtAcXRHruB4s2E",
  authDomain: "school-conection.firebaseapp.com",
  projectId: "school-conection",
  storageBucket: "school-conection.appspot.com",
  messagingSenderId: "726089544078",
  appId: "1:726089544078:web:184228ab794b86d45934df",
  measurementId: "G-8PMCP8L8XL",
};
const firebaseApp = initializeApp(firebaseConfig);
const storage = getStorage(firebaseApp);
console.log("Storge :");
export { storage };

createApp(App)
  .use(Vuex)
  .use(store)
  .use(router)
  .use(PrimeVue)
  .component("Dialog", Dialog)
  .use(Notifications)
  .use(ConfirmationService)
  .mount("#app");
