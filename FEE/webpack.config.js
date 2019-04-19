var path = require('path');
const publicFldrPath = path.join(__dirname, 'public');
const buildJsFldrPath = path.join(publicFldrPath, 'build/js');

module.exports = {
  entry: './app/index.js',
  output: {
    path: buildJsFldrPath,
    filename: 'bundle.js'
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader"
        }
      }
    ]
  }
};