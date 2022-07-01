<template>
  <div class="h-12"></div>
  <div class="home flex">
    <Modal v-if="isShowModal" :msg="message" @close="closeConfirmModal" />
    <ViewPostModal v-if="isShowPostModal" @close="closeViewPost" />
    <LeftFeeds v-if="1" class="hidden lg:block lg:w-1/6 xl:mr-80" />
    <div class="grid w-full 2xl:w-1/3 xl:w-2/3">
      <MainFeeds
        v-if="!isLoading"
        class="static"
        @makecontent="closeCreateContent"
        :contents="contents"
      />
      <Loading v-if="isLoading" />
    </div>
    <div class="right hidden xl:block xl:flex-grow">
      <div class="sticky top-32">
        <EventCalender class="" />
      </div>
    </div>
  </div>
</template>

<script>
import LeftFeeds from "@/components/homepage/LeftFeeds.vue";
import MainFeeds from "@/components/MainFeeds.vue";
import Modal from "@/components/ConfirmModal.vue";
import ViewPostModal from "@/components/ViewPostModal.vue";
import EventCalender from "@/components/homepage/EventCalender.vue";
import Loading from "../components/loading/MainfeedLoading.vue";
import { home } from "../logic/api";

export default {
  name: "Home",
  components: {
    MainFeeds,
    LeftFeeds,
    Modal,
    ViewPostModal,
    Loading,
    EventCalender,
  },
  data() {
    return {
      isShowModal: true,
      isCreateContent: false,
      isShowPostModal: false,
      message: "Bạn có muốn xóa bài đăng ?",
      contents: [],
      isLoading: true,
    };
  },
  async created() {
    document.title = "School connect";
    const a = await home();
    console.log(a);
    // postResponseList forumResponseList data
    this.$store.state.listJoinedForum = a.data.forumResponseList;
    this.$store.state.mainfeeds = a.data.postResponseList;
    console.log("this.$store.state.listJoinedForum");
    console.log(this.$store.state.listJoinedForum);
    this.contents = a.data.postResponseList;
    this.isLoading = false;
  },
  methods: {
    closeConfirmModal() {
      this.isShowModal = false;
    },
    closeCreateContent() {
      this.isCreateContent = !this.isCreateContent;
    },
    closeViewPost() {
      console.log(this.isShowPostModal);
      this.isShowPostModal = !this.isShowPostModal;
    },
    test() {
      console.log(this.contents);
    },
  },
};
</script>

<style lang="scss">
/* Basic editor styles */
.Prose {
  > * + * {
    // margin-left: 1rem;
    font-family: "arial";
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
    padding: 1rem 1rem;
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
    padding-inline: 1mm;
  }

  pre {
    background: #5f5757b9;
    // border-radius: 0.5rem;
    color: #fff;
    font-family: "Ubuntu Mono", "JetBrainsMono", monospace;
    padding: 0.1rem 1rem;

    code {
      color: inherit;
      padding: 0;
      background: none;
      font-size: 0.8rem;
    }
  }

  // img {
  //   max-width: 100%;
  //   height: auto;
  // }

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
