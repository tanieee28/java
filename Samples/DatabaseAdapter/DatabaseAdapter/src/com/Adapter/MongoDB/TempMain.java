package com.Adapter.MongoDB;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bson.Document;
import com.Adapter.MongoDB.*;
import com.mongodb.util.JSON;

public class TempMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MongoDBAdapter adapter = new MongoDBAdapter("mydb", "mycoll");
		Scanner scan = new Scanner(System.in);
		Document doc = new Document("Company","Xoriant");
		String jsonString = "{ 'EmpName' : 'Tanmay' }";
				
		while(true) {
			
			System.out.println("Specify 'I','R','U','D' for Insert,Read,Update,Delete \n \"STOP\" to exit");
			String input = scan.next();
			
			switch(input) {
			
			case "R":
				
				List<String> recordsList = adapter.selectAll();
				
				for(String record:recordsList) {
					System.out.println(record);
				}
				
			break;
				
			case "U":
				
				adapter.update("eq", "Company", "Xoriant", "{'Company' : 'Xoriant Inc' }");
				
			break;
				
			case "I":
				
				adapter.insert(jsonString);
				
			break;
			
			case "D":
				
				adapter.delete("eq", "Company", "Xoriant Inc");
				
			break;
			
			case "STOP":
				
				System.exit(1);
				
			}
			
			System.out.println("Transaction Completed");
		}
	}
	
	

}
