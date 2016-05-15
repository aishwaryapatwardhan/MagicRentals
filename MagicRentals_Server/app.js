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

app.post('/addPostings', postings.addPost_Post);
app.get('/addPostings', postings.addPost_Get);

app.get('/getPostsByUser',postings.getAllPosts_Get);
app.post('/getPostsByUser',postings.getAllPosts_Post);

app.post('/updatePostings', postings.updatePost_Post);
app.get('/updatePostings', postings.updatePost_Get);

app.post('/updateStatus', postings.updateStatus_Post);
app.get('/updateStatus', postings.updateStatus_Get);

app.post('/updateViewCount', postings.updateViewCount_Post);
app.get('/updateViewCount', postings.updateViewCount_Get);

app.get('/searchPosts', postings.searchPosts_Get);
app.post('/searchPosts', postings.searchPosts_Post);

app.get('/saveSearch',postings.saveSearchRes_Get);
app.post('/saveSearch',postings.saveSearchRes_Post);


//login APIs
app.post('/addUser', users.addUser);


//Fav APIs
app.get('/getAllFav', fav.getAllFav_Get);
app.post('/getAllFav', fav.getAllFav_Post);

app.post('/addFav',fav.addFav_Post);
app.get('/addFav',fav.addFav_Get);

//app.post('/removeFav', fav.removeFav);
app.get('/removeFav', fav.removeFav_Get);
app.post('/removeFav', fav.removeFav_Post);

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
