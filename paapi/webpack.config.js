const path = require('path');
const CopyPlugin = require("copy-webpack-plugin");

module.exports = {
    devtool: 'inline-source-map',
    mode: 'development',
    entry: {
        'ssp/index': './src/ssp/index.ts',
        'ssp/logic': './src/ssp/logic.ts'
    },
    module: {
        rules: [
            {
                test: /\.ts$/,
                use: 'ts-loader',
                exclude: [/node_modules/],
            }
        ]
    },
    resolve: {
        extensions: ['.ts','.js'],
    },
    output: {
        filename: '[name].js',
        path: path.resolve(__dirname, 'dist/dev'),
        publicPath: '',
        clean: true,
    },
    // copy pluginの設定
    plugins: [
        new CopyPlugin({
            patterns: [
                {
                    context: path.resolve(__dirname, 'src'),
                    from: path.resolve(__dirname, 'src/**/*.html'),
                    to: path.resolve(__dirname, 'dist/dev/'),
                },
                {
                    context: path.resolve(__dirname, 'src'),
                    from: path.resolve(__dirname, 'src/**/*.png'),
                    to: path.resolve(__dirname, 'dist/dev/'),
                }
            ]
        })
    ],
    devServer: {
        host: '0.0.0.0',
        port: '8080',
        webSocketServer: false,
        static: {
            directory: path.join(__dirname, 'dist/dev')
        },
        allowedHosts: [ 'all' ],
        headers: {
            'Ad-Auction-Allowed': true,
            'SUPPORTS-LOADING-MODE': 'fenced-frame',
            // https://github.com/WICG/turtledove/blob/main/Fenced_Frames_Ads_Reporting.md#cross-origin-support
            'Allow-Fenced-Frame-Automatic-Beacons': true,
        }
    }
}
