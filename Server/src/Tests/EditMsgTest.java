package Tests;

import java.util.Vector;

import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.ServerHandler;
import Connection.protocol.ServerHandlerInterface;

public class EditMsgTest extends AbstractTest{
	
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
		
		assertTrue(handler.editTread(this.userName, this.forumName, this.subForumName, 
				getId(this.title), "changed my mind"));
		
		Vector<String> titles =getTitles();
		assertTrue(titles.contains(this.title));
		assertEquals("changed my mind",handler.getThreadContent(getId(this.title), 
				this.subForumName, this.forumName));
	}
	
	@Test
	public void testOnlyWriterCanEdit(){
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
		assertFalse(handler.editTread("asaf", this.forumName, this.subForumName, 
				getId(this.title), "changed my mind"));
	
	}
	
	
	private Vector<String> getTitles(){
		Vector<String> titles= new Vector<String>();	
		Vector<String> posts =handler.getThreads(this.forumName,this.subForumName);
		String[] title_id;
		for(String post:posts){
			title_id=post.split(Configuration.DELIMITER2);
			titles.add(title_id[0]);
		}
		return titles;
	}
		
	private String getId(String title){
		Vector<String> posts =handler.getThreads(this.forumName,this.subForumName);
		String[] title_id;
		for(String post:posts){
			title_id=post.split(Configuration.DELIMITER2);
			if(title_id[0]==title)
				return title_id[1];
		}
		return "";
	}

}
