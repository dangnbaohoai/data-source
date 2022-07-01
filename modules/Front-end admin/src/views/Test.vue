<template>
  <div class="text-white align-top">
    <input type="number" class="h-10 text-black" name="" id="" v-model="page" />

    <input
      type="number"
      class="h-10 text-blac text-black"
      name=""
      id=""
      v-model="currentPage"
    />

    Page: {{ page }} Current Page {{ currentPage }}
  </div>
  <Pangintion
    :currentPage="currentPage"
    :pages="data"
    :totalPages="page"
    @pagechanged="pagechanged"
  />
</template>
<script>
import Pangintion from "../components/smallmodal/Pagination.vue";
export default {
  name: "  Test",
  components: { Pangintion },
  data() {
    return {
      page: 50,
      currentPage: 2,
      data: [{ name: 0 }, { name: 1 }, { name: 2 }, { name: 3 }, { name: 4 }],
    };
  },
  watch: {
    currentPage: function () {
      let data = [];
      if (this.currentPage > 2 && this.currentPage < this.page) {
        for (let i = this.currentPage - 3; i < this.currentPage + 2; i++) {
          data.push({ name: i });
        }
        this.data = data;
      }

      console.log(data);
    },
  },
  methods: {
    pagechanged(page) {
      this.currentPage = page;
    },
  },
};
</script>
