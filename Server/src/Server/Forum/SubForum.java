package Server.Forum;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import Server.Users.*;
import Configuration.*;
import DataBase.MembersHandler;
import DataBase.ModeratorsHandler;
import DataBase.PostsHandler;
import Server.Posts.*;

public class SubForum {

	private String name;
	private String subject;
	private Vector<OpeningPost> openingPosts;
	private Vector<ModeratorInfo> moderators;
	private Forum forum;
	private int counter;
	private ModeratorsHandler moderatorsHandler; 
	private PostsHandler postsHandler;

	public SubForum(Forum forum, String name,String subject) {
		this.name=name;
		this.subject=subject;
		this.openingPosts = new Vector<OpeningPost>();
		this.moderators= new Vector<ModeratorInfo>();
		ModeratorInfo superAdmin = new ModeratorInfo(Configuration.superAdminName, Configuration.superAdminName);
		this.moderators.add(superAdmin);
		this.forum = forum;
		this.counter = 0;
		this.moderatorsHandler = new ModeratorsHandler();
		this.postsHandler = new PostsHandler();
	}

	public Vector<ModeratorInfo> getMederators() {
		return moderators;
	}
	public boolean isModerator(String userName){
		for (ModeratorInfo moderatorInfo:this.moderators)
			if (moderatorInfo.getModeratorName().compareTo(userName)==0){
				return true;
			}
		return false;
	}
	
	public boolean addModerator(String administrator, String moderatorName) {
		
		Member m = this.forum.getMember(moderatorName);

		if (m == null){
			System.out.println("Can't upgrade " + moderatorName + " to moderator. There is no such member");
			return false;
		}

		//			if (this.forum.getPolicy().canApointedToModerator(getNumOfPosts(moderatorName), m.getSeniorityMonths())){
		if (m.getName().compareTo(Configuration.superAdminName) != 0){
			ModeratorInfo moderatorInfo = new ModeratorInfo(administrator, moderatorName);
			this.moderators.add(moderatorInfo);
			System.out.println(moderatorName + " was added to moderators list.");
			return true;
		}
	
		System.out.println("Probably you are superAdmin, so you are moderator already!");
		if (administrator.compareTo(moderatorName) == 0){
			System.out.println("Probably you are superAdmin, so you are moderator already!");
			return true;
		}
		
		return false;

	}

	//DB ModeratorsHandler
	public void removeModerator(String requesterName,String moderatorName) {
		ModeratorInfo pair = getModeratorInfo(moderatorName);
		String apointedAdmin = pair.getAdministratorName();
		if (this.forum.getPolicy().canRemoveModerator(getMembersRank(requesterName), requesterName, apointedAdmin,isSingleModerator())){
			this.moderatorsHandler.deleteModerator(this.forum.getName(),this.name);
			this.moderators.remove(pair);
		}
	}

	private boolean isSingleModerator() {
		return this.moderators.size()==1;
	}

	private ModeratorInfo getModeratorInfo(String moderatorName) {
		for(ModeratorInfo mod:this.moderators){
			if(moderatorName.compareTo(mod.getModeratorName())==0)
				return mod;
		}
		return null;
	}

	private int getNumOfPosts(String memberName) {
		int numOfPosts = 0;
		for(OpeningPost post:this.openingPosts){
			if(post.getMember().compareTo(memberName)==0){
				numOfPosts++;
			}
			for(FollowPost respons:post.getFollowPosts()){
				if(respons.getMember().compareTo(memberName)==0){
					numOfPosts++;
				}
			}

		}
		return numOfPosts;
	}

	public String getName(){
		return this.name;
	}
	public String getSubject(){
		return this.subject;
	}

	public Vector<OpeningPost> view(){
		return this.openingPosts;
	}
	//uc10
	public OpeningPost postOpeningPost(String writer,String title,String content){
		OpeningPost openingPost = addOpeningPost( writer, title, content);
		this.forum.notifyAllMembers("respons to "+title+", has been posted by "+writer);
		//this.forum.NotifyFriends(writer,"Your friend "+writer+" has posted a post that titled as "+title+" in subforum "+this.name);
		return openingPost;
	}
	//uc10
	private OpeningPost addOpeningPost(String member, String title, String content) {
		this.counter++;
		OpeningPost openingPost=new OpeningPost(this.forum.getForumName()+this.name+this.counter,member,title,content);
		openingPosts.add(openingPost);
		return openingPost;
	}


	//11
	public Vector<String> getOpeningPostsStringList() {
		Vector<String> ans = new Vector<String>();
		for (OpeningPost openingPost:openingPosts)
			ans.add(openingPost.getTitle());
		return ans;
	}
	//11
	public Vector<String> showPost(String postName){
		for (OpeningPost openingPost:openingPosts){
			if (openingPost.getTitle().compareTo(postName)==0)
				return openingPost.getFollowPostsStringList();
		}
		return null;
	}

	//DB PostsHandler
	public boolean addOpeningPosts(String member, String title, String content) {
		this.counter++;
		OpeningPost openingPost = new OpeningPost(this.forum.getForumName()+this.name+counter, member,  title,  content);
		this.postsHandler.addOpeningPost(forum.getName(),this.name,member,this.forum.getForumName()+this.name+counter,title,  content);
		return this.openingPosts.add(openingPost);
	}

	//DB PostsHandler
	public boolean deleteThread(String member, String threadName,String id) {
		for( OpeningPost op: this.openingPosts){
			if(op.getTitle().equals(threadName)&&op.getMember() == member){
				this.openingPosts.remove(op);
				this.postsHandler.deleteOpeningPost(id);
				return true;
			}
		}
		return false;
	}

	public OpeningPost getThread(String currentThreadTitle) {
		for( OpeningPost op: this.openingPosts){
			if(op.getTitle().equals(currentThreadTitle)){
				return op;
			}
		}
		return null;
	}
	//11
	public OpeningPost enterPost(String postTitle){
		for (OpeningPost openingPost:this.openingPosts){
			if (openingPost.getTitle() == postTitle)
				return openingPost;
		}
		return null;
	}		
	//11

	//DB PostsHandler
	public String postFollowPost(String postTitle,String member, String title, String content,String id,String openingThreaID){
		if (content.compareTo("")!=0){
			OpeningPost openingPost = enterPost(postTitle);
			openingPost.addFollowPost(member,title,content);
			this.forum.notifyAllMembers("respons to "+postTitle+", has been posted by "+member.toString());
			//				openingPost.getMember().notifyMember("Member "+member+" has write a follow post to your post that as titled: "+postTitle+" in subforum "+this.name);
			this.postsHandler.addFollowPost(this.forum.getName(),this.name,member,id,openingThreaID,title,content);
			return "your response was published successfuly";
		}
		return "error : The content is empty";
	}

	public Vector<String> getModeratorsStringList(){
		Vector<String> res= new Vector<String>();
		for(ModeratorInfo modinfo:this.moderators){
			res.add(modinfo.getModeratorName());
		}
		return res;
	}

	public boolean havePosted(String memberName) {
		return getNumOfPosts(memberName) > 0;
	}


	public Vector<String> getMemberPosts(String member){
		//
		Vector<String> res= new Vector<String>();
		for (OpeningPost openingPost:this.openingPosts){
			if (member.compareTo(openingPost.getMember())==0)
				res.add(openingPost.getTitle());
			for(FollowPost followPost:openingPost.getFollowPosts()){
				if (member.compareTo(followPost.getMember())==0)
					res.add(followPost.getTitle());
			}
		}
		return res;
	}

	
	public String deletePost(Member member, String title){
		for (OpeningPost openingPost:this.openingPosts){
			if (openingPost.getTitle().compareTo(title)==0){
				if (member.getName().compareTo(openingPost.getMember())==0){
					this.openingPosts.remove(openingPost);
					notifyWatchingUsers();
					return "Opening post was deleted Seccessfuly.";
				}
				else
					return "did not delete Opening post, you are not the writer";
			}
			for(FollowPost followPost:openingPost.getFollowPosts()){
				if (followPost.getTitle().compareTo(title)==0){
					if (member.getName().compareTo(openingPost.getMember())==0){
						this.openingPosts.remove(openingPost);
						notifyWatchingUsers();
						return "follow post was deleted Seccessfuly.";
					}
					else
						return "did not delete Opening post, you are not the writer";
				}
			}
		}
		return "Post not found.";
	}

	private void notifyWatchingUsers() {
		// TODO The system removes the post for all watching users (by the interactive rule)

	}

	private int getMembersRank(String user){
		for(String name:this.forum.getAdmins()){
			if(user.compareTo(name)==0)
				return Configuration.ADMINISTRATOR;
		}
		for(ModeratorInfo mem:this.moderators){
			if(mem.getModeratorName().compareTo(user)==0)
				return Configuration.MODERATOR;
		}
		if(this.forum.getMember(user)!=null)
			return Configuration.MEMBER;
		return Configuration.GEUST;
	}

	public int getTotalNumOfPosts(){
		int num=0;
		for(OpeningPost post:this.openingPosts){
			num +=post.getFollowPosts().size()+1;
		}
		return num;
	}


	public Vector<ModeratorsReport> getModeratorsReport() {
		Vector<ModeratorsReport> res= new Vector<ModeratorsReport>();
		for(ModeratorInfo modInfo:this.moderators){
			res.add(new ModeratorsReport(modInfo,this.subject,getMemberPosts(modInfo.getModeratorName())));
		}
		return res;
	}

	public Vector<String> getThreads() {
		Vector<String> threads = new Vector<String>();
		Vector<OpeningPost> openning = this.openingPosts;
		for (int i = 0; i < this.openingPosts.size(); i++){
			threads.add(openning.elementAt(i).getTitle() + Configuration.DELIMITER2 + openning.elementAt(i).getMember());
		}
		return threads;
	}


	public int getAllPosts(String subForum) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean deleteThread(String iD) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean postModeratorComplaint(String userName, String moderatorUsername, String currentSubForum,
			String complaint) {
		// TODO Auto-generated method stub
		return false;
	}

	public Vector<String> getModerators() {
		Vector<String> moderatorsNames = new Vector<String>();
		for (ModeratorInfo moderatorInfo:this.moderators)
			moderatorsNames.add(moderatorInfo.getModeratorName());
		return moderatorsNames;
	}

	public boolean banModerator(String moderatorUserName) {
		for (ModeratorInfo moderatorInfo:this.moderators)
			if (moderatorInfo.getModeratorName().compareTo(moderatorUserName)==0){
				this.moderators.remove(moderatorInfo);
				return true;
			}
		return false;
	}

	public boolean removeModerator(String moderatorUserName) {
		for (ModeratorInfo moderatorInfo:this.moderators)
			if (moderatorInfo.getModeratorName().compareTo(moderatorUserName)==0){
				this.moderators.remove(moderatorInfo);
				return true;
			}
		return false;
	}

	public boolean replaceModerator(String userName, String newModeratorName, String oldModerator) {
		for (ModeratorInfo moderatorInfo:this.moderators)
			if (moderatorInfo.getModeratorName().compareTo(oldModerator)==0){
				this.moderators.remove(moderatorInfo);
				ModeratorInfo mi = new ModeratorInfo(userName, newModeratorName);
				this.moderators.add(mi);
				return true;
			}
		return false;
	}

	public void setModerators(Vector<ModeratorInfo> moderators) {
		this.moderators = moderators;
		
	}
	
	public void setOpeningPosts(Vector<OpeningPost> openingPosts2) {
		// TODO Auto-generated method stub

	}

}


