package TesterTests;

import Configuration.Configuration;
import Connection.protocol.ServerHandlerInterface;

public class InitializationTest{

	public static void startTest() {
		ServerHandlerInterface serv_handler = AllTest.serv_handler;
		if(serv_handler==null){
			System.out.println("InitializationTest failed - server handler was not created");
			return;
		}
		String response = null;
		Integer testNumber = 1;
		//test1 - check if system is initialize before initialization
		System.out.println("Initialization test "+ testNumber.toString() + " - check if system is initialize before initialization:");
		String is_initialize = serv_handler.isInitialize();
		if(is_initialize == null){
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		}
		else if (is_initialize.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " failed - System does not suppose to be initilize befoee init proccess.");
		}
		else{
			System.out.println("test "+ testNumber.toString() + " succeed - check initialzation status before init proccess.");
		}
				
		//test2 - initialize the system properly
		testNumber++;
		System.out.println("Initialization test "+ testNumber.toString() + " - Trying to initialize the system properly:");
		response = serv_handler.initializeSystem(Configuration.superAdminName, Configuration.superAdminPassword);
		boolean isInitilze = false;
		AllTest.cont = false;
		if(response == null) { 
			System.out.println("test "+ testNumber.toString() + " failed - returned null.");
		} 
		else if (response.startsWith(Configuration.SUCCESS)){
			System.out.println("test "+ testNumber.toString() + " succeed - initialization proccess.");
			isInitilze = true;
			AllTest.cont = true;
		}
		else{
			System.out.println("test "+ testNumber.toString() + " falied - initialization proccess failed.");
		}
		
		if(isInitilze){
			//test3 - check initialization after init
			testNumber++;
			System.out.println("Initialization test "+ testNumber.toString() + " - Check initialization statuse after init proccess:");
			is_initialize = serv_handler.isInitialize();
			if(is_initialize == null){
				System.out.println("test "+ testNumber.toString() + " failed - returned null.");
			}
			else if (is_initialize.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " failed - the system status was suppose to be initialize after init proccess.");
			}
			else{
				System.out.println("test "+ testNumber.toString() + " succeed - initialization status after init.");
			}
			
			//test4 - validate correct superAdmin
			testNumber++;
			System.out.println("Initialization test "+ testNumber.toString() + " - Validate correct superAdmin:");
			response = serv_handler.validateSuperAdmin(Configuration.superAdminName, Configuration.superAdminPassword);
			if(response == null){
				System.out.println("test "+ testNumber.toString() + " failed - returned null.");
			}
			else if (response.startsWith(Configuration.SUCCESS)){
				System.out.println("test "+ testNumber.toString() + " succeed - Super Admin validation was done correctly.");
			}
			else{
				System.out.println("test "+ testNumber.toString() + " falied - Super Admin validation failed.");
			}
			//test5 - validate incorrect superAdmin 
			testNumber++;
			System.out.println("Initialization test "+ testNumber.toString() + " - Validate Incorrect superAdmin -wrong username:");

			response = serv_handler.validateSuperAdmin(Configuration.superAdminName+"1", Configuration.superAdminPassword);
			if(response == null){
				System.out.println("test "+ testNumber.toString() + " failed - returned null.");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " succeed - Super Admin validation with wrong userName was failed as suppoesed.");
			}
			else{
				System.out.println("test "+ testNumber.toString() + " falied - Super Admin validation was succeed with wrong password.");
			}
			//test6 - validate incorrect superAdmin 
			testNumber++;
			System.out.println("Initialization test "+ testNumber.toString() + " - Validate Incorrect superAdmin -wrong password:");

			response = serv_handler.validateSuperAdmin(Configuration.superAdminName, Configuration.superAdminPassword + "1");
			if(response == null){
				System.out.println("test "+ testNumber.toString() + " failed - returned null.");
			}
			else if(response.startsWith(Configuration.FAIL)){
				System.out.println("test "+ testNumber.toString() + " succeed - Super Admin validation with wrong userName was failed as suppoesed.");
			}
			else{
				System.out.println("test "+ testNumber.toString() + " falied - Super Admin validation was succeed with wrong password.");
			}
		}
		
		
	}

}
