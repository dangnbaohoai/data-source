import axios from "axios";
import { getcookie } from "./getcookie";
const base = "https://school-connection.herokuapp.com";
let headers = {
  "content-type": "application/json",
  Authorization: "Bearer " + getcookie("token"),
  "header-name": base,
};
async function AdminCreate(data) {
  const url = "/api/admin/post/create";
  try {
    const res = await axios
      .post(url, data, { headers }, { baseURL: base })
      .then((res) => res.data);
    console.log(res);
    return { message: res.message, status: 0 };
  } catch (err) {
    console.log(err);
    return { message: err.message, status: 0 };
  }
}

export { AdminCreate };
