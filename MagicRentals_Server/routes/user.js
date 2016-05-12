/* GET users listing. */
var mongo = require("./dbconfig");
var formidable = require('formidable');

exports.list = function(req, res){
  res.send('respond with a resource');
};

exports.addUser = function(req, res){

	console.log("This is a UpdatePost's status API call");
	
	var result = {};
	
	var form = new formidable.IncomingForm();
	form.parse(req, function(err, fields, files) {
	     if(err){
	       console.log(err);
	       res.end("sorry, an error occurred");
	       return;
	     }
	     
//	    var uid = req.param('uid');
//	 	var email = req.param('email');
//	 	var deviceID = req.param('deviceID');
//	 	var token = req.param('token');
	    
		var uid = fields.uid;
		var email = fields.email;
		var deviceID = fields.deviceID;
	    console.log(uid + " " + email + " " + deviceID);
	 	mongo.connect(function(err, db){
	 		
	 		if(err){
	 			console.log("Unable to connect to mongo");
	 			result.code = 209;
	 			result.status = "Unable to connect to mongo";
	 			res.json(result);
	 		}else{
	 			
	 			var coll = mongo.collection('users');
	 			coll.findOne(
	 					{ "uid" : uid }, function(error, docs){
	 						if(error){
	 							console.log(error);
	 							result.code = 208;
	 							result.status = "unable to connect to mongo";
	 							res.json(result);
	 						}else{
	 							if(docs){
	 								
	 								coll.update(
	 										{
	 											"uid" : uid
	 										}, 
	 										{
	 											$set: { "email" : email,
	 											    	"deviceID" : deviceID
	 											}
	 										}, 
	 										function(er, result){
	 											if(er){
	 												result.code = 208;
	 												result.status = "unable to validate user";
	 												res.json(result);
	 											}else{
	 												result.code = 200;
	 												result.status = "login successfl";
	 												res.json(result);		
	 											}
	 										});
	 																
	 							}else{
	 								coll.insertOne( 
	 										 {
	 											 	"uid" : uid,
	 												"email" : email,
	 												"deviceID" : deviceID
	 										 },   function(err, docs) {
	 										
	 											 if(err){
	 												 result.code = 208;
	 												 result.status = "Unable login";
	 											 }else{
	 													result.code = 200;
	 													result.status = "sign-up successfl";
	 											 }	
	 											 res.json(result);
	 										 }
	 								);
	 							}
	 						}
	 						
	 					});
	 		}	
	 	});
	 });
};


