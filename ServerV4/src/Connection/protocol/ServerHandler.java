package Connection.protocol;
import java.util.Vector;

import Configuration.Configuration;
import Server.Forum.*;
import Server.Posts.FollowPost;
import Server.Posts.OpeningPost;
import Server.Users.*;


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
	public int numOfForumsReport() {
		return forumSystem.getNumOfForums();
	}

	// 2*
	@Override
	// Checks if Super admin and create a ForumGen Object and save it as a field
	public boolean initializeSystem (String userName, String password) {
		if(userName.compareTo(Configuration.superAdminName)==0 &&
				password.compareTo(Configuration.superAdminPassword)==0){
			System.out.println("success");
			this.forumSystem = new ForumGen();
			return true;
		}
		return false;
	}

	// 3*
	@Override
	public boolean isInitialize() {
		if (this.forumSystem != null)
			return true;
		return false;
	}

	// 4*
	@Override
	public boolean validateSuperAdmin(String userName, String password) {
		if((userName.compareTo(Configuration.superAdminName)==0) &&
				(password.compareTo(Configuration.superAdminPassword)==0))
			return true;
		return false;
	}

	// 5*
	@Override
	public boolean loginSuper(String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	// 6*
	@Override
	public boolean createForum(String forumName, String userName, String hasEmailPolicy, String extendedDeletionPolicy,String minPostForModerator, String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators, String canRemoveSingleModerators, String expirationDateForPassword){
		boolean hasEmailPolicy1 = false;
		if (hasEmailPolicy.compareTo(Configuration.TRUE)==0)
			hasEmailPolicy1 = true;
		boolean extendedDeletionPolicy1 = false;
		if (extendedDeletionPolicy.compareTo(Configuration.TRUE)==0)
			extendedDeletionPolicy1 = true;
		boolean minPostForModerator1 = false;
		if (minPostForModerator.compareTo(Configuration.TRUE)==0)
			minPostForModerator1 = true;
		boolean minSeniorityMonths1 = false;
		if (minSeniorityMonths.compareTo(Configuration.TRUE)==0)
			minSeniorityMonths1 = true;
		boolean onlyApointAdministratorCanRemoveModerators1 = false;
		if (onlyApointAdministratorCanRemoveModerators.compareTo(Configuration.TRUE)==0)
			onlyApointAdministratorCanRemoveModerators1 = true;
		boolean canRemoveSingleModerators1 = false;
		if (canRemoveSingleModerators.compareTo(Configuration.TRUE)==0)
			canRemoveSingleModerators1 = true;
		boolean expirationDateForPassword1 = false;
		if (expirationDateForPassword.compareTo(Configuration.TRUE)==0)
			expirationDateForPassword1 = true;

		Policy policy = new Policy(hasEmailPolicy1, extendedDeletionPolicy1 , minPostForModerator1, minSeniorityMonths1, 
									onlyApointAdministratorCanRemoveModerators1, canRemoveSingleModerators1, expirationDateForPassword1);
		Forum forum = this.forumSystem.createForum(forumName,policy);
		if (forum != null){
			Member admin = new Member(Configuration.superAdminName, forum);
			admin.setStatus(Configuration.CONNECTED);
			forum.addMember(admin);
			return true;
		}
		return false;
	}

	// 7*
	@Override
	public boolean deleteForum(String forumName) {
		if(this.forumSystem.deleteForum(forumName))
			return true;
		return false;
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// FORUM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 1
	@Override
	public Vector<String> DisplayForums() {
		return this.forumSystem.displayForums();
	}
	
	// 2
	@Override
	public String registerToForum(String currentForum, String userName,String password, String Email, String remainderQues, String remainderAns) {
		QuestionAnswerPair questionAnswerPair=new QuestionAnswerPair(remainderQues, remainderAns);
		Forum forum = this.forumSystem.getForum(currentForum);
		if(forum == null) return  Configuration.FAIL+Configuration.DELIMITER1+"Forum does not exist.";
		Member member = forum.regiserToForum(userName, password, Email, questionAnswerPair);
		if (member == null)
			return Configuration.FAIL+Configuration.DELIMITER1+"The user name is taken.";
		if (member.isPending())
			return Configuration.PENDING_STR+Configuration.DELIMITER1+"Please check your EMail and enter the code.";
		return Configuration.SUCCESS+Configuration.DELIMITER1+member.getName()+" Regiser seccessfuly.";
	}

	// 3
	@Override
	public String login(String currentForum, String userName, String password) {
		Forum forum = this.forumSystem.getForum(currentForum);
		Member member = forum.login(userName, password);
		if (member == null)
			return Configuration.FAIL+Configuration.DELIMITER1+"The login faild.\nPlease try again..";
		if (member.isPending())
			return Configuration.PENDING_STR+Configuration.DELIMITER1+"Please check your EMail and enter the code.";
		return Configuration.SUCCESS+Configuration.DELIMITER1+member.getName()+" login seccessfuly.";
	}

	// 4
	@Override
	public boolean logout(String currentForum, String userName) {
		Forum forum = this.forumSystem.getForum(currentForum);
		return forum.logout(userName);
	}

	// 5
	@Override
	public boolean addAdminToForum(String forumName, String adminName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.addAdmin(adminName);
	}

    // 6
	@Override
	public boolean removeAdminFromForum(String forumName, String adminName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.removeAdmin(adminName);
	}

	// 7
	@Override
	public boolean validateByEmail(String userName, String forumName, String code) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.validateEmailCode(userName,code);
	}

	// 8
	@Override
	public boolean banMember(String userName, String forumName,String memberToBanName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.banMember(memberToBanName);
	}

	// 9
	@Override
	public boolean replaceAdmin(String userName, String forum,String newAdminUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		return  forum1.replaceAdmin(userName,newAdminUserName);
	}

	// 10
	@Override
	public Vector<String> postsOfMemberReport(String userName, String forum, String memberName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		return forum1.getPostsOfMember(memberName);
	}

	// 12
	@Override
	public boolean setPolicy(String forumName, String userName,
			String hasEmailPolicy, String extendedDeletionPolicy, String minPostForModerator,
			String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators,
			String canRemoveSingleModerators, String expirationDateForPassword, String interactiveNotifyingPolicys) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.setPolicy(hasEmailPolicy, extendedDeletionPolicy, 
						 minPostForModerator, minSeniorityMonths, onlyApointAdministratorCanRemoveModerators,
						 canRemoveSingleModerators,  expirationDateForPassword,
						 interactiveNotifyingPolicys);
	}

	// 13
	@Override
	public Vector<String> membersOfForum(String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.getMembersNames();
	}

	// 15
	@Override
	public boolean updateMemberType(String userName, String forumName, String type, String memberToUpdate) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(memberToUpdate);
		return member.setType(type);
	}
	
	// 16
	@Override
	public boolean createType(String userName, String forumName, String type) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.addType(type);
	}

	// 17
	@Override
	public boolean isMember(String userName,String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.isMember(userName);
	}

    // 18
	@Override
	public boolean isAdmin(String userName,String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.isAdmin(userName);
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SUBFORUM //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 19
	@Override
	public Vector<String> getSubForums(String currentForum) {
		Forum forum = this.forumSystem.getForum(currentForum);
		return forum.getSubForums();
	}

	// 20
	@Override
	public Vector<String> getThreads(String currentForum, String currentSubForum) {
		Forum forum = this.forumSystem.getForum(currentForum);
		if(forum == null) return null;
		SubForum subForum = forum.getSubForum(currentSubForum);
		if(subForum == null) return null;
		Vector<String> threads = subForum.getThreads();
		if(threads == null) return null;
		return threads;
	}

	// 21
	@Override
	public Vector<String> getSubForumsForModerator(String forumName,
			String moderatorUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.getSubForumsForModerator(moderatorUserName);
	}

	// 22
	@Override
	public boolean upgradeToModerator(String userName, String forum,
			String subForum, String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForum(subForum);
		return subForum1.upgradeToModerator(moderatorUserName);
	}

	// 23
	@Override
	public boolean banModerator(String userName, String forum, String subForum,
			String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		return forum1.getSubForum(subForum).banModerator(moderatorUserName);
	}

	// 24
	@Override
	public boolean removeModerator(String userName, String forum,
			String subForum, String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		return forum1.getSubForum(subForum).removeModerator(moderatorUserName);
	}

	// 25
	@Override
	public boolean replaceModerator(String userName, String forum, String subForum,
			String newModeratorName, String oldModerator) {
		Forum forum1 = this.forumSystem.getForum(forum);
		return forum1.getSubForum(subForum).replaceModerator(userName, newModeratorName, oldModerator);
	}

	// 26
	@Override
	public int numOfPostsInSubForumReport(String userName, String forum,
			String subForum) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subFor = forum1.getSubForumByName(subForum);
		return subFor.getAllPosts(subForum);
	}

	// 27
	@Override
	public Vector<String> listOfModeratorsReport(String userName, String forumName) {
		Forum forum  = this.forumSystem.getForum(forumName);
		return moderators = forum.getModerators();
	}

	// 28
	@Override
	public boolean createSubForum(String ForumName, String subForumName,
			String subject, String moderatorUserName, String userName) {
		Forum forum  = this.forumSystem.getForum(ForumName);
		SubForum newSubforum= new SubForum(forum, subForumName, subject);
		boolean flag = newSubforum.addModerator(userName, moderatorUserName);
		
		if(!flag)
			return false; 
		forum.addSubForum(newSubforum);
		return true; 
	}

	// 29
	@Override
	public boolean deleteSubForum(String forumName, String subForumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.deleteSubforum(subForumName);
	}

	// 30
	@Override
	public boolean isModerator(String userName, String forumName, String SubforumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(SubforumName);
		boolean ans = subForum.getMederators().contains(userName);
		

	}

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// THREAD //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 31
	@Override
	public String getThreadContent(String treadID, String subForumName,
			String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(treadID);
		return thread.getContent();
	}

	// 32
	@Override
	public Vector<String> getThreadResponses(String currentForum,
			String currentSubForum, String currentThreadTitle) {
		Forum forum = this.forumSystem.getForum(currentForum);
		SubForum subForum = forum.getSubForumByName(currentSubForum);
		OpeningPost thread = subForum.getThread(currentThreadTitle);
		return thread.getResponses();
	}

	// 33
	@Override
	public boolean publishNewThread(String forumName, String subForumName,
			String threadTitle, String threadContent, String userName) {
		
		Forum forum = this.forumSystem.getForum(forumName);
		if(forum == null) return Configuration.FAIL;
		SubForum subForum = forum.getSubForumByName(subForumName);
		if(subForum == null) return Configuration.FAIL;
		boolean ans = subForum.addOpeningPosts(userName, threadTitle, threadContent);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 34
	@Override
	public boolean deleteThread(String ID, String subForum, String Forum) {
		Forum forum = this.forumSystem.getForum(Forum);
		SubForum subForum1 = forum.getSubForum(subForum);
		boolean ans = subForum1.deleteThread(ID);
		
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 35
	@Override
	public boolean editTread(String userName, String forum, String subForum,
			String treadID, String newText) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForumByName(subForum);
		OpeningPost thread = subForum1.getThread(treadID);
		if (userName.compareTo(thread.getMember)==0){
			thread.setContent(newText);
			return true;
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// RESPONSE //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 36
	@Override
	public String getResponseContent(String treadID, String responseID,
			String subForumName, String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(treadID);
		FollowPost resp = thread.getFollowPost(responseID);
		return resp.getContent();
	}

	// 37
	@Override
	public boolean postThreadResponse(String responseTitle,
			String responseContent, String forumName,
			String subForumName, String userName, String openningThreadID){
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(openningThreadID);
		FollowPost followPost = new FollowPost(userName,responseTitle,responseContent);
		return thread.addFollowPost(followPost);
	}

	// 38
	@Override
	public boolean deleteThreadResponse(String postID, String responseID,
			String forumName, String subForumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(postID);
		return thread.deleteResponse(responseID);
	}

	// 39
	@Override
	public boolean editResponse(String userName, String forum, String subForum,String openningPostID, String responseID, String newText) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForumByName(subForum);
		OpeningPost thread = subForum1.getThread(openningPostID);
		FollowPost response = thread.getFollowPost(responseID);
		if (userName.compareTo(response.getMember)==0){
			response.setContent(newText);
			return true;
		}
		return false;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// MEMBERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 40
	@Override
	public boolean fileComplaint(String currentForum, String currentSubForum,
			String moderatorUsername, String complaint, String userName, String password) {
		Forum forum1 = this.forumSystem.getForum(currentForum);
		SubForum subForum1 = forum1.getSubForumByName(currentSubForum);
		return subForum1.postModeratorComplaint(userName, moderatorUsername, currentSubForum, complaint);
	}

	// 41
	@Override
	public boolean addFriend(String forumName, String userName, String friendUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(userName);
		return member.addFriend(friendUserName);
	}
		
	// 42
	@Override
	public boolean removeFriend(String userName, String forumName, String friendUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(userName);
		return member.removeFriend(friendUserName);
	}

	// 43
	@Override
	public boolean responseToFreindRequest(String forumName, String userName, String friendUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(userName);
		return member.addFriend(friendUserName);
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
	// 46
	@Override
	public Vector<String> getAllAdminsInForum(String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> admins = forum.getAdmins();
		String ans = "";
		for (String admin: admins){
			ans += admin+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}
	// 47
	@Override
	public Vector<String> getAllModerators(String forumName, String subForumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subforum = forum.getSubForum(subForumName);
		Vector<String> moderators = subforum.getModerators();
		String ans = "";
		for (String moderator: moderators){
			ans += moderator+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

}






















































