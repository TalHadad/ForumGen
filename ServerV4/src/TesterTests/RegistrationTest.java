package TesterTests;

import Configuration.Configuration;
import Connection.protocol.ServerHandlerInterface;

public class RegistrationTest {
	public static void startTest() {
		ServerHandlerInterface serv_handler = AllTest.serv_handler;
		String response = null;
		Integer testNumber = 1;
		if(serv_handler==null ){
			System.out.println("RegistrationTest failed - server handler was not created");
			return;
		}
		response = serv_handler.DisplayForums();
		if( response == null  || response.equals("") ){
			System.out.println("RegistrationTest failed - no forum was created");
			return;
		}
		String[] forums = response.split(Configuration.DELIMITER1);
		if(forums.length == 0){
			System.out.println("RegistrationTest failed - no forum was created");
			return;
		}
		//test1 - register to forum.
		System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - register to forum :");
		String forumName = forums[0];
		boolean user1registered = false;
		response = serv_handler.registerToForum("user_1", forumName, "8282", "user_1@gmail.com", "yes?", "yes!");
		if(response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " succeed - user has register to forum.");
			user1registered = true;
		}
		else if(response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - user has not register to forum.");
		}
		else if(response.startsWith(Configuration.PENDING_STR)){
			System.out.println("test "+ testNumber.toString() + " failed - user has register to forum but as pending.");
		}
		else {
			System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
		}
		
		if(user1registered){
			//test2 - register to forum with existing username.
			testNumber++;
			System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - register to forum with existing username:");
			response = serv_handler.registerToForum("user_1", forumName, "8283", "user_1@yahoo.com", "no?", "no!");
			if(response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " failed - user has register to forum with existing name.");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " success - user has not register to forum.");
			}
			else if(response.startsWith(Configuration.PENDING_STR)){
				System.out.println("test "+ testNumber.toString() + " failed - user has register to forum with existing name.");
			}
			else {
				System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
			}
			
			//test3 - try to login after register
			testNumber++;
			System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - try to login after register:");
			response = serv_handler.login(forumName, "user_1", "8282");
			if(response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " failed - user has login after register (suppose to be already connected).");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " success - user has failed to login after registration.");
			}
			else {
				System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
			}
			
			//test4 - try to logout after register
			testNumber++;
			System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - try to logout after register:");
			response = serv_handler.logout(forumName, "user_1");
			if(response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " success - user has logout after register (suppose to be already connected).");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " failed - user has failed to logout after registration.");
			}
			else {
				System.out.println("test "+ testNumber.toString() + " failed - indefineded response.");
			}
		}
		
	}
}
