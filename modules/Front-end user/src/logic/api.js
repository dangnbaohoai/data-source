import axios from "axios";
import { getcookie } from "./getcookie";
// const base = "https://school-connection.herokuapp.com";
const headers = {
  Authorization: "Bearer " + getcookie("token"),
};
async function login(username, password) {
  const url = "https://school-connection.herokuapp.com/api/auth/signin";
  try {
    const res = await axios
      .post(url, { username: username, password: password })
      .then((res) => res.data);
    document.cookie = "username=" + res.username + ";max-age=86400; path=/;";
    document.cookie = "token=" + res.token + ";max-age=86400; path=/;";
    document.cookie = "userID=" + res.userId + ";max-age=86400; path=/;";
    return { message: "Login success", status: 1 };
  } catch (err) {
    console.log("err");
    console.log(err);
    return { message: err, status: 0 };
  }
}

async function getuser() {
  const url = "/api/user/seeProfile";
  const token = getcookie("token");
  let headers = {
    Authorization: "Bearer " + token,
  };

  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}

async function updateUser(data) {
  const url = "/api/user/updateProfile";

  try {
    const res = await axios.put(url, data, { headers }).then((res) => res);
    console.log(res.status);
    return res.status;
  } catch (err) {
    console.log("Lỗi");
    console.log(err);
    return err.Error;
  }
}
async function changepassword(data) {
  const url = "/api/user/change-password";
  try {
    const res = await axios.put(url, data, { headers }).then((res) => res);
    console.log(res.status);
    return res;
  } catch (err) {
    console.log("Lỗi");
    console.log(err);
    return err;
  }
}

async function resetaccount(data) {
  const url = "/api/auth/forgetPassword";
  try {
    const res = await axios.post(url, data).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function acc() {
  let url = "/api/admin/account/view?page=" + 1 + "&sortBy=" + "userid";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    console.log(res);
    return res.status;
  } catch (err) {
    return err;
  }
}
async function search(key) {
  let url =
    "/api/user/search/" + key + "?page=" + 1 + "&sortBy=" + "createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function home() {
  let url = "/api/user/hone/viewHone?page=" + 1 + "&sortBy=" + "createDate";
  try {
    const res = await axios
      .get(url, { headers })
      .then((res) => res)
      .catch((error) => error);
    return res;
  } catch (err) {
    return err;
  }
}
async function homePost(page) {
  let url = "/api/user/hone/viewHonePost?page=" + page + "&sortBy=createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function autoNotify() {
  let url = "/api/user/notify/ListNotifyAuto";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function getNotify(page) {
  let url = "/api/user/notify/ListNotify?page=" + page + "&sortBy=CreateDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}

async function readNotify(page) {
  let url = "/api/user/notify/readNotify/" + page;
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function forumpost(frmID, page) {
  let url =
    "/api/user/forum/viewPostInForum/" +
    frmID +
    "?page=" +
    page +
    "&sortBy=" +
    "createDate";
  try {
    const res = await axios
      .get(url, { headers })
      .then((res) => res)
      .catch((error) => error);
    return res;
  } catch (err) {
    return err;
  }
}
export {
  login,
  getuser,
  updateUser,
  resetaccount,
  acc,
  search,
  home,
  homePost,
  autoNotify,
  getNotify,
  readNotify,
  changepassword,
  forumpost,
};
