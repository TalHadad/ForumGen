package Tests;

import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.*;

public class FileModeratorComplaint extends AbstractTest{
	
	private String forumName ="dogs";
	private String moderatorUserName ="yael";
	private String userName="Sivan";
	private String modPass="27ya03";
	private String pass="12si34";
	private String modEmail= "nahon.yael@gmail.com";
	private String email= "sivan@gmail.com";
	private String subForumName ="labradudles";
	private String subject ="all about labradudles";
	private String title="my sweet lucky";
	private String content ="i have a 3 year old dog i just love to death";
	
	@Test
	public void testSuccess(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.moderatorUserName,this.forumName, 
				this.modPass, this.modEmail, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.moderatorUserName, Configuration.superAdminName);
		handler.registerToForum(this.userName,this.forumName, 
				this.pass, this.email, "my favorite color?", "green");
		handler.publishNewThread(this.forumName, this.subForumName,this.title,
				this.content, this.userName);
		assertTrue(handler.fileComplaint(this.forumName, this.subForumName,
				this.moderatorUserName, "shes not nice", this.userName, this.pass));
	
	}
	
	@Test
	public void testComplaintNotEmpty(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.moderatorUserName,this.forumName, 
				this.modPass, this.modEmail, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.moderatorUserName, Configuration.superAdminName);
		handler.registerToForum(this.userName,this.forumName, 
				this.pass, this.email, "my favorite color?", "green");
		
		handler.publishNewThread(this.forumName, this.subForumName,this.title,
				this.content, this.userName);
		assertFalse(handler.fileComplaint(this.forumName, this.subForumName,
				this.moderatorUserName, "", this.userName, this.pass));
	
	}
	
	@Test
	public void testMustBeLoggedin(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.moderatorUserName,this.forumName, 
				this.modPass, this.modEmail, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.moderatorUserName, Configuration.superAdminName);
		handler.registerToForum(this.userName,this.forumName, 
				this.pass, this.email, "my favorite color?", "green");
		handler.publishNewThread(this.forumName, this.subForumName,this.title,
				this.content, this.userName);
		handler.logout(this.forumName, this.userName);
		assertFalse(handler.fileComplaint(this.forumName, this.subForumName,
				this.moderatorUserName, "shes not nice", this.userName, this.pass));
	}
	
	@Test
	public void testMemHasOnePost(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.moderatorUserName,this.forumName, 
				this.modPass, this.modEmail, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.moderatorUserName, Configuration.superAdminName);
		handler.registerToForum(this.userName,this.forumName, 
				this.pass, this.email, "my favorite color?", "green");
		handler.publishNewThread(this.forumName, this.subForumName,this.title,
				this.content, this.userName);
		handler.registerToForum("asaf",this.forumName, 
				"1234", "asaf@gmail.com", "my favorite color?", "black");
		assertFalse(handler.fileComplaint(this.forumName, this.subForumName,
				this.moderatorUserName, "shes not nice", "asaf", "1234"));
	
	
		
	}
	
	

}