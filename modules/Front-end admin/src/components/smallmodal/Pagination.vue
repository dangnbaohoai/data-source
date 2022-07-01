<template>
  <ul class="flex rounded-xl">
    <li>
      <button
        class="px-2 py-1 bg-gray-800 rounded-sm mx-2"
        type="button"
        @click="onClickFirstPage"
        :disabled="isInFirstPage"
      >
        First
      </button>
    </li>

    <li>
      <button
        class="px-2 py-1 bg-gray-800 rounded-smd mx-2"
        type="button"
        @click="onClickPreviousPage"
        :disabled="isInFirstPage"
      >
        Previous
      </button>
    </li>

    <!-- Visible Buttons Start -->

    <li v-for="page in pages" :key="page.name">
      <button
        class="px-2 py-1 rounded-sm mx-1"
        type="button"
        :class="page.name == currentPage ? 'bg-gray-800' : 'bg-gray-700'"
        @click="onClickPage(page.name)"
        :disabled="page.isDisabled"
      >
        {{ page.name }}
      </button>
    </li>

    <!-- Visible Buttons End -->

    <li>
      <button
        class="px-2 py-1 bg-gray-800 rounded-sm mx-2"
        type="button"
        @click="onClickNextPage"
        :disabled="isInLastPage"
      >
        Next
      </button>
    </li>

    <li>
      <button
        class="px-2 py-1 bg-gray-800 rounded-sm mx-2"
        type="button"
        @click="onClickLastPage"
        :disabled="isInLastPage"
      >
        Last
      </button>
    </li>
  </ul>
</template>

<script>
export default {
  props: {
    currentPage: Number,
    totalPages: Number,
    pages: {
      type: Object,
    },
  },
  data() {
    return {
      // pages: [
      //   { name: 1 },
      //   { name: 2 },
      //   { name: 3 },
      //   { name: 4 },
      //   { name: 5 },
      //   { name: 6 },
      // ],
    };
  },
  computed: {
    isInFirstPage() {
      return this.currentPage === 1;
    },
    isInLastPage() {
      return this.currentPage === this.totalPages;
    },
  },
  methods: {
    onClickFirstPage() {
      this.$emit("pagechanged", 1);
    },
    onClickPreviousPage() {
      this.$emit("pagechanged", this.currentPage - 1);
    },
    onClickPage(page) {
      this.$emit("pagechanged", page);
    },
    onClickNextPage() {
      this.$emit("pagechanged", this.currentPage + 1);
    },
    onClickLastPage() {
      this.$emit("pagechanged", this.totalPages);
    },
  },
};
</script>
<style scoped>
* {
  color: white;
}
</style>
