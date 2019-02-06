package com.Adapter.MongoDB;

import com.mongodb.DBObject;
import static com.mongodb.client.model.Filters.*;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.util.JSON;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;

import com.mongodb.client.MongoCursor;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
	
public class MongoDBAdapter {		
	
	static String dbUrl;
	static MongoClient clientConnect;
	MongoDatabase db;
	MongoCollection<Document> coll;
	
	/*
	 * Static Block for Connection Establishment to MongoDB
	 */
	static { 
		try {
			InputStream configFileStream   = MongoDBAdapter.class.getResourceAsStream("config.properties");
			Properties props = new Properties();
			props.load(configFileStream);
			configFileStream.close();
			dbUrl = props.getProperty("dbUrl", "mongodb://localhost:27017/");
			MongoClientURI conString = new MongoClientURI(dbUrl);
			clientConnect = new MongoClient(conString);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/*
	 * Constructor
	 */
	MongoDBAdapter(String dbName, String collName) {
	
		db = clientConnect.getDatabase(dbName);
		coll = db.getCollection(collName);
	
	}
	
	/*
	 * Insert One Operation
	 */
	public void insert(String jsonString) {
	
		//DBObject dbObj = (DBObject)JSON.parse(jsonString);
		coll.insertOne(Document.parse(jsonString));
	
	}
	
	/*
	 * Insert Many Operation
	 */
	public void insertMany(List<String> jsonStringList) {
		
		List<Document> documents = new ArrayList<Document>();
		for (int i = 0; i < jsonStringList.size(); i++) {
		    documents.add(Document.parse(jsonStringList.get(i)));
		}
		coll.insertMany(documents);
		
	}
	
	/*
	 * Select All Operation
	 */
	public List<String> selectAll() {
		
		List<String> jsonStringList = new ArrayList<String>();
		MongoCursor<Document> cursor = coll.find().iterator();
		try {
		    while (cursor.hasNext()) {
		        System.out.println(cursor.next().toJson());
		        jsonStringList.add( cursor.next().toJson());
		    }
		 
		        
		} finally {
		   cursor.close();
		}
		return jsonStringList;
		
	}
	
	/*
	 * Update Many Operation
	 */
	public void update(String condition, String fieldName,Object value,String jsonString) {
		
		UpdateResult result;
		switch(condition) {
			
		case "eq":
				result = coll.updateMany(eq(fieldName,value), new Document("$set", Document.parse(jsonString)));
				System.out.println("Modified Count - "+result.getModifiedCount());
				break;
		
		//WE CAN ADD MORE CONDITION DEPENDING ON REQUIREMENTS
				
		}	
	}
	
	/*
	 * Delete Many Operation
	 */
	public void delete(String condition, String fieldName,Object value) {
	
		DeleteResult result;
		switch(condition) {
		
		case "eq":
				result = coll.deleteMany(eq(fieldName,value));
				System.out.println("Deleted Count - "+result.getDeletedCount());
				break;
		
		//WE CAN ADD MORE CONDITION DEPENDING ON REQUIREMENTS
				
		}
		
	}
	
	
}