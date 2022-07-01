import axios from "axios";
import { getcookie } from "./getcookie";
const base = "https://school-connection.herokuapp.com";
let headers = {
  "content-type": "application/json",
  Authorization: "Bearer " + getcookie("token"),
  "header-name": base,
};
async function CreateForum(data) {
  const url = "/api/admin/forum/createForum";
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
async function getForum(page) {
  let url = "/api/admin/forum/view?page=" + page + "&sortBy=" + "forumId";
  let headers = {
    Authorization: "Bearer " + getcookie("token"),
  };
  console.log(headers);
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    console.log("res");
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}

async function getSingleForum(id) {
  let url = "/api/admin/forum/view/" + id;
  let headers = {
    Authorization: "Bearer " + getcookie("token"),
  };
  console.log(headers);
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function deleteForum(id) {
  let url = "/api/admin/forum/delete/" + id;

  try {
    const res = await axios.delete(url, { headers }).then((res) => res.data);
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}
async function updateForum(data) {
  let url = "/api/admin/forum/update";
  console.log(data);
  try {
    const res = await axios.put(url, data, { headers }).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function getExcelForum() {
  let url = "/api/export/forum";
  let headers = {
    Authorization: "Bearer " + getcookie("token"),
  };
  console.log(headers);
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
function getExcel() {
  axios({
    url: "/api/export/forum",
    headers: { headers },
    method: "GET",
    responseType: "blob", // important
  }).then((response) => {
    const url = window.URL.createObjectURL(new Blob([response.data]));
    const link = document.createElement("a");
    link.href = url;
    // const filename = `file.xlsx`;
    link.setAttribute("download", "ExportExcel.xlsx"); //or any other extension
    document.body.appendChild(link);
    link.click();
  });
}
async function submitFile(import_file) {
  let file = new FormData();
  file.append("file", import_file, "import_file.xlsx");
  console.log("======================> formData");
  console.log(file);
  return await axios
    .post(
      "/api/admin/account/importOrderExcel",
      file,
      {
        headers: {
          "Content-Type":
            "multipart/form-data; boundary=<calculated when request is sent>",
          Authorization: "Bearer " + getcookie("token"),
          "header-name": base,
        },
      },
      { baseURL: base }
    )
    .then((response) => response)
    .catch((error) => {
      // code here when an upload is not valid
      error;
    });
}
export {
  getForum,
  CreateForum,
  getSingleForum,
  deleteForum,
  updateForum,
  getExcelForum,
  getExcel,
  submitFile,
};
