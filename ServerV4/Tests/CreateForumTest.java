package Tests;

import java.util.Vector;

import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.*;
import Configuration.Configuration;

public class CreateForumTest extends AbstractTest{
	
	private String forumName ="dogs";
	
	@Test
	public void testSucces(){
		assertTrue(handler.createForum(this.forumName, Configuration.superAdminName,Configuration.FALSE,
				Configuration.FALSE, "2", "3", Configuration.FALSE, Configuration.FALSE, "100",Configuration.OFFLINE));
		Vector<String> forums =handler.DisplayForums();
		assertTrue(forums.contains(forumName));
	}
	
	@Test
	public void testCreatorIsAdmin(){
		handler.createForum(this.forumName, Configuration.superAdminName,Configuration.FALSE,
				Configuration.FALSE, "2", "3", Configuration.FALSE, Configuration.FALSE, "100",Configuration.OFFLINE);
		assertTrue(handler.isAdmin(Configuration.superAdminName, this.forumName));
	}
	
	@Test
	public void testCreatorIsMember(){
		handler.createForum(this.forumName, Configuration.superAdminName,Configuration.FALSE,
				Configuration.FALSE, "2", "3", Configuration.FALSE, Configuration.FALSE, "100",Configuration.OFFLINE);
		assertTrue(handler.isMember(Configuration.superAdminName, this.forumName));
	}
	
	@Test
	public void testPermission(){
		assertFalse(handler.createForum("words", "userName",Configuration.FALSE,
				Configuration.FALSE, "3", "3", Configuration.FALSE, Configuration.FALSE,"200",Configuration.OFFLINE));
	}
	
	@Test
	public void testWrongDetails(){
		assertFalse(handler.createForum("words", Configuration.superAdminName, 
				"", "", Configuration.FALSE, "", "", "", "",""));
	}
	
	
	 


}