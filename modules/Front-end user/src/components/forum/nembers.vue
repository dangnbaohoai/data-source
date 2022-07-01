<template>
  <div class="grid gap-1 lg:grid-cols-2 px-2">
    <div
      class="
        group
        my-2
        bg-white
        hover:bg-gray-300
        p-2
        m-2
        rounded-xl
        shadow-2xl
      "
      v-for="data in datas.data"
      :key="data.userId"
    >
      <div class="flex">
        <div>
          <img class="w-20 h-20 bg-gray-40 rounded-full" :src="data.avatar" />
          <ConfirmPopup></ConfirmPopup>
          <button
            v-if="isCensor"
            class="
              w-20
              h-20
              bg-gray-40
              rounded-full
              bg-gray-300
              opacity-0
              static
              -my-20
              group
              hover:opacity-75
              flex
              items-center
            "
            @click="deletem(data.userId)"
          >
            <strong class="opacity-100 px-2">DELETE</strong>
          </button>
        </div>

        <div class="px-4 my-1 flex-grow">
          <router-link
            class="
              text-xl text-black text-opacity-100 text-left
              rounded-xl
              w-max
              group-hover:underline
            "
            :to="{ path: '/profile/' + data.userId }"
          >
            {{ data.fullName }}
          </router-link>
          <ul class="list-inside bg-rose-200 text-left">
            <li><strong>Email</strong> : {{ data.email }}</li>
            <li><strong>Address</strong> : {{ data.addressOfUser }}</li>
          </ul>
        </div>
        <div class="flex-none w-1 bg-blue-500 rounded-xl my-2"></div>
      </div>
    </div>
  </div>
  <div class="h-96"></div>
</template>

<script>
import ConfirmPopup from "primevue/confirmpopup";
import { memberByUser, deleteMemberOutOfForum } from "../../logic/forumAPI";
export default {
  props: {
    forumId: Number,
    isCensor: Boolean,
  },
  async created() {
    this.datas = await memberByUser(this.forumId);
    console.log(this.datas);
  },
  components: {
    ConfirmPopup,
  },
  data() {
    return {
      datas: [
        {
          id: 1,
          avatar: "https://robohash.org/eiuseosipsam.png?size=50x50&set=set1",
          username: "msargison0",
          email: "mdumini0@ihg.com",
          gender: "Polygender",
          address: "7672 Bay Pass",
        },
      ],
    }; //end return
  },
  methods: {
    async deletem(id) {
      await this.$confirm.require({
        target: event.currentTarget,
        message: "Are you sure you want to kick this user?",
        icon: "pi pi-exclamation-triangle",
        accept: async () => {
          const res = await (
            await deleteMemberOutOfForum(this.forumId, id)
          ).data;
          if (res.message == "delete member successful") {
            this.$root.addnoti({
              group: "top",
              title: "Delete success",
              text: "The user has been kicked out of the forum",
            });
          } else {
            this.$root.addnoti({
              group: "bottom",
              title: "Failure",
              text: res.message,
            });
          }
        },
        reject: () => {
          console.log("It also work" + this.PostID);
        },
      });
    },
    test(id) {
      console.log("OK" + id);
    },
  },
};
</script>

<style lang="css">
.menu {
  display: none;
}

.header:hover .menu {
  display: block;
}
.option {
  visibility: hidden;
  transform: scale(0);
  transition: 0.5s;
}
.showup {
  visibility: visible;
  transform: scale(1);
  transition: 0.5s;
}
.p-confirm-popup {
  /* display: -webkit-box; */
  display: -ms-flexbox;
  display: grid;
  height: 100px;

  border-radius: 0.375rem;
  max-height: 90%;
  -webkit-transform: scale(1);
  transform: scale(1);
  background-color: #ffffff;
  font-size: 5mm;
  box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1),
    0 4px 6px -2px rgba(0, 0, 0, 0.05);
  font-weight: 500;
}
.p-confirm-popup-message {
  padding-inline: 20px;
  background-color: #fee2e2;
  font-size: 1.25rem;
  line-height: 1.75rem;
}
.p-confirm-popup-content {
  font-size: 1.25rem;
  line-height: 1.75rem;
}
.p-button.p-button-text {
  background-color: transparent;
  color: #a5b4fc;
  font-weight: 500;
  border-color: transparent;
}
.p-dialog .p-dialog-footer {
  border-top: 0 none;
  background: #22272d;
  padding: 0 1.5rem 1.5rem 1.5rem;
  text-align: right;
  border-bottom-right-radius: 6px;
  border-bottom-left-radius: 6px;
}
.p-button {
  padding: 2px 20px;
  border-radius: 0.5rem;
}
.p-confirm-popup-reject {
  color: black;
}
.p-confirm-popup-accept {
  background-color: #ef4444;
  color: white;
  font-weight: 500;
}
</style>
