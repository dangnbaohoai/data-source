<template>
  <div class="align-middle bg-gray-900 mr-2">
    <div class="sticky top-20">
      <h1
        class="py-2 px-4 inline-flex align-middle font-sans text-base font-bold"
      ></h1>
      <router-link
        class="
          px-4
          hover:shadow
          my-1
          flex
          align-middle
          font-sans
          text-base
          font-bold
          hover:bg-gray-700
          rounded-r-lg
        "
        v-for="func in functions"
        :key="func.id"
        :to="{ path: func.router }"
      >
        <p v-html="func.icon" class="mt-1 mr-2 text-white"></p>
        <div class="my-2 text-white text-left hidden 2xl:table w-52">
          {{ func.name }}
        </div>
      </router-link>
    </div>
  </div>
</template>

<script>
export default {
  setup() {},
  data() {
    return {
      functions: [
        {
          id: 1,
          name: "Dashboard",
          router: "/",
          icon: "<ion-icon name='bar-chart-outline'size='large'></ion-icon>",
        },
        {
          id: 2,
          name: "Account Management",
          router: "/accountmanagement",
          icon: "<ion-icon name='accessibility-outline' size='large'></ion-icon>",
        },
        // {
        //   id: 3,
        //   name: "Notifications",
        //   router: "/noti",
        //   icon: "<ion-icon name='notifications-outline' size='large'></ion-icon>",
        // },
        {
          id: 4,
          name: "Forums Management",
          router: "/forumManagement",
          icon: "<ion-icon name='people-circle-outline' size='large'></ion-icon>",
        },
        {
          id: 5,
          name: "Event / Policy",
          router: "/Event",
          icon: "<ion-icon name='flag-outline' size='large'></ion-icon>",
        },
        {
          id: 6,
          name: "Report and blocking",
          router: "/ReportandBlocking",
          icon: "<ion-icon name='alert-circle-outline' size='large'></ion-icon>",
        },
      ],
    }; //end return
  },
};
</script>
