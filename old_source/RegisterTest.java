package acceptanceTests;

import java.util.Vector;

//import java.util.Vector;
import org.junit.Test;

//UC-5:
public class RegisterTest extends ForumTests {
	
	@Test
    public void testRegister(){
		String user="Tal";
		String pass="1234";
		String forum="Cats";
		Vector<String> members;
		members = this.bridge.getAllMembers();
		int size = members.size();
		assertFalse(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isForumExist(forum));
		String id = this.bridge.register(user, pass, forum);
		assertNotNull(id); // got id 
		members = this.bridge.getAllMembers();
		assertEquals(members.size(), size+1);
		assertTrue(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isLoggedIn(user));    ///////?
	}
	
	@Test
    public void testUserNameExistInMembers(){
		String user="Sivan";
		String pass="1234";
		String forum="Cats";
		Vector<String> members = this.bridge.getAllMembers();
		int size = members.size();
		assertFalse(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isForumExist(forum));
		String id = this.bridge.register(user, pass, forum);
		assertNotNull(id); // got id 
		members = this.bridge.getAllMembers();
		assertEquals(members.size(), size+1);
		assertTrue(this.bridge.isMemberExist(user));
		id = this.bridge.register(user, pass, forum);
		assertNull(id); // got null id
		members = this.bridge.getAllMembers();
		assertEquals(members.size(), size+1);
	}
	
}
