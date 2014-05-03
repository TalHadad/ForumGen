package acceptanceTests;

import java.util.Vector;

import org.junit.Test;

//UC-9:
public class WatchSubForumsTest extends ForumTests {

	@Test
    public void testgetSubForumList(){
		int i;
		int j;
		int k;
		Vector<String> subforums = this.bridge.getAllSubForums("Software Engineer");
		int size = subforums.size();
		this.bridge.createSubForum("Vali", "Software Engineer", "Sadna");
		subforums = this.bridge.getAllSubForums("Software Engineer");
		assertEquals(subforums.size(), size+1);
	}
}
