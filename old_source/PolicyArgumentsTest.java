package acceptanceTests;

import org.junit.Test;

//UC-3:
public class PolicyArgumentsTest extends ForumTests {

	@Test
    public void testSetPolicyArgs(){
		String admin = "Tal";
		String forumName = "Cats";
		this.bridge.register(admin, "1234", forumName);
		this.bridge.setAsAdmin(admin, forumName);
		assertTrue(this.bridge.isForumExist(forumName));
		assertTrue(this.bridge.isAdmin(admin));
		assertTrue(this.bridge.isLoggedIn(admin));
		assertTrue(this.bridge.isPolicyArgsValid("A-Z", "0-9", 5, 10, 100));
		boolean ans = this.bridge.setMenegmentPolicy(admin, forumName, "A-Z", "0-9", 5, 10, 100);
		assertTrue(ans);
	}
	
}
