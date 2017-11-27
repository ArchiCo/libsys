package database;

import java.util.ArrayList;

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
			tables.add(new Table(books.getName(), books.getColumns()));
			tables.add(new Table(customers.getName(), customers.getColumns()));
			tables.add(new Table(records.getName(), records.getColumns()));
			
			for (Table table : tables) {
				if (!sql.tableIsPresent(table.getTableName())) {
					sql.createTable(table);
				} else {
					System.out.println("Table " + table.getTableName() + " already exists.");
				} 
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private boolean isPresent(Table table, SQLController sql) {
		
		return true;
	}
	
}
