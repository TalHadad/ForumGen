package acceptanceTests;

import java.util.Vector;

import org.junit.Test;

//UC-6:
public class MemberLogInTest extends ForumTests {

	@Test
    public void testLogin(){
		String user = "Tal";
		String pass = "1234";
		String forum = "Cats";
		//user regiser - he is loggedin
		this.bridge.register(user, pass, forum);
		Vector<String> loggedInMembers;
		loggedInMembers = this.bridge.getAllLoggedIn();
		int size = loggedInMembers.size();
		assertTrue(this.bridge.isForumExist(forum));
		assertTrue(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isLoggedIn(user));
		//user logout:
		this.bridge.logout(user);
		assertFalse(this.bridge.isLoggedIn(user));
		assertEquals(loggedInMembers.size(), size-1);
		//user login:
        this.bridge.login(user, pass, forum);
        loggedInMembers = this.bridge.getAllLoggedIn();
		assertEquals(loggedInMembers.size(), size);
		assertTrue(this.bridge.isLoggedIn(user));  

	}
	
	@Test 
    public void testUserNameExistInLoggedInVec(){
		String user = "Sivan";
		String pass = "1234";
		String forum = "Cats";
		Vector<String> loggedIn = this.bridge.getAllLoggedIn();
		int size = loggedIn.size();
		//first login:
		this.bridge.register(user, pass, forum);
		assertTrue(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isLoggedIn(user));
		assertTrue(this.bridge.isForumExist(forum));
		//second login:
		String id = this.bridge.login(user, pass, forum);
		assertNull(id); // got null id
		loggedIn = this.bridge.getAllLoggedIn();
		assertEquals(loggedIn.size(), size+1);
	}
}
