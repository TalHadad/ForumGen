package acceptanceTests;

import java.util.Vector;

import org.junit.Test;

//UC-10:
public class OpeningPostTest extends ForumTests {

	protected void setUp() throws Exception {
        super.setUp();
        String user = "Tal";
		String pass = "1234";
		String forum = "Cats";
		String subForumName = "SiameseCats";
		
		this.bridge.register(user, pass, forum);
		this.bridge.login(user, pass, forum);
		this.bridge.setAsAdmin(user, forum);
		this.bridge.createSubForum(user, forum, subForumName);
	}
	
	@Test
    public void testAddOpeningPost(){
		String user = "Tal";
		String forum = "Cats";
		String subForumName = "SiameseCats";
		Vector<String> allPosts = this.bridge.getMyPosts(user, forum);
		int sizePosts = allPosts.size();
		Vector<String> allTopics = this.bridge.getTopics(forum);
		int sizeTopics = allTopics.size();
		assertTrue(this.bridge.isSubForumExist(forum,subForumName));
		assertTrue(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isLoggedIn(user));
		this.bridge.addOpeningPost(user, forum, subForumName, "bla", "blabla");
		assertTrue(this.bridge.isPostValidByPolicy(forum, "bla", "blabla"));
		allPosts = this.bridge.getMyPosts(user, forum);
		assertEquals(allPosts.size(), sizePosts+1);
		allTopics = this.bridge.getTopics(forum);
		assertEquals(allTopics.size(), sizeTopics+1);
	}
	
	@Test
    public void testBadPermission(){
		String user = "Sivan";
		String pass = "1234";
		String forum = "Cats";
		String subForumName = "SiameseCats";
		Vector<String> allPosts = this.bridge.getMyPosts(user, forum);
		int size = allPosts.size();
		//not a member:
		assertTrue(this.bridge.isSubForumExist(forum,subForumName));
		assertFalse(this.bridge.isMemberExist(user));
		this.bridge.addOpeningPost(user, forum, subForumName, "bla", "blabla");
		assertTrue(this.bridge.isPostValidByPolicy(forum, "bla", "blabla"));
		allPosts = this.bridge.getMyPosts(user, forum);
		assertEquals(allPosts.size(), size);
		//member not logged in:
		this.bridge.register(user, pass, forum);
		this.bridge.logout(user);
		assertTrue(this.bridge.isSubForumExist(forum,subForumName));
		assertTrue(this.bridge.isMemberExist(user));
		assertFalse(this.bridge.isLoggedIn(user));
		this.bridge.addOpeningPost(user, forum, subForumName, "bla", "blabla");
		assertTrue(this.bridge.isPostValidByPolicy(forum, "bla", "blabla"));
		allPosts = this.bridge.getMyPosts(user, forum);
		assertEquals(allPosts.size(), size);
	}
	
	@Test
    public void testEmptyMassage(){
		String user = "Vali";
		String forum = "Cats";
		String subForumName = "SiameseCats";
		Vector<String> allPosts = this.bridge.getMyPosts(user, forum);
		int size = allPosts.size();
		assertTrue(this.bridge.isSubForumExist(forum,subForumName));
		assertTrue(this.bridge.isMemberExist(user));
		assertTrue(this.bridge.isLoggedIn(user));
		//empty title:
		this.bridge.addOpeningPost(user, forum, subForumName, "", "blabla");
		assertFalse(this.bridge.isPostValidByPolicy(forum, "", "blabla"));
		allPosts = this.bridge.getMyPosts(user, forum);
		assertEquals(allPosts.size(), size);
		//empty content:
		this.bridge.addOpeningPost(user, forum, subForumName, "bla", "");
		assertFalse(this.bridge.isPostValidByPolicy(forum, "bla", ""));
		allPosts = this.bridge.getMyPosts(user, forum);
		assertEquals(allPosts.size(), size);
		
	}
}
