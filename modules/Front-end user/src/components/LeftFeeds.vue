<template>
  <div class="w-72 align-middle">
    <div class="sticky top-20 divide-y">
      <h1
        class="
          w-full
          py-2
          px-4
          mb-2
          hover:shadow hover:bg-gray-200
          inline-flex
          align-middle
          font-sans
          text-base
          font-bold
        "
      >
        Danh sách
      </h1>
      <p
        class="
          w-full
          py-2
          px-4
          mb-2
          hover:shadow hover:bg-gray-200
          inline-flex
          align-middle
          font-sans
          text-base
          font-bold
        "
        v-for="forum in forums"
        :key="forum.ID"
      >
        {{ forum.name }}
      </p>
    </div>
  </div>
</template>

<script>
export default {
  setup() {},
  data() {
    return {
      forums: [
        {
          ID: 1,
          name: "👦 Khoa đào tạo quốc tế",
        },
        {
          ID: 2,
          name: "🖼 Cộng đồng khóa K24",
        },
        {
          ID: 3,
          name: "📋 Câu lạc bộ tiếng anh thực tế",
        },
      ],
    }; //end return
  },
};
</script>
