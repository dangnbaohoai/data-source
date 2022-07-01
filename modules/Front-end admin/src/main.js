import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import Notifications from "notiwind";
import "./index.css";
import axios from "axios";
import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";
axios.defaults.headers.get["header-name"] =
  "https://school-connection.herokuapp.com";

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

// Get a reference to the storage service, which is used to create references in your storage bucket
const storage = getStorage(firebaseApp);
export { storage };
createApp(App).use(store).use(router).use(Notifications).mount("#app");
