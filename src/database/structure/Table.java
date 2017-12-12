package database.structure;

import java.util.List;

import database.structure.operand.Syntax;

import java.util.ArrayList;

public class Table extends Syntax{
	protected List<Column> tableColumns;
	protected String       tableName, schemaName;
	public Table () {
		
	}
	
	public Table(String tableName, 
			     ArrayList<Column> columns) throws Exception {
		this(null, tableName, columns);
		
	}
	
	public Table (String schemaName, String tableName, ArrayList<Column> columns) throws Exception {
		if (isValid(tableName) && isValid(columns)) {
			this.schemaName   = schemaName;
			this.tableName    = tableName.toLowerCase();
			this.tableColumns = new ArrayList<Column>(columns);
		}
	}
	
	protected boolean isValid(String tableName) throws Exception {
		if (isInvalid(tableName)) {
			throw new Exception("No table name provided.");
		}
		return true;
	}
	
	protected boolean isValid(ArrayList<Column> columns) throws Exception {
		if (columns.isEmpty() ||
			columns == null) {
			throw new Exception("No columns provided.");
		}

		for (int i = 0; i < columns.size(); i++) {
			if (isInvalid(columns.get(i).getName())) {
				throw new Exception("Column #" + i + " is empty.");
			}
		}
		return true;
	}
	
	public int getColumnSize() {
		return tableColumns.size();
	}
	
	public String getColumnName(int i) {
		return tableColumns.get(i).getName();
	}
	
	public String getColumnType(int i) {
		return tableColumns.get(i).getType();
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public String getSchemaName() {
		return schemaName;
	}
	
	public boolean isPrimaryKey(int i) {
		return tableColumns.get(i).isPrimaryKey();
	}
	
	public String getSqlStructure() {
		String query = tableName + "(";
		String primaryKeyQuery = "PRIMARY KEY(";
		int keyCount = 0;
		for (int i = 0; i < tableColumns.size(); i++) {
			if (tableColumns.get(i).isPrimaryKey()) {
				if (keyCount > 0 ) {
					primaryKeyQuery += ", ";
				}
				primaryKeyQuery += tableColumns.get(i).getName();
				keyCount++;
			}
			query += tableColumns.get(i).getName() + " " +
					 tableColumns.get(i).getType() + ", ";
		}
		primaryKeyQuery += "));";
		query += primaryKeyQuery;
		return query;
	}
	
}
