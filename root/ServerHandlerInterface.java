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
	public String publishNewThread(String forumName,String subForumName, 
			String threadTitle, String threadContent, String userName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
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

--------------------------------------------------------------------------------

	// expecting to return conf.SUCCESS || conf.FAIL
	public String validateByEmail(String userName, String code);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	// if SUCCESS a request will be sent to the friend until he approves 
	public String addFriend(String forumName, String userName, String friendUserName);

	// expecting to receive (conf.SUCCESS: msg || conf.FAIL: msg)
	public String removeFriend(String forumName, String userName, String friendUserName);


	// response to freind request - last argument response = YES/NO
	// ( return value - conf.SUCCESS: msg || conf.FAIL: msg)
	public String responseToFreindRequest(String userNameResponser, String userNameRequester, String response);
	
	public String banMember

****************************
messages from server to client

	// a message sent to the member from the server. used in several ways.
	// when a member publishes a msg, his friends will get a msg from the server.
	public void notifyMember(String text);


	// a message sent to the member from the server. when the requesterUserName send a freind request
	public void notifyFreindRequest(String requesterUserName);
--------------------------------------------------------------------------------

    public String initializeSystem(String userName, String password);

	public boolean isInitialize();

	public String validateSuperAdmin(String userName, String password);

	public String createForum(String forumName, String policyName, String securityName);
	
	

	public String createSubForum(String ForumName,String subForumName, String subject,
			String moderatorUserName,String userName);


	public String deleteSubForum(String forumName,String subForumName);

	

	public String enterNewProperty(String forumName,String property, String newValue, String userName, String password) ;

    public String deleteForum(String forumName);

    public Vector<String> getAllAdminsInForum(String forumName);
        
    public Vector<String> getAllModerators(String forumName, String subForumName);

    public boolean isMember (String userName, String forumName);
        
    public boolean isAdmin (String userName, String forumName);
        
    public boolean isModerator (String userName, String forumName, String subforumName);

    // check if there is such a member who is not an admin in this forum.
    public String addAdminToForum(String forumName, String adminName);
        
    public String removeAdminFromForum(String forumName, String adminName);


}
