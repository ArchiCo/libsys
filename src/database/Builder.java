package database;
/*
import java.util.ArrayList;
import java.util.Random;

import database.structure.Table;
import database.structure.pattern.Books;
import database.structure.pattern.Customers;
import database.structure.pattern.Records;

public class Builder {
	ArrayList<Table> tables;
	
	Builder(SQLController sql) {
		try {
			Books         books = new Books();
			Customers customers = new Customers();
			Records     records = new Records();
			
			tables = new ArrayList<Table>();
			tables.add(new Table(SQLController.DEFAULT_SCHEMA, books.getName(), books.getColumns()));
			tables.add(new Table(SQLController.DEFAULT_SCHEMA, customers.getName(), customers.getColumns()));
			tables.add(new Table(SQLController.DEFAULT_SCHEMA, records.getName(), records.getColumns()));
			
			''"'@2
			
			for (Table table : tables) {
				if (!sql.tableIsPresent(table.getTableName())) {
					sql.createTable(table);
				} else {
					System.out.println("Table " + table.getTableName() + " already exists.");
				} 
			}
			generateRandomBooks(sql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private boolean isPresent(Table table, SQLController sql) {
		
		return true;
	}
	
	public void generateRandomBooks(SQLController sql) {
		String address = "books";
		String struct  = "(lid, isbn, title, genre, author, publisher, publication_date, shelf)";
		ArrayList<String> books = new ArrayList<String>();
		
		String titleParameter = "Test Book ";
		String[] genres = {"Absurdist", "Action", "Adventure", "Comedy", "Crime", "Drama", "Fantasy", "Historical", "Historical fiction", "Horror", "Magical realism", "Mystery", "Paranoid", "Philosophical", "Political", "Romance", "Saga", "Satire", "Science fiction", "Slice of Life", "Social", "Speculative", "Thriller", "Urban", "Western"};
		int randYear = 65;
		int randMonth = 12;
		int randDay = 25;
		String query = "";
		String randomQuery = "";
		Random rand = new Random();
		int bookLimit = 100;
		for (int i = 1; i <= bookLimit; i++) {
			randomQuery = "('" + i + "','" + i + "','" + titleParameter + " " + i +
					  "','" + genres[rand.nextInt(genres.length)] + "','AutoBuilder','LibSys','" +
					(1950 + rand.nextInt(randYear)) + "-" + (rand.nextInt(randMonth)+1) + "-" +
					  (rand.nextInt(randDay)+1)+"','" + ("#" + rand.nextInt(100)+1) + "')";
			query += randomQuery;
			if (i < bookLimit) {
				query += ",";
			}
		}
		sql.debugInsert(address, struct, query);
	}
	
}*/
