/**
 *  Created By : Raghavendra R Guru
 *  Last updated : 04/21/2016
 */

var mongo = require("./dbconfig");
var mailer = require('./mailer');
var utils = require('./utils');
var util = require('util');
var formidable = require('formidable');
var fs = require('fs');
var os = require('os');
var server = require('../bin/www');
var ip = require('ip');

//Add postings - POST
exports.addPost_Post = function(req, res){
		
	console.log("This is a addPost API call");
	
	var result = {};
	var mailOptions={};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	    
	    console.log(fields.user_id + ": user_id, "+fields.Street+" fields.Street, "+fields.bath+" fields.room "+ fields.room + " , " + fields.email +", "+ fields.Mobile);
	   
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
	 	var email = fields.email;
	 	var Mobile = fields.Mobile;
	 	var description = fields.description;
		var other_details = fields.other_details;
	 	var Status = fields.Status;
	 	var view_count = Number(fields.view_count);
	    var nickName = fields.nickName;
	    var Images;
	    
	    if(!user_id || user_id == null || user_id == "null"){
	    	console.log("User Id empty");
			result.code = 212;
			result.status = "Data Missing-user_id";
			res.json(result);
			return;
		}
	    console.log('UID is : '+ user_id);
	    
		if(!nickName || nickName == null || nickName == "null"){
			console.log("Data Missing-nickName ");
			result.code = 212;
			result.status = "Data Missing-nickName";
			res.json(result);
			return;
		}
		
		if(!Street || Street == null || Street == "null"){
			console.log("Data Missing-Street ");
			result.code = 212;
			result.status = "Data Missing-Street";
			res.json(result);
			return;
		}
		
		if(!City || City == null || City == "null"){
			console.log("Data Missing-City ");
			result.code = 212;
			result.status = "Data Missing-city";
			res.json(result);
			return;
		}
		
		if(!State || State == null || State == "null"){
			console.log("Data Missing-State ");
			result.code = 212;
			result.status = "Data Missing-state";
			res.json(result);
			return;
		}
		
		if(!Zip || Zip == null || Zip == "null"){
			console.log("Data Missing-Zip ");
			result.code = 212;
			result.status = "Data Missing-zip";
			res.json(result);
			return;
		}
		
		if(!property_type || property_type == null || property_type == "null"){
			console.log("Data Missing-property_type ");
			result.code = 212;
			result.status = "Data Missing-prop_type";
			res.json(result);
			return;
		}		
		
		if(!bath || isNaN(bath)){
			console.log("Data Missing-Bath ");
			result.code = 212;
			result.status = "Data Missing - bath";
			res.json(result);
			return;
		}
		
		if(!room || isNaN(room)){
			console.log("Data Missing-room ");
			result.code = 212;
			result.status = "Data Missing - room";
			res.json(result);
			return;
		}
		if(!area || isNaN(area)){
			console.log("Data Missing-area ");
			result.code = 212;
			result.status = "Data Missing - area";
			res.json(result);
			return;
		}
		if(!rent || isNaN(rent)){
			console.log("Data Missing-rent");
			result.code = 212;
			result.status = "Data Missing - rent";
			res.json(result);
			return;
		}
		if(!email|| email == null || email == "null"){
			console.log("Data Missing- email");
			result.code = 212;
			result.status = "Data Missing - email";
			res.json(result);
			return;
		}
		if(!Mobile && Mobile == null || Mobile == "null"){
			console.log("Data Missing- mobile");
			result.code = 212;
			result.status = "Data Missing - mob";
			res.json(result);
			return;
			
		}
		if(!description || description == null || description == "null"){
			console.log("Data Missing- desc");
			result.code = 212;
			result.status = "Data Missing - desc";
			res.json(result);
			return;
		}
		
		if(!other_details || other_details == null || other_details == "null"){
			other_details = " ";
		}
		
		if(!Status || Status == null || Status == "null"){
			Status = "Created";
		}
		
		if(!view_count || isNaN(view_count)){
			view_count = 0;
		}
		
	 	
		var id;
		
		if(files && files != null && files.fileUpload ){
			var readStream  = fs.createReadStream(files.fileUpload.path);
			var filePath  = '../public/images/' + files.fileUpload.name;
			var writeStream = fs.createWriteStream(filePath);

			readStream.pipe(writeStream);
			readStream.on('end',function(){
				console.log("successful file copy");
				//fs.unlinkSync(files.fileUpload.path);
				Images = "http://" + ip.address() + ":" + server.config.address().port + "/images/" + files.fileUpload.name;
				console.log('Image is - '+ Images  );
				addRow();
			});
			readStream.on('error',function(err){
				console.log(err);
			});
		}else{
			Images = "http://www.idesignarch.com/wp-content/uploads/Alvhem-Apartment-Interior-Design_4.jpg";
			addRow();
		}
		
		
		function addRow(){
			console.log("In add ROW");
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
									 });
									 
									 result.code = 200; 
									 result.status = "Successfully inserted";
									 res.json(result);
								 }	
								
							 }
					);			
				}	
			});	
		}	 	
	});
};

//Add postings - GET
exports.addPost_Get = function(req, res){
		
	console.log("This is a addPost API call");
	
	var result = {};
	var mailOptions={};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	    
	    	     
	 	var nickName = req.param('nickname');
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
	 	var other_details = req.param('other_details');
	 	var Status = req.param('Status');
	 	var view_count = Number(req.param('view_count'));
	    var nickName = req.param('nickName');
	    var Images;
		
		
	    if(!user_id || user_id == null || user_id == "null"){
	    	console.log("User Id empty");
			result.code = 212;
			result.status = "Data Missing-user_id";
			res.json(result);
			return;
		}
	    console.log('UID is : '+ user_id);
	    
		if(!nickName || nickName == null || nickName == "null"){
			console.log("Data Missing-nickName ");
			result.code = 212;
			result.status = "Data Missing-nickName";
			res.json(result);
			return;
		}
		
		if(!Street || Street == null || Street == "null"){
			console.log("Data Missing-Street ");
			result.code = 212;
			result.status = "Data Missing-Street";
			res.json(result);
			return;
		}
		
		if(!City || City == null || City == "null"){
			console.log("Data Missing-City ");
			result.code = 212;
			result.status = "Data Missing-city";
			res.json(result);
			return;
		}
		
		if(!State || State == null || State == "null"){
			console.log("Data Missing-State ");
			result.code = 212;
			result.status = "Data Missing-state";
			res.json(result);
			return;
		}
		
		if(!Zip || Zip == null || Zip == "null"){
			console.log("Data Missing-Zip ");
			result.code = 212;
			result.status = "Data Missing-zip";
			res.json(result);
			return;
		}
		
		if(!property_type || property_type == null || property_type == "null"){
			console.log("Data Missing-property_type ");
			result.code = 212;
			result.status = "Data Missing-prop_type";
			res.json(result);
			return;
		}		
		
		if(!bath || isNaN(bath)){
			console.log("Data Missing-Bath ");
			result.code = 212;
			result.status = "Data Missing - bath";
			res.json(result);
			return;
		}
		
		if(!room || isNaN(room)){
			console.log("Data Missing-room ");
			result.code = 212;
			result.status = "Data Missing - room";
			res.json(result);
			return;
		}
		if(!area || isNaN(area)){
			console.log("Data Missing-area ");
			result.code = 212;
			result.status = "Data Missing - area";
			res.json(result);
			return;
		}
		if(!rent || isNaN(rent)){
			console.log("Data Missing-rent");
			result.code = 212;
			result.status = "Data Missing - rent";
			res.json(result);
			return;
		}
		if(!email|| email == null || email == "null"){
			console.log("Data Missing- email");
			result.code = 212;
			result.status = "Data Missing - email";
			res.json(result);
			return;
		}
		if(!Mobile && Mobile == null || Mobile == "null"){
			console.log("Data Missing- mobile");
			result.code = 212;
			result.status = "Data Missing - mob";
			res.json(result);
			return;
			
		}
		if(!description || description == null || description == "null"){
			console.log("Data Missing- desc");
			result.code = 212;
			result.status = "Data Missing - desc";
			res.json(result);
			return;
		}
		
		if(!other_details || other_details == null || other_details == "null"){
			other_details = " ";
		}
		
		if(!Status || Status == null || Status == "null"){
			Status = "Created";
		}
		
		if(!view_count || isNaN(view_count)){
			view_count = 0;
		}
		
	 	
		var id;
		
		if(files && files != null && files.fileUpload ){
			var readStream  = fs.createReadStream(files.fileUpload.path);
			var filePath  = '../public/images/' + files.fileUpload.name;
			var writeStream = fs.createWriteStream(filePath);

			readStream.pipe(writeStream);
			readStream.on('end',function(){
				console.log("successful file copy");
//				/fs.unlinkSync(files.fileUpload.path);
				Images = "http://" + ip.address() + ":" + server.config.address().port + "/images/" + files.fileUpload.name;
				addRow();
			});
			readStream.on('error',function(err){
				console.log(err);
			});
		}else{
			Images = "http://www.idesignarch.com/wp-content/uploads/Alvhem-Apartment-Interior-Design_4.jpg";
			addRow();
		}
		
		
		function addRow(){
			console.log("In add ROW");
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
									 });
									 
									 result.code = 200; 
									 result.status = "Successfully inserted";
									 res.json(result);
								 }	
								
							 }
					);			
				}	
			});	
		}
	 	
	});
};

//getPostings posts - GET
exports.getAllPosts_Get = function(req, res){
	
	console.log("In get postings API");
	var result = {};
	
	var user_id = req.param('user_id');
	if(!user_id || user_id == null || user_id == "null"){
    	console.log("User Id empty");
		result.code = 212;
		result.status = "Data Missing";
		res.json(result);
	}
    
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
	     console.log('uid is ' + user_id); 
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
						res.json(result);
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

//getPostings posts - POST
exports.getAllPosts_Post = function(req, res){
	
	console.log("In get postings API");
	var result = {};
	   
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
	     var user_id = fields.user_id; 
	     
	     if(!user_id || user_id == null || user_id == "null"){
	     	console.log("User Id empty");
	 		result.code = 212;
	 		result.status = "Data Missing";
	 		res.json(result);
	 		return;
	 	}
	     
	     console.log('uid is ' + user_id); 
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

//UpdatePostings - POST
exports.updatePost_Post = function(req, res){
		
		console.log("This is a UpdatePost API call");
		
		var result = {};
		var form = new formidable.IncomingForm();
		var mailOptions={};
		
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
		 	var email = fields.email;
		 	var Mobile = fields.Mobile;
		 	var description = fields.description;
		 	var Images = fields.Images;
		 	var other_details = fields.other_details;
		 	var Status = fields.Status;
		 	var view_count = Number(fields.view_count);
		 	var nickName = fields.nickName;
		    
		 	if(!id || id == null || id == "null"){
		    	console.log("User Id empty");
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
		 	
		 	if(!user_id || user_id == null || user_id == "null"){
		    	console.log("User Id empty");
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
		    
			if(!nickName || nickName == null || nickName == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			
			if(!Street || Street == null || Street == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			
			if(!City || City == null || City == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			
			if(!State || State == null || State == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			
			if(!Zip || Zip == null || Zip == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			
			if(!property_type || property_type == null || property_type == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}		
			
			if(!bath || isNaN(bath)){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			
			if(!room || isNaN(room)){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			if(!area || isNaN(area)){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			if(!rent || isNaN(rent)){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			if(!email|| email == null || email == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			if(!Mobile && Mobile == null || Mobile == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			if(!description || description == null || description == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
			}
			if(!Images || Images == null || Images == "null"){
				Images = "https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg";
			}
			
			if(!other_details || other_details == null || other_details == "null"){
				other_details = " ";
			}
			
			if(!Status || Status == null || Status == "null"){
				Status = "Created";
			}
			
			if(!view_count || isNaN(view_count)){
				view_count = 0;
			}
			
			
		 	
			
			mongo.connect(function(err, db){
				
				if(err){
					console.log("Unable to connect to mongo");
					result.code = 209;
					result.status = "Unable to connect to mongo";
					res.json(result);
				}else{
					console.log("Connected to mongo");
					
					var coll = mongo.collection('rental_posting');
					
					console.log("Id is " + id);
					
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
								 		});
										result.code = 200; 
										result.status = "Successfully inserted";
										res.json(result);
									 }
							});
				}
			});
		});
};

//UpdatePostings - GET
exports.updatePost_Get = function(req, res){
		
		console.log("This is a UpdatePost API call");
		
		var result = {};
		var form = new formidable.IncomingForm();
		var mailOptions={};
		
		form.parse(req, function(err, fields, files) {
		     if(err){
		       console.log(err);
		       res.end("sorry, an error occurred");
		       return;
		     }
		    
		 	var nickName = req.param('nickName');
		 	var id = req.param('id');
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
		    
			if(!id || id == null || id == "null"){
		    	console.log("User Id empty");
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
		 	
		 	if(!user_id || user_id == null || user_id == "null"){
		    	console.log("User Id empty");
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
		    
			if(!nickName || nickName == null || nickName == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			
			if(!Street || Street == null || Street == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			
			if(!City || City == null || City == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			
			if(!State || State == null || State == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			
			if(!Zip || Zip == null || Zip == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			
			if(!property_type || property_type == null || property_type == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}		
			
			if(!bath || isNaN(bath)){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			
			if(!room || isNaN(room)){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			if(!area || isNaN(area)){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			if(!rent || isNaN(rent)){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			if(!email|| email == null || email == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			if(!Mobile && Mobile == null || Mobile == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			if(!description || description == null || description == "null"){
				result.code = 212;
				result.status = "Data Missing";
				res.json(result);
				return;
			}
			if(!Images || Images == null || Images == "null"){
				Images = "https://upload.wikimedia.org/wikipedia/commons/1/1e/Stonehenge.jpg";
			}
			
			if(!other_details || other_details == null || other_details == "null"){
				other_details = " ";
			}
			
			if(!Status || Status == null || Status == "null"){
				Status = "Created";
			}
			
			if(!view_count || isNaN(view_count)){
				view_count = 0;
			}			
		 				
			mongo.connect(function(err, db){
				
				if(err){
					console.log("Unable to connect to mongo");
					result.code = 209;
					result.status = "Unable to connect to mongo";
					res.json(result);
				}else{
					console.log("Connected to mongo");
					
					var coll = mongo.collection('rental_posting');
					
					console.log("Id is " + id);
					
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
								 		});
										result.code = 200; 
										result.status = "Successfully inserted";
										res.json(result);
									 }
							});
				}
			});
		});
};

//Update the status of the post - POST
exports.updateStatus_Post = function(req, res){
		
	console.log("This is a UpdatePost's status API call");
	
	var mailOptions = {};
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
//	     	var email = req.param('email');
	     
		var email = fields.email;
     	var id = fields.id; 
     	var Status = fields.Status; 
     
     	console.log(email +" ,"+id+" , "+Status);
     	
     	if(!email || email == null || email == "null"){
	    	console.log("User Id empty");
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
		}
	    
		if(!id || id == null ||  id == "null"){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
		
		if(!Status || Status == null || Status == "null"){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
     	
     	email = email.replace(/(\r\n|\n|\r)/gm,"");
     	id = id.replace(/(\r\n|\n|\r)/gm,"");
     	Status = Status.replace(/(\r\n|\n|\r)/gm,"");
     	
     	console.log(email +" ,"+id+" , "+Status);
     	
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
						{ "_id" : id}, 
						{
							$set : {
								"Status" : Status
							}
						},   function(err, docs) {
						
							 if(err){
								 result.code = 208;
								 result.status = "Unable to update to mongo";
								 res.json(result)
							 }else{
								 mailOptions.from = "magicrentals11@gmail.com";
									mailOptions.to = email;
									mailOptions.subject = "<no reply> New rental detils posted successful";
									mailOptions.text = "mail from magicrentals.. test mail";
									mailOptions.html = "Dear Customer, <br><br>Your add posted scuuesfully. <br><br>Thank you<br>MagicRentals Team";
									mailer.sendMail(mailOptions, function(error, success) {
										console.log('Mail sent');
									});
									res.json(result)
							 }	
						 }
				);			
			}	
		});	
	});
		
};

//Update the status of the post - GET
exports.updateStatus_Get = function(req, res){
		
	console.log("This is a UpdatePost's status API call");
	
	var mailOptions = {};
	var result = {};
	var form = new formidable.IncomingForm();
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
     	var id = req.param('id');
		var Status = req.param('Status');
     	var email = req.param('email');
          
     	console.log(email +" ,"+id+" , "+Status);
     	if(!email || email == null || email == "null"){
	    	console.log("User Id empty");
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
	    
		if(!id || id == null ||  id == "null"){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
		
		if(!Status || Status == null || Status == "null"){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
     	
     	email = email.replace(/(\r\n|\n|\r)/gm,"");
     	id = id.replace(/(\r\n|\n|\r)/gm,"");
     	Status = Status.replace(/(\r\n|\n|\r)/gm,"");
     	
     	console.log(email +" ,"+id+" , "+Status);
     	
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
						{ "_id" : id}, 
						{
							$set : {
								"Status" : Status
							}
						},   function(err, docs) {
						
							 if(err){
								 result.code = 208;
								 result.status = "Unable to update to mongo";
								 res.json(result)
							 }else{
								 mailOptions.from = "magicrentals11@gmail.com";
									mailOptions.to = email;
									mailOptions.subject = "<no reply> New rental detils posted successful";
									mailOptions.text = "mail from magicrentals.. test mail";
									mailOptions.html = "Dear Customer, <br><br>Your add posted scuuesfully. <br><br>Thank you<br>MagicRentals Team";
									mailer.sendMail(mailOptions, function(error, success) {
										console.log('Mail sent');
									});
									res.json(result)
							 }	
						 }
				);			
			}	
		});	
	});
		
};

//Update the view counts of the post - GET
exports.updateViewCount_Get = function(req, res){
		
	console.log("This is a UpdatePost's view count API call");
	var form = new formidable.IncomingForm();
	var result = {};
	
	form.parse(req, function(err, fields, files) {
	    if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	    }
	     
	    var id = req.param('id');
	    var view_count = Number(req.param('view_count'));
	    var email = req.param('email');
	    
	    	
	 	if(!email || email == null || email == "null"){
	    	console.log("User Id empty");
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
	    
		if(!id || id == null || id == "null"){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
		
		if(!view_count || isNaN(view_count)){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
		
	     id = id.replace(/(\r\n|\n|\r)/gm,"");
	     view_count = view_count.replace(/(\r\n|\n|\r)/gm,"");
	     
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
	 							 result.code = 200;
	 							 result.status = "success, ";
	 							 res.json(result);
	 						}	
	 					}
				);			
	 		}	
	 	});	
	});	
};


//Update the view counts of the post - POST
exports.updateViewCount_Post = function(req, res){
	
	console.log("This is a UpdatePost's view count API call");
	var form = new formidable.IncomingForm();
	var result = {};
	
	form.parse(req, function(err, fields, files) {
	    if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	    }
	     
	    var id = fields.id;
	    var view_count = Number(fields.view_count);
	    var email = fields.email;
	    
	    	
	 	if(!email || email == null || email == "null"){
	    	console.log("User Id empty");
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
	    
		if(!id || id == null || id == "null"){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
		
		if(!view_count || isNaN(view_count)){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
		
	     id = id.replace(/(\r\n|\n|\r)/gm,"");
	     view_count = view_count.replace(/(\r\n|\n|\r)/gm,"");
	     
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
	 							 result.code = 200;
	 							 result.status = "success, ";
	 							 res.json(result);
	 						}	
	 					}
				);			
	 		}	
	 	});	
	});	
};

//Save Search Res - GET
exports.saveSearchRes_Get = function(req, res){

	console.log("In save search API");
	var result = {};
	
	
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     	 	
	    var saveSearch = true;
	 	var rate = Number(req.param('rate'));
	 	var user_id = req.param('user_id');
	 	
		if(!user_id || user_id == null || user_id == "null"){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
	 	
		if(!rate || isNaN(rate)){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
	 		 	
	 	var description = req.param('description');
	
	 	if(!description || description == null || description == "null"){
	 		description = '.';
		}
	 		 	
	 	console.log('desc - '+ description);
	 	
	 	var City = req.param('City');
	 	if(!City || City == null || City == "null"){
	 		City = '.';
	 	}
	 	
	 	console.log('City - '+ City);
	 	
	 	var Zip = req.param('Zip');
	 	if(!Zip || Zip == null || Zip == "null"){
	 		Zip = '.';
	 	}
	 	console.log('Zip - '+ Zip);
	 	
	 	var property_type = req.param('property_type');
	 	if(!property_type || property_type == null || property_type == "null"){
	 		property_type = '.';
	 	}
	 	console.log('property_type - '+ property_type);
	 	
	 	//var min_rent = parseInt(req.param('min_rent'));
	 	var min_rent = Number(req.param('min_rent')) ;
	 	if(!min_rent || isNaN(min_rent)){
	 		min_rent = 0;
	 	}
	 	console.log('min_rent - '+ min_rent);
	 	
	 	//var max_rent = parseInt(req.param('max_rent'));
	 	var max_rent = Number(req.param('max_rent'));
	 	if(!max_rent || isNaN(max_rent)){
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
	 			
	 			if(saveSearch == true){
	 				var searchcol = mongo.collection('search_queries');
	 				console.log('Saving search result');
	 				
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
	 								 result.code = 208;
	 								 result.status = "Unable to insert to mongo";
	 								 console.log("Unable to insert to mongo");
	 								 res.json(result);
	 							 }else{
	 								 
	 								 result.code = 200; 
	 								 result.status = "Successfully inserted";
	 								 console.log("Search results saved");
	 								 res.json(result);
	 							 }	
	 						});
	 			}
	 	
	 		
	 		}
	 		
	 	});
	});	

};

//Save Search Res - POST

//Save Search Res - POST
exports.saveSearchRes_Post = function(req, res){

	console.log("In save search API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     	 	
	    var saveSearch = true;
	 	var rate = Number(fields.rate);
	 	var user_id = fields.user_id;
	 	
		if(!user_id || user_id == null || user_id == "null"){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
	 	
		if(!rate || isNaN(rate)){
			result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
	 		 	
	 	var description = fields.description;
	
	 	if(!description || description == null || description == "null"){
	 		description = '.';
		}
	 		 	
	 	console.log('desc - '+ description);
	 	
	 	var City = fields.City;
	 	if(!City || City == null || City == "null"){
	 		City = '.';
	 	}
	 	
	 	console.log('City - '+ City);
	 	
	 	var Zip = fields.Zip;
	 	if(!Zip || Zip == null || Zip == "null"){
	 		Zip = '.';
	 	}
	 	console.log('Zip - '+ Zip);
	 	
	 	var property_type = fields.property_type;
	 	if(!property_type || property_type == null || property_type == "null"){
	 		property_type = '.';
	 	}
	 	console.log('property_type - '+ property_type);
	 	
	 	var min_rent = Number(fields.min_rent) ;
	 	if(!min_rent || isNaN(min_rent)){
	 		min_rent = 0;
	 	}
	 	console.log('min_rent - '+ min_rent);
	 	
	 	var max_rent = Number(fields.max_rent);
	 	if(!max_rent || isNaN(max_rent)){
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
	 			
	 			if(saveSearch == true){
	 				var searchcol = mongo.collection('search_queries');
	 				console.log('Saving search result');
	 				
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
	 								 result.code = 208;
	 								 result.status = "Unable to insert to mongo";
	 								 console.log("Unable to insert to mongo");
	 								 res.json(result);
	 							 }else{
	 								 
	 								 result.code = 200; 
	 								 result.status = "Successfully inserted";
	 								 console.log("Search results saved");
	 								 res.json(result);
	 							 }	
	 						});
	 			}
	 	
	 		
	 		}
	 		
	 	});
	});	

};

//Search for postings - GET
exports.searchPosts_Get = function(req, res){
	
	console.log("In search API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     	 	
	    var saveSearch = Boolean(req.param('saveSearch'));
	 	var rate = Number(req.param('rate'));
	 	var user_id = req.param('user_id');
	 	
	 	var description = req.param('description');
	 	if(!description || description == null || description == "null"){
	 		description = '.';
	 	}
	 	console.log('desc - '+ description);
	 	
	 	var City = req.param('City');
	 	if(!City || City == null || City == "null"){
	 		City = '.';
	 	}
	 	console.log('City - '+ City);
	 	
	 	var Zip = req.param('Zip');
	 	if(!Zip || Zip == null || Zip == "null" ){
	 		Zip = '.';
	 	}
	 	console.log('Zip - '+ Zip);
	 	
	 	var property_type = req.param('property_type');
	 	if(!property_type || property_type == null || property_type == "null"){
	 		property_type = '.';
	 	}
	 	console.log('property_type - '+ property_type);
	 	
	 	//var min_rent = parseInt(req.param('min_rent'));
	 	var min_rent = Number(req.param('min_rent')) ;
	 	if(!min_rent || min_rent == null || min_rent == "null"){
	 		min_rent = 0;
	 	}
	 	console.log('min_rent - '+ min_rent);

	 	var street = Number(req.param('street')) ;
	 	if(!street || street == null || street == "null"){
	 		street = ".";
	 	}
	 	console.log('min_rent - '+ min_rent);
	 	
	 	//var max_rent = parseInt(req.param('max_rent'));
	 	var max_rent = Number(req.param('max_rent'));
	 	if(!max_rent || max_rent == null){
	 		max_rent = Number.MAX_VALUE;
	 	}
	 	console.log('max_rent - '+ max_rent);
	 	
	 	if(!user_id || user_id == null || user_id == "null"){
	 		result.code = 210;
 			result.status = "User ID is empty";
 			res.json(result);
	 	}
	 	
		 	mongo.connect(function(err, db){
	 		
	 		if(err){
	 			console.log("Unable to connect to mongo");
	 			result.code = 209;
	 			result.status = "Unable to connect to mongo";
	 			res.json(result);
	 		}else{
	
	 			console.log("Connected to mongo");
	 			var coll = mongo.collection('rental_posting');
	 			
	 			coll.find( { $and : [ { "description" : { $regex: description, $options:"i" } }, 
	 			                      { "address.City" : { $regex: City, $options:"i" } }, 
	 			                      { "address.Zip" : { $regex: Zip } }, 
	 			                      { "property_type" : { $regex: property_type,  $options:"i"  } },
	 			                      { "rent" : { $lt : max_rent, $gt : min_rent } }] } )
	 			                      .toArray(function(err, docs) {	
	 				var myArray = [];
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
	 				
	 			});
	
	 		}
	 		
	 	});
	});	
};


exports.searchPosts_Post = function(req, res){
	
	console.log("In search API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	    if(err){
	      console.log(err);
	      res.end("sorry, an error occurred");
	      return;
	    }
	     	 	
	    var saveSearch = true;
	    var user_id = fields.user_id;
		 	
	    if(!user_id || user_id == null || user_id == "null"){
	    	result.code = 212;
			result.status = "Data Missing";
			res.json(result);
			return;
		}
		 			 	
	    var description = fields.description;
		
		if(!description || description == null || description == "null"){
			description = '.';
		}
		 		 	
		console.log('desc - '+ description);
		 	
		var City = fields.City;
		if(!City || City == null || City == "null"){
			City = '.';
		}
		 	
		console.log('City - '+ City);
		 	
		var Zip = fields.Zip;
		if(!Zip || Zip == null || Zip == "null"){
			Zip = '.';
		}
		
		console.log('Zip - '+ Zip);
		 	
		var property_type = fields.property_type;
		if(!property_type || property_type == null || property_type == "null"){
			property_type = '.';
		}
		
		console.log('property_type - '+ property_type);
		 	
		var min_rent = Number(fields.min_rent) ;
		if(!min_rent || isNaN(min_rent)){
			min_rent = 0;
		}
		
		console.log('min_rent - '+ min_rent);
		 	
		var max_rent = Number(fields.max_rent);
		if(!max_rent || isNaN(max_rent)){
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

 			console.log("Connected to mongo");
 			var coll = mongo.collection('rental_posting');
 			
 			coll.find( { $and : [ { "description" : { $regex: description, $options:"i" } }, 
 			                      { "address.City" : { $regex: City, $options:"i" } }, 
 			                      { "address.Zip" : { $regex: Zip } }, 
 			                      { "property_type" : { $regex: property_type,  $options:"i"  } },
 			                      { "rent" : { $lt : max_rent, $gt : min_rent } }] } )
 			                      .toArray(function(err, docs) {	
 				var myArray = [];
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
 				
 			});

 		}
 		
 	});
	});	
};

