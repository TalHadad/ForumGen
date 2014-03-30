package acceptanceTests;

import java.util.Vector;

import org.junit.Test;

//UC-4:
public class GuestLogInTest extends ForumTests {

	@Test
    public void testGuestLogin(){
		String forum="Cats";
		Vector<String> guests = this.bridge.getAllGuests();
		int size = guests.size();
		assertTrue(this.bridge.isForumExist(forum));
        this.bridge.loginAsGuest(forum);
        guests = this.bridge.getAllGuests();
		assertEquals(guests.size(),size+1); 

	}
	
}
