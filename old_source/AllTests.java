package acceptanceTests;

import java.util.Vector;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {
	// git test
	private static Vector<TestSuite> suiteVec;
	private static String testName;
	private static String suiteName;

	public static void main(String[] args) {
		//version 1.0:
		junit.textui.TestRunner.run(suite());
		//initVec();
		// Runs an entire TestSuite using text UI
	//	for(int i=0; i<suiteVec.size(); i++)
		//	junit.textui.TestRunner.run(suiteVec.elementAt(i));
	}

	public static void addTest(String testName, String suiteName){
		setSuiteName(suiteName);
		setTestName(testName);
		suiteVec.add((TestSuite)suite());
	}
	
	public static Test suite(){
		TestSuite suite= new TestSuite("SystemTests");
		suite.addTest(new TestSuite(InitForumTest.class)); //UC-1
		suite.addTest(new TestSuite(CreateForumTest.class));  //UC-2
		suite.addTest(new TestSuite(PolicyArgumentsTest.class));  //UC-3
		suite.addTest(new TestSuite(GuestLogInTest.class));  //UC-4
		suite.addTest(new TestSuite(RegisterTest.class));  //UC-5
        suite.addTest(new TestSuite(MemberLogInTest.class));  //UC-6
        suite.addTest(new TestSuite(MemberLogOutTest.class));  //UC-7
        suite.addTest(new TestSuite(CreateSubForumTest.class));  //UC-8
        suite.addTest(new TestSuite(WatchSubForumsTest.class));  //UC-9
        suite.addTest(new TestSuite(OpeningPostTest.class));  //UC-10
        suite.addTest(new TestSuite(FollowingPostTest.class));  //UC-11
        suite.addTest(new TestSuite(FileComplaintTest.class));  //UC-13
        
        return suite;
	}
	
/*	public static void removeTest(String testName){
		TestSuite test;
		for(int i=0; i<suite.countTestCases(); i++){
			if(testName == suite.getName())
				
		}
	}*/
	
	private static void initVec(){
		setSuiteVec(new Vector<TestSuite>());
		testName="";
		suiteName="";
	
		TestSuite connectingSuite = new TestSuite("ConnectingTests");
        connectingSuite.addTest(new TestSuite(InitForumTest.class)); //UC-1
        connectingSuite.addTest(new TestSuite(RegisterTest.class));  //UC-5
        connectingSuite.addTest(new TestSuite(GuestLogInTest.class));  //UC-4
        connectingSuite.addTest(new TestSuite(MemberLogInTest.class));  //UC-6
        connectingSuite.addTest(new TestSuite(MemberLogOutTest.class));  //UC-7
        suiteVec.add(connectingSuite);
        

        TestSuite postingSuite = new TestSuite("PostingTests");
        postingSuite.addTest(new TestSuite(InitForumTest.class)); //UC-1
        postingSuite.addTest(new TestSuite(RegisterTest.class));  //UC-5
        postingSuite.addTest(new TestSuite(GuestLogInTest.class));  //UC-4
        postingSuite.addTest(new TestSuite(MemberLogInTest.class));  //UC-6
        postingSuite.addTest(new TestSuite(OpeningPostTest.class));  //UC-10
        postingSuite.addTest(new TestSuite(FollowingPostTest.class));  //UC-11
        postingSuite.addTest(new TestSuite(MemberLogOutTest.class));  //UC-7
        suiteVec.add(postingSuite);
	
        TestSuite ForumSuite= new TestSuite("ForumTests");
        ForumSuite.addTest(new TestSuite(CreateForumTest.class));  //UC-2
        ForumSuite.addTest(new TestSuite(PolicyArgumentsTest.class));  //UC-3
        ForumSuite.addTest(new TestSuite(CreateSubForumTest.class));  //UC-8
        ForumSuite.addTest(new TestSuite(WatchSubForumsTest.class));  //UC-9
        suiteVec.add(ForumSuite);
	
	}
	
/*	public static Test suite(){
		int index = foundSuite(getSuiteName());
		if(index != -1){
			testName = suiteVec.elementAt(index).getName();
		}
		TestSuite suite= new TestSuite(testName);
		testName += ".class";
        suite.addTest(new TestSuite(testName));
        return suite;
    }*/

	public static Vector<TestSuite> getSuiteVec() {
		return suiteVec;
	}

	public static void setSuiteVec(Vector<TestSuite> suiteVec) {
		AllTests.suiteVec = suiteVec;
	}
	
	private static int foundSuite(String name){
		for(int i=0; i<suiteVec.size(); i++)
			if(name.equals(suiteVec.elementAt(i).getName()))
				return i;
		return -1;
	}
	
	private static String getSuiteName(){
		return suiteName;
	}

	private static void setSuiteName(String name){
		suiteName = name;
	}
	
	private static void setTestName(String name){
		testName = name;
	}
	
}
