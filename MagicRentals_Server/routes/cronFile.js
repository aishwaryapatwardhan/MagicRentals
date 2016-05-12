var CronJob = require('cron').CronJob;
var utils = require('./utils');

var dailyJob = new CronJob({
	  cronTime: '00 30 0 * * 1-6',
	  onTick: function() {
	    console.log('Scheduled job running... daily');
	    utils.notify(null, 2, function() {
	    	console.log('daily notification triggered');
	    })
	  },
	  start: true,
	  timeZone: 'America/Los_Angeles'
});

var weeklyJob = new CronJob({
	  cronTime: '00 00 20 * * 0',
	  onTick: function() {
	    console.log('Scheduled job running...weekly');
	    utils.notify(null, 3, function() {
	    	console.log('daily notification triggered');
	    })
	  },
	  start: true,
	  timeZone: 'America/Los_Angeles'
});

exports.startDaily = function(){
	console.log('Start the cron job');
	dailyJob.start();
}; 

exports.startWeekly = function(){
	console.log('Start the cron job');
	weeklyJob.start();
};
