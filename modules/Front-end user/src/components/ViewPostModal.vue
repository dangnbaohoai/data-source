<template>
  <div
    class="
      min-w-screen
      h-screen
      animated
      fadeIn
      faster
      fixed
      left-0
      top-0
      block
      justify-center
      items-start
      inset-0
      z-50
      overflow-auto
    "
    style="background-color: rgba(255, 255, 255, 0.7)"
    id="modal-id"
    @click="close"
  >
    <div
      class="
        max-w-2xl
        w-1/2
        overflow-hidden
        rounded-lg
        shadow-lg
        text-left
        block
        my-5
        divide-y
        bg-gray-50
        mx-auto
      "
    >
      <div class="not-italic font-light px-6 text-yellow-600 bg-gray-100">
        > Tên group hoặc topic
      </div>
      <div class="Prose px-6 py-4">
        <div class="user textcentre flex mb-5">
          <img
            class="inline object-cover w-12 h-12 mr-5 rounded-full"
            :src="content.Avatar"
            alt="Profile image"
          />
          <div>
            <h4 class="text-xl font-semibold tracking-tight text-gray-800">
              {{ content.Name }}
            </h4>
            <p class="text-xs mr-2">date {{ content.Date }}</p>
          </div>
        </div>
        <div>
          <h1 class="mb-3 text-xl font-semibold tracking-tight text-gray-800">
            This is the title
          </h1>

          <p class="leading-normal text-gray-700 text-left">
            {{ content.Text }}
          </p>
        </div>
      </div>

      <div class="likezone px-6">
        <button class="button-54 flex" role="button">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            class="h-6 w-6"
            fill="none"
            viewBox="0 0 24 24"
            stroke="currentColor"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              stroke-width="2"
              d="M4.318 6.318a4.5 4.5 0 000 6.364L12 20.364l7.682-7.682a4.5 4.5 0 00-6.364-6.364L12 7.636l-1.318-1.318a4.5 4.5 0 00-6.364 0z"
            />
          </svg>
          {{ content.Like }} Likes
        </button>
      </div>
    </div>
    <!--------------------------------------------------------------------------------- Comment -->
    <div
      class="
        max-w-2xl
        w-1/2
        overflow-hidden
        rounded-lg
        shadow-lg
        text-left
        block
        my-2
        divide-y
        bg-gray-50
        mx-auto
      "
    >
      <div class="space-y-4 mx-4 my-2">
        <!-- Comment funtion -->
        <div class="flex">
          <div class="flex-shrink-0 mr-3">
            <img
              class="mt-2 rounded-full w-8 h-8 sm:w-10 sm:h-10 text-left"
              src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"
              alt=""
            />
          </div>
          <div
            class="
              flex-1
              border
              rounded-lg
              sm:px-1 sm:py-1
              leading-relaxed
              text-left
              flex
              justify-items-stretch
            "
          >
            <textarea class="w-full resize-none rounded-l-lg bg-gray-100" />

            <button
              class="
                flex
                p-2
                bg-blue-400
                hover:bg-blue-500
                focus:bg-gray-400
                rounded-r-lg
              "
            >
              <svg
                class="h-8 w-8 text-white"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                stroke-width="2"
                stroke="currentColor"
                fill="none"
                stroke-linecap="round"
                stroke-linejoin="round"
              >
                <path stroke="none" d="M0 0h24v24H0z" />
                <path d="M15 10l-4 4l6 6l4 -16l-18 7l4 2l2 6l3 -4" />
              </svg>
            </button>
          </div>
        </div>
        <!-- END comment -->
        <div class="flex">
          <div class="flex-shrink-0 mr-3">
            <img
              class="mt-2 rounded-full w-8 h-8 sm:w-10 sm:h-10 text-left"
              src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"
              alt=""
            />
          </div>
          <div
            class="
              flex-1
              border
              rounded-lg
              px-4
              py-2
              sm:px-6 sm:py-4
              leading-relaxed
              text-left
            "
          >
            <strong>Sarah</strong>
            <span class="text-xs text-gray-400">3:34 PM</span>
            <p class="text-sm">
              Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
              nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam
              erat, sed diam voluptua.
            </p>
            <div class="mt-4 flex items-center">
              <div class="flex -space-x-2 mr-2">
                <img
                  class="rounded-full w-6 h-6 border border-white"
                  src="https://images.unsplash.com/photo-1554151228-14d9def656e4?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=100&h=100&q=80"
                  alt=""
                />
                <img
                  class="rounded-full w-6 h-6 border border-white"
                  src="https://images.unsplash.com/photo-1513956589380-bad6acb9b9d4?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=100&h=100&q=80"
                  alt=""
                />
              </div>
              <div class="text-sm text-gray-500 font-semibold">5 Replies</div>
            </div>
          </div>
        </div>

        <div class="flex">
          <div class="flex-shrink-0 mr-3">
            <img
              class="mt-2 rounded-full w-8 h-8 sm:w-10 sm:h-10"
              src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"
              alt=""
            />
          </div>
          <div
            class="
              flex-1
              border
              rounded-lg
              px-4
              py-2
              sm:px-6 sm:py-4
              leading-relaxed
              text-left
            "
          >
            <strong>Sarah</strong>
            <span class="text-xs text-gray-400">3:34 PM</span>
            <p class="text-sm">
              Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam
              nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam
              erat, sed diam voluptua.
            </p>

            <h4
              class="
                my-5
                uppercase
                tracking-wide
                text-gray-400
                font-bold
                text-xs
              "
            >
              Replies
            </h4>

            <div class="space-y-4">
              <div class="flex">
                <div class="flex-shrink-0 mr-3">
                  <img
                    class="mt-3 rounded-full w-6 h-6 sm:w-8 sm:h-8"
                    src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"
                    alt=""
                  />
                </div>
                <div
                  class="
                    flex-1
                    bg-gray-100
                    rounded-lg
                    px-4
                    py-2
                    sm:px-6 sm:py-4
                    leading-relaxed
                  "
                >
                  <strong>Sarah</strong>
                  <span class="text-xs text-gray-400">3:34 PM</span>
                  <p class="text-xs sm:text-sm">
                    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed
                    diam nonumy eirmod tempor invidunt ut labore et dolore magna
                    aliquyam erat, sed diam voluptua.
                  </p>
                </div>
              </div>
              <div class="flex">
                <div class="flex-shrink-0 mr-3">
                  <img
                    class="mt-3 rounded-full w-6 h-6 sm:w-8 sm:h-8"
                    src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"
                    alt=""
                  />
                </div>
                <div
                  class="
                    flex-1
                    bg-gray-100
                    rounded-lg
                    px-4
                    py-2
                    sm:px-6 sm:py-4
                    leading-relaxed
                  "
                >
                  <strong>Sarah</strong>
                  <span class="text-xs text-gray-400">3:34 PM</span>
                  <p class="text-xs sm:text-sm">
                    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed
                    diam nonumy eirmod tempor invidunt ut labore et dolore magna
                    aliquyam erat, sed diam voluptua.
                  </p>
                </div>
              </div>
              <div class="flex">
                <div class="flex-shrink-0 mr-3">
                  <img
                    class="mt-3 rounded-full w-6 h-6 sm:w-8 sm:h-8"
                    src="https://images.unsplash.com/photo-1604426633861-11b2faead63c?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=200&h=200&q=80"
                    alt=""
                  />
                </div>
                <div
                  class="
                    flex-1
                    bg-gray-100
                    rounded-lg
                    px-4
                    py-2
                    sm:px-6 sm:py-4
                    leading-relaxed
                  "
                >
                  <strong>Sarah</strong>
                  <span class="text-xs text-gray-400">3:34 PM</span>
                  <p class="text-xs sm:text-sm">
                    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed
                    diam nonumy eirmod tempor invidunt ut labore et dolore magna
                    aliquyam erat, sed diam voluptua.
                  </p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      content: {
        ID: 3,
        Name: "Jonny",
        Date: 12 - 5 - 2021,
        Avatar: "https://robohash.org/velvoluptatemea.png?size=50x50&set=set1",
        Text: "Today is the day",
        Likes: 45,
      },
    };
  },
  methods: {
    close() {
      console.log("CLOSE");
      this.$emit("close");
    },
  },
};
</script>

<style></style>
