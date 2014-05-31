package acceptanceTests;

import java.util.Vector;

import org.junit.Test;

public class FileComplaintTest extends ForumTests {

	@Test
	protected void setUp() throws Exception {
        super.setUp();
        String forum = "Cats";
        String moderator = "Asaf";
        String subForumName = "Black Cats";
        this.bridge.setAsModerator(moderator, forum);
		this.bridge.setAsAdmin(moderator, forum);
		this.bridge.createSubForum(moderator, forum, subForumName);
	}
	
	@Test
    public void testFileComplaint(){
		String name = "Tal";
		String pass = "1234";
		String forum = "Cats";
		String moderator = "Asaf";
		String subForumName = "Black Cats";
		assertTrue(this.bridge.isSubForumExist(forum, subForumName));
		this.bridge.register(name, pass, forum);
		assertTrue(this.bridge.isMemberExist(name));
		assertTrue(this.bridge.isLoggedIn(name));
		this.bridge.addOpeningPost(name, forum, subForumName, "bla", "blabla");
		assertTrue(this.bridge.getMyPosts(name, forum).size() > 0);
		Vector<String> complains = this.bridge.getMyComplaints(name, forum, moderator);
		int size = complains.size();
		this.bridge.fileAComplaint(name, forum, moderator, "blabla");
		complains = this.bridge.getMyComplaints(name, forum, moderator);
		assertEquals(complains.size(), size+1);
	}
	
	@Test
    public void testBadPermission(){
		String name = "Sivan";
		String pass = "1234";
		String forum = "Cats";
		String subForumName = "Black Cats";
		String moderator = "Asaf";
		//not a member:
		assertTrue(this.bridge.isSubForumExist(forum, subForumName));
		assertFalse(this.bridge.isMemberExist(name));
		assertFalse(this.bridge.isLoggedIn(name));
		Vector<String> complains = this.bridge.getMyComplaints(name, forum, moderator);
		int size = complains.size();
		this.bridge.fileAComplaint(name, forum, moderator, "blabla");
		complains = this.bridge.getMyComplaints(name, forum, moderator);
		assertEquals(complains.size(), size);
		//not logged in:
		this.bridge.register(name, pass, forum);
		this.bridge.addOpeningPost(name, forum, subForumName, "bla", "blabla");
		this.bridge.logout(name);
		assertTrue(this.bridge.isMemberExist(name));
		assertFalse(this.bridge.isLoggedIn(name));
		assertTrue(this.bridge.getMyPosts(name, forum).size() > 0);
		complains = this.bridge.getMyComplaints(name, forum, moderator);
		this.bridge.fileAComplaint(name, forum, moderator, "blabla");
		complains = this.bridge.getMyComplaints(name, forum, moderator);
		assertEquals(complains.size(), size);
	}

}
