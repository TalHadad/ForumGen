package TesterTests;

import Connection.protocol.ServerHandler;
import Connection.protocol.ServerHandlerInterface;

public class AllTest {
	
	public static ServerHandlerInterface serv_handler;
	
	public static boolean cont = true;

	public static void main(String args[]) {
	
	serv_handler = new ServerHandler();
	
	//Initialization tests
	System.out.println();
	System.out.println("---------- Initialization Test ----------");
	System.out.println("-----------------------------------------");

	InitializationTest.startTest();
	
	//ForumCreatetionTest tests
	System.out.println();
	System.out.println("---------- Forum Createtion Test ----------");
	System.out.println("-------------------------------------------");

	ForumCreatetionTest.startTest();

	//RegistrationTest tests
	System.out.println("---------- Registration Test ----------");
	System.out.println("---------------------------------------");

	RegistrationTest.startTest();

	//LoginLogoutTest tests
	System.out.println();
	System.out.println("---------- Login/Logout Test ------------");
	System.out.println("-----------------------------------------");

	LoginLogoutTest.startTest();
	
	//AddRemoveForumAdminTest tests
	System.out.println("---------- Add Remove Forum Admin Test ----------");
	System.out.println("-------------------------------------------------");
	AddRemoveForumAdminTest.startTest();
	
	//CreateSubForumTest tests
	System.out.println();
	System.out.println("---------- Create Sub Forum Test ----------");
	System.out.println("-------------------------------------------");
	CreateSubForumTest.startTest();

	//PublishThread tests
	System.out.println();
	System.out.println("---------- Publish Thread Test ----------");
	System.out.println("-----------------------------------------");
	PublishThreadTest.startTest();
	
	//PublishResponse tests
	System.out.println();
	System.out.println("---------- PublishResponse Test ----------");
	System.out.println("------------------------------------------");
	PublishResponse.startTest();
	
	
}
}
