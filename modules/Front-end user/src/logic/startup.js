import { getuser } from "../logic/api.js";
// import Vuex from "vuex";
async function header() {
  let res = {};
  if (
    localStorage.getItem("avartar") == null &&
    localStorage.getItem("username") == null &&
    localStorage.getItem("userId") == null
  ) {
    console.log("--- basic data from API ");
    res = await getuser();
    localStorage.setItem("avartar", res.avatar);
    localStorage.setItem("username", res.userName);
    localStorage.setItem("userId", res.userId);
  } else {
    console.log("--- basic data from LOCAL");
    res = {
      avatar: localStorage.getItem("avartar"),
      userName: localStorage.getItem("username"),
      userId: localStorage.getItem("userId"),
    };
  }
  return { avatar: res.avatar, username: res.userName, userId: res.userId };
}
//noti
//message
export { header };
