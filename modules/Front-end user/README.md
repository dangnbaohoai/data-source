# Front-end site of School connect

## Yarn setup using npm
```
npm install --global yarn
```
## Yarn set version
```
yarn set version 1.22.11
```
## Project setup
```
yarn install
```

### Compiles and hot-reloads for development
```
yarn serve
```

### Compiles and minifies for production
```
yarn build
```

### Lints and fixes files
```
yarn lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

# Peronal

## Deploy problems
> This site has been deploy to Netlify but can't  back-end API from Heroku, except login API and site preview API, i don't know why 👹👹👹👹 --> Redirect
```
https://schoolconnect.netlify.app/
```

## USING
```
Yarn / VueJS 3 / VueX / Tailwind CSS v2.2.6 / FireBase / SCSS / Axios / Tiptap
```

## Sloved and unslove task -Frontend
- [x] Main page show newest contents.
    - [x] Left feed contain forum list.
    - [x] Right feed contain calander and event.
- [x] Create post.
    - [x] Edittor for create new post.
    - [ ] Edittor for edit old post.

- [x] Forum page show forum post and members.
    - [x] Reported post -[ ] Delete post
    - [x] Reported comment
    - [x] Censor post
    - [x] Censor user

- [x] Personal page.
    - [x] Edit personal data.
    - [x] Change avatar
- [x] Other user personal page.
    - [x] Open messenger
    - [x] Upload inmage to firebase store.
- [x] Censor function:
    - [x] Dash board for quick review status.
    - [x] Post report  -Delete - Viewpost - -ActionPost
    - [x] Censor post
    - [x] Censor user
- [x] Messenger /Normal api/
    - [x] Create new chat session.
    - [x] View chat list.
    - [ ] Upload image
- [ ] Account management
    - [x] Change password
        - [x] Strange of password
        - [x] 8 degit requied
