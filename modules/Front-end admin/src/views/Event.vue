<template>
  <div>
    <CreateContent />
    <Post />
  </div>
</template>

<script>
import CreateContent from "../components/editor/CreateContent.vue";
import Post from "../components/editor/viewAdminPost.vue";
export default {
  components: {
    CreateContent,
    Post,
  },
};
</script>

<style></style>
