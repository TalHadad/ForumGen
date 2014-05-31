package TestPackage;

import Forum.Configuration;

public class CreateSubForumTest {

	public static void startTest() {
		ServerHandlerInterface serv_handler = AllTest.serv_handler;
		String response = null;
		Integer testNumber = 1;
		if(serv_handler==null){
			System.out.println("CreateSubForumTest failed - server handler was not created");
			return;
		}
		response = serv_handler.DisplayForums();
		if( response == null  || response.equals("") ){
			System.out.println("CreateSubForumTest failed - no forum was created");
			return;
		}
		
		String forumName = response.split(Configuration.DELIMITER1)[0];
		
		//test1 - no subForums in forum.
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - no forums in forum before create sub forum:");
		response = serv_handler.getSubForums(forumName);
		if(response.equals("")){
			System.out.println("test "+ testNumber.toString() + " success - no sub forums in the forum.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - there is sub forum in the system that not created by user.");
		}
		
		testNumber++;
		
		//test2 - create sub forum in forum that does not exist
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - create sub forum in forum that does not exist:");
		response = serv_handler.createSubForum(forumName + "1", "sub_forum", "subject", "moderatorUserName", Configuration.superAdminName);
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - sub forum was not created.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - sub forum was created in forum that does not exists.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		
		//test3 - create sub forum with missing fields
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - create sub forum with missing fields:");
		response = serv_handler.createSubForum(forumName, null, null, "moderatorUserName", Configuration.superAdminName);
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - no sub forums was created.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - sub forum with empty fields was created.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		
		//test4 - create sub forum with moderator that does not exists
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - create sub forum with moderator that does not exists:");
		response = serv_handler.createSubForum(forumName, "sub_forum_0", "subject_0", "user_not_exists", Configuration.superAdminName);
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - no sub forums was created.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - sub forum with moderator that does not exist.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		//test5 - create sub forum properly
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - create sub forum properly:");
		response = serv_handler.createSubForum(forumName, "sub_forum1","subject1", "user_1", Configuration.superAdminName);
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " success - subforum_1 was created.");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - subforum_1 was created.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		//test5 - verify create sub forum
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - verify create sub forum:");
		response = serv_handler.getSubForums(forumName);
		String[] subforums = response.split(Configuration.DELIMITER1);
		if(subforums.length == 0){
			System.out.println("test "+ testNumber.toString() + " failed - subforum_1 was not shown.");
		}
		else if (response.contains("sub_forum1")){
			System.out.println("test "+ testNumber.toString() + " succeed - sub_forum1 is shoewn.");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - sub_forum1 isnt shoewn.");
		}
		
		
		testNumber++;
		
		//test6 - create sub forum with existing name
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - create sub forum with existing name:");
		response = serv_handler.createSubForum(forumName, "sub_forum1","subject1", "user_1", Configuration.superAdminName);
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - the sub forum was not created.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - subforum_1 was created twice.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		
		//test7 - create second sub_forum
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - create second sub_forum:");
		response = serv_handler.createSubForum(forumName, "sub_forum2","subject2", "user_2", Configuration.superAdminName);
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " success - sub forum2 was created.");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - subforum_2 was not created twice.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
				
		//test8 - remove second sub_forum
		System.out.println("CreateSubForumTest test "+ testNumber.toString() + " - remove sub forum properly:");
		response = serv_handler.deleteSubForum(forumName, "sub_forum2");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " success -  sub forum2 was deleted.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - success -  sub forum2 was not deleted.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;

	}

}
