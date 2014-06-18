package Tests;

import junit.framework.*;

import Configuration.Configuration;
import Connection.protocol.ServerHandler;

public class AbstractTest extends TestCase{
	protected ServerHandler handler = new ServerHandler();
	 protected void setUp() throws Exception {
	        super.setUp();
	        handler.initializeSystem(Configuration.superAdminName, Configuration.superAdminPassword);
	 }
	 
	 protected void tearDown() throws Exception {
	        handler.cleanAllData(Configuration.superAdminName,Configuration.superAdminPassword);
	        super.tearDown();
	 }

}
