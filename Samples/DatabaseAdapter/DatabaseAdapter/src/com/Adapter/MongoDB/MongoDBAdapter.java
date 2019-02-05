package com.Adapter.MongoDB;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
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
	
	MongoDBAdapter(String dbName, String collName) {
		db = clientConnect.getDatabase(dbName);
		coll = db.getCollection(collName);
	}
	
	public void insert(Document doc) {
		coll.insertOne(doc);
	}

}