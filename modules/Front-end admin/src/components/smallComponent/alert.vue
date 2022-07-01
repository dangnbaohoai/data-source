<template>
  <div class="flex w-full my-2">
    <div
      class="flex-grow text-white font-bold py-2 px-10 rounded-l-md"
      :class="[`bg-${color}-400`]"
    >
      {{ header.message }}
    </div>
    <button
      class="
        group
        bg-red-400
        text-white
        font-bold
        px-4
        rounded-r-md
        hover:bg-white
        border-2
        justify-items-center
      "
      :class="[`bg-${color}-400 border-${color}-400`]"
      @click="header.message = null"
    >
      <svg
        class="h-8 w-8 text-white"
        :class="[`group-hover:bg-${color}-400`]"
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
    props: {
      color: {
        type: String,
        default: "red",
      },
      msg: {
        type: String,
        default: "UNKNOWN ERROR !!!",
      },
    },
    indeterminate: Boolean,
  },
};
</script>

<style></style>
