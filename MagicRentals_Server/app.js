var express = require('express');
var http = require('http');
var path = require('path');
var favicon = require('static-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');

var routes = require('./routes');
var users = require('./routes/user');
var fav = require('./routes/favorites');

//postings APIs
var postings = require('./routes/postings');


var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(favicon());
app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded());
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(app.router);

app.get('/', routes.index);
app.get('/users', users.list);

//posting APIs
app.post('/addPostings', postings.addPost);
app.get('/getPostsByUser',postings.getAllPosts);
app.post('/getPostsByUser',postings.getAllPosts);
app.post('/updatePostings', postings.updatePost);
app.post('/updateStatus', postings.updateStatus);
app.post('/updateViewCount', postings.updateViewCount);
app.get('/updateViewCount', postings.updateViewCount);
app.get('/searchPosts', postings.searchPosts);
app.post('/searchPosts', postings.searchPosts);
app.get('/saveSearch',postings.saveSearchRes);
//login APIs
app.post('/addUser', users.addUser);


//Fav APIs
//app.get('/getAllFav', fav.getAllFav);
app.post('/getAllFav', fav.getAllFav);
//app.post('/addFav',fav.addFav);
app.get('/addFav',fav.addFav);
//app.post('/removeFav', fav.removeFav);
app.get('/removeFav', fav.removeFav);
app.get('/checkFav',fav.checkFav);

//test API
app.get('/test', routes.test123);

/// catch 404 and forwarding to error handler
app.use(function(req, res, next) {
    var err = new Error('Not Found');
    err.status = 404;
    next(err);
});

/// error handlers

// development error handler
// will print stacktrace
if (app.get('env') === 'development') {
    app.use(function(err, req, res, next) {
        res.render('error', {
            message: err.message,
            error: err
        });
    });
}

// production error handler
// no stacktraces leaked to user
app.use(function(err, req, res, next) {
    res.render('error', {
        message: err.message,
        error: {}
    });
});


module.exports = app;
