package Tests;

import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.*;

public class InitializationTest extends AbstractTest{
	
	@Override
	 protected void setUp() throws Exception {
        super.setUp();
        handler.cleanAllData(Configuration.superAdminName, Configuration.superAdminPassword);
 }
 
	
	@Test
	public void testPermission(){
		assertFalse(handler.initializeSystem("userName", "password"));
	}
	
	@Test
	public void testSuccess(){
		boolean ans=handler.initializeSystem(Configuration.superAdminName, Configuration.superAdminPassword);
		assertTrue(ans);
	}
	
	@Test
	public void testOnlyInitOnce(){
		handler.initializeSystem(Configuration.superAdminName, Configuration.superAdminPassword);
		assertFalse(handler.initializeSystem(Configuration.superAdminName, Configuration.superAdminPassword));
	}
	
	

}
