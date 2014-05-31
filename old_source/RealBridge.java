package driver;

import implementaion.ServerHandler;

import java.util.Vector;

import bridge.ForumBridge;

public class RealBridge implements ForumBridge{

	@Override
	public void initialize(String userName, String password,
			String securityPolicy) {
		ServerHandler.initializeSystem(userName, password);
		
	}

	@Override
	public boolean isInitialize() {
		return ServerHandler.isInitialize();
	}

	@Override
	public String getSecurityPolicy() {
		return ServerHandler.getSecurityPolicy();
	}

	@Override
	public void cleanAllData() {
		ServerHandler.cleanAllData();
	}

	@Override
	public Vector<String> getMyPosts(String userName, String forumName) {
		return ServerHandler.getMyPosts(userName, forumName);
	}

	@Override
	public Vector<String> getAllForums() {
		return ServerHandler.DisplayForums();
	}

	@Override
	public Vector<String> getAllGuests() {
		return ServerHandler.getAllGuests();
	}

	@Override
	public Vector<String> getAllMembers() {
		return ServerHandler.getAllMembers();
	}

	@Override
	public Vector<String> getAllLoggedIn() {
		return ServerHandler.getAllLoggedIn();
	}

	@Override
	public Vector<String> getAllSubForums(String forumName) {
		return ServerHandler.getAllLoggedIn();
	}

	@Override
	public void createForum(String userName, String forumName,
			String validCharsForUserName, String validCharsForPassword,
			int minimalSizeOfPass, int maximalTitleSizeForMessage,
			int maximalContentSizeForMessage) {
		String policy = validCharsForUserName +","+ validCharsForPassword;
		String security = String.valueOf(minimalSizeOfPass) +","+ String.valueOf(maximalTitleSizeForMessage) +","+ String.valueOf(maximalContentSizeForMessage);
		ServerHandler.createForum(forumName, policy, security);	
	}

	@Override
	public void createSubForum(String userName, String forumName,
			String subForumName) {
		ServerHandler.createSubForum(forumName, subForumName, subForumName, null);//moderator
	}

	@Override
	public String register(String userName, String password, String forumName) {
		String email = userName + "@gmail.com";
		boolean ans = ServerHandler.registerToForum(forumName, userName, password, userName, userName, email);
		if(!ans)
			return null;
		return userName;
	}

	@Override
	public boolean isMemberInForum(String userName, String forumName) {
		return ServerHandler.isMemberInForum(userName, forumName);
	}

	@Override
	public boolean isForumExist(String forumName) {
		return ServerHandler.DisplayForums().contains(forumName);
	}

	@Override
	public boolean isSubForumExist(String forumName, String subForumName) {
		return ServerHandler.getSubForums(forumName).contains(subForumName);
	}

	@Override
	public boolean isMemberExist(String userName) {
		return ServerHandler.getAllMembers().contains(userName);
	}

	@Override
	public boolean isSuperAdmin(String userName) {
		return ServerHandler.isSuperAdmin(userName);
	}

	@Override
	public boolean isAdmin(String userName) {
		return ServerHandler.isAdmin(userName);
	}

	@Override
	public boolean isModerator(String userName) {
		return ServerHandler.isModerator(userName);
	}

	@Override
	public void setAsAdmin(String userName, String forumName) {
		ServerHandler.setAsAdmin(userName,forumName);
	}

	@Override
	public void setAsModerator(String userName, String forumName) {
		ServerHandler.setAsModerator(userName,forumName);
	}

	@Override
	public boolean setMenegmentPolicy(String userName, String forumName,
			String validCharsForUserName, String validCharsForPassword,
			int minimalSizeOfPass, int maximalTitleSizeForMessage,
			int maximalContentSizeForMessage) {
		return ServerHandler.enterNewProperty(validCharsForPassword, validCharsForUserName, String.valueOf(maximalContentSizeForMessage), String.valueOf(maximalTitleSizeForMessage));
	}

	@Override
	public String login(String userName, String password, String forumName) {
		boolean ans = ServerHandler.login(forumName, userName, password);
		if(!ans)
			return null;
		return userName;
	}

	@Override
	public String loginAsGuest(String forumName) {
		boolean ans = ServerHandler.EnterForum(forumName);
		if(!ans)
			return null;
		return forumName;
	}

	@Override
	public String logout(String userName) {
		boolean ans = ServerHandler.logout(null, userName, null);
		if(!ans)
			return null;
		return userName;
	}

	@Override
	public boolean isLoggedIn(String userName) {
		return ServerHandler.getAllLoggedIn().contains(userName);
	}

	@Override
	public Vector<String> getPolicyForForum(String forumName) {
		return ServerHandler.getPolicyForForum(forumName);
	}

	@Override
	public boolean isPolicyArgsValid(String validCharsForUserName,
			String validCharsForPassword, int minimalSizeOfPass,
			int maximalTitleSizeForMessage, int maximalContentSizeForMessage) {
		return ServerHandler.isPolicyArgsValid(validCharsForUserName, validCharsForPassword, minimalSizeOfPass, maximalTitleSizeForMessage, maximalContentSizeForMessage);
	}

	@Override
	public void addOpeningPost(String userName, String forumName,
			String subForumName, String title, String content) {
		ServerHandler.publishNewThread(forumName, subForumName, title, content, userName, null);
		
	}

	@Override
	public boolean isPostValidByPolicy(String forumName, String title,
			String content) {
		return ServerHandler.isPostValidByPolicy(forumName, title, content);
	}

	@Override
	public void addFollowingPost(String userName, String forumName,
			String subForumName, String openPost, String title, String content) {
		ServerHandler.postThreadResponse(content, title, forumName, subForumName, userName, null);
		
	}

	@Override
	public Vector<String> getTopics(String forumName) {
		return ServerHandler.getSubForums(forumName);
	}

	@Override
	public Vector<String> getFriendsList(String userName) {
		return ServerHandler.getFriendsList(userName);
	}

	@Override
	public Vector<String> getMyComplaints(String userName, String forumName,
			String moderator) {
		return ServerHandler.getMyComplaints(userName, forumName, moderator);
	}

	@Override
	public void fileAComplaint(String userName, String forumName,
			String moderator, String compaint) {
		ServerHandler.fileComplaint(forumName, null, moderator, compaint, userName, null);
		
	}

}

