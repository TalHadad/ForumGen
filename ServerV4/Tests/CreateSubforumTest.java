package Tests;

import java.util.Vector;

import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.*;


public class CreateSubforumTest extends AbstractTest{
	private String forumName ="dogs";
	private String userName ="yael";
	private String pass="27ya03";
	private String email= "nahon.yael@gmail.com";
	private String subForumName ="labradudles";
	private String subject ="all about labradudles";
	
	@Test
	public void testSuccess(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.userName,this.forumName, 
				this.pass, this.email, "my mom?", "adi");
		assertTrue( handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.userName, Configuration.superAdminName));
		Vector<String> subforums =handler.getSubForums(this.forumName);
		assertTrue(subforums.contains(this.subForumName));
	}
	
	@Test
	public void testUniqueName(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.userName,this.forumName, 
				this.pass, this.email, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.userName, Configuration.superAdminName);
		assertFalse( handler.createSubForum(this.forumName, this.subForumName,
				"some other subject", this.userName, Configuration.superAdminName));
	
	}
}
