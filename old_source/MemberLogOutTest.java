package acceptanceTests;

import java.util.Vector;

import org.junit.Test;

//UC-7:
public class MemberLogOutTest extends ForumTests {

	@Test
    public void testLogOut(){
		String user="Tal";
		Vector<String> loggedInMembers;
		loggedInMembers = this.bridge.getAllLoggedIn();
		int size = loggedInMembers.size();
		//user register - he is loggedin:
		this.bridge.register(user, "1234", "Cats");
		assertTrue(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isLoggedIn(user));
		assertEquals(loggedInMembers.size(), size+1);
		//user logout:
		String id = this.bridge.logout(user);
		assertNotNull(id); // got id
		loggedInMembers = this.bridge.getAllLoggedIn();
		assertEquals(loggedInMembers.size(), size);
		assertFalse(this.bridge.isLoggedIn(user));   

	}
	
	@Test 
    public void testUsertIsLoggedOut(){
		String user="Sivan";
		String pass = "1234";
		String forum="Cats";
		Vector<String> loggedIn = this.bridge.getAllLoggedIn();
		int size = loggedIn.size();
		//user register - he is loggedin:
		this.bridge.register(user, pass, forum);
		assertTrue(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isLoggedIn(user));
		assertTrue(this.bridge.isForumExist(forum));
		assertEquals(loggedIn.size(), size+1);
		//first logout:
		String id = this.bridge.logout(user);
		assertNotNull(id); // got id for log out 
		loggedIn = this.bridge.getAllLoggedIn();
		assertEquals(loggedIn.size(), size);
		assertFalse(this.bridge.isLoggedIn(user));
		//second logout:
		id = this.bridge.logout(user);
		assertNull(id); // got null id for log out 
		loggedIn = this.bridge.getAllLoggedIn();
		assertEquals(loggedIn.size(), size);
		assertFalse(this.bridge.isLoggedIn(user));
	}
}
