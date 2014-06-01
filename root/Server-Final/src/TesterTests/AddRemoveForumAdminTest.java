package TesterTests;

import Configuration.Configuration;
import Connection.protocol.ServerHandlerInterface;
public class AddRemoveForumAdminTest {

	public static void startTest() {
		ServerHandlerInterface serv_handler = AllTest.serv_handler;
		String response = null;
		Integer testNumber = 1;
		if(serv_handler==null){
			System.out.println("AddRemoveForumAdminTest failed - server handler was not created");
			return;
		}
		response = serv_handler.DisplayForums();
		if( response == null  || response.equals("") ){
			System.out.println("AddRemoveForumAdminTest failed - no forum was created");
			return;
		}
		
		String forumName = response.split(Configuration.DELIMITER1)[0];

		//test1 - add not existing user as admin.
		System.out.println("AddRemoveForumAdminTest test "+ testNumber.toString() + " - add not existing user as admin:");
		response = serv_handler.addAdminToForum(forumName, "user_not_exiting");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - user_not_exiting was add as admin.");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - user_not_exiting was not add as admin.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;
		//test1 - add user_1 as admin.
		System.out.println("AddRemoveForumAdminTest test "+ testNumber.toString() + " - add user_1 as admin:");
		boolean user1admin =false;
		response = serv_handler.addAdminToForum(forumName, "user_2");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " success - user_1 was add as admin.");
			user1admin = true;
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - user_1 was not add as admin.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		if(user1admin){
			//test2 - add admin user that is already admin.
			testNumber++;	
			System.out.println("AddRemoveForumAdminTest test "+ testNumber.toString() + " - add admin user that is already admin:");
			response = serv_handler.addAdminToForum(forumName, "user_1");
			if(response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " failed - user_1 is admin and was add as admin .");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " success - user_1 was not add as admin.");
			}
			else {
				System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
			}
			
			//test4 - remove user 1  admin.
			testNumber++;
			System.out.println("AddRemoveForumAdminTest test "+ testNumber.toString() + " - add admin user that is already admin:");
			response = serv_handler.removeAdminFromForum(forumName, "user_1");
			if(response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " success - user_1 is not longer administrator .");
				user1admin = false;
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " success - user_1 was not remove from adminstrator.");
			}
			else {
				System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
			}
			
			if(!user1admin){
				//test5 - remove admin that is not admin.
				testNumber++;
				System.out.println("AddRemoveForumAdminTest test "+ testNumber.toString() + " - remove admin that is not admin:");
				response = serv_handler.removeAdminFromForum(forumName, "user_1");
				if(response.startsWith(Configuration.SUCCESS)){
					System.out.println("test "+ testNumber.toString() + " failed - user1 was remove but is not admin .");
				}
				else if(response.startsWith(Configuration.FAIL)){
					System.out.println("test "+ testNumber.toString() + " success - user_1 was not remove.");
				}
				else {
					System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
				}
			}
			
		}
		//test6 - remove not existing user admin.
		System.out.println("AddRemoveForumAdminTest test "+ testNumber.toString() + " - add not existing user as admin:");
		response = serv_handler.removeAdminFromForum(forumName, "user_not_exiting");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - user_not_exiting was remove as admin.");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - user_not_exiting was not remove as admin.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
	}

}
