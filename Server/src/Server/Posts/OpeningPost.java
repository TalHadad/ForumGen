package Server.Posts;
import java.util.Vector;

import DataBase.PostsHandler;
import Server.Users.*;
import Server.Forum.*;

public class OpeningPost extends AbstractPost{

	private Vector<FollowPost> followPosts;
	private int counter;
	private PostsHandler postsHandler;

	public OpeningPost(String id,String member, String title, String content) {
		super(id,member,title,content);
		this.followPosts=new Vector<FollowPost>();
		this.counter =0;
		this.postsHandler = new PostsHandler();
	}

	//11
	public Vector<String> getFollowPostsStringList() {
		Vector<String> ans = new Vector<String>();
		for (FollowPost followPost:followPosts)

			ans.add(followPost.getTitle());
		return ans;
	}

	public boolean addFollowPost(String member,String title,String responseContent) {
		this.counter++;
		FollowPost followPost = new FollowPost(this.getID()+this.counter,member, title, responseContent);
		return this.followPosts.add(followPost);
	}


	public boolean deletePost(String member, String currentThreadTitle) {
		for (FollowPost followPost:followPosts)
			if ((followPost.getTitle().equals(currentThreadTitle)) && (followPost.getMember().compareTo(member)== 0)){
				this.followPosts.remove(followPost);
				return true;
			}
		return false;
	}



	public Vector<FollowPost> getFollowPosts() {
		return this.followPosts;
	}

	public String editPost(String member,String content){
		if(this.getMember().compareTo(member)==0){
			this.setContent(content);
			for(FollowPost fp:this.followPosts){
				fp.notifyMember("post "+this.getTitle()+", by member "+member+" has made changes to content");
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
	//DB PostsHandler
	public boolean deleteResponse(String responseID) {
		for (int i=0; i < this.followPosts.size(); i++)
			if (this.followPosts.elementAt(i).equals(responseID)){
				this.followPosts.remove(i);
				this.postsHandler.deleteFollowPost(responseID);
				return true;
			}
		return false;
	}

	public void setFollowPosts(Vector<FollowPost> followPosts2) {
		// TODO Auto-generated method stub
		
	}



}
