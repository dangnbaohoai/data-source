import axios from "axios";
import { getcookie } from "./getcookie";
let headers = {
  Authorization: "Bearer " + getcookie("token"),
};
let a = null;
async function viewForumJoin(id) {
  const url =
    "/api/user/forum/viewForumJoin/" +
    id +
    "?page=" +
    1 +
    "&sortBy=" +
    "createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}

async function joinForum(id) {
  const url = "/api/user/forum/joinForum/" + id;
  const a = null;
  try {
    const res = await axios.post(url, a, { headers }).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}

async function leaveForum(id) {
  const url = "/api/user/forum/leaveForum/" + id;
  try {
    const res = await axios.delete(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function memberByCensored(id) {
  const url =
    "/api/censor/member/viewMemberByCensor/" +
    id +
    "?page=" +
    1 +
    "&sortBy=" +
    "createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function memberByUser(id) {
  const url =
    "/api/user/forum/viewMemberByMember/" +
    id +
    "?page=" +
    1 +
    "&sortBy=" +
    "createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function memberNotCensored(id, page) {
  const url =
    "/api/censor/member/viewMemberNotCensored/" +
    id +
    "?page=" +
    page +
    "&sortBy=" +
    "createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function deleteMemberWaiting(forumid, id) {
  const url =
    "/api/censor/member/deleteMember?forumId=" + forumid + "&userId=" + id;
  try {
    const res = await axios.delete(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function censorMember(forumid, userid) {
  const url =
    "/api/censor/member/censorMember?forumId=" + forumid + "&userId=" + userid;
  try {
    const res = await axios.put(url, a, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function contentsWaitToCensor(id) {
  const url =
    "/api/censor/post/viewPostNeedCensor/" +
    id +
    "?page=" +
    1 +
    "&sortBy=" +
    "createDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function deleteWaittoCensorPost(id, forumid) {
  const url =
    "/api/censor/post/deletePost?forumId=" + id + "&postId=" + forumid;
  try {
    const res = await axios.put(url, a, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function sensorPost(forumid, id) {
  const url =
    "/api/censor/post/censorPost?forumId=" + forumid + "&postId=" + id;
  try {
    const res = await axios.put(url, a, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function getPostReport(forumId, page) {
  let url =
    "/api/censor/reportPost/viewPostReport/" +
    forumId +
    "?page=" +
    page +
    "&sortBy=CreateDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function DeleteReport(id, forumId) {
  const url = "/api/censor/reportPost/deletePost/" + id + "?forumId=" + forumId;
  const a = null;
  try {
    const res = await axios
      .post(url, a, { headers })
      .then((res) => res)
      .catch((err) => err);
    return res;
  } catch (err) {
    return err;
  }
}
async function cancelPostReport(id, forumId) {
  const url =
    "/api/censor/reportPost/cancelPostReport/" + id + "?forumId=" + forumId;
  const a = null;
  try {
    const res = await axios.post(url, a, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function getEvent(id, page) {
  let url =
    "/api/user/forum/viewEventInForum/" +
    id +
    "?page=" +
    page +
    "&sortBy=" +
    "dateoOfEvent";
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
async function deleteMemberOutOfForum(forumid, id) {
  const url =
    "/api/censor/member/deleteMember?forumId=" + forumid + "&userId=" + id;
  try {
    const res = await axios.delete(url, { headers }).then((res) => res);
    return res;
  } catch (err) {
    return err;
  }
}
async function getCMTReport(forumId, page) {
  let url =
    "/api/censor/reportComment/viewReport/" +
    forumId +
    "?page=" +
    page +
    "&sortBy=modifiedDate";
  try {
    const res = await axios.get(url, { headers }).then((res) => res.data);
    return res;
  } catch (err) {
    return err;
  }
}
async function DeleteCMTReport(id, forumId) {
  const url =
    "/api/censor/reportComment/deleteComment/" + id + "?forumId=" + forumId;
  const a = null;
  try {
    const res = await axios
      .post(url, a, { headers })
      .then((res) => res)
      .catch((err) => err);
    return res;
  } catch (err) {
    return err;
  }
}
async function CancelCMTReport(id, forumId) {
  const url =
    "/api/censor/reportComment/cancelCommentReport/" +
    id +
    "?forumId=" +
    forumId;
  const a = null;
  try {
    const res = await axios
      .post(url, a, { headers })
      .then((res) => res)
      .catch((err) => err);
    return res;
  } catch (err) {
    return err;
  }
}
export {
  viewForumJoin,
  joinForum,
  leaveForum,
  memberNotCensored,
  censorMember,
  deleteMemberWaiting,
  contentsWaitToCensor,
  deleteWaittoCensorPost,
  sensorPost,
  memberByCensored,
  getPostReport,
  DeleteReport,
  cancelPostReport,
  memberByUser,
  getEvent,
  deleteMemberOutOfForum,
  getCMTReport,
  DeleteCMTReport,
  CancelCMTReport,
};
