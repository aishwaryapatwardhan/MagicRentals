var gcm = require('node-gcm');
var mongo = require("./dbconfig");


exports.push = function (query,itmnm, itmdsc, type, callback){	


function constructNotification (myarr, type, id){
	 
	var uid;
	mongo.connect(function(err, db){
		if(err){
			console.log('unable to connect to mongo');
			return;
		}else{
			
			var coll = mongo.collection('rental_posting');
			
			for(var i=0; i< myarr.length; i++ ){
				
				
				//console.log(myarr[i].user_id+' uid');
				coll.find( { $and : [ { "description" : { $regex: myarr[i].description } }, 
				                      { "address.City" : { $regex: myarr[i].City } }, 
				                      { "address.Zip" : { $regex: myarr[i].Zip } }, 
				                      { "property_type" : { $regex: myarr[i].property_type } },
				                      { "rent" : { $lt : myarr[i].max_rent, $gt : myarr[i].min_rent } }] } )
				                      .toArray(function(err, docs) {	
					if(docs){	
						
						if(type == 1 && id != null){
							for(var j = 0 ; j<docs.length; j++ ){
								if(docs[j]._id == id){
									console.log('send notification to him');
									if(myarr[i]){
										uid = myarr[i].user_id;
										console.log("hey..");
										console.log(uid);
									}
								}
							}
						}else if(type ==2 || type ==3){
							console.log('weekly and monthly notifications');
						}
//						result.data = docs;
//						result.code = 200; 
//						result.status = "Successful";
						
					}else{						
						console.log('No updates');
					}							
				});
				//console.log(myarr[i].user_id);
			}		
		}
	});
}


function constructNotification1 (myarr, type, id){
	 
	mongo.connect(function(err, db){
		if(err){
			console.log('unable to connect to mongo');
			return;
		}else{
			
			var coll = mongo.collection('rental_posting');
			uid = myarr.user_id;
			coll.find( { $and : [ { "description" : { $regex: myarr.description } }, 
			                      { "address.City" : { $regex: myarr.City } }, 
			                      { "address.Zip" : { $regex: myarr.Zip } }, 
			                      { "property_type" : { $regex: myarr.property_type } },
			                      { "rent" : { $lt : myarr.max_rent, $gt : myarr.min_rent } }] } )
			                      .toArray(function(err, docs) {	
			                    	  if(docs){
			                    		  //const docs1 = docs;
			                    		  if(type == 1 && id != null){
			                    			  for(var j = 0 ; j<docs.length; j++ ){
			                    				  if(docs[j]._id == id){
			                    					  const results = docs[j];
			                    					  console.log('send notification to him');
			                    					  var coll1 = mongo.collection('users');
			                    					  coll1.findOne({ "uid" : uid },function(err, doc){
			                    						  if(err){
			                    							  console.log('error');
			                    						  }else{
			                    							  console.log('user details');
			                    							  console.log(doc);
			                    							  console.log(results);
			                    						  }
			                    					  });
			                    				  }
			                    			  }
			                    		  }else if(type ==2 || type ==3){
			                    			  console.log('weekly and monthly notifications');
			                    		  }
			                    	  }else{						
			                    		  console.log('No updates');
			                    	  }							
			                      });				
			}
		});
}


exports.push = function (query,itmnm, itmdsc, type, device_tokens, callback){	

	
		//var device_tokens = []; //create array for storing device tokens
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
	

