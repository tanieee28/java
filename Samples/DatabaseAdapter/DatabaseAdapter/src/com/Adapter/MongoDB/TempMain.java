package com.Adapter.MongoDB;

import org.bson.Document;
import com.Adapter.MongoDB.*;

public class TempMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Document doc = new Document("Company","Xoriant");
		MongoDBAdapter adapter = new MongoDBAdapter("mydb", "mycoll");
		adapter.insert(doc);
		System.out.println("Transaction Completed");
	}

}
