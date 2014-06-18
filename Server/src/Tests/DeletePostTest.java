package Tests;

import java.util.Vector;

import org.junit.Test;

import junit.framework.*;
import Configuration.Configuration;
import Connection.protocol.ServerHandler;
import Connection.protocol.ServerHandlerInterface;

public class DeletePostTest extends AbstractTest{

	private String forumName ="dogs";
	private String moderatorUserName ="yael";
	private String userName1="Sivan";
	private String userName2="Asaf";
	private String modPass="27ya03";
	private String pass1="12si34";
	private String pass2="23si45";
	private String modEmail= "nahon.yael@gmail.com";
	private String email1= "sivan@gmail.com";
	private String email2= "Asaf@gmail.com";
	private String subForumName ="labradudles";
	private String subject ="all about labradudles";
	private String threadTitle="my sweet lucky";
	private String responseTitle="me to!";
	private String threadContent ="i have a 3 year old dog i just love to death";
	private String responseContent ="they are cute ha?!";
	
	@Test
	public void testDeleteResponseSuccess(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.moderatorUserName,this.forumName, 
				this.modPass, this.modEmail, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.moderatorUserName, Configuration.superAdminName);
		handler.registerToForum(this.userName1,this.forumName, 
				this.pass1, this.email1, "my favorite color?", "green");
		handler.publishNewThread(this.forumName, this.subForumName,this.threadTitle,
				this.threadContent, this.userName1);
		
		handler.registerToForum(this.userName2,this.forumName, 
				this.pass2, this.email2, "my favorite color?", "black");
		
		Vector<String> Threads=handler.getThreads(this.forumName,this.subForumName);
		String threadId =getId(this.threadTitle,Threads);
		Vector<String> Responses =handler.getThreadResponses(this.forumName,
				this.subForumName, threadId);
		String responseId =getId(this.responseTitle,Responses);
		handler.postThreadResponse(this.responseTitle, this.responseContent,
				this.forumName, this.subForumName, this.userName2,threadId);
		assertTrue(handler.deleteThreadResponse(threadId, responseId, this.forumName, 
				this.subForumName));
		
		Vector<String> responsesTitles =getTitles(Responses);
		assertFalse(responsesTitles.contains(this.responseTitle));			
	} 
	
	@Test
	public void testDeleteThreadSuccess(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.moderatorUserName,this.forumName, 
				this.modPass, this.modEmail, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.moderatorUserName, Configuration.superAdminName);
		handler.registerToForum(this.userName1,this.forumName, 
				this.pass1, this.email1, "my favorite color?", "green");
		handler.publishNewThread(this.forumName, this.subForumName,this.threadTitle,
				this.threadContent, this.userName1);
		
		handler.registerToForum(this.userName2,this.forumName, 
				this.pass2, this.email2, "my favorite color?", "black");
		
		Vector<String> Threads=handler.getThreads(this.forumName,this.subForumName);
		String threadId =getId(this.threadTitle,Threads);
		Vector<String> Responses =handler.getThreadResponses(this.forumName,
				this.subForumName, threadId);
		String responseId =getId(this.responseTitle,Responses);
		handler.postThreadResponse(this.responseTitle, this.responseContent,
				this.forumName, this.subForumName, this.userName2,threadId);
		Threads=handler.getThreads(this.forumName,this.subForumName);
		threadId =getId(this.threadTitle,Threads);
		assertTrue(handler.deleteThread(threadId, this.subForumName, this.forumName));
		Vector<String> threadsTitles=getTitles(Threads);
		assertFalse(threadsTitles.contains(this.threadTitle));
	}
		
	@Test
	public void testValidThreadId(){
		handler.createForum(this.forumName, Configuration.superAdminName
				,Configuration.FALSE,Configuration.FALSE, "0", "0", Configuration.FALSE,
				Configuration.FALSE,"100",Configuration.OFFLINE);
		handler.registerToForum(this.moderatorUserName,this.forumName, 
				this.modPass, this.modEmail, "my mom?", "adi");
		handler.createSubForum(this.forumName, this.subForumName,
				this.subject, this.moderatorUserName, Configuration.superAdminName);
		handler.registerToForum(this.userName1,this.forumName, 
				this.pass1, this.email1, "my favorite color?", "green");
		handler.publishNewThread(this.forumName, this.subForumName,this.threadTitle,
				this.threadContent, this.userName1);
		assertFalse(handler.deleteThread("", this.subForumName, this.forumName));
	}
	
	private String getId(String title, Vector<String> posts){
		String[] title_id;
		for(String post:posts){
			title_id=post.split(Configuration.DELIMITER2);
			if(title_id[0]==title)
				return title_id[1];
		}
		return "";
	}
	
	private Vector<String> getTitles(Vector<String> posts){
		Vector<String> titles= new Vector<String>();
		String[] title_id;
		for(String post:posts){
			title_id=post.split(Configuration.DELIMITER2);
			titles.add(title_id[0]);
		}
		return titles;
	}
	

}

