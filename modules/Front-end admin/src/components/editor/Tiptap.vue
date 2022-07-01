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
        h1
      </button>
      <button
        class="menubar__button"
        @click="editor.chain().focus().toggleHeading({ level: 2 }).run()"
        :class="{ 'is-active': editor.isActive('heading', { level: 2 }) }"
      >
        h2
      </button>
      <button
        class="menubar__button"
        viewBox="0 0 24 24"
        @click="editor.chain().focus().toggleHeading({ level: 3 }).run()"
        :class="{ 'is-active': editor.isActive('heading', { level: 3 }) }"
      >
        h3
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
    <editor-content class="Prose py-4" :editor="editor" v-model="text" />
  </div>
</template>

<script>
import { Editor, EditorContent } from "@tiptap/vue-3";
import StarterKit from "@tiptap/starter-kit";

export default {
  components: {
    EditorContent,
  },
  props: ["msg"],
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
    };
  },

  mounted() {
    this.editor = new Editor({
      extensions: [StarterKit],
      content: `
        <h1>Viết nội dung vào đây </h1>
      `,
    });
  },

  beforeUnmount() {
    this.editor.destroy();
  },
  methods: {
    sendcontent() {
      this.$emit("posting", this.editor.getHTML());
    },
  },
};
</script>
