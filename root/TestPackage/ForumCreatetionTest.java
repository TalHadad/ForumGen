package TestPackage;

import Forum.Configuration;

public class ForumCreatetionTest {
	public static void startTest() {
		ServerHandlerInterface serv_handler = AllTest.serv_handler;
		String response = null;
		Integer testNumber = 1;
		if(serv_handler==null){
			System.out.println("ForumCreatetionTest failed - server handler was not created");
			return;
		}
		//test1 - display forum before starting.
		System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - varify that threre are not forums :");
		response = serv_handler.DisplayForums();
		if(response == null){
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		}
		else if (response.equals("")){
			System.out.println("test "+ testNumber.toString() + " succeed - no forum in the system.");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - displays forum before forum creation.");
		}
		
		//test2 - try to create forum without name
		testNumber++;
		System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - try to create forum without name :");
		//String d = Configuration.DELIMITER1;
		//String policy1 = "policy1"+d+Configuration.f
		response = serv_handler.createForum(null, Configuration.superAdminName,	 Configuration.FALSE, Configuration.FALSE, "0"	, "0", Configuration.FALSE, Configuration.FALSE,"19.04.14");
		if(response == null){
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		}
		else if (response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " succeed - created forum without name was failed as supposed.");
			response = serv_handler.DisplayForums();
			if(response == null){
				System.out.println("test "+ testNumber.toString() + ".1 failed - returned null.");
			}
			else if (response.equals("")){
				System.out.println("test "+ testNumber.toString() + ".1 succeed - no forum in the system.");
			}
			else{
				System.out.println("test "+ testNumber.toString() + ".1 failed - displays forum although no forum was created.");
			}
		}
		else if (response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - created forum without name.");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - undefinded response.");
		}
		
		//test3 - try to create forum
		testNumber++;
		System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - varify that threre are not forums :");
		response = serv_handler.createForum(null, Configuration.superAdminName,	 Configuration.FALSE, Configuration.FALSE, "0"	, "0", Configuration.FALSE, Configuration.FALSE, "19.04.14");
		if(response == null){
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		}
		else if (response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " succeed - created forum with no name was failed as supposed.");
		}
		else if (response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - created forum with no name.");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - undefinded response.");
		}

		testNumber++;
		// test 4 -  create correct forum
		System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - create correct forum :");
		String forumname = "my_forum";
		response = serv_handler.createForum(forumname, Configuration.superAdminName,	 Configuration.FALSE, Configuration.FALSE, "0"	, "0", Configuration.FALSE, Configuration.FALSE, "19.04.14");

		if(response == null){
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		}
		else if (response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " succeed - created forum " + forumname + ".");
		}
		else if (response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - did not created forum " + forumname + ".");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - undefinded response.");
		}
		testNumber++;
		
		// test 5 -  verify forum was created
		System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - create correct forum :");
		response = serv_handler.DisplayForums();
		if(response == null){
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		}
		else if (response.contains(forumname)){
			System.out.println("test "+ testNumber.toString() + " succeed - forum " + forumname + " is shoewn.");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - forum " + forumname + " isnt shoewn.");
		}
		
		// test 6 -  create correct forum
		System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - create correct forum :");
		String forumname2 = "my_forum2";
		response = serv_handler.createForum(forumname2, Configuration.superAdminName,	 Configuration.FALSE, Configuration.FALSE, "0"	, "0", Configuration.FALSE, Configuration.FALSE,  "19.04.14");
		if(response == null){
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		}
		else if (response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " succeed - created forum " + forumname + ".");
		}
		else if (response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - did not created forum " + forumname + ".");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - undefinded response.");
		}
		testNumber++;
		
		// test 7 -  verify forum was created
		System.out.println("ForumCreatetionTest test "+ testNumber.toString() + " - create correct forum :");
		response = serv_handler.deleteForum(forumname2);
		if(response == null){
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		}
		else if (response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " succeed - deleted forum " + forumname2 + ".");
		}
		else if (response.startsWith(Configuration.FAIL)){
			System.out.println("test "+ testNumber.toString() + " failed - did not deleted forum " + forumname2 + ".");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " failed - undefinded response.");
		}

	}
}
