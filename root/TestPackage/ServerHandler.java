package TestPackage;

public class ServerHandler implements ServerHandlerInterface {

	@Override
	public String DisplayForums() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String registerToForum(String userName, String forumName,
			String password, String Email, String remainderQues,
			String remainderAns) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String login(String forumName, String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String logout(String forumName, String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSubForums(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getThreads(String forumName, String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getThreadContent(String treadID, String subForumName,
			String forumName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getThreadResponses(String forumName, String subForumName,
			String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String publishNewThread(String forumName, String subForumName,
			String threadTitle, String threadContent, String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String postThreadResponse(String responseContent,
			String currentThreadTitle, String forumName, String subForumName,
			String userName, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteThread(String id, String subForum, String Forum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteThreadResponse(String postID, String responseID,
			String forumName, String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String fileComplaint(String forumName, String subForumName,
			String moderatorUsername, String complaint, String userName,
			String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSubForumsForModerator(String forumName,
			String moderatorUserName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addAdminToForum(String forumName, String adminName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String removeAdminFromForum(String forumName, String adminName) {
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
	public String editTread(String userName, String forum, String subForum,
			String treadID, String newText) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String editResponse(String userName, String forum, String subForum,
			String treadID, String responseID, String newText) {
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
	public String createSubForum(String forumName, String subForumName,
			String subject, String moderatorUserName, String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteForum(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteSubForum(String forumName, String subForumName) {
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
	public String initializeSystem(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String isInitialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String validateSuperAdmin(String userName, String password) {
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
	public String getResponseContent(String treadID, String responseID,
			String subForumName, String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String loginSuper(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String createForum(String forumName, String userName,
			String hasEmailPolicy, String extendedDeletionPolicy,
			String minPostForModerator, String minSeniorityMonths,
			String onlyApointAdministratorCanRemoveModerators,
			String canRemoveSingleModerators, String expirationDateForPassword) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setPolicy(String forumName, String userName,
			String hasEmailPolicy, String extendedDeletionPolicy,
			String minPostForModerator, String minSeniorityMonths,
			String onlyApointAdministratorCanRemoveModerators,
			String canRemoveSingleModerators, String expirationDateForPassword,
			String interactiveNotifyingPolicys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String isMember(String userName, String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String isAdmin(String userName, String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllAdminsInForum(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String isModerator(String userName, String forumName,
			String subforumName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllModerators(String forumName, String subForumName) {
		// TODO Auto-generated method stub
		return null;
	}


}
