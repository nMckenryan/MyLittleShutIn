package mylittleshutin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class handles database operations.
 * @author Nigel
 */
public class DatabaseOps {	
	String url = "jdbc:derby:DATABASES/SAVEDB;create=true"; 
	String usernameDerby="pdc";
	String passwordDerby="pdc";	
	Connection conn = null;
	
	public void estabConnection() { //Creates connection
            try {
                conn = DriverManager.getConnection(url, usernameDerby, passwordDerby);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseOps.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
	
	public void closeConnection() {
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException ex) {
				System.err.println("Cannot connect: " + ex);
            }
        }
    }
		
	public void createDatabase() { //Creates statements required to intially create database	
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			statement.executeUpdate("CREATE SCHEMA PDC");
			String sqlCreate = "CREATE TABLE SAVES"
						+ "(saveNumber INT PRIMARY KEY not NULL, "
						+ "shutName VARCHAR(20), shutWeight DOUBLE not NULL,"
						+ "shutBoredom INT, shutHealth INT, shutHunger INT, shutFunds INT,"
						+ "shutAlive BOOLEAN, shutDaysPassed INT,"
						+ "shutHungerGains FLOAT, shutBoredomGains FLOAT, "
						+ "shutHealthGains FLOAT,shutWeightGains FLOAT,"
						+ "shutFundGains FLOAT, shutInventory VARCHAR(40))";
			statement.executeUpdate(sqlCreate);
		} catch (SQLException ex) {
			System.out.println("Saves Table Found");
		} 
	}
	
	public void createNewRecord(String shutInName) {
		ShutIn newSI = new ShutIn();
		newSI.setShutInName(shutInName);
		if(conn != null) {
			try {
				Statement statement = conn.createStatement();
				
				//Getting Highest ShutInID and increases the number by one for assignment to new record.
				ResultSet rs = statement.executeQuery("SELECT MAX(SAVENUMBER) as maxId FROM SAVES");
				if(rs.next()) {
					newSI.setSaveID(rs.getInt("maxId") + 1);
				}
				
				String sqlCreate = "INSERT INTO SAVES VALUES (" + newSI + ")";
				
				try {
					statement.executeUpdate(sqlCreate);
					System.out.println(newSI.getSaveID() + newSI.getShutInName() + " Inserted");
				} catch (SQLIntegrityConstraintViolationException err) { //Catches error if sample record exist.
					System.out.println("Record already exists! " + err);
				}
			} catch (SQLException ex) {
				System.err.println("Cannot insert record" + ex);
				Logger.getLogger(DatabaseOps.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			System.out.println("Could not insert new Record. no connection");
		}
	}
	
	public void insertSamples() { //Inserts sample records to DB
		if(conn != null) {
				try {
					Statement statement = conn.createStatement();
					//Inserting Initial Values (First value is for testing purposes)
					// 0 = ID, 1 = name, 2 = weight, 3 = boredom, 4 = health, 5 = hunger, 6 = funds
					//7 = isAlive, 8 = days, 9 = hungerG, 10= boredG, 11 = healthG, 12 = weightG, 13 = fundG, 14 = inven
					String sqlCreate = "INSERT INTO SAVES VALUES "
						+ "(0, 'TESTTESTTEST', 111.0, 2, 4, 5, 60, false, 10, 8.0, 9.0, -10.0, 11.0, 12.0, 'nada'),"
						+ "(1, 'Big McHuge', 200.0, 50, 100, 23, 29, true, 20, 6.0, 7.0, -0.5, 12.0, 50.0, 'nada')";
					try {
						statement.executeUpdate(sqlCreate);
						System.out.println("Sample Records created");
					} catch (SQLIntegrityConstraintViolationException err) { //Catches error if sample record exist.
						System.out.println("Sample Records already exist");
					}
				} catch (SQLException ex) {
					System.err.println("Cannot insert sample records" + ex);
					Logger.getLogger(DatabaseOps.class.getName()).log(Level.SEVERE, null, ex);
				}
		} else {
			System.out.println("Could not insert samples. no connection");
		}
	}
	
	public void saveRecord(ShutIn savedChar) {//Saves progress of character to Database
		if(conn != null && savedChar != null) {
			try {
				Statement statement = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				String sqlQuery = "UPDATE SAVES SET shutWeight = " + savedChar.getBodyweight()
						+ ", shutBoredom = " + savedChar.getBoredom() + ", shutHealth = " + savedChar.getHealth() +", "
						+ "shutHunger = " +savedChar.getHunger() +", shutFunds = " + savedChar.getFunds() + ", "
						+ "shutAlive = " + savedChar.checkAlive() +", shutDaysPassed = " + savedChar.getDaysPassed() +", "
						+ "shutHungerGains = " + savedChar.getHungerGains() +", shutBoredomGains = " + savedChar.getBoredomGains() +", "
						+ "shutHealthGains = " + savedChar.getHealthGains() +", shutWeightGains = " + savedChar.getWeightGains() +", "
						+ "shutFundGains = " + savedChar.getFundGains() +", shutInventory = '" + savedChar.getInventory() + "' "
						+ "WHERE SAVENUMBER = " + savedChar.getSaveID();
				statement.executeUpdate(sqlQuery);
				
			} catch(Exception err) {
				System.out.println("Could not save Record: " + err);
			}
		} else {
			System.err.println("Could not save. Connection: " + conn + "Char: " + savedChar);
		}
	}
	
	public ArrayList<String> getRecord(String name) { //Retreives record from database
		ShutIn returnedShutIn = null;
		ArrayList retrievedList = null;
		String sqlQuery = null;
		
        if(conn != null) {
			try {
				Statement statement = conn.createStatement(
						ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				if(name == "ß") { //ALT+225 ß is a key that gets all saves. (except the test save)
					 sqlQuery = "SELECT * FROM SAVES WHERE NOT (SAVENUMBER = 0 AND SHUTNAME = 'TESTTESTTEST')";
				} else {
					sqlQuery = "SELECT * FROM SAVES WHERE SHUTNAME = '" + name + "'";
				}
				ResultSet rs = statement.executeQuery(sqlQuery);

				if(!rs.next()) {
					System.out.println("No Results Retrieved");
				} else {
					retrievedList = new ArrayList();
					do {
						returnedShutIn = new ShutIn();  
						returnedShutIn.setSaveID(Integer.parseInt(rs.getString("SAVENUMBER")));
						returnedShutIn.setShutInName(rs.getString("SHUTNAME"));
						returnedShutIn.setBodyweight(Float.parseFloat(rs.getString("SHUTWEIGHT")));
						returnedShutIn.changeHealth(Integer.parseInt(rs.getString("SHUTHEALTH")));	
						returnedShutIn.changeFunds(Integer.parseInt(rs.getString("SHUTFUNDS")));
						returnedShutIn.setAlive(Boolean.parseBoolean(rs.getString("SHUTALIVE")));
						returnedShutIn.setDaysPassed(Integer.parseInt(rs.getString("SHUTDAYSPASSED")));					

						returnedShutIn.changeHungerGains(Float.parseFloat(rs.getString("SHUTHUNGERGAINS")));
						returnedShutIn.changeBoredomGains(Float.parseFloat(rs.getString("SHUTBOREDOMGAINS")));
						returnedShutIn.changeHealthGains(Float.parseFloat(rs.getString("SHUTHEALTHGAINS")));
						returnedShutIn.changeWeightGains(Float.parseFloat(rs.getString("SHUTWEIGHTGAINS")));
						returnedShutIn.changeFundGains(Float.parseFloat(rs.getString("SHUTFUNDGAINS")));
						returnedShutIn.addInventory(rs.getString("SHUTINVENTORY"));

						retrievedList.add(returnedShutIn);
					} while(rs.next());
				}
			} catch (SQLException ex) {
				Logger.getLogger(DatabaseOps.class.getName()).log(Level.SEVERE, null, ex);
			}
		} else {
			System.err.println("Record not retrieved, No Connection.");
		}		
        return retrievedList;
	}
}