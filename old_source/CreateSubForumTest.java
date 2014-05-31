
package acceptanceTests;

import java.util.Vector;

import org.junit.Test;

//UC-8:
public class CreateSubForumTest extends ForumTests {

	@Test
    public void testCreateSubForum(){
		String admin = "Vali";
		String forumName = "Software Engineer";
		String subForumName = "Sadna";
		Vector<String> subforums = this.bridge.getAllSubForums(forumName);
		int size = subforums.size();
		assertFalse(this.bridge.isSubForumExist(forumName,subForumName));
		assertTrue(this.bridge.isAdmin(admin));
		this.bridge.createSubForum(admin, forumName, subForumName);
		assertTrue(this.bridge.isMemberInForum(admin, subForumName));
		subforums = this.bridge.getAllSubForums(forumName);
		assertEquals(subforums.size(), size+1);
	}
	
	//test for not super admin:
	@Test
    public void testPermission(){
		String user="Sivan";
		String pass="1234";
		String forumName = "Software Engineer";
		String subForumName = "Sadna";
		Vector<String> subforums = this.bridge.getAllSubForums(forumName);
		assertEquals(subforums.size(), 0);
		String id = this.bridge.register(user, pass, forumName);
		assertNotNull(id);
		assertFalse(this.bridge.isSubForumExist(forumName,subForumName));
		assertFalse(this.bridge.isAdmin(user));
		this.bridge.createSubForum(user, forumName, subForumName);
		subforums = this.bridge.getAllSubForums(forumName);
		assertEquals(subforums.size(), 0);
		assertFalse(this.bridge.isSubForumExist(forumName,subForumName));
	}
	
}
