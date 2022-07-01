module.exports = {
  // options...
  devServer: {
    proxy: "https://school-connection.herokuapp.com",
  },
  pwa: {
    workboxOptions: {
      exclude: [/_redirects/]
    }
  },
};
