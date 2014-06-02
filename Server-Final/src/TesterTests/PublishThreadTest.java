package TesterTests;

import Configuration.Configuration;
import Connection.protocol.ServerHandlerInterface;

public class PublishThreadTest {

	public static void startTest() {
		ServerHandlerInterface serv_handler = AllTest.serv_handler;
		String response = null;
		Integer testNumber = 1;
		if(serv_handler==null){
			System.out.println("PublishThreadTest failed - server handler was not created");
			return;
		}
		response = serv_handler.DisplayForums();
		if( response == null  || response.equals("") ){
			System.out.println("PublishThreadTest failed - no forum was created");
			return;
		}
		
		String forumName = response.split(Configuration.DELIMITER1)[0];
		
		response = serv_handler.getSubForums(forumName);
		String[] subforums = response.split(Configuration.DELIMITER1);
		if(subforums.length == 0 ){
			System.out.println("test "+ testNumber.toString() + " failed - subforum_1 was not shown.");
			return;
		}
		String subForumName = subforums[0].split(Configuration.DELIMITER2)[0];
		
		//test 1 - verify no thread in subForum
		System.out.println("PublishThreadTest test "+ testNumber.toString() + " - verify no thread in sub Forum:");
		response = serv_handler.getThreads(forumName, subForumName);
		if(response.equals("")){
			System.out.println("test "+ testNumber.toString() + " success - no threads in new su bforum.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - there are threads in new sub forum.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		//test 2 - publish thread with empty fields
		System.out.println("PublishThreadTest test "+ testNumber.toString() + " - publish thread with empty fields:");
		response = serv_handler.publishNewThread(forumName, subForumName, null, null, "user_1");
		if(response.equals("")){
			System.out.println("test "+ testNumber.toString() + " success - thread with empty fields was not published.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - thread with empty fields was published.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		//test 3 - verify no thread in subForum after publish
		System.out.println("PublishThreadTest test "+ testNumber.toString() + " - verify no thread in sub Forum after publish 'bad' massage:");
		response = serv_handler.getThreads(forumName, subForumName);
		if(response.equals("")){
			System.out.println("test "+ testNumber.toString() + " success - no threads in new su bforum.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - there are threads in new sub forum.");
		}
		
		testNumber++;
		//test 4 - publish thread with of user that doesn't exists
		System.out.println("PublishThreadTest test "+ testNumber.toString() + " - publish thread with of user that doesn't exists:");
		response = serv_handler.publishNewThread(forumName, subForumName, "thread_title", "thread_content", "user_not_exists");
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - thread of not existing user was published.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - thread of not existing user was published.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		//test 5 - publish thread
		System.out.println("PublishThreadTest test "+ testNumber.toString() + " - publish thread:");
		response = serv_handler.publishNewThread(forumName, subForumName, "thread_title", "thread_content", "user_1");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " success - thread was published.");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - thread was not published.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		//test 6 - verify thread in subForum after publish
		System.out.println("PublishThreadTest test "+ testNumber.toString() + " - verify thread in sub Forum after publish 'bad' massage:");
		response = serv_handler.getThreads(forumName, subForumName);
		String[] allthreads = response.split(Configuration.DELIMITER1);
		boolean found = false;
		String thread_id = null;
		for(int i=0;i<allthreads.length;i++){
			if("thread_title".equals(allthreads[i].split(Configuration.DELIMITER2)[0])){
				found = true;
				thread_id = allthreads[i].split(Configuration.DELIMITER2)[1];
				break;
			}
		}
		if(found){
			System.out.println("test "+ testNumber.toString() + " success - thread was published.");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - thread was not published.");
		}
		
		if(thread_id!=null){
			testNumber++;
			//test 7 - remove thread
			System.out.println("PublishThreadTest test "+ testNumber.toString() + " - remove thread:");
			response = serv_handler.deleteThread(thread_id, subForumName, forumName);
			if(response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " success - thread was deleted.");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " failed - thread was not deleted.");
			}
			else {
				System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
			}
			
			testNumber++;
			//test 6 - verify thread remove from subForum after deletion
			System.out.println("PublishThreadTest test "+ testNumber.toString() + " -  verify thread remove from subForum after deletion:");
			response = serv_handler.getThreads(forumName, subForumName);
			allthreads = response.split(Configuration.DELIMITER1);
			found = false;
			for(int i=0;i<allthreads.length;i++){
				if("thread_title".equals(allthreads[i].split(Configuration.DELIMITER2)[0])){
					found = true;
					thread_id = allthreads[i].split(Configuration.DELIMITER2)[1];
					break;
				}
			}
			if(found){
				System.out.println("test "+ testNumber.toString() + " failed - thread was not deleted.");
			}
			else{
				System.out.println("test "+ testNumber.toString() + " succeed - thread was deleted.");
			}
		}
		
	}

}
