package Connection.protocol;

import java.util.Vector;

//Fina3
public interface ServerHandlerInterface {

	// CONFIGURATION FILE CONTAINS:
	// delim1, delim2, SUCCESS, FAIL, 
	//11 14

    
	// 1*
	// only superAdmin can execute this func.
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public int numOfForumsReport();

	// 2*
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	// what should it do?
    public boolean initializeSystem(String userName, String password);
	
	// 3*
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public boolean isInitialize();

	// 4*
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public boolean validateSuperAdmin(String userName, String password);

	// 5*
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String loginSuper(String userName, String password) ;

	// 6*
	// expecting to receive (conf.SUCCESS msg)
	// need to set the policy while creating a new forum	
	public boolean createForum(String forumName, String userName, String hasEmailPolicy, String extendedDeletionPolicy,
								String minPostForModerator, String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators, 
								String canRemoveSingleModerators, String expirationDateForPassword, String interactiveNotifyingPolicys);

	// 7*
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public boolean deleteForum(String forumName);

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// FORUM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 1
	// entering to the system will return all the forum names separeted by conf.delim1
	public Vector<String> DisplayForums();

	// 2
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg || conf.PENDING) 
	// decided by forum policy. might get pending until validation by mail.
	public String registerToForum(String userName, String forumName,
			String password, String email, String remainderQues, String remainderAns);
	
	// 3
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg || conf.PENDING) 
	// if pending, need to pop a code validation window.
	public String login(String forumName, String userName, String password) ;

	// 4
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean logout(String forumName, String userName) ;
	
	// 5
	// check if there is such a member who is not an admin in this forum.
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
    public boolean addAdminToForum(String forumName, String adminName);

    // 6
    // expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
    public boolean removeAdminFromForum(String forumName, String adminName);
    
    // 7
	// expecting to return conf.SUCCESS || conf.FAIL
	public boolean validateByEmail(String userName,String forumName, String code);
	
	// 8
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// server side has to check if the response content matches the sub forum subject
	//** need to make sure about policy. perhaps members can be banned automaticaly because of policy
	public boolean banMember(String userName, String forumName, String memberToBanName);
	
	// 9
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// changing <userName> status to member and promote <newAdminUserName> to Administrator
	public boolean replaceAdmin (String userName, String forum, String newAdminUserName);
	
	// 10
	// expecting to return all the posts names separeted by conf.delim1
	// <userName> is Admin, return all type of posts
	public Vector<String> postsOfMemberReport (String userName, String forum, String memberName);

//	// 11
//	// expecting to return all the following posts separated by conf.delim1
//	public Vector<String> getFollowPostsStringList();
//	
	// 12
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// interactiveNotifyingPolicy can be online offline or selective, only.
	// *** need to check what is suspension policy for members described in the first demand form. ***
	public boolean setPolicy(String forumName, String userName, String hasEmailPolicy, String extendedDeletionPolicy,
			String minPostForModerator, String minSeniorityMonths, String onlyApointAdministratorCanRemoveModerators, 
			String canRemoveSingleModerators, String expirationDateForPassword, String interactiveNotifyingPolicys);

	// 13
	// only superAdmin can execute this func.
	// expecting to receive a list of member names seperated by conf.delim1
	public Vector<String> membersOfForum(String forumName);

	// 14
	// expecting a forum name. Each forum has its types.
	public Vector<String> getTypes(String forumName);
	
	// 15
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// Such as gold, silver, etc...
	public boolean updateMemberType(String userName, String forumName, String type, String memberToUpdate);

	// 16
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public boolean createType(String userName, String forumName, String type);

	// 16.5
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public boolean deleteType(String forumName, String type);
	
	// 17
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public boolean isMember (String userName, String forumName);
        
    // 18
    // expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
    public boolean isAdmin (String userName, String forumName);

	// 18.5 1\5
    //expecting to return a list of admin separated by conf.delim1
    public Vector<String> getAllAdminsInForum(String forumName);

    // 18.5 2\5
    // return all the posts written by userName [ title (delime2) subForum (delime2) forum (delime2) ]
    //separated by delim1
    public Vector<String> getMyPosts(String userName, String forumName);

	// 18.5 3\5
    //return all the freinds of userName separated by delim1
    public Vector<String> getMyFreinds(String userName, String forumName);
    
    // 18.5 4\5  
    //return all freind request sent to userName, separated by delim1
    public Vector<String> getMyRequests(String userName, String forumName);
    
    // 18.5 5\5
    //return all jointed members in forums, separated by delim1
    public Vector<String> getJoinedMemberInForums();
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SUBFORUM //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 19
	// return subForums as string of pairs, name (conf.delim2) subject, pairs are separated by conf.delim1. 
	// the function recieves a forum name.
	public  Vector<String> getSubForums(String forumName);

	// 20
	// entering to the subForum will return string of pairs, openning post name delim2 id, pairs are separated by conf.delim1. 
	public  Vector<String> getThreads(String forumName,String subForumName) ;
	
	// 21
	//expecting to return a list of sub forum names separated by conf.delim1
	public  Vector<String> getSubForumsForModerator(String forumName, String moderatorUserName);
	
	// 22
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean upgradeToModerator (String userName, String forum, String subForum, String moderatorUserName);

	// 23
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// ban gust for a limited time (by policy? if not need to add time argument)
	public boolean banModerator (String userName, String forum, String subForum, String moderatorUserName);

	// 24
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// chage his status to member
	public boolean removeModerator (String userName, String forum, String subForum, String moderatorUserName);
	
	// 25
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// <userName> is Administrator, promot <newModeratorName> to Moderator and changing <oldModerator> status to member
	public boolean replaceModerator (String userName, String forum, String subForum, String newModeratorName, String oldModerator);
	
	// 26
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	// return "Total posts: <num>, Threads: <num>"
	public int numOfPostsInSubForumReport (String userName, String forum, String subForum);

	// 27
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	//<userName> is Admin
	public Vector<String> listOfModeratorsReport (String userName, String forumName);
	
	// 28
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)
	public boolean createSubForum(String forumName,String subForumName, String subject, String moderatorUserName, String userName);

	// 29
	// expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	
	public boolean deleteSubForum(String forumName,String subForumName);

	// 30
    // expecting to receive (conf.SUCCESS msg|| conf.FAIL msg)	    
    public boolean isModerator (String userName, String forumName, String subforumName);

	// 30.5
    //expecting to return a list of moderators separated by conf.delim1
    public Vector<String> getAllModerators(String forumName, String subForumName);

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// THREAD //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 31
	// returns the content of a post specified by ID(unique by subForum)
	public String getThreadContent(String treadID, String subForumName, String forumName);
	
	// 32
	// entering to the post will return string of pairs, post title delim2 id, pairs are separeted by conf.delim1
	// sending ID of the openning post.
	public Vector<String> getThreadResponses(String forumName,
			String subForumName, String id);

	// 33
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// server side has to check if the new thread content matches the sub forum subject
	public boolean publishNewThread(String forumName,String subForumName, 
			String threadTitle, String threadContent, String userName);
	
	// 34
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean deleteThread(String treadID, String subForum, String forum);

	// 35
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean editTread(String userName, String forum, String subForum, String treadID, String newText);
	
	// 35.5
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean isItMyPost(String userName, String forumName, String subForum, String postID);
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// RESPONSE //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 36
	// returns the content of a post specified by ID(unique by subForum)
	public String getResponseContent(String treadID, String responseID, String subForumName, String forumName);

	// 37
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// server side has to check if the response content matches the sub forum subject
	public boolean postThreadResponse(String responseTitle,
			String responseContent, String forumName,
			String subForumName, String userName, String openningThreadID);

	// 38
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)			NEED TO CHECK ID
	public boolean deleteThreadResponse(String postID,
			String responseID, String forumName,
			String subForumName);
	// 39
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean editResponse(String userName, String forum, String subForum, String treadID, String responseID, String newText);
	
	// 39.5
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean isItMyResponse(String userName, String forumName, String subForum, String postID, String responseID);
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// MEMBERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 40
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean fileComplaint(String forumName,
			String subForumName, String moderatorUsername, String complaint,
			String userName, String password);
	// 41
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// if SUCCESS a request will be sent to the friend until he approves 
	public boolean addFriend(String forumName, String userName, String friendUserName);
	// 42
	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean removeFriend(String userName, String forumName, String friendUserName);
	// 43
	// response to freind request - last argument response = YES/NO
	// ( return value - conf.SUCCESS: msg || conf.FAIL: msg)
	public boolean responseToFreindRequest(String forumName, String userName, String friendUserName);

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
	
	public void cleanAllData(String userName, String password);
    ////////////////////////////////////////////////////////
                
}
