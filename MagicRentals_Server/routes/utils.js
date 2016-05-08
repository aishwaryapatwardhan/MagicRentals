var gcm = require('node-gcm');
var mongo = require("./dbconfig");

exports.push = function (query,itmnm, itmdsc, type, callback){	
	
		var device_tokens = []; //create array for storing device tokens
		//var device_tokens = "APA91bG-6WnC1_qLPT3Pq69OEFj2MUxbaI2j9X1kXlBE0NFb9ZHU_k56eO6fCNlDUEIu-qdJ6OWTsiD6YM11aMF2ULhfON78vc8yalCP2auNYsro2jVl0tx8LYOhHwCrR7G-UGYUCyQK"
	    var retry_times = 4; //the number of times to retry sending the message if it fails

	    var sender = new gcm.Sender('AIzaSyBtXqwpDcgQRfkB05emjYLqeooq1Hkw1YE'); //create a new sender
	    var message = new gcm.Message(); //create a new message

	    message.addData('title', type +' Alert - '+itmnm );
	    message.addData('message', itmdsc);
	    message.addData('sound', 'notification');

	    message.collapseKey = 'testing'; //grouping messages
	    message.delayWhileIdle = true; //delay sending while receiving device is offline
	    message.timeToLive = 3; //the number of seconds to keep the message on the server if the device is offline
		
	};
	
exports.notify = function(id, type, callback){
	
	console.log("In notify method");
	
	if(type == 1 || type == 2 || type == 3){
		
		console.log(' notify type ' + type);
		
		if(type == 1 && id == null){
			callback();
		}else{
			mongo.connect(function(err, db){
				if(err){
					console.log('unable to connect to mongo');
					callback();
				}else{
					var searchcol = mongo.collection('search_queries');
					var cursor = searchcol.find( {  "rate" : type } );
					cursor.each(function(err, doc) {
					      if (doc != null) {
					         console.log(doc);
					      } else {
					    	 console.log("no docs");
					    	 callback();
					      }
					   });
				}
			});
		}	
	}else{
		console.log('Invalid');
		callback();
	}
};
	

