var Promise = require('promise');
const express = require('express')
//var logger = require('winston');
const mysql = require('mysql');
const config = require('../../config');
let dbconfig = config.dbconfig;

var sql = "CALL insert_player(?,?)"; // Full name of stored procedure

module.exports = function(req, res, next) {
        //logger.debug('COC_Postcocdbinfo is starting');

            var reqBody = req.body;
            var reqParams = req.params;

            var pool  = mysql.createPool({
                connectionLimit : 10,
                host            : 'localhost',
                user            : 'spacetest',
                password        : '123',
                database        : 'spacetraders'
            });

            // console.log('get req post body is '+ reqBody.docTitle);
            //   console.log('get req post body is '+ reqBody.docAuthor);
            // console.log('get req post body is '+ reqBody.docContent);

        return new Promise(function (resolve, reject){ 
            // var pool  = mysql.createPool(dbconfig);
            console.log('get req post body is ');
             
            pool.getConnection(function(err, connection) {
                console.log('get req post body is ' + reqBody.username);
                console.log('get req post body is ' + reqBody.currency);
			    if (err) {
                    console.log(err);
                    res.status(500).send(err);
                    reject(Error(err.message));
                    connection.release();
			        return reject(err);
			    }
			    console.log('get req post body is '+ reqBody.username);
                console.log('get req post body is '+ reqBody.currency);

			    connection.query(sql,[reqBody.username,reqBody.currency], function(err, results, fields) {
                    if (err) {
                        return reject(err);
                    }
                    console.log('changed ' + results.changedRows + ' rows');
                    console.log(results.insertId);
                    res.send(results.changedRows + '   ' + results.insertId);
                    //Return the connection to the pool
                    connection.release();
                    
                    resolve();
		      	});
          }); 
      });  
}

