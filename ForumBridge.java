package bridge;

import java.util.Vector;

public interface ForumBridge {

	public void initialize(String userName, String password, String securityPolicy);
	
	public boolean isInitialize();
	
	public String getSecurityPolicy();
	
	public void cleanAllData();
	
	public Vector<String> getMyPosts(String userName, String forumName);//maybe without forum name
	
	public Vector<String> getAllForums();
	
	public Vector<String> getAllGuests();
	
	public Vector<String> getAllMembers();
	
	public Vector<String> getAllLoggedIn();
	
	public Vector<String> getAllSubForums(String forumName);
	
	//public int selectForum(String forumName);
	
	public void createForum(String userName, String forumName, String validCharsForUserName,
				String validCharsForPassword, int minimalSizeOfPass, int maximalTitleSizeForMessage, int maximalContentSizeForMessage);
	
	public void createSubForum(String userName, String forumName, String subForumName);
	
	public String register(String userName, String password, String forumName);
	
	public boolean isMemberInForum(String userName, String forumName);
	
	public boolean isForumExist(String forumName);
	
	public boolean isSubForumExist(String forumName, String subForumName);
	
	public boolean isMemberExist(String userName);
	
	public boolean isSuperAdmin(String userName);
	
	public boolean isAdmin(String userName);
	
	public boolean isModerator(String userName);
	
	public void setAsAdmin(String userName, String forumName);
	
	public void setAsModerator(String userName, String forumName);
	
	public boolean setMenegmentPolicy(String userName, String forumName, String validCharsForUserName, String validCharsForPassword, int minimalSizeOfPass, int maximalTitleSizeForMessage, int maximalContentSizeForMessage);

	public String login(String userName, String password, String forumName);
	
	public String loginAsGuest(String forumName);
	
	public String logout(String userName);
	
	public boolean isLoggedIn(String userName);
	
	public Vector<String> getPolicyForForum(String forumName);
	
	public boolean isPolicyArgsValid(String validCharsForUserName, String validCharsForPassword, int minimalSizeOfPass, int maximalTitleSizeForMessage, int maximalContentSizeForMessage);
	
	public void addOpeningPost(String userName, String forumName, String subForumName, String title, String content);
	
	public boolean isPostValidByPolicy(String forumName, String title, String content);

	public void addFollowingPost(String userName, String forumName, String subForumName, String openPost, String title, String content);
	
	public Vector<String> getTopics(String forumName);
	
	public Vector<String> getFriendsList(String userName);
	
	public Vector<String> getMyComplaints(String userName, String forumName, String moderator);
	
	public void fileAComplaint(String userName, String forumName, String moderator, String compaint);
}

