package Server.Posts;
import java.util.Vector;

import Server.Users.*;
import Server.Forum.*;

public class OpeningPost extends AbstractPost{

	private Vector<FollowPost> followPosts;
	
	public OpeningPost(String member, String title, String content) {
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
	public boolean AddPost(String member,String title,String responseContent) {
		FollowPost followPost = new FollowPost(member, title, responseContent);
		return this.followPosts.add(followPost);
	}
	public boolean deletePost(Member member, String currentThreadTitle) {
		for (FollowPost followPost:followPosts)
			if ((followPost.getTitle().equals(currentThreadTitle)) && (followPost.getMember() == member)){
				this.followPosts.remove(followPost);
				return true;
			}
		return false;
	}
	public boolean addFollowPost(FollowPost followPost) {
		this.followPosts.add(followPost);
		return true;
	}
	public Vector<FollowPost> getFollowPosts() {
		return this.followPosts;
	}
	
	public void addFollowPost(Member member, String title, String content) {
		FollowPost followPost= new FollowPost(member.getName(),title,content);
		this.followPosts.add(followPost);
	}
	
	public String editPost(String member,String content){
		if(this.getMember().getName().compareTo(member)==0){
			this.setContent(content);
			for(FollowPost fp:this.followPosts){
				fp.getMember().notifyMember("post "+this.getTitle()+", by member "+member+" has made changes to content");
			}
			refreshWatchingUsers();
			return "Post was edited seccessfuly";
		}
		return "You are not the writer of this post";
	}
	private void refreshWatchingUsers() {
		// TODO refresh the screen of watching users that they will see the changes
		
	}
	public Vector<String> getResponses() {
		return getFollowPostsStringList();
	}
	public FollowPost getFollowPost(String responseID) {
		for (int i=0; i < this.followPosts.size(); i++)
			if (this.followPosts.elementAt(i).equals(responseID))
				return this.followPosts.elementAt(i);
		return null;
	}
	public boolean deleteResponse(String responseID) {
		for (int i=0; i < this.followPosts.size(); i++)
			if (this.followPosts.elementAt(i).equals(responseID)){
				this.followPosts.remove(i);
				return true;
			}
		return false;
	}

}
