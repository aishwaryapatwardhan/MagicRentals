/* GET home page. */

var mongo = require("./dbconfig");

exports.index = function(req, res){
	res.render('index', { title: 'Express' });
};



//Test API
exports.test123 = function(req, res){
		
	console.log("This is a test API call");
	
	mongo.connect(function(err, db){
		
		if(err){
			console.log("Unable to connect to mongo");
			res.send("Unable to connect to mongo");
		}else{
			console.log("Connected to mongo");
			
			var coll = mongo.collection('rental_posting');
			
			coll.find().toArray(function(err, docs) {
				
				var myArray = [];
				if(docs){												
					for(var i=0; i<docs.length; i++){
						console.log(docs[i].user_id);
						myArray.push({ "user_id" :docs[i].user_id, 
								"property_type":docs[i].property_type});
					}					
				}else{						
					//res.statusCode  = 401;
				}							
				//res.send(JSON.stringify(myArray));
			});
			
			res.send("Connected to mongo");
		}	
	});	
};
