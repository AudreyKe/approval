const path = require('path');
const express = require('express');
const apiMocker = require('webpack-api-mocker');

const app = express();

apiMocker(app, path.resolve('./src/libs/mocker/index.js'))
app.listen(8000);
console.log('=> http://localhost:8000')
