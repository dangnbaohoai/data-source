<template>
  <div class="flex w-full my-2 text-white text-xl" v-if="msgs">
    <div class="flex-grow font-bold py-1 px-10" :class="[`bg-${color}-400 `]">
      {{ msgs }}
    </div>
    <button
      class="
        group
        bg-red-400
        text-white
        font-bold
        px-2
        hover:bg-white
        border-2
        justify-items-center
      "
      :class="[`bg-${color}-400 border-${color}-400`]"
      @click="close"
    >
      <svg
        class="h-8 w-8 text-white group-hover:bg-white"
        :class="[`group-hover:text-${color}-400`]"
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
    </button>
  </div>
</template>

<script>
export default {
  props: {
    color: {
      type: String,
      default: "red",
    },
    msgs: {
      type: String,
      default: "UNKNOWN ERROR !!!",
    },
    indeterminate: Boolean,
  },
  methods: {
    close() {
      console.log("Err close");
      this.$emit("close");
    },
  },
};
</script>

<style></style>
