package Tests;



import junit.framework.TestSuite;

import org.junit.Test;

import Configuration.Configuration;

public class ApointBandModeratorTest extends AbstractTest{
	private String forumName ="dogs";
	private String userName ="yael";
	private String pass="27ya03";
	private String email= "nahon.yael@gmail.com";
	private String subForumName ="labradudles";
	private String subject ="all about labradudles";
	
	@Test
	public void testApointSuccess(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.userName,this.forumName, 
				this.pass, this.email, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,this.subject,
			this.userName, Configuration.superAdminName);
		handler.registerToForum("sivan",this.forumName, 
				"1111", "sivan@gmail.com", "my mom?", "lala");
		handler.registerToForum("sivana",this.forumName, 
				"2222", "sivana@gmail.com", "my mom?", "baba");
		assertTrue(handler.upgradeToModerator(Configuration.superAdminName, this.forumName, 
				this.subForumName, "sivan"));
		assertTrue(handler.isModerator("sivan", this.forumName, this.subForumName));
	}
	
	@Test
	public void testPermission(){
		assertFalse(handler.upgradeToModerator(this.userName, this.forumName, this.subForumName,
				"sivana"));
		assertFalse(handler.banModerator(this.userName, this.forumName, this.subForumName,
				"sivan"));
	}
	
	@Test
	public void testBanSuccess(){
		assertTrue(handler.banModerator(Configuration.superAdminName, this.forumName,
				this.subForumName, "sivan"));
		assertFalse(handler.isModerator("sivan", this.forumName, this.subForumName));
	}

}
