<template>
  <div>
    <div v-if="editor">
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleBold().run()"
        :class="{ 'is-active': editor.isActive('bold') }"
      >
        <svg
          class="h-5 w-5 text-black"
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
          <path d="M7 5h6a3.5 3.5 0 0 1 0 7h-6z" />
          <path d="M13 12h1a3.5 3.5 0 0 1 0 7h-7v-7" />
        </svg>
      </button>

      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleItalic().run()"
        :class="{ 'is-active': editor.isActive('italic') }"
      >
        <!-- In nghien -->
        <svg
          class="h-5 w-5 text-black"
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
          <line x1="11" y1="5" x2="17" y2="5" />
          <line x1="7" y1="19" x2="13" y2="19" />
          <line x1="14" y1="5" x2="10" y2="19" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleStrike().run()"
        :class="{ 'is-active': editor.isActive('strike') }"
      >
        <!-- Gach ngang chu -->
        <svg
          class="h-5 w-5 text-black"
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
          <line x1="5" y1="12" x2="19" y2="12" />
          <path d="M16 6.5a4 2 0 0 0 -4 -1.5h-1a3.5 3.5 0 0 0 0 7" />
          <path d="M16.5 16a3.5 3.5 0 0 1 -3.5 3h-1.5a4 2 0 0 1 -4 -1.5" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleCode().run()"
        :class="{ 'is-active': editor.isActive('code') }"
      >
        <!-- code block -->
        <svg
          class="h-5 w-5 text-black"
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
          <polyline points="7 8 3 12 7 16" />
          <polyline points="17 8 21 12 17 16" />
          <line x1="14" y1="4" x2="10" y2="20" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().unsetAllMarks().run()"
      >
        <!-- clear marks -->
        <svg
          class="h-5 w-5 text-black"
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
          <path d="M17 15l4 4m0 -4l-4 4" />
          <path d="M7 6v-1h11v1" />
          <line x1="7" y1="19" x2="11" y2="19" />
          <line x1="13" y1="5" x2="9" y2="19" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().clearNodes().run()"
      >
        <svg
          class="h-6 w-6 text-black"
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
          <path d="M19.95 11a8 8 0 1 0 -.5 4m.5 5v-5h-5" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleHeading({ level: 1 }).run()"
        :class="{ 'is-active': editor.isActive('heading', { level: 1 }) }"
      >
        <svg
          class="h-6 w-6 text-black"
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          aria-hidden="true"
          role="img"
          preserveAspectRatio="xMidYMid meet"
          viewBox="0 0 24 24"
        >
          <path
            d="M11 7h2v10h-2v-4H7v4H5V7h2v4h4V7zm6.57 0A4.742 4.742 0 0 1 15 9v1h2v7h2V7h-1.43z"
            fill="currentColor"
          />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
        :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }"
      >
        <svg
          class="h-6 w-6 text-black"
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          aria-hidden="true"
          role="img"
          preserveAspectRatio="xMidYMid meet"
          viewBox="0 0 24 24"
        >
          <path
            d="M9 7h2v10H9v-4H5v4H3V7h2v4h4V7zm8 8c.51-.41.6-.62 1.06-1.05c.437-.4.848-.828 1.23-1.28a6.4 6.4 0 0 0 .85-1.28c.199-.39.305-.822.31-1.26a3.007 3.007 0 0 0-.27-1.28a2.902 2.902 0 0 0-.76-1a3.485 3.485 0 0 0-1.17-.63a4.766 4.766 0 0 0-1.5-.22c-.359 0-.717.033-1.07.1a4.895 4.895 0 0 0-1 .29a4.431 4.431 0 0 0-.86.49c-.287.21-.561.437-.82.68l1.24 1.22a5.829 5.829 0 0 1 1-.7c.35-.201.747-.304 1.15-.3a1.91 1.91 0 0 1 1.27.38c.311.278.477.684.45 1.1a2.127 2.127 0 0 1-.36 1.11a7.126 7.126 0 0 1-1 1.25c-.44.43-.98.92-1.59 1.43c-.61.51-1.41 1.06-2.16 1.65V17h8v-2h-4z"
            fill="currentColor"
          />
        </svg>
      </button>
      <button
        class="menubar__button"
        viewBox="0 0 24 24"
        @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
        :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }"
      >
        <svg
          class="h-6 w-6 text-black"
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          aria-hidden="true"
          role="img"
          preserveAspectRatio="xMidYMid meet"
          viewBox="0 0 24 24"
        >
          <path
            d="M14.11 14.218c.355.287.749.523 1.17.7c.434.181.9.273 1.37.27c.484.017.965-.086 1.4-.3a.92.92 0 0 0 .55-.84a1.136 1.136 0 0 0-.15-.58a1.137 1.137 0 0 0-.51-.43a3.65 3.65 0 0 0-1-.27a9.786 9.786 0 0 0-1.52-.1v-1.57a4.73 4.73 0 0 0 2.17-.36a1.06 1.06 0 0 0 .63-.93a.9.9 0 0 0-.38-.81a2.002 2.002 0 0 0-1.13-.28a2.836 2.836 0 0 0-1.14.28a5.568 5.568 0 0 0-1.06.62l-1.22-1.39a6.55 6.55 0 0 1 1.64-.9a5.354 5.354 0 0 1 1.9-.33a6.448 6.448 0 0 1 1.56.17c.431.101.84.277 1.21.52c.325.211.595.495.79.83c.19.342.287.729.28 1.12c.011.48-.177.943-.52 1.28a3.867 3.867 0 0 1-1.46.86v.06c.609.14 1.175.425 1.65.83c.437.382.68.94.66 1.52c.005.421-.113.835-.34 1.19a2.853 2.853 0 0 1-.9.88a4.462 4.462 0 0 1-1.32.55a6.395 6.395 0 0 1-1.57.19a6.42 6.42 0 0 1-2.31-.37a4.29 4.29 0 0 1-1.57-1l1.12-1.41zM9 11H5V7H3v10h2v-4h4v4h2V7H9v4z"
            fill="currentColor"
          />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleBulletList().run()"
        :class="{ 'is-active': editor.isActive('bulletList') }"
      >
        <!-- bullet list cham dau dong -->
        <svg
          class="h-6 w-6 text-black"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <line x1="8" y1="6" x2="21" y2="6" />
          <line x1="8" y1="12" x2="21" y2="12" />
          <line x1="8" y1="18" x2="21" y2="18" />
          <line x1="3" y1="6" x2="3.01" y2="6" />
          <line x1="3" y1="12" x2="3.01" y2="12" />
          <line x1="3" y1="18" x2="3.01" y2="18" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleOrderedList().run()"
        :class="{ 'is-active': editor.isActive('orderedList') }"
      >
        <!-- ordered list danh số -->
        <svg
          class="h-6 w-6 text-black"
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
          <path d="M3.5 5.5l1.5 1.5l2.5 -2.5" />
          <path d="M3.5 11.5l1.5 1.5l2.5 -2.5" />
          <path d="M3.5 17.5l1.5 1.5l2.5 -2.5" />
          <line x1="11" y1="6" x2="20" y2="6" />
          <line x1="11" y1="12" x2="20" y2="12" />
          <line x1="11" y1="18" x2="20" y2="18" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleCodeBlock().run()"
        :class="{ 'is-active': editor.isActive('codeBlock') }"
      >
        <!-- code block -->
        <svg
          class="h-5 w-5 text-black"
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
          <path
            d="M7 4a2 2 0 0 0 -2 2v3a2 3 0 0 1 -2 3a2 3 0 0 1 2 3v3a2 2 0 0 0 2 2"
          />
          <path
            d="M17 4a2 2 0 0 1 2 2v3a2 3 0 0 0 2 3a2 3 0 0 0 -2 3v3a2 2 0 0 1 -2 2"
          />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleBlockquote().run()"
        :class="{ 'is-active': editor.isActive('blockquote') }"
      >
        <!-- blockquote -->
        <svg
          class="h-5 w-5 text-black"
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
          <line x1="4" y1="4" x2="4" y2="20" />
          <rect x="8" y="9" width="12" height="6" rx="2" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().setHorizontalRule().run()"
      >
        <!-- horizontal rule gach ngang -->
        <svg
          class="h-6 w-6 text-black"
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
          <line x1="5" y1="12" x2="19" y2="12" />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().undo().run()"
      >
        <!-- undo -->
        <svg
          class="h-5 w-5 text-black"
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
          <path d="M15 4.55a8 8 0 0 0 -6 14.9m0 -4.45v5h-5" />
          <path
            d="M13 19.95a8 8 0 0 0 5.3 -12.8"
            stroke-dasharray=".001 4.13"
          />
        </svg>
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().redo().run()"
      >
        <!-- redo -->
        <svg
          class="h-5 w-5 text-black"
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
          <path d="M9 4.55a8 8 0 0 1 6 14.9m0 -4.45v5h5" />
          <path
            d="M11 19.95a8 8 0 0 1 -5.3 -12.8"
            stroke-dasharray=".001 4.13"
          />
        </svg>
      </button>
    </div>
    <editor-content
      class="Prose py-4"
      :editor="editor"
      v-model="text"
      @click="remove"
    />
  </div>
</template>

<script>
import { Editor, EditorContent } from "@tiptap/vue-3";
import StarterKit from "@tiptap/starter-kit";

export default {
  components: {
    EditorContent,
  },
  props: {
    content: String,
    msg: Boolean,
  },
  emits: ["accepted"],
  watch: {
    msg() {
      this.sendcontent();
    },
  },

  data() {
    return {
      editor: null,
      text: null,
      isFirst: true,
    };
  },

  mounted() {
    if (this.content != "" && this.content != null) {
      this.editor = new Editor({
        extensions: [StarterKit],
        content: this.content,
      });
      this.isFirst = false;
    } else {
      this.editor = new Editor({
        extensions: [StarterKit],
        content: `
        <h1>Viết nội dung vào đây </h1>
      `,
      });
    }
  },

  beforeUnmount() {
    this.editor.destroy();
  },
  methods: {
    sendcontent() {
      this.$emit("posting", this.editor.getHTML());
    },
    remove() {
      if (this.isFirst) {
        this.editor.commands.clearContent();
        this.isFirst = false;
      }
    },
    setcontent(content) {
      this.editor.commands.setContent(content);
    },
  },
};
</script>
