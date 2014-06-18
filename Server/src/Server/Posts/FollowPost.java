package Server.Posts;
import Server.Users.*;
import Server.Forum.*;

public class FollowPost extends AbstractPost{
	
	
	public FollowPost(String id, String userName, String title, String content) {
		super(id,  userName, title, content);
	}

	public void notifyMember(String string) {
		// TODO Auto-generated method stub
		
	}

}
