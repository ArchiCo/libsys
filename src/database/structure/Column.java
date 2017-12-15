package database.structure;

import database.structure.operand.Syntax;

public class Column extends Syntax{
	String name, datatype;
	boolean primaryKey = false;
	
	public Column (String name, String datatype) throws Exception {
		this(name, datatype, false);
	}
	
	public Column (String name, String datatype, boolean primaryKey) throws Exception {
		if (isInvalid(name)) {
			throw new Exception("Column's name is invalid.");
		}
		
		if (isInvalid(datatype)) {
			throw new Exception("Column's datatype is invalid.");
		}
		
		this.name       = name.toLowerCase();
		this.datatype   = datatype.toLowerCase();
		this.primaryKey = primaryKey;
	}
	
	protected String  getName()      { return       name; }
	protected String  getType()      { return   datatype; }
	protected boolean isPrimaryKey() { return primaryKey; }
	
}
