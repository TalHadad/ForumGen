package ListenerServer.protocol;

import java.util.Vector;

public interface ServerHandlerInterface {

	// CONFIGURATION FILE CONTAINS:
	// delim1, delim2, SUCCESS, FAIL, 

	// entering to the system will return all the forum names separeted by conf.delim1
	public String DisplayForums();

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg || conf.PENDING) 
	// decided by forum policy. might get pending until validation by mail.
	public String registerToForum(String forumName, String userName,
			String password, String Email, String remainderQues, String remainderAns);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg || conf.PENDING)
	// if pending, need to pop a code validation window.
	public String login(String forumName, String userName, String password) ;

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String logout(String forumName, String userName) ;

	// return subForums as string of pairs, name (conf.delim2) subject, pairs are separated by conf.delim1. 
	// the function recieves a forum name.
	public String getSubForums(String forumName);

	// entering to the subForum will return string of pairs, openning post name delim2 id, pairs are separated by conf.delim1. 
	public String getThreads(String forumName,String subForumName) ;

	// returns the content of a post specified by ID(unique by subForum)
	public String getPostContent(String ID, String subForumName, String forumName);

	// entering to the post will return string of pairs, post title delim2 id, pairs are separeted by conf.delim1
	// sending ID of the openning post.
	public String getThreadResponses(String forumName,
			String subForumName, String ID);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// server side has to check if the new thread content matches the sub forum subject
	public String publishNewThread(String forumName,String subForumName, 
			String threadTitle, String threadContent, String userName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// server side has to check if the response content matches the sub forum subject
	public String postThreadResponse(String responseContent,
			String currentThreadTitle, String forumName,
			String subForumName, String userName, String ID);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String deleteThread(String ID, String subForum, String Forum);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)			NEED TO CHECK ID
	public String deleteThreadResponse(String postID,
			String responseID, String forumName,
			String subForumName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String fileComplaint(String forumName,
			String subForumName, String moderatorUsername, String complaint,
			String userName, String password);

	//expecting to return a list of sub forum names separated by conf.delim1
	public String getSubForumsForModerator(String forumName, String moderatorUserName);

	// check if there is such a member who is not an admin in this forum.
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
    public String addAdminToForum(String forumName, String adminName);
        
    // expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
    public String removeAdminFromForum(String forumName, String adminName);


--------------------------------------------------------------------------------

	// expecting to return conf.SUCCESS || conf.FAIL
	public String validateByEmail(String userName, String code);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// if SUCCESS a request will be sent to the friend until he approves 
	public String addFriend(String forumName, String userName, String friendUserName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String removeFriend(String userName, String forumName, String friendUserName);

	// response to freind request - last argument response = YES/NO
	// ( return value - conf.SUCCESS: msg || conf.FAIL: msg)
	public String responseToFreindRequest(String userName, String userNameResponser, String response);
	
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	//** need to make sure about policy. perhaps members can be banned automaticaly because of policy
	public String banMember(String userName, String forumName, String memberToBanName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String editOpenningPost(String userName, String forum, String subForum, String openningPostID, String newText);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String editResponse(String userName, String forum, String subForum, String openningPostID, String responseID, String newText);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String upgradeToModerator (String userName, String forum, String subForum, String moderatorUserName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// ban gust for a limited time (by policy? if not need to add time argument)
	public String banModerator (String userName, String forum, String subForum, String moderatorUserName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// chage his status to member
	public String removeModerator (String userName, String forum, String subForum, String moderatorUserName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// changing <userName> status to member and promot <newAdminUserName> to Administrator
	public String replaceAdmin (String userName, String forum, String newAdminUserName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// <userName> is Administrator, promot <newModeratorName> to Moderator and changing <oldModerator> status to member
	public String replaceModerator (String userName, String forum, String newModeratorName, String oldModerator);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String numOfPostsInSubForumReport (String userName, String forum, String subForum);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	//<userName> is Admin
	public String postsOfMemberReport (String userName, String forum, String memberName);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	//<userName> is Admin
	public String listOfModeratorsReport (String userName, String forumName);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// need to set the policy while creating a new forum
	public String createForum(String forumName, String userName, String policy, String security);
	
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// interactiveNotifyingPolicy can be online offline or selective, only.
	// *** need to check what is suspention policy for members described in the first demand form. ***
	public String setPolicy(String forumName, String userName, String hasEmailPolicy, String extendedDeletionPolicy,
			String policyForUpdatingRank, String minPostForModerator, String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators, 
			String String canRemoveSingleModerators, String expirationDateForPassword, String interactiveNotifyingPolicyS);

	// only superAdmin can execute this func.
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String numOfForumsReport();
	
	// only superAdmin can execute this func.
	// expecting to receive a list of member names seperated by conf.delim1
	public String membersOfForum(String forumName);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String createSubForum(String forumName,String subForumName, String subject, String moderatorUserName, String userName);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String deleteForum(String forumName);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public String deleteSubForum(String forumName,String subForumName);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// Such as gold, silver, etc...
	public String updateMemberType(String userName, String forumName, String type, String memberToUpdate);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public String createType(String userName, String forumName, String type);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public boolean isMember (String userName, String forumName);
        
    // expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
    public boolean isAdmin (String userName, String forumName);

    // expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	    
    public boolean isModerator (String userName, String forumName, String subforumName);

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	// what should it do?
    public String initializeSystem(String userName, String password);
	
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public String isInitialize();

	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String validateSuperAdmin(String userName, String password);

	//public String enterNewProperty(String forumName,String property, String newValue, String userName, String password) ;


****************************
messages from server to client

	// a message sent to the member from the server. used in several ways.
	// when a member publishes a msg, his friends will get a msg from the server.
	public void notifyMember(String text);


	// a message sent to the member from the server. when the requesterUserName send a freind request
	public void notifyFreindRequest(String requesterUserName);
}
