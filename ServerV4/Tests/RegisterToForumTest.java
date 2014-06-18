package Tests;


import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.*;

public class RegisterToForumTest extends AbstractTest{
	
	private String forumName ="dogs";
	private String userName ="yael";
	private String pass="27ya03";
	private String email= "nahon.yael@gmail.com";
	
	@Test
	public void testSucces(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		assertTrue(handler.registerToForum(this.userName,
				this.forumName, this.pass, this.email, "my mom?", "adi").contains(Configuration.SUCCESS));
		assertTrue(handler.isMember(this.userName, this.forumName));
	}
	
	@Test
	public void testUniqueName(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.userName,
				this.forumName, this.pass, this.email, "my mom?", "adi").contains(Configuration.SUCCESS);
		assertTrue(handler.registerToForum(this.userName,
				this.forumName, this.pass, this.email, "my mom?", "adi").contains(Configuration.FAIL));
	}
	
	@Test
	public void testUniqueEmail(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.userName,
				this.forumName, this.pass, this.email, "my mom?", "adi").contains(Configuration.SUCCESS);
		assertTrue(handler.registerToForum("sivan",
				this.forumName, this.pass, this.email, "my mom?", "adi").contains(Configuration.FAIL));
	}

}
