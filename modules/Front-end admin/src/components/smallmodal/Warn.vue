<template>
  <div class="warn flex align-middle mt-4 w-full">
    <svg
      class="h-7 w-7 text-red-700"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      stroke-width="2"
      stroke-linecap="round"
      stroke-linejoin="round"
    >
      <polygon
        points="7.86 2 16.14 2 22 7.86 22 16.14 16.14 22 7.86 22 2 16.14 2 7.86 7.86 2"
      />
      <line x1="12" y1="8" x2="12" y2="12" />
      <line x1="12" y1="16" x2="12.01" y2="16" />
    </svg>
    <div class="bg-red-700 flex-1 text-left pt-0.5">
      <span class="text-white pl-2">{{ msg }}</span>
    </div>
    <svg
      class="h-7 w-7 text-white bg-red-700 hover:bg-red-50 hover:text-red-700"
      viewBox="0 0 24 24"
      fill="none"
      stroke="currentColor"
      stroke-width="2"
      stroke-linecap="round"
      stroke-linejoin="round"
      @click="turnOff"
    >
      <rect x="3" y="3" width="18" height="18" rx="2" ry="2" />
      <line x1="9" y1="9" x2="15" y2="15" />
      <line x1="15" y1="9" x2="9" y2="15" />
    </svg>
  </div>
</template>

<script>
export default {
  props: {
    msg: String,
  },
  methods: {
    turnOff() {
      this.$emit("turnAlertOff");
    },
  },
};
</script>

<style></style>
