import axios from "axios";
import { getcookie } from "./getcookie";
// const base = "https://school-connection.herokuapp.com";
const headers = {
  Authorization: "Bearer " + getcookie("token"),
};
async function viewRooms(page) {
  let url = "/api/user/roomChat/viewRoom?page=" + page + "&sortBy=modifiedDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function createRoomChat(data) {
  const url = "/api/user/roomChat/creteRoom";
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
//----------------------------[Chat room]
async function viewMessagesInRoom(roomid, page) {
  let url =
    "/api/user/chatMessage/viewMessagesInRoom/" +
    roomid +
    "?page=" +
    page +
    "&sortBy=CreateDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res.data;
  } catch (err) {
    return err;
  }
}
async function autoLoad(id, lastmsg) {
  let url =
    "/api/user/chatMessage/autoLoadMessage/" + id + "?lastMessage=" + lastmsg;
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function deleteMessage(idmsg) {
  let url = "/api/user/chatMessage/deleteMessage/" + idmsg;
  return await axios
    .delete(url, { headers })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      return err;
    });
}
async function sendMSGwAPI(data) {
  const url = "/api/user/chatMessage/sendMessageNoWebsocket";
  return await axios
    .post(url, data, { headers })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      return err;
    });
}
async function searchRoom(username, page) {
  let url =
    "/api/user/roomChat/Search/" +
    username +
    "?page=" +
    page +
    "&sortBy=modifiedDate";
  return await axios
    .get(url, { headers })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      return err;
    });
}
async function reportRoomChat(data) {
  const url = "/api/user/report/reportRoomChat";
  return await axios
    .post(url, data, { headers })
    .then((res) => {
      return res;
    })
    .catch((err) => {
      return err;
    });
}
export {
  viewRooms,
  createRoomChat,
  viewMessagesInRoom,
  autoLoad,
  deleteMessage,
  sendMSGwAPI,
  searchRoom,
  reportRoomChat,
};
