package TestPackage;

import Forum.Configuration;

public class PublishResponse {

	public static void startTest() {
		ServerHandlerInterface serv_handler = AllTest.serv_handler;
		String response = null;
		Integer testNumber = 1;
		if(serv_handler==null){
			System.out.println("PublishResponse failed - server handler was not created");
			return;
		}
		response = serv_handler.DisplayForums();
		if( response == null  || response.equals("") ){
			System.out.println("PublishResponse failed - no forum was created");
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
		
		//test 1 - publish thread
		System.out.println("PublishResponse test "+ testNumber.toString() + " - publish thread:");
		response = serv_handler.publishNewThread(forumName, subForumName, "thread_title2", "thread_content2", "user_1");
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
		//test 2 - verify thread in subForum after publish
		System.out.println("PublishResponse test "+ testNumber.toString() + " - verify thread in sub Forum after publish 'bad' massage:");
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
			//test 3 - publish response with empty fields
			System.out.println("PublishResponse test "+ testNumber.toString() + " - publish response with empty fields:");
			response = serv_handler.postThreadResponse(null, null, forumName, subForumName, "user_1", thread_id);
			if(response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " failed - thread response with empty fields was deleted.");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " succeed - thread response with empty fields was deleted.");
			}
			else {
				System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
			}
			
			testNumber++;
			
			//test 3 - publish response
			System.out.println("PublishResponse test "+ testNumber.toString() + " - publish response with empty fields:");
			response = serv_handler.postThreadResponse("response_title","response_content" , forumName, subForumName, "user_1", thread_id);
			if(response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " failed - thread response with empty fields was deleted.");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " succeed - thread response with empty fields was deleted.");
			}
			else {
				System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
			}
			
			testNumber++;
			
			//test 6 - verify response in subForum after publish
			System.out.println("PublishThreadTest test "+ testNumber.toString() + " - verify response in sub Forum after publish 'bad' massage:");
			response = serv_handler.getThreads(forumName, subForumName);
			String[] allresponse = response.split(Configuration.DELIMITER1);
			boolean responseFound = false;
			String response_id = null;
			for(int i=0;i<allresponse.length;i++){
				if("response_title".equals(allthreads[i].split(Configuration.DELIMITER2)[0])){
					responseFound = true;
					response_id = allthreads[i].split(Configuration.DELIMITER2)[1];
					break;
				}
			}
			if(responseFound){
				System.out.println("test "+ testNumber.toString() + " success - thread was published.");
			
			}
			else{
				System.out.println("test "+ testNumber.toString() + " failed - thread was not published.");
			}
		}
		
		
	}

}
