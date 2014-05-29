package Forum;
import java.util.Collection;
import java.util.Vector;

import Users.*;
import Posts.*;

public class SubForum {

		private String name;
		private String subject;
		private Vector<OpeningPost> openingPosts;
		private Vector<ModeratorInfo> moderators;
		private Forum forum;
		
		public SubForum(Forum forum, String name,String subject) {
			this.name=name;
			this.subject=subject;
			this.openingPosts = new Vector<OpeningPost>();
			this.moderators= new Vector<ModeratorInfo>();
			this.forum = forum;
		}
		
		public Vector<ModeratorInfo> getMederators() {
			return moderators;
		}

		public boolean addModerator(String administrator,String moderatorName) {
			Member m=this.forum.getMember(moderatorName);
			if (this.forum.getPolicy().canApointedToModerator(getNumOfPosts(moderatorName), m.getSeniorityMonths())){
				ModeratorInfo moderatorInfo =new ModeratorInfo(administrator,moderatorName);
				this.moderators.add(moderatorInfo);
				return true;
			}
			return false;
		}
		
		public void removeModerator(String requesterName,String moderatorName) {
			ModeratorInfo pair= getModeratorInfo(moderatorName);
			String apointedAdmin =pair.getAdministratorName();
			if (this.forum.getPolicy().canRemoveModerator(getMembersRank(requesterName), requesterName, apointedAdmin,isSingleModerator())){
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
				if(post.getMember().getName().compareTo(memberName)==0){
					numOfPosts++;
				}
				for(FollowPost respons:post.getFollowPosts()){
					if(respons.getMember().getName().compareTo(memberName)==0){
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
		public OpeningPost postOpeningPost(Member writer,String title,String content){
			OpeningPost openingPost = addOpeningPost( writer, title, content);
			this.forum.notifyAllMembers("respons to "+title+", has been posted by "+writer.getName());
			this.forum.NotifyFriends(writer,"Your friend "+writer.getName()+" has posted a post that titled as "+title+" in subforum "+this.name);
			return openingPost;
		}
		//uc10
		private OpeningPost addOpeningPost(Member member, String title, String content) {
			OpeningPost openingPost=new OpeningPost(member,title,content);
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

		public boolean addOpeningPosts(Member member, String title, String content) {
			return this.openingPosts.add(new OpeningPost(member,title,content));
		}

		public boolean deleteThread(Member member, String threadName) {
			for( OpeningPost op: this.openingPosts){
				if(op.getTitle().equals(threadName)&&op.getMember()==member){
					this.openingPosts.remove(op);
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
		
		
		public String postFollowPost(String postTitle,Member member, String title, String content){
			if (content.compareTo("")!=0){
				OpeningPost openingPost = enterPost(postTitle);
				openingPost.addFollowPost(member,title,content);
				this.forum.notifyAllMembers("respons to "+postTitle+", has been posted by "+member.getName());
				openingPost.getMember().notifyMember("Member "+member+" has write a follow post to your post that as titled: "+postTitle+" in subforum "+this.name);
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
			return getNumOfPosts(memberName)>0;
		}

			
		public Vector<String> getMemberPosts(String member){
			//
				 Vector<String> res= new Vector<String>();
				for (OpeningPost openingPost:this.openingPosts){
					if (member.compareTo(openingPost.getMember().getName())==0)
						res.add(openingPost.getTitle());
					for(FollowPost followPost:openingPost.getFollowPosts()){
						if (member.compareTo(followPost.getMember().getName())==0)
							res.add(followPost.getTitle());
					}
				}
				return res;
		}
		
		public String deletePost(Member member, String title){
			for (OpeningPost openingPost:this.openingPosts){
				if (openingPost.getTitle().compareTo(title)==0){
					if (member.getName().compareTo(openingPost.getMember().getName())==0){
						this.openingPosts.remove(openingPost);
						notifyWatchingUsers();
						return "Opening post was deleted Seccessfuly.";
					}
					else
						return "did not delete Opening post, you are not the writer";
				}
				for(FollowPost followPost:openingPost.getFollowPosts()){
					if (followPost.getTitle().compareTo(title)==0){
						if (member.getName().compareTo(openingPost.getMember().getName())==0){
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
		
		

}

		
