/**
 *  Created By : Raghavendra R Guru
 *  Last updated : 04/21/2016
 */

var mongo = require("./dbconfig");

//Add postings
exports.addPost = function(req, res){
		
	console.log("This is a addPost API call");
	
	var result = {};
	
	var user_id = req.param('user_id');
	var Street = req.param('Street');
	var City = req.param('City');
	var State = req.param('State');
	var Zip = req.param('Zip');
	var property_type = req.param('property_type');
	var bath = req.param('bath');
	var room = req.param('room');
	var area = req.param('area');
	var rent = req.param('rent');
	var email = req.param('email');
	var Mobile = req.param('Mobile');
	var description = req.param('description');
	var Images = req.param('Images');
	var other_details = req.param('other_details');
	var Status = req.param('Status');
	var view_count = req.param('view_count');
	
	mongo.connect(function(err, db){
		
		if(err){
			console.log("Unable to connect to mongo");
			result.code = 209;
			result.status = "Unable to connect to mongo";
			res.json(result);
		}else{
			console.log("Connected to mongo");
			
			var coll = mongo.collection('rental_posting');
			
			coll.insertOne( 
					 {
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
					 },   function(err, docs) {
					
						 if(err){
							 result.code = 208;
							 result.status = "Unable to insert to mongo";
						 }else{
							 result.code = 200; 
							 result.status = "Successfully inserted";
						 }						    	 
					 }
			);			
			res.json(result);
		}	
	});	
};


