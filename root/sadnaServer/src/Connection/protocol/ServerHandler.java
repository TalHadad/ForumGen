package Connection.protocol;
import java.util.Vector;

import Configuration.*;
import Server.Forum.*;
import Server.Users.*;
import Server.Posts.*;


public class ServerHandler implements ServerHandlerInterface{
	
	private ForumGen forumSystem;
	
	public ServerHandler(){
		this.forumSystem= new ForumGen();
	}

	@Override
	public String initializeSystem (String userName, String password) {
		if(userName.compareTo(this.forumSystem.getSuperAdminName())==0 &&
				password.compareTo(this.forumSystem.getSuperAdminPassword())==0)
			 return Configuration.SUCCESS ;
		 return Configuration.FAIL+" error unauthorized user";
	}
	@Override
	
	public String isInitialize() {
		return this.forumSystem.getIsInit();
	}

	@Override
	public String validateSuperAdmin(String userName, String password) {
		System.out.println("username:"+userName+ "pass: "+password);
		if((userName.compareTo(Configuration.superAdminName)==0) &&
				(password.compareTo(Configuration.superAdminPassword)==0))
			return Configuration.SUCCESS+" 0";
		else
		return Configuration.FAIL+" error : user not superAdmin";
	}

	@Override
	public String DisplayForums() {
		return this.forumSystem.displayForums();
	}

	@Override
	public String getSubForums(String currentForum) {
		Vector<SubForum> subforums=this.forumSystem.getForum(currentForum).getSubForums();
		Vector<String> res = new Vector<String>();
		for(SubForum sf:subforums){
			res.add(sf.getSubject());
		}
		return res;
	}

	@Override
	public String registerToForum(String currentForum, String userName,
			String password, String Email, String remainderQues,
			String remainderAns) {
		QuestionAnswerPair questionAnswerPair=new QuestionAnswerPair(remainderQues, remainderAns);
		return this.forumSystem.getForum(currentForum).regiserToForum(userName, password, Email, questionAnswerPair);
	}

	@Override
	public String createSubForum(String ForumName, String subForumName,
			String subject, String moderatorUserName, String userName) {
		SubForum newSubforum= new SubForum(this.forumSystem.getForum(ForumName), subForumName, subject);
		boolean flag=newSubforum.addModerator(userName, moderatorUserName);
		if(!flag)
			return Configuration.FAIL+" error : requested moderator is not quelafied";
		this.forumSystem.getForum(ForumName).addSubForum(newSubforum);
		return Configuration.SUCCESS+" subforum created successfuly";
		
	}

	@Override
	public String logout(String currentForum, String userName) {
		return this.forumSystem.getForum(currentForum).logout(userName);
	}

	@Override
	public String login(String currentForum, String userName, String password) {
		return this.forumSystem.getForum(currentForum).login(userName,password);
	}

	@Override
	public String deleteSubForum(String forumName, String subForumName) {
		return this.forumSystem.getForum(forumName).deleteSubforum(subForumName);
	}

	@Override
	public String getThreads(String currentForum, String currentSubForum) {
		return this.forumSystem.getForum(currentForum).showSubForum(currentSubForum);
	}


	@Override
	public String fileComplaint(String currentForum, String currentSubForum,
			String moderatorUsername, String complaint, String userName,
			String password) {
		return this.forumSystem.getForum(currentForum).postModeratorComplaint(userName, moderatorUsername, currentSubForum, complaint);
	}

	@Override
	public String postThreadResponse(String responseContent,
			String currentThreadTitle, String currentForum,
			String currentSubForum, String userName,
			String title) {
		Member mem= this.forumSystem.getForum(currentForum).getMember(userName);
		return this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).postFollowPost(currentThreadTitle, mem, currentThreadTitle, responseContent);
	}

	@Override
	public String getThreadResponses(String currentForum,
			String currentSubForum, String currentThreadTitle) {
		return this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).getThread(currentThreadTitle).getFollowPostsStringList();
	}


	@Override
	public String enterNewProperty(String forumName, String property,
			String newValue, String userName, String password) {
		//TODO
		return"";
	}


	@Override
	public String getAllAdminsInForum(String forumName) {
		return this.forumSystem.getForum(forumName).getAdmins();
	}

	@Override
	public String getAllModerators(String forumName, String subForumName) {
		return this.forumSystem.getForum(forumName).getSubForumByName(subForumName).getModeratorsStringList();
	}

	@Override
	public String isMember(String userName,String forumName) {
		Member mem = this.forumSystem.getForum(forumName).getMember(userName);
		return (mem!=null);
	}

	@Override
	public String isAdmin(String userName,String forumName) {
		return this.forumSystem.getForum(forumName).getAdmins().contains(userName);
	}

	@Override
	public String isModerator(String userName, String forumName, String SubforumName) {
		return this.forumSystem.getForum(forumName).getSubForumByName(SubforumName).getModeratorsStringList().contains(userName);	}

	@Override
	public String addAdminToForum(String forumName, String adminName) {
		this.forumSystem.getForum(forumName).addAdmin(adminName);
		if(isAdmin(adminName, forumName))
			return Configuration.SUCCESS+" admin "+adminName+" was successfuly added";
		return Configuration.FAIL+" error : could not add admin";
	}

	@Override
	public String removeAdminFromForum(String forumName, String adminName) {
		return this.forumSystem.getForum(forumName).deleteAdmin(adminName);
	}

	@Override
	public String getPostContent(String ID, String subForumName,String forumName) {
		return this.forumSystem.getForum(forumName).getSubForumByName(currentSubForum).getThread(currentThreadTitle).getContent();
		
	}
	
	public String getOpenningPostContent(String ID, String ID , String subForumName,String forumName) {
		return this.forumSystem.getForum(forumName).getSubForumByName(subForumName).getThread(currentThreadTitle).getContent();
	}

	@Override
	public String publishNewThread(String forumName, String subForumName,
			String threadTitle, String threadContent, String userName) {
		Member mem= this.forumSystem.getForum(currentForum).getMember(userName);
		OpeningPost newPost =this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).postOpeningPost(mem, threadTitle, threadContent);
		if(newPost!=null)
			return Configuration.SUCCESS+" new post published successfuly";
		return Configuration.FAIL+" error : could not publish post";
	}

	@Override
	public String deleteThread(String ID, String subForum, String Forum) {
		Member mem= this.forumSystem.getForum(Forum).getMember(userName);
		boolean flag= this.forumSystem.getForum(Forum).getSubForumByName(subForum).deleteThread(mem, threadName);
		if(flag)
			return Configuration.SUCCESS+" thread dleted succssfuly";
		return Configuration.FAIL+" error : could not delete thread";
	}

	@Override
	public String deleteThreadResponse(String postID, String responseID,
			String forumName, String subForumName) {
		Member mem= this.forumSystem.getForum(forumName).getMember(userName);
		return this.forumSystem.getForum(forumName).getSubForumByName(subForumName).deletePost(mem, responseNumber);
		// TODO mixup between client imp and server imp; need to make adjustments;
	}

	@Override
	public String deleteForum(String forumName) {
		//TODO
		return "";
	}
	
	@Override
	public String getSubForumsForModerator(String forumName,
			String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validateByEmail(String userName, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addFriend(String forumName, String userName,
			String friendUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeFriend(String userName, String forumName,
			String friendUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String responseToFreindRequest(String userName,
			String userNameResponser, String response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String banMember(String userName, String forumName,
			String memberToBanName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String editResponse(String userName, String forum, String subForum,
			String openningPostID, String responseID, String newText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String upgradeToModerator(String userName, String forum,
			String subForum, String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String banModerator(String userName, String forum, String subForum,
			String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeModerator(String userName, String forum,
			String subForum, String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String replaceAdmin(String userName, String forum,
			String newAdminUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String replaceModerator(String userName, String forum,
			String newModeratorName, String oldModerator) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String numOfPostsInSubForumReport(String userName, String forum,
			String subForum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postsOfMemberReport(String userName, String forum,
			String memberName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String listOfModeratorsReport(String userName, String forumName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String setPolicy(String forumName, String userName,
			String hasEmailPolicy, String extendedDeletionPolicy,
			String policyForUpdatingRank, String minPostForModerator,
			String minSeniorityMonths,
			String onlyApointAdministratorCanRemoveModerators,
			String canRemoveSingleModerators, String expirationDateForPassword,
			String interactiveNotifyingPolicyS) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String numOfForumsReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String membersOfForum(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateMemberType(String userName, String forumName,
			String type, String memberToUpdate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createType(String userName, String forumName, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void notifyMember(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyFreindRequest(String requesterUserName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getThreadContent(String treadID, String subForumName,
			String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getResponseContent(String treadID, String responseID,
			String subForumName, String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String editTread(String userName, String forum, String subForum,
			String treadID, String newText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createForum(String forumName, String userName, String policy) {
		if(this.forumSystem.createForum(forumName, new Policy(), new Security()))
			return Configuration.SUCCESS+" created a new forum successfull";
		return Configuration.FAIL+" error acurred during forum creation";
	}
	

	
}






















































