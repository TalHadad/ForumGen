package Connection.protocol;
import java.util.Vector;

import Configuration.*;
import Server.Forum.*;
import Server.Users.*;
import Server.Posts.*;


public class ServerHandler implements ServerHandlerInterface{
	
	private ForumGen forumSystem;
	
	public ServerHandler(){
		this.forumSystem= null;
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SYSTEM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 1*
	@Override
	public String numOfForumsReport() {
		String num = forumSystem.getNumOfForums().toString(); // TODO Auto-generated method stub
		return Configuration.SUCCESS+" The number of forums is the system is "+num+" ." ;
	}

	// 2*
	@Override
	// Checks if Super admin and create a ForumGen Object and save it as a field
	public String initializeSystem (String userName, String password) {
		if(userName.compareTo(this.forumSystem.getSuperAdminName())==0 &&
				password.compareTo(this.forumSystem.getSuperAdminPassword())==0){
			this.forumSystem = new ForumGen();
			return Configuration.SUCCESS+" The system is initialized successully." ;
		}
		return Configuration.FAIL+" Unauthorized user.";
	}

	// 3*
	@Override
	public String isInitialize() {
		if (this.forumSystem != null)
			return Configuration.SUCCESS+" The system is initialized.";
		return Configuration.FAIL+" The system is not initialized.";
	}

	// 4*
	@Override
	public String validateSuperAdmin(String userName, String password) {
		if((userName.compareTo(Configuration.superAdminName)==0) &&
				(password.compareTo(Configuration.superAdminPassword)==0))
			return Configuration.SUCCESS+" The user is super administrator.";
		else
		return Configuration.FAIL+" The user is not super administrator.";
	}

	// 5*
	@Override
	public String loginSuper(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// FORUM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 1
	@Override
	public String DisplayForums() {
		Vector<String> forums = this.forumSystem.displayForums();
		String ans="";
		for (String forum: forums){
			ans+=forum+Configuration.DELIMITER1;
		}
		//remove the last redundant delim1 
		return ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
	}
	
	// 2
	@Override
	public String registerToForum(String currentForum, String userName,String password, 
									String Email, String remainderQues, String remainderAns) {
		QuestionAnswerPair questionAnswerPair=new QuestionAnswerPair(remainderQues, remainderAns);
		Forum forum = this.forumSystem.getForum(currentForum);
		Member member = forum.regiserToForum(userName, password, Email, questionAnswerPair);
		if (!member)
			return Configuration.FAIL+" The user name is taken.";
		if (member.isPending())
			return Configuration.PENDDING+" Please check your EMail and enter the code.";
		return Configuration.SUCCESS+" "+member.getName()+"Regiser seccessfuly.";
	}

	// 3
	@Override
	public String login(String currentForum, String userName, String password) {
		return this.forumSystem.getForum(currentForum).login(userName,password);
	}

	// 4
	@Override
	public String logout(String currentForum, String userName) {
		return this.forumSystem.getForum(currentForum).logout(userName);
	}

	// 5
	@Override
	public String addAdminToForum(String forumName, String adminName) {
		this.forumSystem.getForum(forumName).addAdmin(adminName);
		if(isAdmin(adminName, forumName))
			return Configuration.SUCCESS+" admin "+adminName+" was successfuly added";
		return Configuration.FAIL+" error : could not add admin";
	}

    // 6
	@Override
	public String removeAdminFromForum(String forumName, String adminName) {
		return this.forumSystem.getForum(forumName).deleteAdmin(adminName);
	}

	// 7
	@Override
	public String validateByEmail(String userName, String code) {
		// TODO Auto-generated method stub
		return null;
	}

	// 8
	@Override
	public String banMember(String userName, String forumName,
			String memberToBanName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 9
	@Override
	public String replaceAdmin(String userName, String forum,
			String newAdminUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 10
	@Override
	public String postsOfMemberReport(String userName, String forum,
			String memberName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 11
	@Override
	public String createForum(String forumName, String userName, String policy) {
		if(this.forumSystem.createForum(forumName, new Policy(), new Security()))
			return Configuration.SUCCESS+" created a new forum successfull";
		return Configuration.FAIL+" error acurred during forum creation";
	}

	// 12
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

	// 13
	@Override
	public String membersOfForum(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 14
	@Override
	public String deleteForum(String forumName) {
		//TODO
		return "";
	}

	// 15
	@Override
	public String updateMemberType(String userName, String forumName,
			String type, String memberToUpdate) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 16
	@Override
	public String createType(String userName, String forumName, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	// 17
	@Override
	public String isMember(String userName,String forumName) {
		Member mem = this.forumSystem.getForum(forumName).getMember(userName);
		return (mem!=null);
	}
    // 18
	@Override
	public String isAdmin(String userName,String forumName) {
		return this.forumSystem.getForum(forumName).getAdmins().contains(userName);
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SUBFORUM //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 19
	@Override
	public String getSubForums(String currentForum) {
		Vector<SubForum> subforums=this.forumSystem.getForum(currentForum).getSubForums();
		Vector<String> res = new Vector<String>();
		for(SubForum sf:subforums){
			res.add(sf.getSubject());
		}
		return res;
	}

	// 20
	@Override
	public String getThreads(String currentForum, String currentSubForum) {
		return this.forumSystem.getForum(currentForum).showSubForum(currentSubForum);
	}

	// 21
	@Override
	public String getSubForumsForModerator(String forumName,
			String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 22
	@Override
	public String upgradeToModerator(String userName, String forum,
			String subForum, String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 23
	@Override
	public String banModerator(String userName, String forum, String subForum,
			String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 24
	@Override
	public String removeModerator(String userName, String forum,
			String subForum, String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 25
	@Override
	public String replaceModerator(String userName, String forum,
			String newModeratorName, String oldModerator) {
		// TODO Auto-generated method stub
		return null;
	}

	// 26
	@Override
	public String numOfPostsInSubForumReport(String userName, String forum,
			String subForum) {
		// TODO Auto-generated method stub
		return null;
	}

	// 27
	@Override
	public String listOfModeratorsReport(String userName, String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 28
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

	// 29
	@Override
	public String deleteSubForum(String forumName, String subForumName) {
		return this.forumSystem.getForum(forumName).deleteSubforum(subForumName);
	}

	// 30
	@Override
	public String isModerator(String userName, String forumName, String SubforumName) {
		return this.forumSystem.getForum(forumName).getSubForumByName(SubforumName).getModeratorsStringList().contains(userName);	
	}

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// THREAD //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 31
	@Override
	public String getThreadContent(String treadID, String subForumName,
			String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 32
	@Override
	public String getThreadResponses(String currentForum,
			String currentSubForum, String currentThreadTitle) {
		return this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).getThread(currentThreadTitle).getFollowPostsStringList();
	}

	// 33
	@Override
	public String publishNewThread(String forumName, String subForumName,
			String threadTitle, String threadContent, String userName) {
		Member mem= this.forumSystem.getForum(currentForum).getMember(userName);
		OpeningPost newPost =this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).postOpeningPost(mem, threadTitle, threadContent);
		if(newPost!=null)
			return Configuration.SUCCESS+" new post published successfuly";
		return Configuration.FAIL+" error : could not publish post";
	}

	// 34
	@Override
	public String deleteThread(String ID, String subForum, String Forum) {
		Member mem= this.forumSystem.getForum(Forum).getMember(userName);
		boolean flag= this.forumSystem.getForum(Forum).getSubForumByName(subForum).deleteThread(mem, threadName);
		if(flag)
			return Configuration.SUCCESS+" thread dleted succssfuly";
		return Configuration.FAIL+" error : could not delete thread";
	}

	// 35
	@Override
	public String editTread(String userName, String forum, String subForum,
			String treadID, String newText) {
		// TODO Auto-generated method stub
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// RESPONSE //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 36
	@Override
	public String getResponseContent(String treadID, String responseID,
			String subForumName, String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 37
	@Override
	public String postThreadResponse(String responseContent,
			String currentThreadTitle, String currentForum,
			String currentSubForum, String userName,
			String title) {
		Member mem= this.forumSystem.getForum(currentForum).getMember(userName);
		return this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).postFollowPost(currentThreadTitle, mem, currentThreadTitle, responseContent);
	}

	// 38
	@Override
	public String deleteThreadResponse(String postID, String responseID,
			String forumName, String subForumName) {
		Member mem= this.forumSystem.getForum(forumName).getMember(userName);
		return this.forumSystem.getForum(forumName).getSubForumByName(subForumName).deletePost(mem, responseNumber);
		// TODO mixup between client imp and server imp; need to make adjustments;
	}

	// 39
	@Override
	public String editResponse(String userName, String forum, String subForum,
			String openningPostID, String responseID, String newText) {
		// TODO Auto-generated method stub
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// MEMBERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 40
	@Override
	public String fileComplaint(String currentForum, String currentSubForum,
			String moderatorUsername, String complaint, String userName,
			String password) {
		return this.forumSystem.getForum(currentForum).postModeratorComplaint(userName, moderatorUsername, currentSubForum, complaint);
	}

	// 41
	@Override
	public String addFriend(String forumName, String userName,
			String friendUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 42
	@Override
	public String removeFriend(String userName, String forumName,
			String friendUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	// 43
	@Override
	public String responseToFreindRequest(String userName,
			String userNameResponser, String response) {
		// TODO Auto-generated method stub
		return null;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// messages from server to client /////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 44
	@Override
	public void notifyMember(String text) {
		// TODO Auto-generated method stub
		
	}
	// 45
	@Override
	public void notifyFreindRequest(String requesterUserName) {
		// TODO Auto-generated method stub
		
	}

}






















































