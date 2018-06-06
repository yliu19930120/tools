package com.t.util;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * 
* @author Liu Yonghua
* @Email liuyonghua@xinpibao.com
* @date 2018年6月6日  下午2:50:10
 */
public class MongoUtil {
public static final String DB_NAME_DEFAULT="xinpibao";
	
	private static final String DB_HOST="127.0.0.1";

	private static MongoClient mongoClient=null;


	public static MongoCollection<Document> getCollection( String dbName, String collName ) {
		mongoClient = new MongoClient(DB_HOST);  
		MongoDatabase database=mongoClient.getDatabase( dbName );
		MongoCollection<Document> collection=database.getCollection( collName );
		return collection.withWriteConcern( WriteConcern.W1 );
	}
	
	public static <T> T adaptToJava( Document t, Class<T> classOfT, Gson gson ) {
		
		String json=gson.toJson( t );
		return gson.fromJson( json, classOfT );
	}
	
	public static DB getDB(String db) {
		return new DB(mongoClient, db);
	}

}
