import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/:catchAll(.*)",
    name: "NotFound2",
    component: () => import(/* webpackChunkName: "about" */ "../views/404.vue"),
  },
  {
    path: "/NotFound",
    name: "NotFound",
    component: () => import(/* webpackChunkName: "about" */ "../views/404.vue"),
  },
  {
    path: "/accountmanagement",
    name: "accountmanagement",
    component: () => import("../views/accountManagement.vue"),
    children: [
      {
        path: "",
        name: "TableofUser",
        component: () => import("../components/table/TableofUser.vue"),
      },
      {
        path: "new",
        name: "New User",
        component: () => import("../components/form/newMember.vue"),
      },
      {
        path: "imported",
        name: "Imported user",
        component: () => import("../components/table/TableImportedUser.vue"),
      },
    ],
  },
  {
    path: "/forumManagement",
    name: "forumManagement",
    component: () => import("../views/forumManagement.vue"),
    children: [
      {
        path: "",
        name: "TableofForum",
        component: () => import("../components/table/TableofUForum.vue"),
      },
      {
        path: "new",
        name: "New",
        component: () => import("../components/form/newForum.vue"),
      },
      {
        path: "edit/:id",
        name: "UpdateForum",
        component: () => import("../components/form/newForum.vue"),
        props: true,
      },
    ],
  },
  {
    path: "/Event",
    name: "Event",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Event.vue"),
    children: [
      {
        path: "post",
        name: "TableofAdminPost",
        component: () => import("../components/editor/viewAdminPost.vue"),
      },
    ],
  },
  {
    path: "/ReportandBlocking",
    name: "ReportandBlocking",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/ReportandBlocking.vue"),
    children: [
      {
        path: "",
        name: "TableofReport",
        component: () => import("../components/table/TableofReport.vue"),
      },
      {
        path: "blocked",
        name: "TableofBlockedChat",
        component: () =>
          import("../components/table/TableofBannedRoomChat.vue"),
      },
    ],
  },
  {
    path: "/chart",
    name: "Evchartent",
    component: () =>
      import(/* webpackChunkName: "about" */ "../components/BarChart.vue"),
  },
  {
    path: "/test",
    name: "Evchartent",
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Test.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
