import java.util.Vector;


public class OpeningPost extends AbstractPost{
	
	private Vector<FollowPost> followPosts;
	
	public OpeningPost(Member member, String title, String content) {
		super(member,title,content);
		this.followPosts=new Vector<FollowPost>();
	}
	//11
	public Vector<String> getFollowPostsStringList() {
		Vector<String> ans = new Vector<String>();
		for (FollowPost followPost:followPosts)
			ans.add(followPost.getTitle());
		return ans;
	}
}
