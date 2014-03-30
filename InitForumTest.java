package acceptanceTests;

import org.junit.Test;

//UC-1:
public class InitForumTest extends ForumTests {

	@Test
    public void testInitialize(){
		assertFalse(this.bridge.isInitialize());
		this.bridge.initialize("Yael", "1111", "blabla");
		assertTrue(this.bridge.isInitialize());
		assertTrue(this.bridge.isSuperAdmin("Yael"));
		assertNotNull(this.bridge.getSecurityPolicy());
	}
	
	/*@Test
    public void testSuperAdminHasBeenSet(){
		assertTrue(this.bridge.isInitialize());
		assertTrue(this.bridge.isSuperAdmin("Yael"));
	}*/
}
