import { createRouter, createWebHistory } from "vue-router";
import Home from "../views/Home.vue";

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    title: "Homepage Title",
  },
  {
    path: "/test",
    name: "Test",
    component: () => import("../views/Test.vue"),
    children: [
      {
        path: "search",
        name: "search",
        component: () => import("../components/Search.vue"),
      },
    ],
  },
  {
    path: "/Forum",
    name: "ForumList",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import("../views/ForumList.vue"),
  },
  {
    path: "/Forum/:forumId",
    name: "Forum",
    component: () => import("../views/DetailForum.vue"),
    children: [
      {
        path: "",
        name: "feeds",
        component: () => import("../components/MainFeeds.vue"),
      },
      {
        path: "nembers",
        name: "nembers",
        component: () => import("../components/forum/nembers.vue"),
      },
      {
        path: "events",
        name: "Event",
        component: () => import("../components/forum/viewEvent.vue"),
      },
      {
        path: "memberCensorship",
        name: "memberCensorship",
        component: () =>
          import("../components/forum/censor/memberCensorship.vue"),
      },
      {
        path: "postCensorship",
        name: "postCensorship",
        component: () =>
          import("../components/forum/censor/contentCensorship.vue"),
      },
      {
        path: "post/:postId",
        name: "detailPost",
        component: () => import("../components/detailPost.vue"),
        props: true,
      },
      {
        path: "postReport",
        name: "postReport",
        component: () => import("../components/forum/censor/contentReport.vue"),
        props: true,
      },
      {
        path: "commentReport",
        name: "commentReport",
        component: () => import("../components/forum/censor/commentReport.vue"),
        props: true,
      },
    ],
    props: true,
  },
  {
    path: "/:catchAll(.*)",
    name: "NotFound",
    component: () => import("../views/404.vue"),
  },
  {
    path: "/myprofile",
    name: "MyProfile",
    component: () => import("../views/profilePersona.vue"),
  },
  {
    path: "/profile/:userid",
    name: "Profile",
    component: () => import("../views/otherUserPersona.vue"),
    props: true,
  },
  {
    path: "/noti",
    name: "noti",
    component: () => import("../views/notify.vue"),
    props: true,
  },
  {
    path: "/Messenger",
    name: "Messenger",
    component: () => import("../views/Messenger.vue"),
    meta: { title: "Skills - MyApp" },
    props: true,
    children: [
      {
        path: ":userID",
        name: "Messenger",
        component: () => import("../views/Messenger.vue"),
        props: true,
      },
    ],
  },
  {
    path: "/Account/ChangePassword",
    name: "ChangePassword",
    component: () => import("../components/account/changePassword.vue"),
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
