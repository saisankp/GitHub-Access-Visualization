module.exports = {
  transpileDependencies: ["vuetify"],
  configureWebpack: {
    devServer: {
      proxy : {
        '/api': {
          target: 'http://localhost:3000'
      }
      },
      historyApiFallback: true,
      noInfo: true,
      progress: false,
      watchOptions: {
        poll: true,
      },
    },
  },
};
