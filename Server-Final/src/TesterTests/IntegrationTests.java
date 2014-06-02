package TesterTests;

import javax.print.attribute.standard.Severity;

import Configuration.Configuration;
import Connection.protocol.ServerHandler;
import Connection.protocol.ServerHandlerInterface;

public class IntegrationTests {
	public static ServerHandlerInterface serv_handler;
	
	public static boolean cont = true;

	public static void main(String args[]) {
	
	serv_handler = new ServerHandler();
	
	IntegrationTest1();
	
	IntegrationTest2();

	
	}
	// Integratin Test 1 - create forum,sub forum, and register
	// Integratin Test 1 - publish massage and watch it
	private static void IntegrationTest1() {
		serv_handler.initializeSystem(Configuration.superAdminName, Configuration.superAdminPassword);
		serv_handler.createForum("forum1", Configuration.superAdminName, Configuration.FALSE, Configuration.FALSE, "0", "3", Configuration.FALSE, Configuration.FALSE, "2");
		serv_handler.createSubForum("forum1", "subforum1", "subject",Configuration.superAdminName, Configuration.superAdminName);
		serv_handler.registerToForum("user_1","forum1" , "8282", "user_1@gmail.com", "yes?"	, "yes");
		serv_handler.publishNewThread("forum1", "subforum1", "Title", "contant", "user_1");
		String response = serv_handler.getThreads("forum1", "subforum1");
		String[] threads  = response.split(Configuration.DELIMITER1);
		for(int i = 0; i<threads.length;i++){
			System.out.println("thread " + i + " : " + threads[i]);
		}
	}

	private static void IntegrationTest2() {
		serv_handler.initializeSystem(Configuration.superAdminName, Configuration.superAdminPassword);
		serv_handler.createForum("forum2", Configuration.superAdminName, Configuration.FALSE, Configuration.FALSE, "0", "3", Configuration.FALSE, Configuration.FALSE, "2");
		serv_handler.createSubForum("forum2", "subforum2", "subject",Configuration.superAdminName, Configuration.superAdminName);
		serv_handler.registerToForum("user_2","forum2" , "8282", "user_1@gmail.com", "yes?"	, "yes");
		String response = serv_handler.logout("forum2", "user_2");
		if(response!= null && response.startsWith(Configuration.SUCCESS));
		else{
			System.out.println("Integration Test 2 failed - logout failed");
			return;
		}
		response = serv_handler.login("forum2", "user_2","8282");
		if(response!= null && response.startsWith(Configuration.SUCCESS));
		else{
			System.out.println("Integration Test failed - login failed");
			return;
		}
		response = serv_handler.logout("forum2", "user_2");
		if(response!= null && response.startsWith(Configuration.SUCCESS))
			System.out.println("Integration Test 2 succeed.");
		else{
			System.out.println("Integration Test 2 failed - logout failed");
			return;
		}
		response = serv_handler.login("forum2", "user_2","8282");
	}
}