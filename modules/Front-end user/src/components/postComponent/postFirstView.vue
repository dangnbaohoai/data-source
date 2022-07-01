<template>
  <div class="not-italic font-light px-6 text-yellow-600 bg-gray-100 w-full">
    > {{ content.forumName }} {{ content.postId }}
  </div>
  <div class="Prose px-6 py-4 bg-white">
    <div class="user textcentre flex mb-5">
      <img
        class="
          avatar
          inline
          object-cover
          w-16
          h-16
          mr-5
          border-gray-400
          rounded-full
          border-2 border-dashed
          flex-none
        "
        :src="content.avatar"
        alt="Profile image"
      />
      <div>
        <div class="flex items-center group">
          <router-link
            class="
              text-xl text-black text-opacity-100 text-left
              font-bold
              rounded-xl
              max-w-max
              hover:underline
            "
            :to="{ path: '/profile/' + content.userId }"
          >
            {{ content.createBy }} </router-link
          ><span v-if="content.position == 'teacher'" class="flex items-center"
            ><svg
              class="h-4 w-4 mx-2 text-green-500"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M9 12l2 2 4-4M7.835 4.697a3.42 3.42 0 001.946-.806 3.42 3.42 0 014.438 0 3.42 3.42 0 001.946.806 3.42 3.42 0 013.138 3.138 3.42 3.42 0 00.806 1.946 3.42 3.42 0 010 4.438 3.42 3.42 0 00-.806 1.946 3.42 3.42 0 01-3.138 3.138 3.42 3.42 0 00-1.946.806 3.42 3.42 0 01-4.438 0 3.42 3.42 0 00-1.946-.806 3.42 3.42 0 01-3.138-3.138 3.42 3.42 0 00-.806-1.946 3.42 3.42 0 010-4.438 3.42 3.42 0 00.806-1.946 3.42 3.42 0 013.138-3.138z"
              />
            </svg>
            <p class="opacity-0 group-hover:opacity-100">is Teacher</p>
          </span>
        </div>

        <div class="text-xs mr-2 hover:underline cursor-pointer" @click="push">
          {{ Showdate }}
          <!-- <time-ago
            refresh
            long
            locale="en"
            :datetime="new Date(content.createDate)"
          >
            <template v-slot="{ timeago }">
              <span class="text-gray-600 text-sm">
                {{ timeago }}
              </span>
            </template>
          </time-ago> -->
        </div>
      </div>
      <div class="flex-grow content-end"></div>
      <div class="grid justify-items-end">
        <actionPanel
          :PostID="content.postId"
          :ForumID="content.forumId"
          :userId="content.userId"
          @remove="remove"
          @edit="edit"
          :isuserisCensor="isuserisCensor"
        />
      </div>
    </div>
    <div v-if="content.nameOfEvent">
      <h2 class="border-2 mx-4">📢 Event {{ content.nameOfEvent }}</h2>
      <div class="flex items-center">
        <div>
          <strong>Location: {{ content.addressOfEvent }}</strong
          ><br />
          <strong
            >At {{ content.dateOfEvent }} {{ content.timeOfEvent }}</strong
          >
        </div>
        <div class="flex-grow"></div>
        <button
          class="
            flex-0
            focus:outline-none
            flex
            items-center
            font-bold
            px-4
            py-1
            group
            transition
            duration-300
            ease-in-out
            origin-center
            transform
          "
          @click="joinEvent(content.postId)"
          :class="[
            !joined
              ? 'text-green-500 hover:bg-green-500 hover:text-white hover:border-white'
              : 'text-red-500 hover:bg-red-500 hover:text-white hover:border-white',
          ]"
        >
          <svg
            class="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              :class="{ 'rotate-45': joined }"
              class="
                transition
                duration-300
                ease-in-out
                origin-center
                transform
              "
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M12 4v16m8-8H4"
            />
          </svg>

          <span>{{ joined ? "Leave event" : "Join event" }}</span>
        </button>
        <!-- <button
          class="
            flex-0
            text-white
            focus:outline-none
            flex
            items-center
            font-bold
            px-4
            py-1
            
            group
            
            transition
            delay-150
          "
          @click="leaveEvent(content.postId)"
          v-if="joined"
        >
          <svg
            class="h-5 w-5"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M6 18L18 6M6 6l12 12"
            />
          </svg>

          <span>Leave event</span>
        </button> -->
      </div>
    </div>
    <div>
      <p
        class="leading-normal text-gray-700 text-left"
        v-html="content.content"
      ></p>
    </div>
    <link-Preview :url="firstLink" v-if="isShowLP" />
  </div>
  <VuePictureSwipe
    v-if="content.linkImage != []"
    class="bg-white overflow-x-scroll"
    :items="ImgItems"
    :options="{ shareEl: false }"
  />
  <div class="likezone px-4 flex bg-white">
    <button
      class="px-4 py-1 flex hover:bg-gray-200 my-2 rounded-l-md"
      role="button"
      @click="likePost"
      :class="[isLiked ? 'bg-green-300' : '']"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-6 w-6 pr-2"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
        />
      </svg>
      {{ numberLikes }}
    </button>
    <button
      class="px-4 py-1 flex hover:bg-gray-200 my-2 rounded-r-md"
      role="button"
      @click="push"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-6 w-6 pr-2"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"
        />
      </svg>
      {{ content.numberComments }} Comment
    </button>
  </div>
  <!-- <Alert :indeterminate="true" /> -->
</template>

<script>
import linkPreview from "./linkPreview.vue";
import actionPanel from "./actionPanel.vue";
import VuePictureSwipe from "vue-picture-swipe";
// import Date from "../smallComponent/time.vue";
import {
  joinEvent,
  leaveEvent,
  likePost,
  unlikePost,
} from "../../logic/postAPI";
export default {
  components: { linkPreview, actionPanel, VuePictureSwipe },
  props: {
    content: Object,
    isuserisCensor: Boolean,
  },
  emits: ["remove", "edit"],
  setup(props) {
    var urlR = /\bhttps?::\/\/\S+|\bwww?.\S+/;
    var URL = props.content.content.match(urlR);
    console.log(props.content);
    let firstLink = null;
    let isShowLP = false;
    if (URL) {
      firstLink = URL[0];
      isShowLP = true;
    }
    const date = new Date(props.content.createDate);
    let Showdate = "";
    if (date.getDay() == 1) {
      Showdate =
        "Today " +
        date
          .toLocaleTimeString()
          .replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
    } else {
      Showdate = date.toLocaleTimeString();
    }
    let ImgItems = ImageList(props.content.linkImage, props.content.createBy);
    return {
      isShowLP,
      ImgItems,
      Showdate,
      firstLink,
    };
  },
  created() {
    this.joined = this.content.statusJoinEventOfUser;
    this.numberLikes = this.content.numberLike;
  },
  data() {
    return {
      al: {
        msg: "",
        color: "green",
      },
      joined: 0,
      isLiked: false,
      numberLikes: 0,
    };
  },
  methods: {
    viewPost() {
      this.$emit("showUp");
    },
    push() {
      this.$router.push({
        path: "/Forum/" + this.content.forumId + "/post/" + this.content.postId,
      });
    },
    async joinEvent(postID) {
      if (!this.joined) {
        let msg = await joinEvent(postID);
        msg = msg.message;
        if (msg == "user join event success") {
          this.$root.addnoti({
            group: "top",
            title: "Success",
            text: "You are has joined the event",
          });
          this.joined = 1;
        } else {
          this.$root.addnoti({
            group: "bottom",
            title: "Error",
            text: msg,
          });
        }
      } else {
        this.leaveEvent(postID);
      }
    },
    async leaveEvent(postID) {
      let msg = await leaveEvent(postID);
      msg = msg.message;

      if (msg == "user leave event success") {
        this.$root.addnoti({
          group: "top",
          title: "Success",
          text: "You are has leave the event",
        });
        this.joined = 0;
      } else {
        this.$root.addnoti({
          group: "bottom",
          title: "Error",
          text: msg,
        });
      }
    },
    clearal() {
      this.al.msg = "";
    },

    async likePost() {
      if (this.isLiked) {
        const res = await unlikePost(this.content.postId);
        console.log(res);
        if (res.message == "Unlike post successfully") {
          this.isLiked = false;
          this.numberLikes--;
        } else {
          this.$root.addnoti({
            group: "bottom",
            title: "Unknown Error",
            text: res,
          });
        }
      } else {
        const res = await likePost(this.content.postId);

        if (res.message == "like post successfully") {
          this.isLiked = true;
          this.numberLikes++;
        } else {
          this.$root.addnoti({
            group: "bottom",
            title: "Unknown Error",
            text: res,
          });
        }
      }
    },
    remove() {
      this.$emit("remove", this.content);
    },
    edit() {
      this.$emit("edit", this.content);
    },
  },
};

function ImageList(imgs, caption) {
  let ImgResult = [];
  for (let i = 0; i < imgs.length; i++) {
    ImgResult.push({
      src: imgs[i],
      thumbnail: imgs[i],
      w: 0,
      h: 0,
      title: "Photo of " + caption,
    });
  }
  return ImgResult;
}
</script>

<style>
.avatar {
  object-fit: contain;
}
.my-gallery {
  display: flex;
  width: max-content;
  overflow-x: scroll;
  scroll-snap-type: mandatory;
  scroll-snap-points-x: repeat(100vh);
  scroll-snap-type: x mandatory;

  /* when two values set first is block, second inline */
}
figure {
  scroll-snap-align: center;
  width: 200px;
  height: 200px;
  display: flex;
}
figure > a > img {
  object-fit: cover;
  width: 200px;
  height: 200px;
}
</style>
