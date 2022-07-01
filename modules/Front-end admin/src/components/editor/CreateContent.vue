<template>
  <div class="mx-20 text-left text-white w-full text-4xl">
    <h1>Create new post</h1>
  </div>

  <div id="modal-id" class="mx-20 flex 2xl:my-10">
    <div
      class="
        max-w-2xl
        overflow-hidden
        rounded-lg
        shadow-lg
        text-left
        block
        divide-y
        bg-gray-50
        mr-10
      "
    >
      <div class="title flex py-2 mx-2" v-if="isEvent">
        <p class="text-xl mr-4">Event title:</p>
        <input
          class="truncate flex-grow bg-gray-50 border-b-2 text-xl"
          type="text"
          name=""
          id=""
          v-model="post.nameEvent"
        />
      </div>
      <div class="title flex py-2 mx-2" v-if="isEvent">
        <p class="text-xl mr-4">Address of event:</p>
        <input
          class="truncate flex-grow bg-gray-50 border-b-2 text-xl"
          type="text"
          name=""
          id=""
          v-model="post.addressOfEvent"
        />
      </div>
      <tiptap :msg="msg" @posting="posting" />
      <div v-if="isEvent" class="flex h-6">
        <p>Date :</p>
        <input
          type="date"
          id="birthdaytime"
          name=""
          v-model="post.dateOfEvent"
        /><br />
        <p class="mx-4">Time:</p>
        <input type="time" id="birthdaytime" name="" />
      </div>
      <div class="flex justify-items-center m-2 text-center rounded-lg pt-2">
        <div
          class="
            text-center
            w-max
            text-white
            px-4
            mr-2
            bg-red-700
            border-4 border-white
            hover:bg-red-300 hover:border-red-800
            rounded-lg
          "
          v-if="!isEvent"
        >
          Bài đăng
        </div>
        <div
          class="
            text-center
            w-max
            text-black
            px-4
            mr-2
            bg-white
            border-4
            hover:bg-gray-600 hover:text-white
            rounded-lg
          "
          v-on:click="thatAevent"
          v-if="isEvent"
        >
          Bài đăng
        </div>
        <div
          class="
            text-right
            w-max
            text-white
            px-4
            mx-2
            bg-red-700
            border-4 border-white
            hover:bg-red-300 hover:border-red-800
            rounded-lg
          "
          v-if="isEvent"
        >
          Sự kiện
        </div>
        <!-- neu day la event -->

        <div
          class="
            text-right
            w-max
            text-black
            px-4
            mx-2
            bg-white
            border-4
            hover:bg-gray-600 hover:text-white
            rounded-lg
          "
          v-if="!isEvent"
          v-on:click="thatAevent"
        >
          Sự kiện
        </div>
      </div>
      <uploadImage />
      <div aria-label="Page navigation" class="py-2">
        <div class="px-4 flex justify-end">
          <button
            class="
              bg-blue-500
              hover:bg-blue-700
              text-white
              font-bold
              py-2
              px-4
              rounded-full
            "
            @click="msg = !msg"
          >
            Đăng bài
          </button>
        </div>
      </div>
      <div>
        <warn v-if="warnmsg" :msg="warnmsg" />
      </div>
    </div>
    <div class="h-80">
      <table
        class="
          border-collapse
          table-fixed
          border border-green-800 border-dashed
          bg-gray-900
          text-white
        "
      >
        <thead>
          <th class="px-2 border border-green-600">ID</th>
          <th class="w-full border border-green-600">Name</th>
          <th class="w-1/5 border border-green-600"></th>
        </thead>
        <tbody>
          <tr
            class="
              border border-green-600 border-dashed
              hover:bg-gray-800
              cursor-pointer
            "
            v-for="forum in forumlist"
            :key="forum.forumId"
            :class="[post.forumId.includes(forum.forumId) ? 'bg-gray-700' : '']"
          >
            <td @click="setForum(forum.forumId)">
              {{ forum.forumId }}
            </td>
            <td @click="setForum(forum.forumId)" class="text-left pl-2">
              {{ forum.nameForum }}
            </td>
            <td class="hover:bg-green-500 grid justify-items-stretch h-full">
              <svg
                class="h-6 w-6 text-white"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                stroke-width="2"
                stroke="currentColor"
                fill="none"
                stroke-linecap="round"
                stroke-linejoin="round"
              >
                <path stroke="none" d="M0 0h24v24H0z" />
                <polyline points="16 4 20 4 20 8" />
                <line x1="14" y1="10" x2="20" y2="4" />
                <polyline points="8 20 4 20 4 16" />
                <line x1="4" y1="20" x2="10" y2="14" />
                <polyline points="16 20 20 20 20 16" />
                <line x1="14" y1="14" x2="20" y2="20" />
                <polyline points="8 4 4 4 4 8" />
                <line x1="4" y1="4" x2="10" y2="10" />
              </svg>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
import Tiptap from "./Tiptap.vue";
import uploadImage from "./uploadInmage.vue";
import { getForum } from "../../logic/ForumAPI";
import { AdminCreate } from "../../logic/AdminAPI";
export default {
  components: {
    Tiptap,
    uploadImage,
  },
  data() {
    return {
      isEvent: 0,
      msg: false,
      post: {
        nameEvent: "",
        content: "",
        dateOfEvent: "",
        time: null,
        forumId: [],
      },
      forumlist: [],
      warnmsg: null,
      page: 1,
    };
  },
  async created() {
    this.forumlist = (await getForum(this.page)).listResult;
    console.log("=========this.forumlist");
    console.log(this.forumlist);
  },
  methods: {
    thatAevent() {
      this.isEvent = !this.isEvent;
    },
    async posting(content) {
      if (this.post.forumId == "") {
        this.$root.addnoti({
          group: "bottom",
          title: " Fail",
          text: "Choice at least 1 forum ! Plz",
        });
      } else {
        this.post.content = content;
        console.log(this.post);
        const res = await AdminCreate(this.post);

        if (res.message == "created success") {
          this.$root.addnoti({
            group: "top",
            title: " success",
            text: res.message,
          });
        } else {
          this.$root.addnoti({
            group: "top",
            title: " success",
            text: res.message,
          });
        }
      }
    },
    setForum(id) {
      if (!this.post.forumId.includes(id)) {
        this.post.forumId.push(id);
        console.log(this.post.forumId);
      } else {
        this.post.forumId.splice(location(id, this.post.forumId));
        console.log(this.post.forumId);
      }
    },
  },
};
function location(a, b) {
  for (let i = 0; i < a.length; i++) {
    if (b[i] == a) return i;
  }
}
</script>
<style lang="scss">
/* Basic editor styles */
.Prose {
  > * + * {
    margin-left: 2rem;
    font-family: "arial";
    margin: 1rem;
  }

  ul,
  ol {
    padding: 0 1rem;
  }

  h1 {
    font-size: 2rem;
    font-family: "arial";
    line-height: 1.1;
    font-weight: bold;
    padding: 1rem 4rem;
    color: white;
    background-color: #0d0d0d;
  }
  h2 {
    font-size: 1.5rem;
    margin-left: 1rem;
    font-family: "arial";
    line-height: 1.1;
    font-weight: bold;
  }

  h3 {
    font-size: 1.5rem;
    margin-left: 1rem;
    font-family: "arial";
    line-height: 1.1;
  }

  h2,
  h3 {
    padding: 0.5rem;
  }

  ul {
    list-style-type: circle;
  }

  ol {
    list-style-type: number;
  }

  ul,
  ol {
    padding-left: 2rem;
  }

  code {
    background-color: rgba(#616161, 0.1);
    color: #d83c3c;
    border-radius: 0.375rem;
    padding-inline: 2mm;
  }

  pre {
    background: #5f5757b9;
    border-radius: 0.5rem;
    color: #fff;
    font-family: "JetBrainsMono", monospace;
    padding: 0.5rem 1rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  img {
    max-width: 100%;
    height: auto;
  }

  blockquote {
    margin-left: 2mm;
    margin-top: 2mm;
    padding-left: 2rem;
    border-left: 4px solid;
    border-right-color: rgba(#0d0d0d, 0.1);
  }

  hr {
    border: none;
    border-top: 2px solid rgba(#0d0d0d, 0.1);
    margin: 2rem 0;
  }
}

.menubar {
  margin-bottom: 1rem;
  transition: visibility 0.2s 0.4s, opacity 0.2s 0.4s;

  &.is-hidden {
    visibility: hidden;
    opacity: 0;
  }
  p {
    top: 0%;
  }
  &.is-focused {
    visibility: visible;
    opacity: 1;
    transition: visibility 0.2s, opacity 0.2s;
  }

  &__button {
    font-weight: bold;
    display: inline-flex;
    background: transparent;
    border: 0;
    color: black;
    padding: 0.2rem 0.5rem;
    margin-right: 0.2rem;
    border-radius: 3px;
    cursor: pointer;

    &:hover {
      background-color: rgba(0, 0, 0, 0.05);
    }

    &.is-active {
      background-color: rgba(0, 0, 0, 0.3);
    }
  }

  span#{&}__button {
    font-size: 13.3333px;
  }
}
</style>
