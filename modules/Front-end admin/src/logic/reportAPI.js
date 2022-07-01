import axios from "axios";
import { getcookie } from "./getcookie";
const base = "https://school-connection.herokuapp.com";
let headers = {
  "content-type": "application/json",
  Authorization: "Bearer " + getcookie("token"),
  "header-name": base,
};
async function getReport(page) {
  let url =
    "api/admin/reportRoom/viewReport?page=" + page + "&sortBy=createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    console.log("res");
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}
async function cancelRoomReport(id) {
  const url = "/api/admin/reportRoom/cancelRoomReport/" + id;
  const data = null;
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
async function blockRoom(id) {
  const url = "/api/admin/reportRoom/blockRoom/" + id;
  const data = null;
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
async function viewReportBlock(page) {
  let url =
    "/api/admin/Unblock/viewReportBlock?page=" + page + "&sortBy=createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    console.log("res");
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}
async function unblock(id) {
  const url = "/api/admin/Unblock/UnblockRoom/" + id;
  const data = null;
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
export { getReport, cancelRoomReport, blockRoom, viewReportBlock, unblock };
