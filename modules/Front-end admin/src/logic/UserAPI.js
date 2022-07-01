import axios from "axios";
import { getcookie } from "./getcookie";
const base = "https://school-connection.herokuapp.com";
let headers = {
  Authorization: "Bearer " + getcookie("token"),
};
async function getacc(page) {
  let url = "/api/admin/account/view?page=" + page + "&sortBy=" + "userid";
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}
async function deleteacc(id) {
  let url = "/api/admin/account/delete/" + id;

  try {
    const res = await axios.delete(url, { headers }).then((res) => res.data);
    console.log(res);
    return res;
  } catch (err) {
    return err;
  }
}
function getExcel() {
  axios({
    url: "/api/export/account",
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
export { getacc, deleteacc, getExcel, submitFile };
