<template>
  <p>{{ Showdate }}</p>
</template>

<script>
export default {
  props: {
    date: String,
  },
  data() {
    return {
      Showdate: null,
    };
  },
  created() {
    const tdate = new Date(this.date);
    const current = new Date();
    if (tdate.toDateString() == current.toDateString()) {
      this.Showdate =
        "Today " +
        tdate
          .toLocaleTimeString()
          .replace(/([\d]+:[\d]{2})(:[\d]{2})(.*)/, "$1$3");
    } else {
      this.Showdate = tdate.toLocaleString();
    }
  },
};
</script>

<style></style>
