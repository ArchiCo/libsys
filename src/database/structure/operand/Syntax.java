package database.structure.operand;

public abstract class Syntax {
	protected boolean isInvalid(String string) {
		if (string.equals("") == true || 
			string.isEmpty()  == true ||
		    string == null) {
			return true;
		} else {
			return false;
		}
	}
}
