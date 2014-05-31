package TestPackage;

import Forum.Configuration;

public class LoginLogoutTest {

	public static void startTest() {
		ServerHandlerInterface serv_handler = AllTest.serv_handler;
		String response = null;
		Integer testNumber = 1;
		if(serv_handler==null){
			System.out.println("LoginLogoutTest failed - server handler was not created");
			return;
		}
		response = serv_handler.DisplayForums();
		if( response == null  || response.equals("") ){
			System.out.println("LoginLogoutTest failed - no forum was created");
			return;
		}
		String forumName = response.split(Configuration.DELIMITER1)[0];
		response = serv_handler.registerToForum("user_2", forumName, "8282", "user2@gmail.com", "yes?", "yes");
		if(response == null || !response.startsWith(Configuration.SUCCESS)){
			System.out.println("LoginLogoutTest failed - cant register to forum");
			return;
		}
		
		//test1 - logout after registration.
		System.out.println("LoginLogoutTest test "+ testNumber.toString() + " - try to logout after register:");
		response = serv_handler.logout(forumName, "user_2");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " success - user has logout after register (suppose to be already connected).");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - user has failed to logout after registration.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		//test2 - login user that doesnt exist.
		System.out.println("LoginLogoutTest test "+ testNumber.toString() + " - try to logout after register:");
		response = serv_handler.login(forumName, "user_not_exist","8282");
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - user that doesnt exist cant login.");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - user that doesnt exist has login.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
				
		testNumber++;
		//test3 - login with wrong password.
		System.out.println("LoginLogoutTest test "+ testNumber.toString() + " - try to login with wrong password");
		response = serv_handler.login(forumName, "user_2","1111");
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - cant log-in with worng password.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - login with wrong password.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
				
		testNumber++;
		
		//test3 - login of user.
		System.out.println("LoginLogoutTest test "+ testNumber.toString() + " - normal login");
		response = serv_handler.login(forumName, "user_2","8282");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " success - login user_2.");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - has failed.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;	
		//test4 - login of user already connected.
		System.out.println("LoginLogoutTest test "+ testNumber.toString() + " - try to login of user already connected");
		response = serv_handler.login(forumName, "user_2","8282");
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - login of connected user has failed.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - login of connected user has succeed.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;	
		//test4 - logout of user already connected.
		System.out.println("LoginLogoutTest test "+ testNumber.toString() + " - try to logout of user already connected");
		response = serv_handler.logout(forumName, "user_2");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " success - logout of connected user has succeed.");
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - logout of connected user has failed.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		testNumber++;	
		//test4 - logout of user that already logged-out.
		System.out.println("LoginLogoutTest test "+ testNumber.toString() + " - try to logout of user that already logged-out");
		response = serv_handler.logout(forumName, "user_2");
		if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " success - logout of not connected user has failed.");
		}
		else if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - logout of connected user has succeed.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
	}

}
