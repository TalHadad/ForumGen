package Tests;

import java.util.Vector;

import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.ServerHandler;
import Connection.protocol.ServerHandlerInterface;

public class DeleteSubforumTest extends AbstractTest{

	private String forumName="dogs";
	private String userName ="yael";
	private String pass="27ya03";
	private String email= "nahon.yael@gmail.com";
	private String subForumName ="labradudles";
	private String subject ="all about labradudles";

	@Test
	public void validSubforumNameTest(){
		assertFalse(handler.deleteSubForum(this.forumName, "subFo"));
	}
	
	@Test
	public void testSuccess(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.userName,this.forumName, 
				this.pass, this.email, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.userName, Configuration.superAdminName);
		assertTrue(handler.deleteSubForum(this.forumName, this.subForumName));
		Vector<String> subforums =handler.getSubForums(this.forumName);
		assertFalse(subforums.contains(this.subForumName));
	
	}
	
	

}
