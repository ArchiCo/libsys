package database;

public class Credentials {
	private final String adminUser = "AdminUser";
	private final String adminPass = "AdminPass";
			
	private String dbHostname  = "ec2-184-72-243-166.compute-1.amazonaws.com";
	private int    dbPort      = 5432;
	private String dbName      = "dbeqbssbap89ck";
	private String dbUsername  = "epoxusywcguize";
	private String dbPassword  = "9f5ce8aee62b66e69b388c89446c87518822090137cc57e861478f2ffea2376a";
	
	Credentials() {
		
	}
	
	Credentials(String hostname,
			    int    port,
			    String database,
			    String username,
			    String password) {
		
		dbHostname = hostname;
		dbPort     = port;
		dbName     = database;
		dbUsername = username;
		dbPassword = password;
	}
	
	public String getHostname() { return dbHostname; }
	public int    getPort()     { return     dbPort; }
	public String getDatabase()	{ return     dbName; }
	public String getUsername() { return dbUsername; }
	public String getPassword() { return dbPassword; }
	
	private boolean isAuthorized(String id, String pass) throws Exception {
		if (  id.equals(adminUser) == false ||
			pass.equals(adminPass) == false) {
			throw new Exception("Unauthorized access.");
		} else {
			return true;
		}
	}
	
	public void setHostname(String id, String pass, String newHost) throws Exception {
		if (isAuthorized(id, pass)) {
		    dbHostname = newHost;
		}
	}
	
	public void setPort(String id, String pass, int newPort) throws Exception {
		if (isAuthorized(id, pass)) {
		    dbPort = newPort;
		}
	}
	
	public void setDatabaseName(String id, String pass, String newDb) throws Exception {
		if (isAuthorized(id, pass)) {
		    dbName = newDb;
		}
	}
	
	public void setUsername(String id, String pass, String newUser) throws Exception {
		if (isAuthorized(id, pass)) {
		    dbUsername = newUser;
		}
	}
	
	public void setPassword(String id, String pass, String newPass) throws Exception {
		if (isAuthorized(id, pass)) {
		    dbPassword = newPass;
		}
	}

}
