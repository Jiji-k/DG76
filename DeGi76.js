var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');

var indexRouter = require('./routes/index');
var usersRouter = require('./routes/users');

var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'ejs');

app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));

app.use('/', indexRouter);
app.use('/users', usersRouter);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;

var firebase = require('firebase-admin');
var request = require('request');
var {google} = require('googleapis');

// Fetch the service account key JSON file contents
var key = require("./routes/degi76-e7d8a-firebase-adminsdk-klent-070bb93fe8.json");

var API_KEY = 'AIzaSyCWYL1nqsrug3kGGuigzE5TEJLSoO7mWgc';

// Initialize the app with a service account, granting admin privileges
firebase.initializeApp({
  credential: firebase.credential.cert(key),
  databaseURL: "https://degi76-e7d8a-default-rtdb.firebaseio.com"
});
ref = firebase.database().ref();
const admin = require('firebase-admin');

function associateResultingReaction(text) {
  if (text === 1) {
    return "Fire!";
  } else {
    return "Safe, Chill";
  }
}

function listenForDataPush() {
    const dataRef = ref.child('fire');
    const reactionRef = ref.child('reaction');
    dataRef.on('child_added', function(requestSnapshot) {
      var text = requestSnapshot.val();
      // Call associateResultingReaction function with the text value
      var resultingReaction = associateResultingReaction(text);
      reactionRef.set({
        value: resultingReaction
      }).then(() => {
        console.log('Reaction updated in Firebase');
      }).catch((error) => {
        console.error('Error updating reaction in Firebase:', error);
      });
    }, function(error) {
      console.error(error);
    });
  }
  
listenForDataPush();
