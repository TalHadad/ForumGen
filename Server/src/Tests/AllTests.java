package Tests;


import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;
import junit.framework.TestSuite;


public  class AllTests {
	
	public static Test suite(){
		TestSuite suite= new TestSuite();
		suite.addTest(new JUnit4TestAdapter(InitializationTest.class));
		suite.addTest(new JUnit4TestAdapter(CreateForumTest.class));
		suite.addTest(new JUnit4TestAdapter(CreateSubforumTest.class));
		suite.addTest(new JUnit4TestAdapter(LoginLogoutTest.class));
		suite.addTest(new JUnit4TestAdapter(ApointBandModeratorTest.class));
		suite.addTest(new JUnit4TestAdapter(EditPolicyTest.class));
		suite.addTest(new JUnit4TestAdapter(PublishNewThreadTest.class));
		suite.addTest(new JUnit4TestAdapter(PublishResponseTest.class));
		suite.addTest(new JUnit4TestAdapter(EditMsgTest.class));
		suite.addTest(new JUnit4TestAdapter(FileModeratorComplaint.class));
		suite.addTest(new JUnit4TestAdapter(RegisterToForumTest.class));
		suite.addTest(new JUnit4TestAdapter(DeleteForumTest.class));
		suite.addTest(new JUnit4TestAdapter(DeleteSubforumTest.class));
		suite.addTest(new JUnit4TestAdapter(DeletePostTest.class));
	
        return suite;
	}
	
	public static  void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}
	
	

}
