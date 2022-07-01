import axios from "axios";
import { getcookie } from "./getcookie";
let headers = {
  Authorization: "Bearer " + getcookie("token"),
};
async function Post(data) {
  const url = "/api/user/post/createPost";
  data.userId = parseInt(getcookie("userID"));
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}

async function deletePost(forumId, postId) {
  const url =
    "/api/user/post/deletePost?forumId=" + forumId + "&postId=" + postId;
  let headers = {
    Authorization: "Bearer " + getcookie("token"),
  };
  let data = null;
  try {
    const res = await axios.put(url, data, { headers }).then((res) => res);
    return res.data.message;
  } catch (err) {
    console.error("Lỗi");
    console.log(err);
    return err.Error;
  }
}
async function updatePost(data) {
  const url = "/api/user/post/updatePost";
  try {
    const res = await axios.put(url, data, { headers }).then((res) => res);
    console.log(res.status);
    return res.data;
  } catch (err) {
    console.log(err);
    return err.Error;
  }
}
async function detailPost(forumId, postId) {
  const url =
    "/api/user/comment/viewPostDetail/" +
    postId +
    "?forum=" +
    forumId +
    "&page=1&sortBy=CreateDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    console.log(err);
    return err;
  }
}
async function ReportPost(data) {
  const url = "/api/user/report/reportPost";
  data.userNameReport = getcookie("username");
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function ReportCMT(data) {
  const url = "/api/user/report/reportComment";
  data.userNameReport = getcookie("username");
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function joinEvent(id) {
  const url = "/api/user/Event/joinEvent/" + id;
  const data = {};
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function leaveEvent(id) {
  const url = "/api/user/Event/leaveEvent/" + id;
  const data = {};
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function viewEventJoin(month, year) {
  const url = "/api/user/Event/viewEventJoin?month=" + month + "&year=" + year;
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function viewCommentInPost(postID, page) {
  const url =
    "/api/user/comment/viewCommentInPost/" +
    postID +
    "?page=" +
    page +
    "&sortBy=CreateDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    console.log(err);
    return err;
  }
}
async function CommentPost(data) {
  const url = "/api/user/comment/commentInPost";
  data.userNameReport = getcookie("username");
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function viewReplyComment(postID, cmtID) {
  const url =
    "/api/user/comment/viewReplyComment/" + postID + "?commentId=" + cmtID;
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    console.log(err);
    return err;
  }
}
async function deleteComment(cmtID, frmID) {
  const url =
    "/api/user/comment/deleteComment?commentId=" + cmtID + "&forumId=" + frmID;
  try {
    const res = await axios.delete(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    console.log(err);
    return err;
  }
}
async function updateComment(cmtID, data) {
  const url = "/api/user/comment/updateComment/" + cmtID;
  let headers = {
    Authorization: "Bearer " + getcookie("token"),
  };
  try {
    const res = await axios.put(url, data, { headers }).then((res) => res);
    return res.data;
  } catch (err) {
    console.error("Lỗi");
    console.log(err);
    return err.Error;
  }
}
async function deleteRepComment(cmtID, frmID) {
  //Khoong duc su dung api nay
  const url =
    "/api/user/comment/deleteComment?commentId=" + cmtID + "&forumId" + frmID;
  try {
    const res = await axios.delete(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    console.log(err);
    return err;
  }
}
async function likePost(id) {
  const url = "/api/user/interact/likePost/" + id;
  const data = null;
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function unlikePost(id) {
  const url = "/api/user/interact/UnlikePost/" + id;
  const data = null;
  try {
    const res = await axios
      .post(url, data, { headers })
      .then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}

export {
  Post,
  deletePost,
  updatePost,
  detailPost,
  ReportPost,
  viewCommentInPost,
  CommentPost,
  viewReplyComment,
  deleteComment,
  updateComment,
  joinEvent,
  leaveEvent,
  viewEventJoin,
  deleteRepComment,
  likePost,
  unlikePost,
  ReportCMT,
};
