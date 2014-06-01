package TesterTests;

import Connection.protocol.ServerHandler;
import Connection.protocol.ServerHandlerInterface;

public class AllTest {
	
	public static ServerHandlerInterface serv_handler;
	
	public static boolean cont = true;

	public static void main(String args[]) {
	
	serv_handler = new ServerHandler();
	
	//Initialization tests
	System.out.println("---------- Initialization Test ----------");
	InitializationTest.startTest();
	
	//ForumCreatetionTest tests
	System.out.println("---------- Forum Createtion Test ----------");
	ForumCreatetionTest.startTest();

	//RegistrationTest tests
	System.out.println("---------- Registration Test ----------");
	RegistrationTest.startTest();

	//LoginLogoutTest tests
	System.out.println("---------- Login/Logout Test ----------");
	LoginLogoutTest.startTest();
	
	//AddRemoveForumAdminTest tests
	System.out.println("---------- Add Remove Forum Admin Test ----------");
	AddRemoveForumAdminTest.startTest();
	
	//CreateSubForumTest tests
	System.out.println("---------- Create Sub Forum Test ----------");
	CreateSubForumTest.startTest();

	//PublishThread tests
	System.out.println("---------- Publish Thread Test ----------");
	PublishThreadTest.startTest();
	
	//PublishResponse tests
	System.out.println("---------- PublishResponse Test ----------");
	PublishResponse.startTest();
	
	
}
}
