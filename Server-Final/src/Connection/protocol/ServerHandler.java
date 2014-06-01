package Connection.protocol;
import java.util.Vector;

import Configuration.Configuration;
import Server.Forum.*;
import Server.Forum.ForumGen;
import Server.Forum.Policy;
import Server.Forum.QuestionAnswerPair;
import Server.Forum.SubForum;
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
	public String numOfForumsReport() {
		String num = forumSystem.getNumOfForums().toString();
		return Configuration.SUCCESS+Configuration.DELIMITER1+"The number of forums is the system is "+num+" ." ;
	}

	// 2*
	@Override
	// Checks if Super admin and create a ForumGen Object and save it as a field
	public String initializeSystem (String userName, String password) {
		System.out.println("username -" + userName);
		System.out.println("password -" + password);
		if(userName.compareTo(Configuration.superAdminName)==0 &&
				password.compareTo(Configuration.superAdminPassword)==0){
			System.out.println("success");
			this.forumSystem = new ForumGen();
			return Configuration.SUCCESS+Configuration.DELIMITER1+"The system is initialized successully." ;
		}
		else
		System.out.println("fail");
		return Configuration.FAIL+Configuration.DELIMITER1+"Unauthorized user.";
	}

	// 3*
	@Override
	public String isInitialize() {
		if (this.forumSystem != null)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"The system is initialized.";
		return Configuration.FAIL+Configuration.DELIMITER1+"The system is not initialized.";
	}

	// 4*
	@Override
	public String validateSuperAdmin(String userName, String password) {
		if((userName.compareTo(Configuration.superAdminName)==0) &&
				(password.compareTo(Configuration.superAdminPassword)==0))
			return Configuration.SUCCESS+Configuration.DELIMITER1+"The user is super administrator.";
		else
		return Configuration.FAIL+Configuration.DELIMITER1+"The user is not super administrator.";
	}

	// 5*
	@Override
	public String loginSuper(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	// 6*
	@Override
	public String createForum(String forumName, String userName, String hasEmailPolicy, String extendedDeletionPolicy,
								String minPostForModerator, String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators, 
								String canRemoveSingleModerators, String expirationDateForPassword){
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
			return Configuration.SUCCESS+Configuration.DELIMITER1+forumName+"forum created a successfull";
		}
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to create "+forumName+" forum";
	}

	// 7*
	@Override
	public String deleteForum(String forumName) {
		if(this.forumSystem.deleteForum(forumName))
			return Configuration.SUCCESS+"Delete the forum successfull";
		return Configuration.FAIL+"Fail to delete the forum";
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
		if (ans.compareTo("")==0)
			return "";
		return ans.substring(0, (ans.length()-Configuration.DELIMITER1.length()));
	}
	
	// 2
	@Override
	public String registerToForum(String currentForum, String userName,String password, 
									String Email, String remainderQues, String remainderAns) {
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
	public String logout(String currentForum, String userName) {
		Forum forum = this.forumSystem.getForum(currentForum);
		boolean seccess = forum.logout(userName);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+" logout seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"The logout faild.\nPlease try again..";
	}

	// 5
	@Override
	public String addAdminToForum(String forumName, String adminName) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean seccess = forum.addAdmin(adminName);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+adminName+" is now administrator.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Failed to add administrator.";
	}

    // 6
	@Override
	public String removeAdminFromForum(String forumName, String adminName) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean seccess = forum.removeAdmin(adminName);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+adminName+" removed from administration seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Failed to remove administrator.";
	}

	// 7
	@Override
	public String validateByEmail(String userName, String forumName, String code) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean seccess = forum.validateEmailCode(userName,code);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+" validated seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Code do not match.";
	}

	// 8
	@Override
	public String banMember(String userName, String forumName,
			String memberToBanName) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean seccess = forum.banMember(memberToBanName);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+" seccessfuly baned.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to ban "+memberToBanName+" .";
	}

	// 9
	@Override
	public String replaceAdmin(String userName, String forum,
			String newAdminUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		boolean seccess = forum1.replaceAdmin(userName,newAdminUserName);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+newAdminUserName+" seccessfuly replaced administration with "+newAdminUserName+" .";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to replaced administration.";
	}

	// 10
	@Override
	public String postsOfMemberReport(String userName, String forum,
			String memberName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		Vector<String> posts = forum1.getPostsOfMember(memberName);
		String ans = "";
		for (String post: posts){
			ans += post+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 12
	@Override
	public String setPolicy(String forumName, String userName,
			String hasEmailPolicy, String extendedDeletionPolicy, String minPostForModerator,
			String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators,
			String canRemoveSingleModerators, String expirationDateForPassword, String interactiveNotifyingPolicys) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean seccess = forum.setPolicy(hasEmailPolicy, extendedDeletionPolicy, 
						 minPostForModerator, minSeniorityMonths, onlyApointAdministratorCanRemoveModerators,
						 canRemoveSingleModerators,  expirationDateForPassword,
						 interactiveNotifyingPolicys);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"Policy has updated seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to updated policy.";
	}

	// 13
	@Override
	public String membersOfForum(String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> membersNames = forum.getMembersNames();
		String ans = "";
		for (String memberName: membersNames)
			ans += memberName+Configuration.DELIMITER1;
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;

	}

	// 15
	@Override
	public String updateMemberType(String userName, String forumName,
			String type, String memberToUpdate) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(memberToUpdate);
		boolean seccess = member.setType(type);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"Member type has updated seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to updated member type.";
	}
	
	// 16
	@Override
	public String createType(String userName, String forumName, String type) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean seccess = forum.addType(type);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+type+"type has added seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to add "+ type+" type.";
	}

	// 17
	@Override
	public String isMember(String userName,String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean seccess = forum.isMember(userName);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+"is a member in this forum.";
		return Configuration.FAIL+Configuration.DELIMITER1+userName+"is not a member in this forum.";
	}
    // 18
	@Override
	public String isAdmin(String userName,String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean seccess = forum.isAdmin(userName);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+"is an administrator in this forum.";
		return Configuration.FAIL+Configuration.DELIMITER1+userName+"is not an administrator in this forum.";
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SUBFORUM //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 19
	@Override
	public String getSubForums(String currentForum) {
		Forum forum = this.forumSystem.getForum(currentForum);
		Vector<String> subForums = forum.getSubForums();
		String ans="";
		for(String subForum:subForums){
			ans += subForum+Configuration.DELIMITER1;
		}
		if (ans.compareTo("")==0)
			return "";
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 20
	@Override
	public String getThreads(String currentForum, String currentSubForum) {
		Forum forum = this.forumSystem.getForum(currentForum);
		if(forum == null) return "";
		SubForum subForum = forum.getSubForum(currentSubForum);
		if(subForum == null) return "";
		Vector<String> threads = subForum.getThreads();
		if(threads == null) return "";
		String ans="";
		for(String thread:threads){
			ans += thread+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 21
	@Override
	public String getSubForumsForModerator(String forumName,
			String moderatorUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> subForums = forum.getSubForumsForModerator(moderatorUserName);
		String ans="";
		for(String subForum :subForums){
			ans += subForum+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 22
	@Override
	public String upgradeToModerator(String userName, String forum,
			String subForum, String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForum(subForum);
		boolean seccess = subForum1.upgradeToModerator(moderatorUserName);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+"is a moderator in this subforum.";
		return Configuration.FAIL+Configuration.DELIMITER1+userName+"Fail to apoint to moderator in this subforum.";
	}

	// 23
	@Override
	public String banModerator(String userName, String forum, String subForum,
			String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		boolean ans = forum1.getSubForum(subForum).banModerator(moderatorUserName);
		
		if (ans == true)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 24
	@Override
	public String removeModerator(String userName, String forum,
			String subForum, String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		boolean ans = forum1.getSubForum(subForum).removeModerator(moderatorUserName);
		
		if (ans == true)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 25
	@Override
	public String replaceModerator(String userName, String forum, String subForum,
			String newModeratorName, String oldModerator) {
		Forum forum1 = this.forumSystem.getForum(forum);
		boolean ans = forum1.getSubForum(subForum).replaceModerator(userName, newModeratorName, oldModerator);
		
		if (ans == true)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 26
	@Override
	public String numOfPostsInSubForumReport(String userName, String forum,
			String subForum) {
		
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subFor = forum1.getSubForumByName(subForum);
		int totalAmountOfPosts = subFor.getAllPosts(subForum);
		return "Total posts: " + totalAmountOfPosts +", Threads: "+ subFor.getTotalNumOfPosts();
	}

	// 27
	@Override
	public String listOfModeratorsReport(String userName, String forumName) {
		Forum forum  = this.forumSystem.getForum(forumName);
		Vector<String> moderators = forum.getModerators();
		String ans="";
		for(String moderator:moderators){
			ans += moderator+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
		
	}

	// 28
	@Override
	public String createSubForum(String ForumName, String subForumName,
			String subject, String moderatorUserName, String userName) {
		
		Forum forum  = this.forumSystem.getForum(ForumName);
		SubForum newSubforum= new SubForum(forum, subForumName, subject);
		boolean flag = newSubforum.addModerator(userName, moderatorUserName);
		
		if(!flag)
			return Configuration.FAIL+" error : requested moderator is not quelafied";
		
		forum.addSubForum(newSubforum);
		
		return Configuration.SUCCESS+" subforum created successfuly";
		
	}

	// 29
	@Override
	public String deleteSubForum(String forumName, String subForumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		boolean ans = forum.deleteSubforum(subForumName);
		
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 30
	@Override
	public String isModerator(String userName, String forumName, String SubforumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(SubforumName);
		boolean ans = subForum.getMederators().contains(userName);
		
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
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
	public String getThreadResponses(String currentForum,
			String currentSubForum, String currentThreadTitle) {
		Forum forum = this.forumSystem.getForum(currentForum);
		SubForum subForum = forum.getSubForumByName(currentSubForum);
		OpeningPost thread = subForum.getThread(currentThreadTitle);
		Vector<String> responses = thread.getResponses();
		String ans = "";
		for (String response:responses)
			ans+=response+Configuration.DELIMITER1;
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 33
	@Override
	public String publishNewThread(String forumName, String subForumName,
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
	public String deleteThread(String ID, String subForum, String Forum) {
		Forum forum = this.forumSystem.getForum(Forum);
		SubForum subForum1 = forum.getSubForum(subForum);
		boolean ans = subForum1.deleteThread(ID);
		
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 35
	@Override
	public String editTread(String userName, String forum, String subForum,
			String treadID, String newText) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForumByName(subForum);
		OpeningPost thread = subForum1.getThread(treadID);
		thread.setContent(newText);
		
		return Configuration.SUCCESS;
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
	public String postThreadResponse(String responseTitle,
			String responseContent, String forumName,
			String subForumName, String userName, String openningThreadID){
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(openningThreadID);
		FollowPost followPost = new FollowPost(userName,responseTitle,responseContent);
		boolean seccess = thread.addFollowPost(followPost);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"Response is added seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Failed to add Response.";
	}

	// 38
	@Override
	public String deleteThreadResponse(String postID, String responseID,
			String forumName, String subForumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(postID);
		boolean ans = thread.deleteResponse(responseID);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
		
	}

	// 39
	@Override
	public String editResponse(String userName, String forum, String subForum,
			String openningPostID, String responseID, String newText) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForumByName(subForum);
		OpeningPost thread = subForum1.getThread(openningPostID);
		FollowPost response = thread.getFollowPost(responseID);
		response.setContent(newText);
		
		return Configuration.SUCCESS;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// MEMBERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 40
	@Override
	public String fileComplaint(String currentForum, String currentSubForum,
			String moderatorUsername, String complaint, String userName, String password) {
		Forum forum1 = this.forumSystem.getForum(currentForum);
		SubForum subForum1 = forum1.getSubForumByName(currentSubForum);
		boolean seccess = subForum1.postModeratorComplaint(userName, moderatorUsername, currentSubForum, complaint);
		if (seccess)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"Response is added seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Failed to add Response.";

	}

	// 41
	@Override
	public String addFriend(String forumName, String userName, String friendUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(userName);
		boolean ans = member.addFriend(friendUserName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
		
	}
		
	// 42
	@Override
	public String removeFriend(String userName, String forumName, String friendUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(userName);
		boolean ans = member.removeFriend(friendUserName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 43
	@Override
	public String responseToFreindRequest(String forumName, String userName, String friendUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(userName);
		boolean ans = member.addFriend(friendUserName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
		
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

	@Override
	public String getAllAdminsInForum(String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> admins = forum.getAdmins();
		String ans = "";
		for (String admin: admins){
			ans += admin+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	@Override
	public String getAllModerators(String forumName, String subForumName) {
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






















































