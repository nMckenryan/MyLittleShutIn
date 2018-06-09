/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mylittleshutin;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Arrays.asList; 
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nigel
 */
public class DatabaseOpsTest {
	Connection conn;
	
	public DatabaseOpsTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * Test of estabConnection method, of class DatabaseOps.
	 */
	@Test
	public void testEstabConnection() {
		System.out.println("estabConnection");
		DatabaseOps instance = new DatabaseOps();
		instance.estabConnection();
	}

	/**
	 * Test of closeConnection method, of class DatabaseOps.
	 */
	@Test
	public void testCloseConnection() {
		System.out.println("closeConnection");
		DatabaseOps instance = new DatabaseOps();
		instance.closeConnection();
	}

	/**
	 * Test of createDatabase method, of class DatabaseOps.
	 */
	@Test
	public void testCreateDatabase() {
		System.out.println("createDatabase");
		DatabaseOps instance = new DatabaseOps();
		instance.estabConnection();
		instance.createDatabase();
	}
	
	@Test
	public void testInsertSamples() {
		System.out.println("InsertSamples");
		DatabaseOps instance = new DatabaseOps();
		instance.estabConnection();
		instance.insertSamples();
	}

	/**
	 * Test of getRecord method, of class DatabaseOps.
	 */
	@Test
	public void testGetRecord() {
		System.out.println("getRecord");
		String searchData = "TESTTESTTEST";
		DatabaseOps instance = new DatabaseOps();
		instance.estabConnection();	
		ShutIn expResult = new ShutIn("0, TESTTESTTEST, 111.0, 20, 94, 10, 110, false, 10, 9.0, 10.0, -9.7, 11.5, 32.0, nada");
		ShutIn result = ShutIn.listToCharacter(instance.getRecord(searchData));
		expResult.equals(result);
		assertEquals(expResult, result);
		System.out.println(result + "\n\n" + expResult);
		//Not working, though functions are identical
		//expected: mylittleshutin.ShutIn<0, 'TESTTESTTEST', 111.0, 20, 94, 10, 110, false, 10, 9.0, 10.0, -9.7, 11.5, 32.0, 'nada'>
		//but was:  mylittleshutin.ShutIn<0, 'TESTTESTTEST', 111.0, 20, 94, 10, 110, false, 10, 9.0, 10.0, -9.7, 11.5, 32.0, 'nada'>
		//junit.framework.AssertionFailedError
		//	at mylittleshutin.DatabaseOpsTest.testGetRecord(DatabaseOpsTest.java:103)

	}
}