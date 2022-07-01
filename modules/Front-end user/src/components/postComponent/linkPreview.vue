<template>
  <a
    class="
      max-w-xs
      mx-auto
      bg-white
      rounded-xl
      shadow-md
      overflow-auto
      md:max-w-2xl
      h-14
    "
    :href="data.url"
  >
    <div class="md:flex bg-white hover:bg-gray-100 w-full grid">
      <div class="md:flex-shrink-0">
        <img
          class="h-48 w-full object-cover md:h-full md:w-48"
          :src="data.image"
          alt="Man looking at item at a store"
        />
      </div>
      <div class="p-8 w-4/5">
        <div
          class="uppercase tracking-wide text-sm text-indigo-500 font-semibold"
        >
          {{ data.title }}
        </div>
        <a
          href="#"
          class="
            block
            mt-1
            text-lg
            leading-tight
            font-medium
            text-black
            hover:underline
            overflow-ellipsis overflow-hidden
            h-24
          "
          >{{ data.description }}</a
        >
        <p class="mt-2 text-gray-500 truncate">
          {{ data.url }}
        </p>
      </div>
    </div>
  </a>
  <!-- <button @click="print">here</button> -->
</template>
<script>
import axios from "axios";
export default {
  props: {
    url: String,
  },
  data() {
    return {
      data: [{}],
    };
  },

  async created() {
    await axios
      .post("https://api.linkpreview.net", {
        q: this.url,
        key: "5209cc3314826231cff6bbc89cc500f8",
      })
      .then((resp) => {
        this.data = resp.data;
        // console.log("Từ data");
        // console.log(this.data);
      });
  },

  methods: {},
};
// description: "Celebrating the late We:wa! #GoogleDoodle"
// image: "https://www.google.com/logos/doodles/2021/celebrating-the-late-wewa-6753651837109042.3-2xa.gif"
// title: "Celebrating the late We:wa"
// url: "http://www.google.com"
</script>

<style></style>
