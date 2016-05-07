var gcm = require('node-gcm');
var mongo = require("./dbconfig");


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
	
			coll.find( { $and : [ { "description" : { $regex: myarr.description } }, 
			                      { "address.City" : { $regex: myarr.City } }, 
			                      { "address.Zip" : { $regex: myarr.Zip } }, 
			                      { "property_type" : { $regex: myarr.property_type } },
			                      { "rent" : { $lt : myarr.max_rent, $gt : myarr.min_rent } }] } )
			                      .toArray(function(err, docs) {	
			                    	  if(docs){	
			                    		  if(type == 1 && id != null){
			                    			  for(var j = 0 ; j<docs.length; j++ ){
			                    				  if(docs[j]._id == id){
			                    					  console.log('send notification to him');
			                    					  if(myarr){
			                    						  uid = myarr.user_id;
			                    						  console.log("hey..");
			                    						  console.log(uid);
			                    					  }	
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
		console.log(' ID ' + id);
		if(type == 1 && id == null){
			console.log('in callback');
			callback();
		}else{
			mongo.connect(function(err, db){
				if(err){
					console.log('unable to connect to mongo');
					callback();
				}else{
					var searchcol = mongo.collection('search_queries');
//					var cursor = searchcol.find( {  "rate" : type } );
//					cursor.each(function(err, doc) {
//					      if (doc != null) {
//					    	 console.log("first");
//					         console.log(doc);
//					      } else {
//					    	 console.log("no docs");
//					    	 callback();
//					      }
//					   });
					
					searchcol.find( {  "rate" : type } ).toArray(function(err, docs) {
						if(docs){												
							var myArray = [];
							for(var i=0; i<docs.length; i++){
								//myArray.push({ "user_id":docs[i].user_id, "rate": docs[i].rate, "description":docs[i].description, "City":docs[i].City,"Zip": docs[i].Zip, "Make": docs[i].Make, "property_type":docs[i].property_type, "max_rent":docs[i].max_rent, "min_rent":docs[i].min_rent});
								var ma = docs[i];
								constructNotification1(ma, type, id);
							}
							console.log('length is '+ myArray.length);
//							constructNotification1(myArray, type, id);
						}else{						
							console.log('No Docs');
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
	

