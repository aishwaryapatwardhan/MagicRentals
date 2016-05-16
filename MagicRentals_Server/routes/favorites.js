var mongo = require("./dbconfig");
var mailer = require('./mailer');
var utils = require('./utils');
var formidable = require('formidable');

//Add Fav - GET
exports.addFav_Get = function(req, res){
	
	console.log("In addfav API");
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
		 
		 if(!uid || uid == null || uid == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }

		 if(!ids || ids == null || ids == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }
		 
		 console.log(uid + " , " + ids);
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

//Add Fav - POST
exports.addFav_Post = function(req, res){
	
	console.log("In addfav API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
		
	     var uid = fields.uid; 
	     var ids = refields.ids; 
		 
		 if(!uid || uid == null || uid == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }

		 if(!ids || ids == null || ids == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }
		 
		 console.log(uid + " , " + ids);
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

//Remove Fav - GET
exports.removeFav_Get = function(req, res){
	
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
		
		 if(!uid || uid == null || uid == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }

		 if(!ids || ids == null || ids == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }
		 
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

//Remove Fav - POST
exports.removeFav_Post = function(req, res){
	
	console.log("In Remove Fav API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
	     var uid = fields.uid;
		 var ids = fields.ids;
		
		 if(!uid || uid == null ||  uid == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }

		 if(!ids || ids == null || ids == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }
		 
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

//getAllFav - POST
exports.getAllFav_Post = function(req, res){
	
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
	     if(!uid || uid == null || uid == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }

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

//getAllFav - GET
exports.getAllFav_Get = function(req, res){
	
	console.log("In getFav API");
	var result = {};
	
	var form = new formidable.IncomingForm();
	
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
	     var uid = req.param('uid');
		
//	     var uid = fields.uid; 
	     if(!uid || uid == null || uid == "null"){
			 console.log("Data is empty");
			 result.code = 211;
			 result.status = "Data Empty";
			 res.json(result);
			 return;
		 }

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

}

//exports.checkFav = function(req, res){
//	
//	var uid = req.param('uid');
//	var ids = req.param('ids');
//	var result = {};
//	
//	if(uid === null || ids === null){
//		 result.code = 210;
//		 result.status = "data empty";
//		 res.json(result);
//	 }
//
//	 console.log(uid + " " + ids);
//	
//	 mongo.connect(function(err, db){
//			
//			if(err){
//				console.log("Unable to connect to mongo");
//				result.code = 209;
//				result.status = "Unable to connect to mongo";
//				res.json(result);
//			}else{ 
//				
//				var favcol = mongo.collection('users');
//				
//				favcol.findOne(
//						{ "ids": { $all: [ ids ] } },
//						   function(err, docs){
//							   if(docs){		
//				 					result.data = docs;
//				 					result.code = 200; 
//				 					result.status = "Successful";
//				 					res.json(result);
//				 					
//				 				}else{						
//				 					 result.code = 208;
//				 					 result.status = "Unable to get data";
//				 					 res.json(result);
//				 				}							
//						   }
//						);
//			}
//	 });
//};
