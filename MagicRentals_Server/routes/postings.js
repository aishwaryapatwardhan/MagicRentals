/**
 *  Created By : Raghavendra R Guru
 *  Last updated : 04/21/2016
 */

var mongo = require("./dbconfig");
var mailer = require('./mailer');
var utils = require('./utils');
var formidable = require('formidable');

//Add postings
exports.addPost = function(req, res){
		
	console.log("This is a addPost API call");
	
	var result = {};
	var mailOptions={
//	        from : "magicrentals11@gmail.com",
//	        to : "raghavendra1810@gmail.com",
//	        subject : "Message from magic rentals",
//	        text : "mail from magicrentals.. test mail",
//	        html : "HTML GENERATED"
	};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	    
	    console.log(fields.user_id + ": user_id, "+fields.Street+" fields.Street, "+fields.bath+" fields.room "+ fields.room + " , " + fields.email +", "+ fields.Mobile);
//	    var user_id = fields.user_id;
//	 	var Street = fields.Street;
//	 	var City = fields.City;
//	 	var State = fields.State;
//	 	var Zip = fields.Zip;
//	 	var property_type = fields.property_type;
//	 	var bath = Number(fields.bath);
//	 	var room = Number(fields.room);
//	 	var area = Number(fields.area);
//	 	var rent = Number(fields.rent);
//	 	var email = fields.email;
//	 	var Mobile = fields.Mobile;
//	 	var description = fields.description;
//	 	var Images = fields.Image;
//	 	var other_details = fields.other_details;
//	 	var Status = fields.Status;
//	 	var view_count = Number(fields.view_count);
//	    var nickName = fields.nickName;
	     
	 	var nickName = req.param('nickName');
		var user_id = req.param('user_id');
		var Street = req.param('Street');
		var City = req.param('City');
		var State = req.param('State');
		var Zip = req.param('Zip');
		var property_type = req.param('property_type');
		var bath = Number(req.param('bath'));
		var room = Number(req.param('room'));
		var area = Number(req.param('area'));
		var rent = Number(req.param('rent'));
		var email = req.param('email');
		var Mobile = req.param('Mobile');
		var description = req.param('description');
		var Images = req.param('Images');
		var other_details = req.param('other_details');
		var Status = req.param('Status');
		var view_count = Number(req.param('view_count'));
		
		if(nickName === null){
			nickName = " ";
		}
		if(user_id === null){
			user_id = " ";
		}
		if(Street === null){
			Street = " ";
		}
		if(City === null){
			City = " ";
		}
		if(State === null){
			State = " ";
		}
		if(Zip === null){
			Zip = " ";
		}
		if(property_type === null){
			property_type = " ";
		}
		if(isNaN(bath)){
			bath = 0;
		}
		if(isNaN(room)){
			room = 0;
		}
		if(isNaN(area)){
			area = 0;
		}
		if(isNaN(rent)){
			rent = 0;
		}
		if(email === null){
			email = "raghuisguru1@gmail.com";
		}
		if(Mobile === null){
			Mobile = " ";
		}
		if(description === null){
			description = " ";
		}
		if(Images === null){
			Images = "https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg";
		}
		if(other_details === null){
			other_details = " ";
		}
		if(Status === null){
			Status = "Active";
		}
		if(isNaN(view_count)){
			view_count = 0;
		}
		
	 	
		var id;
		
		mongo.connect(function(err, db){
			
			if(err){
				console.log("Unable to connect to mongo");
				result.code = 209;
				result.status = "Unable to connect to mongo";
				res.json(result);
			}else{
				console.log("Connected to mongo");
				
				var coll = mongo.collection('rental_posting');
				
				id = user_id.replace(/\s+/g, '')+Street.replace(/\s+/g, '')+City.replace(/\s+/g, '')+State+Zip.replace(/\s+/g, '');
				
				coll.insertOne( 
						 {
							 	"_id" : id,
								"user_id" : user_id,
								"nickName" : nickName,
								"address" : {
										"Street" : Street,
										"City"   : City,
										"State"  : State,
										"Zip"    : Zip
									    },
								"property_type" : property_type,
								"units"  : {
										"bath" : bath,
										"room" : room,
										"area" : area 
									   },
								"rent"  : rent,
								"Contact_info" : {
										    "email" : email,
										    "Mobile": Mobile
										 },
								"description" : description,
								"Images" : Images,
								"other_details" : other_details,
								"Status" : Status,
								"view_count" : view_count
						 },   function(err, docs) {
						
							 if(err){
								 result.code = 208;
								 result.status = "Duplicate records";
								 res.json(result);
							 }else{
								 utils.notify(id,1,function(){
									console.log('notification triggered.');
									mailOptions.from = "magicrentals11@gmail.com";
									mailOptions.to = email;
									mailOptions.subject = "<no reply> New rental detils posted successful";
									mailOptions.text = "mail from magicrentals.. test mail";
									mailOptions.html = "Dear Customer, <br><br>Your add posted scuuesfully. <br><br>Thank you<br>MagicRentals Team";
									mailer.sendMail(mailOptions, function(error, success) {
										console.log('Mail sent');
									});
								 })
								 
								 result.code = 200; 
								 result.status = "Successfully inserted";
								 res.json(result);
							 }	
							
						 }
				);			
			}	
		});	
	 	
	});
};

//getPostings posts
exports.getAllPosts = function(req, res){
	
	console.log("In get postings API");
	var result = {};
	
	var user_id = req.param('user_id');
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
	     //var user_id = fields.user_id; 
	     
	     mongo.connect(function(err, db){
			
			if(err){
				console.log("Unable to connect to mongo");
				result.code = 209;
				result.status = "Unable to connect to mongo";
				res.json(result);
			}else{
				
				console.log("Connected to mongo");
				var coll = mongo.collection('rental_posting');
				
				coll.find( { "user_id" : user_id } ).toArray(function(err, docs) {	
					var myArray = [];
					if(docs){		
						result.data = docs;
						result.code = 200; 
						result.status = "Successful";
						
					}else{						
						 result.code = 208;
						 result.status = "Unable to get data";
					}							
					res.json(result);
				});
	
			}
			
		});
	});
};

//UpdatePostings posts
exports.updatePost = function(req, res){
		
		console.log("This is a UpdatePost API call");
		
		var result = {};
		var form = new formidable.IncomingForm();
		
		form.parse(req, function(err, fields, files) {
		     if(err){
		       console.log(err);
		       res.end("sorry, an error occurred");
		       return;
		     }
		    
		    console.log(fields.user_id + ": user_id, "+fields.Street+" fields.Street, "+fields.bath+" fields.room "+ fields.room + " , " + fields.email +", "+ fields.Mobile);
		    
		    var id = fields.id;
		    var user_id = fields.user_id;
		 	var Street = fields.Street;
		 	var City = fields.City;
		 	var State = fields.State;
		 	var Zip = fields.Zip;
		 	var property_type = fields.property_type;
		 	var bath = Number(fields.bath);
		 	var room = Number(fields.room);
		 	var area = Number(fields.area);
		 	var rent = Number(fields.rent);
		 	var email = req.param(fields.email);
		 	var Mobile = req.param(fields.Mobile);
		 	var description = req.param(fields.description);
		 	var Images = req.param(fields.Images);
		 	var other_details = req.param(fields.other_details);
		 	var Status = req.param(fields.Status);
		 	var view_count = Number(req.param(fields.view_count));
		 	
//		 	var id = req.param('id');
//			var user_id = req.param('user_id');
//			var Street = req.param('Street');
//			var City = req.param('City');
//			var State = req.param('State');
//			var Zip = req.param('Zip');
//			var property_type = req.param('property_type');
//			var bath = Number(req.param('bath'));
//			var room = Number(req.param('room'));
//			var area = Number(req.param('area'));
//			var rent = Number(req.param('rent'));
//			var email = req.param('email');
//			var Mobile = req.param('Mobile');
//			var description = req.param('description');
//			var Images = req.param('Images');
//			var other_details = req.param('other_details');
//			var Status = req.param('Status');
//			var view_count = Number(req.param('view_count'));
			
			
			mongo.connect(function(err, db){
				
				if(err){
					console.log("Unable to connect to mongo");
					result.code = 209;
					result.status = "Unable to connect to mongo";
					res.json(result);
				}else{
					console.log("Connected to mongo");
					
					var coll = mongo.collection('rental_posting');
					
					var objId = 'ObjectId("' + id + '")';
		//			objId.concat(id);
		//			objId.concat('")');
					console.log("Id is " + objId);
					
					coll.update( 
							{ _id : id}, 
							{
								$set : {
									"user_id" : user_id,
									"address" : {
											"Street" : Street,
											"City"   : City,
											"State"  : State,
											"Zip"    : Zip
										    },
									"property_type" : property_type,
									"units"  : {
											"bath" : bath,
											"room" : room,
											"area" : area 
										   },
									"rent"  : rent,
									"Contact_info" : {
											    "email" : email,
											    "Mobile": Mobile
											 },
									"description" : description,
									"Images" : Images,
									"other_details" : other_details,
									"Status" : Status,
									"view_count" : view_count
								}
							},   function(err, docs) {
							
								 if(err){
									 result.code = 208;
									 result.status = "Unable to update to mongo";
								 }else{
									 mailer.sendMail(function(error, success) {
										 result.code = 200; 
										 result.status = "Successfully updated";
										 result.data = docs;
									 });
								 }	
								 res.json(result);
							 }
					);			
				}	
			});	
		 	
		 	
		 	
		});
};

//Update the status of the post
exports.updateStatus = function(req, res){
		
	console.log("This is a UpdatePost's status API call");
	
	var result = {};
	var form = new formidable.IncomingForm();
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
//	     	var id = req.param('id');
//			var Status = req.param('Status');
		
     	var id = fields.user_id; 
     	var Status = refields.Status; 
     
		mongo.connect(function(err, db){
			
			if(err){
				console.log("Unable to connect to mongo");
				result.code = 209;
				result.status = "Unable to connect to mongo";
				res.json(result);
			}else{
				console.log("Connected to mongo");
				
				var coll = mongo.collection('rental_posting');
							
				coll.update( 
						{ _id : id}, 
						{
							$set : {
								"Status" : Status
							}
						},   function(err, docs) {
						
							 if(err){
								 result.code = 208;
								 result.status = "Unable to update to mongo";
							 }else{
								 mailer.sendMail(function(error, success) {
									 result.code = 200; 
									 result.status = "Successfully updated";
									 result.data = docs;
								 });
							 }	
							 res.json(result);
						 }
				);			
			}	
		});	
	});
		
};

//Update the view counts of the post
exports.updateViewCount = function(req, res){
		
	console.log("This is a UpdatePost's view count API call");
	
	var result = {};
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
//	    var id = req.param('id');
//	 	var view_count = req.param('view_count');
	     
	     var id = fields.id;
	     var view_count = fields.view_count;
	 	
	 	 mongo.connect(function(err, db){
	 		
	 		if(err){
	 			console.log("Unable to connect to mongo");
	 			result.code = 209;
	 			result.status = "Unable to connect to mongo";
	 			res.json(result);
	 		}else{
	 			console.log("Connected to mongo");
	 			
	 			var coll = mongo.collection('rental_posting');
	 						
	 			coll.update(
	 					{ _id : id}, 
						{
							$set : {
								"view_count" : view_count
							}
						},
	 						function(err, docs) {
	 							 if(err){
	 								 result.code = 208;
	 								 result.status = "Unable to update to mongo";
	 								 res.json(result);
	 							 }else{
	 								 if(docs.length > 0){
	 									 result.code = 200;
	 									 result.status = "success, ";
	 									 res.json(result);
	 								 }else{
	 									 
	 									 result.code = 200;
	 									 result.status = "success, ";
	 									 res.json(result);
	 								 }
	 							 }	
	 						 }
	 					);			
	 		}	
	 	});	
	     
	});	
};

//Search for postings
exports.searchPosts = function(req, res){
	
	console.log("In search API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
//	    var saveSearch = Boolean(fields.saveSearch);
//	 	var rate = Number(fields.rate);
//	 	var user_id = req.param(fields.user_id);
//	 	
//	 	var description = req.param(fields.description);
//	 	if(!description){
//	 		description = '.';
//	 	}
//	 	console.log('desc - '+ description);
//	 	
//	 	var City = fields.City;
//	 	if(!City){
//	 		City = '.';
//	 	}
//	 	console.log('City - '+ City);
//	 	
//	 	var Zip = fields.Zip;
//	 	if(!Zip){
//	 		Zip = '.';
//	 	}
//	 	console.log('Zip - '+ Zip);
//	 	
//	 	var property_type = fields.property_type;
//	 	if(!property_type){
//	 		property_type = '.';
//	 	}
//	 	console.log('property_type - '+ property_type);
//	 	
//	 	//var min_rent = parseInt(req.param('min_rent'));
//	 	var min_rent = Number(fields.min_rent) ;
//	 	if(!min_rent){
//	 		min_rent = 0;
//	 	}
//	 	console.log('min_rent - '+ min_rent);
//	 	
//	 	//var max_rent = parseInt(req.param('max_rent'));
//	 	var max_rent = Number(fields.max_rent);
//	 	if(!max_rent){
//	 		max_rent = Number.MAX_VALUE;
//	 	}
//	 	console.log('max_rent - '+ max_rent);
	 	
	     var saveSearch = Boolean(req.param('saveSearch'));
	 	var rate = Number(req.param('rate'));
	 	var user_id = req.param('user_id');
	 	
	 	var description = req.param('description');
	 	if(!description){
	 		description = '.';
	 	}
	 	console.log('desc - '+ description);
	 	
	 	var City = req.param('City');
	 	if(!City){
	 		City = '.';
	 	}
	 	console.log('City - '+ City);
	 	
	 	var Zip = req.param('Zip');
	 	if(!Zip){
	 		Zip = '.';
	 	}
	 	console.log('Zip - '+ Zip);
	 	
	 	var property_type = req.param('property_type');
	 	if(!property_type){
	 		property_type = '.';
	 	}
	 	console.log('property_type - '+ property_type);
	 	
	 	//var min_rent = parseInt(req.param('min_rent'));
	 	var min_rent = Number(req.param('min_rent')) ;
	 	if(!min_rent){
	 		min_rent = 0;
	 	}
	 	console.log('min_rent - '+ min_rent);
	 	
	 	//var max_rent = parseInt(req.param('max_rent'));
	 	var max_rent = Number(req.param('max_rent'));
	 	if(!max_rent){
	 		max_rent = Number.MAX_VALUE;
	 	}
	 	console.log('max_rent - '+ max_rent);
	 	
	 	mongo.connect(function(err, db){
	 		
	 		if(err){
	 			console.log("Unable to connect to mongo");
	 			result.code = 209;
	 			result.status = "Unable to connect to mongo";
	 			res.json(result);
	 		}else{
	 			
	 			if(saveSearch === true){
	 				var searchcol = mongo.collection('search_queries');
	 				
	 				searchcol.insertOne(
	 						{
	 							"user_id" : user_id,
	 							"rate" : rate,
	 							"description" : description,
	 							"City" : City,
	 							"Zip" : Zip,
	 							"property_type" : property_type,
	 							"max_rent" : max_rent,
	 							"min_rent" : min_rent						
	 							
	 						},function(err, docs) {
	 							
	 							 if(err){
//	 								 result.code = 208;
//	 								 result.status = "Unable to insert to mongo";
	 								 console.log("Unable to insert to mongo");
	 							 }else{
//	 								 mailer.sendMail(function(error, success) {
//	 									 result.code = 200; 
//	 									 result.status = "Successfully inserted";
//	 								 });
	 								 console.log("Search results saved");
	 							 }	
	 						});
	 			}
	 	
	 			console.log("Connected to mongo");
	 			var coll = mongo.collection('rental_posting');
	 			
	 			coll.find( { $and : [ { "description" : { $regex: description } }, 
	 			                      { "address.City" : { $regex: City } }, 
	 			                      { "address.Zip" : { $regex: Zip } }, 
	 			                      { "property_type" : { $regex: property_type } },
	 			                      { "rent" : { $lt : max_rent, $gt : min_rent } }] } )
	 			                      .toArray(function(err, docs) {	
	 				var myArray = [];
	 				if(docs){		
	 					result.data = docs;
	 					result.code = 200; 
	 					result.status = "Successful";
	 					
	 				}else{						
	 					 result.code = 208;
	 					 result.status = "Unable to get data";
	 				}							
	 				res.json(result);
	 			});

	 		}
	 		
	 	});
	});	
};

//Add Fav
exports.addFav = function(req, res){
	
	console.log("In search API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
	     var uid = req.param('uid');
		 var ids = req.param('ids');
		
//	     var uid = fields.uid; 
//	     var ids = refields.ids; 

		 console.log(uid + " " + ids);
		 mongo.connect(function(err, db){
				
				if(err){
					console.log("Unable to connect to mongo");
					result.code = 209;
					result.status = "Unable to connect to mongo";
					res.json(result);
				}else{
					
					var favcol = mongo.collection('users');
					
					favcol.update(
							   { "uid" : uid },
							   { $push: { "ids" : ids } },
							   function(err, docs){
								   if(docs){		
					 					result.data = docs;
					 					result.code = 200; 
					 					result.status = "Successful";
					 					res.json(result);
					 					
					 				}else{						
					 					 result.code = 208;
					 					 result.status = "Unable to get data";
					 					 res.json(result);
					 				}							
							   }
							);
				}
		 });
	});
};

//Remove Fav
exports.removeFav = function(req, res){
	
	console.log("In Remove Fav API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
	     var uid = req.param('uid');
		 var ids = req.param('ids');
		
//	     var uid = fields.uid; 
//	     var ids = refields.ids; 

		 mongo.connect(function(err, db){
				
				if(err){
					console.log("Unable to connect to mongo");
					result.code = 209;
					result.status = "Unable to connect to mongo";
					res.json(result);
				}else{
					
					var favcol = mongo.collection('users');
					
					favcol.update(
							   { "uid" : uid },
							   { $pull: { "ids" : ids } },
							   function(err, docs){
								   if(docs){		
					 					result.data = docs;
					 					result.code = 200; 
					 					result.status = "Successful";
					 					res.json(result);
					 					
					 				}else{						
					 					 result.code = 208;
					 					 result.status = "Unable to get data";
					 					 res.json(result);
					 				}							
					 				
							   }
							);
				}
		 });
	});
};

//getAllFav
exports.getAllFav = function(req, res){
	
	console.log("In getFav API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
//	     var uid = req.param('uid');
		
	     var uid = fields.uid; 
	     uid = uid.replace(/(\r\n|\n|\r)/gm,"");
	     console.log('uid '+uid);
	     console.log(uid);

		 mongo.connect(function(err, db){
				
				if(err){
					console.log("Unable to connect to mongo");
					result.code = 209;
					result.status = "Unable to connect to mongo";
					res.json(result);
				}else{
					
					var favcol = mongo.collection('users');
					
					favcol.findOne(
							{ "uid" : uid },{"_id" : 0 , "ids" : 1 },
							function(err, docs){

								if(docs){	
									console.log(docs);
									const myfav = docs["ids"];
									console.log(myfav);
									var postcol = mongo.collection('rental_posting');
									postcol.find(
											{ "_id" : { $in : myfav}}
									).toArray(function(err, result11) {
										if(err){
											result.code = 208;
						 					result.status = "Unable to get data";
						 					res.json(result);
										}else{
											result.data = result11;
						 					result.code = 200; 
						 					result.status = "Successful";	
						 					res.json(result);
										}
									});
				 						
				 				}else{						
				 					 result.code = 208;
				 					 result.status = "Unable to get data";
				 					res.json(result);
				 				}							
				 				
							
							}
					);
				}
		 });
	});
};