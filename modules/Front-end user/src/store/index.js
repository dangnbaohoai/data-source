import { createStore } from "vuex";

export default createStore({
  state: {
    value: "",
    avatar: "",
    listJoinedForum: [],
    userID: 0,
  },
  getters: {
    listJoinedForum: (state) => state.listJoinedForum,
    userID: (state) => state.userID,
  },
  mutations: {},
  actions: {},
  modules: {},
});
