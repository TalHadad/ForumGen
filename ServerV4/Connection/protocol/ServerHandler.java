package Connection.protocol;
import java.util.Vector;

import javax.swing.JOptionPane;

import Configuration.Configuration;
import Server.Forum.*;
import Server.Posts.AbstractPost;
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
		if(!isInitialize())
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
	public String loginSuper(String userName, String password) {
		// TODO Auto-generated method stub
		return Configuration.SUCCESS;
	}

	// 6*
	@Override
	public boolean createForum(String forumName, String userName, String hasEmailPolicy, String extendedDeletionPolicy,String minPostForModerator, String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators, String canRemoveSingleModerators, String expirationDateForPassword, String interactiveNotifyingPolicys){
		if (userName.compareTo(Configuration.superAdminName)==0)
			return false;
		int minPostForModerator1 = 0;
		int minSeniorityMonths1 = 0;
		int expirationDateForPassword1 = 0;
		String interactiveNotifyingPolicys1 = "";
		boolean ans = false;
		boolean correctArgsflag = true;

		boolean hasEmailPolicy1 = false;
		if (hasEmailPolicy.compareTo(Configuration.TRUE)==0)
			hasEmailPolicy1 = true;
		boolean extendedDeletionPolicy1 = false;
		if (extendedDeletionPolicy.compareTo(Configuration.TRUE)==0)
			extendedDeletionPolicy1 = true;

		try{
			minPostForModerator1 = Integer.parseInt(minPostForModerator);
			minSeniorityMonths1 = Integer.parseInt(minSeniorityMonths);
			expirationDateForPassword1 = Integer.parseInt(expirationDateForPassword);

			interactiveNotifyingPolicys1 = interactiveNotifyingPolicys;
		}
		catch (NumberFormatException nfe) {
			System.out.println("Input must be a number.");
			ans = false;
			correctArgsflag = false;
		}

		boolean onlyApointAdministratorCanRemoveModerators1 = false;
		if (onlyApointAdministratorCanRemoveModerators.compareTo(Configuration.TRUE)==0)
			onlyApointAdministratorCanRemoveModerators1 = true;
		boolean canRemoveSingleModerators1 = false;
		if (canRemoveSingleModerators.compareTo(Configuration.TRUE)==0)
			canRemoveSingleModerators1 = true;


		Policy policy = new Policy(hasEmailPolicy1, extendedDeletionPolicy1 , minPostForModerator1, minSeniorityMonths1, 
				onlyApointAdministratorCanRemoveModerators1, canRemoveSingleModerators1, expirationDateForPassword1, interactiveNotifyingPolicys1);
		Forum forum = this.forumSystem.createForum(forumName,policy);
		if (forum != null && correctArgsflag){
			//			Member admin = new Member(Configuration.superAdminName, forum);
			//			admin.setStatus(Configuration.CONNECTED);
			//			forum.addMember(admin);
			ans = true;
		}

		return ans;
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
	public String registerToForum(String userName, String currentForum, String password, String Email, String remainderQues, String remainderAns) {
		QuestionAnswerPair questionAnswerPair = new QuestionAnswerPair(remainderQues, remainderAns);
		Forum forum = this.forumSystem.getForum(currentForum);
		if(forum == null) 
			return  Configuration.FAIL+Configuration.DELIMITER1+" Forum does not exist.";
		Member member = forum.regiserToForum(userName, password, Email, questionAnswerPair);
		if (member == null)
			return Configuration.FAIL+Configuration.DELIMITER1+" The user name is taken.";
		if (member.isPending())
			return Configuration.PENDING_STR+Configuration.DELIMITER1+" Please check your EMail and enter the code.";
		
		return Configuration.SUCCESS+Configuration.DELIMITER1+member.getName()+" Regiser successfully. status : " + member.getStatus();
	}

	// 3
	@SuppressWarnings("null")
	@Override
	public String login(String currentForum, String userName, String password) {
		Forum forum = this.forumSystem.getForum(currentForum);
		Member member = null;

		if (forum == null){
			member = forum.loginSuper(userName, password);
			if (member == null){
				System.out.println("You are not super administrator.");
				return Configuration.FAIL+Configuration.DELIMITER1+" The login failed.\nPlease try again..";
			}
			return Configuration.SUCCESS+Configuration.DELIMITER1+member.getName()+" login successfully.";
		}

		member = forum.login(userName, password);
		if (member == null)
			return Configuration.FAIL+Configuration.DELIMITER1+" The login failed.\nPlease try again..";
		if (member.isPending())
			return Configuration.PENDING_STR+Configuration.DELIMITER1+" Please check your EMail and enter the code.";
		return Configuration.SUCCESS+ " " + Configuration.DELIMITER1 + " " + member.getName()+" login successfully.";
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

	//14
	public Vector<String> getTypes(String forumName){
		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> types = new Vector<String>();
		types = forum.getStatuses();
		return types;
	}
	// 15
	@Override
	public boolean updateMemberType(String userName, String forumName, String type, String memberToUpdate) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(memberToUpdate);
		if (forum.getStatuses().contains(type))
			return member.setType(type);
		return false;
	}

	// 16
	public boolean createType(String userName, String forumName, String type) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.addType(type);
	}

	// 16.5
	public boolean deleteType(String forumName, String type) {
		Forum forum = this.forumSystem.getForum(forumName);
		for (int i = 0; i < forum.getStatuses().size(); i++)
			if (forum.getStatuses().elementAt(i).compareTo(type) == 0){
				forum.getStatuses().remove(i);
				return true;
			}
		return false;
	}

	// 17
	public boolean isMember(String userName,String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.isMember(userName);
	}

	// 18
	public boolean isAdmin(String userName,String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		if (forum==null)
			return false;
		return forum.isAdmin(userName);
	}

	// 18.5 1/5
	public Vector<String> getAllAdminsInForum(String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> admins = forum.getAdmins();
		//		String ans = "";
		//		for (String admin: admins){
		//			ans += admin+Configuration.DELIMITER1;
		//		}
		//		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		//		return ans;
		return admins;
	}

	// 18.5 2/5
	public Vector<String> getMyPosts(String userName, String forumName) {

		Vector<String> ans = new Vector<String>();
		Forum forum = this.forumSystem.getForum(forumName);

		Member mem = forum.getMember(userName);
		Vector<AbstractPost> posts = mem.getPosts();
		for(int i=0; i<posts.size(); i++)
			ans.add(posts.elementAt(i).getTitle());
		//		
		//		
		//		
		//		Vector<SubForum> subs = forum.getSubForumList();
		//		
		//		Vector<OpeningPost> topics;
		//		
		//		if (subs != null)
		//			for (int i = 0; i < subs.size(); i++){
		//				topics = subs.elementAt(i).view();
		//				for (int j = 0; j < topics.size(); j++){
		//					if (topics.elementAt(j).getMember().compareTo(userName) == 0)
		//						ans.add(topics.elementAt(j).getTitle());
		//				}
		//			}
		return ans;
	}

	// 18.5 3/5
	public Vector<String> getMyFreinds(String userName, String forumName) {

		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> friends = new Vector<String>();
		Vector<Member> forumMembers = forum.getMembers();
		for (int i = 0; i < forumMembers.size(); i++)
			if (forumMembers.elementAt(i).getName().compareTo(userName) == 0)
				friends = forumMembers.elementAt(i).getFriends();

		return friends;
	}

	// 18.5 4/5
	public Vector<String> getMyRequests(String userName, String forumName){
		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> friends = new Vector<String>();
		Vector<Member> forumMembers = forum.getMembers();
		for (int i = 0; i < forumMembers.size(); i++)
			if (forumMembers.elementAt(i).getName().compareTo(userName) == 0)
				friends = forumMembers.elementAt(i).getFriendRequests();

		return friends;
	}

	// 18.5 5/5
	public Vector<String> getJoinedMemberInForums() {

		Vector<Member> membersInAllForums = new Vector<Member>();
		Vector<String> ans = new Vector<String>();

		for (int i = 0; i < forumSystem.getNumOfForums(); i++)
			membersInAllForums.addAll(forumSystem.getForums().elementAt(i).getMembers());

		for (int i = 0; i < membersInAllForums.size(); i++)
			for (int j = i+1; j < membersInAllForums.size(); j++)
				if (membersInAllForums.elementAt(i).getName().compareTo(membersInAllForums.elementAt(j).getName()) ==0)
					ans.add(membersInAllForums.elementAt(i).getName());

		for (int i = 0; i < ans.size(); i++)
			for (int j = i+1; j < ans.size(); j++)
				if (ans.elementAt(i).compareTo(ans.elementAt(j)) == 0)
					ans.remove(j);

		return ans;

	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SUBFORUM //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 19
	public Vector<String> getSubForums(String currentForum) {
		Forum forum = this.forumSystem.getForum(currentForum);
		if (currentForum.compareTo("") == 0)
			return null;
		return forum.getSubForums();
	}

	// 20
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
	public Vector<String> getSubForumsForModerator(String forumName,
			String moderatorUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.getSubForumsForModerator(moderatorUserName);
	}

	// 22
	public boolean upgradeToModerator(String userName, String forum, String subForum, String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForum(subForum);
		return forum1.upgradeToModerator(subForum1.getName(), userName, moderatorUserName);
	}

	// 23
	public boolean banModerator(String userName, String forum, String subForum, String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		return forum1.getSubForum(subForum).banModerator(moderatorUserName);
	}

	// 24
	public boolean removeModerator(String userName, String forum, String subForum, String moderatorUserName) {
		Forum forum1 = this.forumSystem.getForum(forum);
		return forum1.getSubForum(subForum).removeModerator(moderatorUserName);
	}

	// 25
	public boolean replaceModerator(String userName, String forum, String subForum,
			String newModeratorName, String oldModerator) {
		Forum forum1 = this.forumSystem.getForum(forum);
		return forum1.getSubForum(subForum).replaceModerator(userName, newModeratorName, oldModerator);
	}

	// 26
	public int numOfPostsInSubForumReport(String userName, String forum,
			String subForum) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subFor = forum1.getSubForumByName(subForum);
		return subFor.getAllPosts(subForum);
	}

	// 27
	public Vector<String> listOfModeratorsReport(String userName, String forumName) {
		Forum forum  = this.forumSystem.getForum(forumName);
		return forum.getModerators();
	}

	// 28
	public boolean createSubForum(String ForumName, String subForumName,
			String subject, String moderatorUserName, String userName) {
		Forum forum  = this.forumSystem.getForum(ForumName);
		if (forum.getSubForums().contains(subForumName))
			return false;
		SubForum newSubforum= new SubForum(forum, subForumName, subject);
		boolean flag = newSubforum.addModerator(userName, moderatorUserName);

		if(!flag)
			return false; 

		forum.addSubForum(newSubforum);
		System.out.println("ADDED "+newSubforum.getName());
		return true; 
	}

	// 29
	public boolean deleteSubForum(String forumName, String subForumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		return forum.deleteSubforum(subForumName);
	}

	// 30
	public boolean isModerator(String userName, String forumName, String SubforumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(SubforumName);
		boolean ans = false;

		if (subForum == null)
			System.out.println("Go home you are drunk exception!! no sub forum.");
		else
			ans = subForum.getMederators().contains(userName);

		return ans;
	}

	// 30.5
	// 18.5 1/6
	public Vector<String> getAllModerators(String forumName, String subForumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Vector<String> moderators = new Vector<String>();
		try{
			if (forum == null)
				System.out.println("NULLLLLLLL - forum");
			SubForum subforum = forum.getSubForum(subForumName);
			if (subforum == null)
				System.out.println("NULLLLLLLL - subforum");
			moderators = subforum.getModerators();
		} catch(Exception e){
			System.out.println("Someone got NULL!!");
		}
		return moderators;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// THREAD //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 31
	public String getThreadContent(String treadID, String subForumName, String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(treadID);
		return thread.getContent();
	}

	// 32
	public Vector<String> getThreadResponses(String currentForum,
			String currentSubForum, String currentThreadTitle) {
		Forum forum = this.forumSystem.getForum(currentForum);
		SubForum subForum = forum.getSubForumByName(currentSubForum);
		OpeningPost thread = subForum.getThread(currentThreadTitle);
		return thread.getResponses();
	}

	// 33
	public boolean publishNewThread(String forumName, String subForumName,
			String threadTitle, String threadContent, String userName) {

		AbstractPost post;
		Forum forum = this.forumSystem.getForum(forumName);
		if(forum == null) return false;
		SubForum subForum = forum.getSubForumByName(subForumName);
		if(subForum == null) return false;
		boolean ans = subForum.addOpeningPosts(userName, threadTitle, threadContent);
		post = subForum.getThread(threadTitle);
		Member mem = forum.getMember(userName);
		mem.getPosts().add(post);
		if (ans)
			return true;
		return false;
	}

	// 34
	public boolean deleteThread(String ID, String subForum, String Forum) {
		Forum forum = this.forumSystem.getForum(Forum);
		SubForum subForum1 = forum.getSubForum(subForum);
		boolean ans = subForum1.deleteThread(ID);

		if (ans)
			return true;
		return false;
	}

	// 35
	public boolean editTread(String userName, String forum, String subForum, String treadID, String newText) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForumByName(subForum);
		OpeningPost thread = subForum1.getThread(treadID);
		if (userName.compareTo(thread.getMember())==0){
			thread.setContent(newText);
			return true;
		}
		return false;
	}

	// 35.5
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean isItMyPost(String userName, String forumName, String subForum, String postID){
		Forum forum1 = this.forumSystem.getForum(forumName);
		SubForum subForum1 = forum1.getSubForumByName(subForum);
		OpeningPost thread = subForum1.getThread(postID);
		if (userName.compareTo(thread.getMember())==0)
			return true;
		return false;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// RESPONSE //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 36
	public String getResponseContent(String treadID, String responseID,
			String subForumName, String forumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(treadID);
		FollowPost resp = thread.getFollowPost(responseID);
		return resp.getContent();
	}

	// 37
	public boolean postThreadResponse(String responseTitle,
			String responseContent, String forumName,
			String subForumName, String userName, String openningThreadID){
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(openningThreadID);
		return thread.addFollowPost(userName,responseTitle,responseContent);
	}

	// 38
	public boolean deleteThreadResponse(String postID, String responseID,
			String forumName, String subForumName) {
		Forum forum = this.forumSystem.getForum(forumName);
		SubForum subForum = forum.getSubForumByName(subForumName);
		OpeningPost thread = subForum.getThread(postID);
		return thread.deleteResponse(responseID);
	}

	// 39
	public boolean editResponse(String userName, String forum, String subForum,String openningPostID, String responseID, String newText) {
		Forum forum1 = this.forumSystem.getForum(forum);
		SubForum subForum1 = forum1.getSubForumByName(subForum);
		OpeningPost thread = subForum1.getThread(openningPostID);
		FollowPost response = thread.getFollowPost(responseID);
		if (userName.compareTo(response.getMember())==0){
			response.setContent(newText);
			return true;
		}
		return false;
	}

	// 39.5
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean isItMyResponse(String userName, String forumName, String subForum, String postID, String responseID){
		Forum forum1 = this.forumSystem.getForum(forumName);
		SubForum subForum1 = forum1.getSubForumByName(subForum);
		OpeningPost thread = subForum1.getThread(postID);
		FollowPost response = thread.getFollowPost(responseID);
		if (userName.compareTo(response.getMember())==0)
			return true;
		return false;
	}


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// MEMBERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 40
	public boolean fileComplaint(String currentForum, String currentSubForum,
			String moderatorUsername, String complaint, String userName, String password) {
		Forum forum1 = this.forumSystem.getForum(currentForum);
		SubForum subForum1 = forum1.getSubForumByName(currentSubForum);
		return subForum1.postModeratorComplaint(userName, moderatorUsername, currentSubForum, complaint);
	}

	// 41
	public boolean addFriend(String forumName, String userName, String friendUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(userName);
		return member.addFriend(friendUserName);
	}

	// 42
	public boolean removeFriend(String userName, String forumName, String friendUserName) {
		Forum forum = this.forumSystem.getForum(forumName);
		Member member = forum.getMember(userName);
		return member.removeFriend(friendUserName);
	}

	// 43
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

	@Override
	public void cleanAllData(String userName, String pass) {
		this.forumSystem = null;
	}



}






















































