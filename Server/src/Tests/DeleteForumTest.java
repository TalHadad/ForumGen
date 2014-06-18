package Tests;

import java.util.Vector;

import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.ServerHandler;
import Connection.protocol.ServerHandlerInterface;

public class DeleteForumTest extends AbstractTest{

	private String forumName ="dogs";
	
	@Test
	public void testValidForumName(){
		assertFalse(handler.deleteForum("for"));
	}
	
	@Test
	public void testSucces(){
		handler.createForum(this.forumName, Configuration.superAdminName,Configuration.FALSE,
				Configuration.FALSE, "2", "3", Configuration.FALSE, Configuration.FALSE, "100",Configuration.OFFLINE);
		assertTrue(handler.deleteForum(this.forumName));
		Vector<String> forums =handler.DisplayForums();
		assertFalse(forums.contains(this.forumName));
	
	}
	
}