package Tests;


import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.*;
	
public class LoginLogoutTest extends AbstractTest{

		

	private String forumName ="dogs";
	private String userName ="yael";
	private String pass="27ya03";
	private String email= "nahon.yael@gmail.com";
	
	@Test
	public void testLoginSucces(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.userName,this.forumName, this.pass, this.email, 
				"my mom?", "adi");	
		handler.logout(this.forumName, this.userName);
		assertTrue(handler.login(this.forumName, this.userName, 
				this.pass).contains(Configuration.SUCCESS));	
	}
	
	@Test
	public void testWrongPass(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum("sivan",this.forumName, "1234", "sivan@google.com", 
				"my favorite color?", "green");
		handler.logout(this.forumName, "sivan");
		assertEquals(Configuration.FAIL,handler.login(this.forumName, "sivan", 
				this.pass));
	}
	
	@Test
	public void testFakeMember(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		assertTrue(handler.login(this.forumName, "tal", 
				"11111").contains(Configuration.FAIL));
	}
	
	@Test
	public void testWrongForum(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.userName,this.forumName, this.pass, this.email, 
				"my mom?", "adi");
		handler.logout(this.forumName, this.userName);
		handler.createForum("cats", Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		assertTrue(handler.login(this.forumName, this.userName, this.pass).contains(Configuration.FALSE));
	}
	
	@Test
	public void testLogoutsSucces(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		
		handler.registerToForum(this.userName,this.forumName, this.pass, this.email, 
				"my mom?", "adi");
		assertTrue(handler.logout(this.forumName, this.userName));
	}
	
	@Test
	public void testLogoutTwice(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "2", "3", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		
		handler.registerToForum(this.userName,this.forumName, this.pass, this.email, 
				"my mom?", "adi");
		assertFalse(handler.logout(this.forumName, this.userName));
	}
}
