let path = require('path');
let ExtractTextPlugin = require('extract-text-webpack-plugin');

module.exports = {
    entry: './app/js/index.js',
    output: {
        filename: 'bundle.js',
        path: path.resolve(__dirname, 'dist')
    },
    module: {
        rules: [
            {
                test: /\.css$/,
                use: ExtractTextPlugin.extract({
                    use: 'css-loader',
                })
            },
            {
                test: /\.js$/,
                exclude: /node_modules/,
                loader: 'babel-loader',
                query: {
                    presets: ['env'],
                },
            },
            {test: /bootstrap[\/\\]dist[\/\\]js[\/\\]umd[\/\\]/, loader: 'imports-loader?jQuery=jquery'},
            {test: /\.(woff2?|svg)$/, loader: 'url-loader?limit=10000'},
            {test: /\.(ttf|eot)$/, loader: 'file-loader'},
        ]
    }
    ,plugins: [
        new ExtractTextPlugin('styles.css'),
    ]
};