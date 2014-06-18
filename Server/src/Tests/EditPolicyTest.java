package Tests;
import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.*;

public class EditPolicyTest extends AbstractTest{
	
	
	@Test
	public void testSuccess(){
		handler.createForum("dogs", Configuration.superAdminName, Configuration.FALSE,
				Configuration.FALSE, "3", "3", Configuration.FALSE, Configuration.FALSE,
				"200",Configuration.OFFLINE);
		assertTrue(handler.setPolicy("dogs",  Configuration.superAdminName, Configuration.TRUE,
				Configuration.FALSE, "3", "3", Configuration.FALSE, Configuration.FALSE,
				"200",Configuration.OFFLINE));
	}
	
	@Test
	public void testMissingArgs(){
		handler.createForum("dogs", Configuration.superAdminName, Configuration.FALSE,
				Configuration.FALSE, "3", "3", Configuration.FALSE, Configuration.FALSE,
				"200",Configuration.OFFLINE);
	
		assertFalse(handler.setPolicy("dogs",  Configuration.superAdminName, Configuration.TRUE,
				Configuration.FALSE, "3", "3", "", Configuration.FALSE,
				"200",Configuration.OFFLINE));

	}
}
