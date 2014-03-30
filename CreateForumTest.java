package acceptanceTests;

import java.util.Vector;

import org.junit.Test;

//UC-2:
public class CreateForumTest extends ForumTests {

	@Test
    public void testCreateForum(){  //add isLoggedIn?
		String admin = "Yael";
		String forumName = "Dogs";
		Vector<String> forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
		assertTrue(this.bridge.isSuperAdmin(admin));
		//check if policy is set:
		this.bridge.createForum(admin, forumName, "a-z", "a-z", 4, 20, 200);
		assertTrue(this.bridge.isPolicyArgsValid("a-z", "a-z", 4, 20, 200));
		forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 4);
		assertTrue(this.bridge.isForumExist(forumName));
		assertTrue(this.bridge.isMemberInForum(admin, forumName));
		
	}
	 
	//test for bad policy args:
	@Test
    public void testBadPolicyArgs(){
		String admin = "Sivan";
		String forumName = "Dogs";
		Vector<String> forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
		assertTrue(this.bridge.isSuperAdmin(admin));
		//empty name:
		assertFalse(this.bridge.isPolicyArgsValid("", "a-z", 4, 20, 200));
		this.bridge.createForum(admin, forumName, "", "a-z", 4, 20, 200);
		forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
		//empty password:
		assertFalse(this.bridge.isPolicyArgsValid("a-z", "", 4, 20, 200));
		this.bridge.createForum(admin, forumName, "a-z", "", 4, 20, 200);
		forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
		//password too short:
		assertFalse(this.bridge.isPolicyArgsValid("a-z", "a-z", 0, 20, 200));
		this.bridge.createForum(admin, forumName, "a-z", "a-z", 0, 20, 200);
		forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
		//title too short:
		assertFalse(this.bridge.isPolicyArgsValid("a-z", "a-z", 4, 0, 200));
		this.bridge.createForum(admin, forumName, "a-z", "a-z", 4, 0, 200);
		forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
		//content too short:
		assertFalse(this.bridge.isPolicyArgsValid("a-z", "a-z", 4, 20, 0));
		this.bridge.createForum(admin, forumName, "a-z", "a-z", 4, 20, 0);
		forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
		
	}
	
	//test for not super admin:
	@Test
    public void testPermission(){
		String admin = "Vali";
		String forumName = "Dogs";
		Vector<String> forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
		assertTrue(this.bridge.isPolicyArgsValid("a-z", "a-z", 4, 20, 200));
		assertFalse(this.bridge.isSuperAdmin(admin));
		this.bridge.createForum(admin, forumName, "a-z", "a-z", 4, 20, 200);
		forums = this.bridge.getAllForums();
		assertEquals(forums.size(), 3);
		assertFalse(this.bridge.isForumExist(forumName));
	}
}
