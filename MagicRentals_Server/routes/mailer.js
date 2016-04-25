/**
 *  Created By : Raghavendra R Guru
 *  Last updated : 04/21/2016
 */

var nodemailer = require('nodemailer');
var smtpTransport = require("nodemailer-smtp-transport");

//send email
exports.sendMail = function(callback){

	console.log("This is a send mail block - 1");
	var transport = nodemailer.createTransport(smtpTransport({
	    host : "smtp.gmail.com",
	    secureConnection : false,
	    port: 587,
	    auth : {
	        user : "magicrentals11@gmail.com",
	        pass : "magicrentals1100"
	    }
	}));
	
	console.log("This is a send mail block - 2");

	var mailOptions={
	        from : "magicrentals11@gmail.com",
	        to : "raghavendra1810@gmail.com",
	        subject : "Message from magic rentals",
	        text : "mail from magicrentals.. test mail",
	        html : "HTML GENERATED"
	};
	
	console.log("This is a send mail block - 3");
	console.log(mailOptions);
	transport.sendMail(mailOptions, function(error, response){
		if(error){
			console.log(error,null);
	        callback(error,null);
	    }else{
	    	console.log("Message sent: " + response.message);
	        console.log(null,"success");
	    }
	});
}; 

