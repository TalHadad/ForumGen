package TestPackage;
//Fina3
public interface ServerHandlerInterface {

	// CONFIGURATION FILE CONTAINS:
	// delim1, delim2, SUCCESS, FAIL, 
	//11 14

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SYSTEM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 1*
	// only superAdmin can execute this func.
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String numOfForumsReport();

	// 2*
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	// what should it do?
    public String initializeSystem(String userName, String password);
	
	// 3*
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public String isInitialize();

	// 4*
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String validateSuperAdmin(String userName, String password);

	// 5*
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String loginSuper(String userName, String password) ;

	// 6*
	// expecting to receive (conf.SUCCESS msg)
	// need to set the policy while creating a new forum	
	public String createForum(String forumName, String userName, String hasEmailPolicy, String extendedDeletionPolicy,
								String minPostForModerator, String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators, 
								String canRemoveSingleModerators, String expirationDateForPassword);

	// 7*
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String deleteForum(String forumName);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// FORUM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 1
	// entering to the system will return all the forum names separeted by conf.delim1
	public String DisplayForums();

	// 2
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg || conf.PENDING) 
	// decided by forum policy. might get pending until validation by mail.
	public String registerToForum(String userName, String forumName,
			String password, String email, String remainderQues, String remainderAns);

	// if pending, need to pop a code validation window.
	public String login(String forumName, String userName, String password) ;

	// 4
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String logout(String forumName, String userName) ;
	
	// 5
	// check if there is such a member who is not an admin in this forum.
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
    public String addAdminToForum(String forumName, String adminName);

    // 6
    // expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
    public String removeAdminFromForum(String forumName, String adminName);
    
    // 7
	// expecting to return conf.SUCCESS || conf.FAIL
	public String validateByEmail(String userName, String code);
	
	// 8
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// server side has to check if the response content matches the sub forum subject
	//** need to make sure about policy. perhaps members can be banned automaticaly because of policy
	public String banMember(String userName, String forumName, String memberToBanName);
	
	// 9
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// changing <userName> status to member and promot <newAdminUserName> to Administrator
	public String replaceAdmin (String userName, String forum, String newAdminUserName);
	
	// 10
	// expecting to return all the posts names separeted by conf.delim1
	//<userName> is Admin, return all type of posts
	public String postsOfMemberReport (String userName, String forum, String memberName);

	// 12
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// interactiveNotifyingPolicy can be online offline or selective, only.
	// *** need to check what is suspention policy for members described in the first demand form. ***
	public String setPolicy(String forumName, String userName, String hasEmailPolicy, String extendedDeletionPolicy,
			String minPostForModerator, String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators, 
			String canRemoveSingleModerators, String expirationDateForPassword, String interactiveNotifyingPolicys);

	// 13
	// only superAdmin can execute this func.
	// expecting to receive a list of member names seperated by conf.delim1
	public String membersOfForum(String forumName);

	// 15
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// Such as gold, silver, etc...
	public String updateMemberType(String userName, String forumName, String type, String memberToUpdate);

	// 16
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public String createType(String userName, String forumName, String type);

	// 17
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public String isMember (String userName, String forumName);
        
    // 18
    // expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
    public String isAdmin (String userName, String forumName);

	// 18.5
    //expecting to return a list of admin separated by conf.delim1
    public String getAllAdminsInForum(String forumName);


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SUBFORUM //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 19
	// return subForums as string of pairs, name (conf.delim2) subject, pairs are separated by conf.delim1. 
	// the function recieves a forum name.
	public String getSubForums(String forumName);

	// 20
	// entering to the subForum will return string of pairs, openning post name delim2 id, pairs are separated by conf.delim1. 
	public String getThreads(String forumName,String subForumName) ;
	
	// 21
	//expecting to return a list of sub forum names separated by conf.delim1
	public String getSubForumsForModerator(String forumName, String moderatorUserName);
	
	// 22
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String upgradeToModerator (String userName, String forum, String subForum, String moderatorUserName);

	// 23
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// ban gust for a limited time (by policy? if not need to add time argument)
	public String banModerator (String userName, String forum, String subForum, String moderatorUserName);

	// 24
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// chage his status to member
	public String removeModerator (String userName, String forum, String subForum, String moderatorUserName);
	
	// 25
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// <userName> is Administrator, promot <newModeratorName> to Moderator and changing <oldModerator> status to member
	public String replaceModerator (String userName, String forum, String newModeratorName, String oldModerator);
	
	// 26
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// return "Total posts: <num>, Threads: <num>"
	public String numOfPostsInSubForumReport (String userName, String forum, String subForum);

	// 27
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	//<userName> is Admin
	public String listOfModeratorsReport (String userName, String forumName);
	
	// 28
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public String createSubForum(String forumName,String subForumName, String subject, String moderatorUserName, String userName);

	// 29
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public String deleteSubForum(String forumName,String subForumName);

	// 30
    // expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	    
    public String isModerator (String userName, String forumName, String subforumName);

	// 30.5
    //expecting to return a list of moderators separated by conf.delim1
    public String getAllModerators(String forumName, String subForumName);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// THREAD //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 31
	// returns the content of a post specified by ID(unique by subForum)
	public String getThreadContent(String treadID, String subForumName, String forumName);
	
	// 32
	// entering to the post will return string of pairs, post title delim2 id, pairs are separeted by conf.delim1
	// sending ID of the openning post.
	public String getThreadResponses(String forumName,
			String subForumName, String id);

	// 33
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// server side has to check if the new thread content matches the sub forum subject
	public String publishNewThread(String forumName,String subForumName, 
			String threadTitle, String threadContent, String userName);
	
	// 34
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String deleteThread(String treadID, String subForum, String forum);

	// 35
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String editTread(String userName, String forum, String subForum, String treadID, String newText);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// RESPONSE //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 36
	// returns the content of a post specified by ID(unique by subForum)
	public String getResponseContent(String treadID, String responseID, String subForumName, String forumName);

	// 37
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// server side has to check if the response content matches the sub forum subject
	public String postThreadResponse(String responseTitle,
			String responseContent, String forumName,
			String subForumName, String userName, String openningThreadID);

	// 38
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)			NEED TO CHECK ID
	public String deleteThreadResponse(String postID,
			String responseID, String forumName,
			String subForumName);
	// 39
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String editResponse(String userName, String forum, String subForum, String treadID, String responseID, String newText);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// MEMBERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 40
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String fileComplaint(String forumName,
			String subForumName, String moderatorUsername, String complaint,
			String userName, String password);
	// 41
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// if SUCCESS a request will be sent to the friend until he approves 
	public String addFriend(String forumName, String userName, String friendUserName);
	// 42
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String removeFriend(String userName, String forumName, String friendUserName);
	// 43
	// response to freind request - last argument response = YES/NO
	// ( return value - conf.SUCCESS: msg || conf.FAIL: msg)
	public String responseToFreindRequest(String userName, String userNameResponser, String response);

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// messages from server to client //////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 44
	// a message sent to the member from the server. used in several ways.
	// when a member publishes a msg, his friends will get a msg from the server.
	public void notifyMember(String text);

	// 45
	// a message sent to the member from the server. when the requesterUserName send a freind request
	public void notifyFreindRequest(String requesterUserName);

	//public String enterNewProperty(String forumName,String property, String newValue, String userName, String password) ;
	
	
        
    
}
