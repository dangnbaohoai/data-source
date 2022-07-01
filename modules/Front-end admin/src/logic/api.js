import axios from "axios";
import { getcookie } from "./getcookie";
const base = "https://school-connection.herokuapp.com";
let headers = {
  Authorization: "Bearer " + getcookie("token"),
  "header-name": "https://school-connection.herokuapp.com",
};
async function login(username, password) {
  const url = base + "/api/auth/signinAdmin";

  try {
    const res = await axios
      .post(url, { username: username, password: password }, { headers })
      .then((res) => res.data);
    if (res.message != "login fall") {
      document.cookie = "username=" + res.username + ";max-age=86400; path=/;";
      document.cookie = "token=" + res.token + ";max-age=86400; path=/;";
      document.cookie = "userID=" + res.userId + ";max-age=86400; path=/;";
    } else {
      return { message: "404", status: 0 };
    }
    return { message: "Wrong username of password", status: 1 };
  } catch (err) {
    console.log(err);
    return { message: err, status: 0 };
  }
}
async function getuser() {
  const url = "/api/user/seeProfile";
  const token = getcookie("token");
  let headers = {
    Authorization: "Bearer " + token,
    "header-name": "https://school-connection.herokuapp.com",
  };
  console.log(headers);
  try {
    const res = await axios
      .get(url, { headers }, { baseURL: base })
      .then((res) => res.data);

    return res;
  } catch (err) {
    return err;
  }
}
// --------------------------------------- ERROR: CORS
async function signUp(data) {
  const url = "/api/admin/account/create";
  let headers = {
    Authorization: "Bearer " + getcookie("token"),
  };
  try {
    const res = await axios
      .post(url, data, { headers }, { baseURL: base })
      .then((res) => res.data);
    console.log(res);
    return { message: res.message, status: 0 };
  } catch (err) {
    console.log(err);
    return { message: err, status: 0 };
  }
}

async function getForum() {
  let url = "/api/admin/forum/view?page=" + 1 + "&sortBy=" + "forumId";
  let headers = {
    Authorization: "Bearer " + getcookie("token"),
  };
  console.log(headers);
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}

async function getfreeCensor() {
  let url = "/api/admin/forum/viewCensor";
  let headers = {
    Authorization: "Bearer " + getcookie("token"),
  };
  console.log(headers);
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}

async function updateUser(data) {
  const url = "/api/admin/account/update";
  const token = getcookie("token");
  let headers = {
    Authorization: "Bearer " + token,
  };
  try {
    const res = await axios
      .put(url, data, { headers }, { baseURL: base })
      .then((res) => res.data);
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}

async function resetaccount(data) {
  const url = "/api/auth/forgetPassword";
  try {
    const res = await axios
      .post(url, data, { baseURL: base })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function AdminPost(frmID, page) {
  let url =
    "/api/admin/post/view" + "?page=" + page + "&sortBy=" + "createDate";
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
  getuser,
  login,
  updateUser,
  resetaccount,
  signUp,
  getForum,
  getfreeCensor,
  AdminPost,
};
