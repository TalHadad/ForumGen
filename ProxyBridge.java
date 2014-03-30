package bridge;

import java.util.Vector;

public class ProxyBridge implements ForumBridge {

	public ForumBridge real;
	
	private boolean talLoggedIn;
	private boolean YaelIsSuperAdmin;
	private Vector<String> members;
	private Vector<String> forums;
	private Vector<String> loggdin;
	private Vector<String> admins;
	private Vector<String> talPosts;
	private Vector<String> guests;
	private Vector<String> subs;
	private Vector<String> mods;
	private Vector<String> complaints;
	
	public ProxyBridge() {
		this.real=null;
		this.members = new Vector<String>();
		this.forums = new Vector<String>();
		this.loggdin = new Vector<String>();
		this.admins = new Vector<String>();
		this.talPosts = new Vector<String>();
		this.guests = new Vector<String>();
		this.subs = new Vector<String>();
		this.mods = new Vector<String>();
		this.complaints = new Vector<String>();
		this.talLoggedIn = false;
		this.YaelIsSuperAdmin = false;
	}
	
	@Override
    public void cleanAllData() {
        if(this.real !=null)
            this.real.cleanAllData();
        else{
        	this.talLoggedIn = false;
        	this.YaelIsSuperAdmin = false;
        	this.members = null;
        	this.forums = null;
        	this.loggdin = null;
        	admins = null;
        	talPosts = null;
        	guests = null;
        	subs = null;
        	mods = null;
        	complaints = null;
        }
    }

	@Override
	public void initialize(String userName, String password, String securityPolicy) {
		if(this.real !=null)
            this.real.initialize(userName,password,securityPolicy);
		else{
			this.YaelIsSuperAdmin = true;
			this.members.add(userName);
			this.loggdin.add(userName);
		}
	}
	
	@Override
	public Vector<String> getAllForums() {
		if(this.real !=null)
			return this.real.getAllForums();
		else
			return this.forums;
	}
	
	@Override
	public Vector<String> getAllMembers() {
		if(this.real !=null)
			return this.real.getAllMembers();
		else 
			return this.members;
	}
	
	@Override
	public Vector<String> getAllLoggedIn() {
		if(this.real !=null)
			return this.real.getAllLoggedIn();
		else 
			return this.loggdin;
	}
	
	@Override
	public void createForum(String userName, String forumName,
			String validCharsForUserName, String validCharsForPassword,
			int minimalSizeOfPass, int maximalTitleSizeForMessage,
			int maximalContentSizeForMessage) {
		if(this.real !=null)
			this.real.createForum(userName, forumName, validCharsForUserName, validCharsForPassword,
				minimalSizeOfPass, maximalTitleSizeForMessage, maximalContentSizeForMessage);
		else{
			if(userName.equals("Yael"))
				forums.add(forumName);
		}
	}
	
	@Override
	public void createSubForum(String userName, String forumName, String subForumName) {
		if(this.real !=null)
			this.real.createSubForum(userName, forumName, subForumName);
		else{
			if(isAdmin(userName))
				subs.add(subForumName);
		}
	}
	
	@Override
	public String register(String userName, String password, String forumName) {
		if(this.real !=null)
			return this.real.register(userName, password, forumName);
		else{
			if(!members.contains(userName)){
				members.add(userName);
				loggdin.add(userName);
				return userName;
			}
		}
		return null;
	}
	
	@Override
	public boolean isMemberInForum(String userName, String forumName) {
		if(this.real !=null)
			return this.real.isMemberInForum(userName, forumName);
		else
			if(this.talLoggedIn || userName.equals("Yael") || userName.equals("Vali"))
				return true;
		return false;
	}
	
	@Override
	public boolean isForumExist(String forumName) {
		if(this.real !=null)
			return this.real.isForumExist(forumName);
		return this.forums.contains(forumName);
	}
	
	@Override
	public boolean isMemberExist(String userName) {
		if(this.real !=null)
			return this.real.isMemberExist(userName);
		return this.members.contains(userName);
	}
	
	@Override
	public boolean isAdmin(String userName) {
		if(this.real !=null)
			return this.real.isAdmin(userName);
		return admins.contains(userName);
	}
	
	@Override
	public boolean isModerator(String userName) {
		if(this.real !=null)
			return this.real.isModerator(userName);
		return mods.contains(userName);
	}
	
	@Override
	public void setAsAdmin(String userName, String forumName) {
		if(this.real !=null)
			this.real.setAsAdmin(userName, forumName);
		admins.add(userName);
	}
	
	@Override
	public void setAsModerator(String userName, String forumName) {
		if(this.real !=null)
			this.real.setAsModerator(userName, forumName);
		mods.add(userName);
	}

	@Override
	public String logout(String userName) {
		if(this.real !=null)
			return this.real.logout(userName);
		if(loggdin.contains(userName)){
			loggdin.remove(userName);
			return userName;
		}
		return null;
	}
	
	@Override
	public boolean isLoggedIn(String userName) {
		if(this.real !=null)
			return this.real.isLoggedIn(userName);
		return loggdin.contains(userName);
	}
	
	@Override
	public boolean isPolicyArgsValid(String validCharsForUserName,
			String validCharsForPassword, int minimalSizeOfPass,
			int maximalTitleSizeForMessage, int maximalContentSizeForMessage) {
		if(this.real !=null)
			return this.real.isPolicyArgsValid(validCharsForUserName, validCharsForPassword,
				minimalSizeOfPass, maximalTitleSizeForMessage, maximalContentSizeForMessage);
		return (!validCharsForPassword.equals("")) && (!validCharsForUserName.equals("")) && 
				(minimalSizeOfPass!=0) && (maximalContentSizeForMessage!=0) && (maximalTitleSizeForMessage!=0);
	}
	
	@Override
	public boolean isInitialize() {
		if(this.real !=null)
			return this.real.isInitialize();
		return YaelIsSuperAdmin;
	}
	
	@Override
	public String getSecurityPolicy() {
		if(this.real !=null)
			return this.real.getSecurityPolicy();
		return "bla";
	}
	
	@Override
	public Vector<String> getMyPosts(String userName, String forumName) {
		if(this.real !=null)
			return this.real.getMyPosts(userName,forumName);
		return talPosts;
	}
	
	@Override
	public Vector<String> getAllGuests() {
		if(this.real !=null)
			return this.real.getAllGuests();
		return guests;
	}
	
	@Override
	public Vector<String> getAllSubForums(String forumName) {
		if(this.real !=null)
			return this.real.getAllSubForums(forumName);
		return subs;
	}
	
	@Override
	public boolean isSubForumExist(String forumName,String subForumName) {
		if(this.real !=null)
			return this.real.isSubForumExist(forumName, subForumName);
		return subs.contains(subForumName);
	}
	
	@Override
	public boolean isSuperAdmin(String userName) {
		if(this.real !=null)
			return this.real.isSuperAdmin(userName);
		return userName.equals("Yael") || userName.equals("Sivan");
	}
	
	@Override
	public boolean setMenegmentPolicy(String userName, String forumName,
			String validCharsForUserName, String validCharsForPassword,
			int minimalSizeOfPass, int maximalTitleSizeForMessage,
			int maximalContentSizeForMessage) {
		if(this.real !=null)
			return this.real.setMenegmentPolicy(userName,forumName,validCharsForUserName,validCharsForPassword,
					minimalSizeOfPass,maximalTitleSizeForMessage,maximalContentSizeForMessage);
		return true;
		
	}
	
	@Override
	public String login(String userName, String password, String forumName) {
		if(this.real !=null)
			return this.real.login(userName,password,forumName);
		if(userName.equals("Tal")){
			loggdin.add(userName);
			return userName;
		}
		return null;
	}
	
	@Override
	public String loginAsGuest(String forumName) {
		if(this.real !=null)
			return this.real.loginAsGuest(forumName);
		guests.add(forumName);
		return forumName;
	}
	
	@Override
	public Vector<String> getPolicyForForum(String forumName) {
		if(this.real !=null)
			this.real.getPolicyForForum(forumName);
		return null;
	}
	
	@Override
	public void addOpeningPost(String userName, String forumName,
			String subForumName, String title, String content) {
		if(this.real !=null)
			this.real.addOpeningPost(userName,forumName,subForumName, title, content);
		if(isMemberExist(userName) && isLoggedIn(userName) && !title.isEmpty() && !content.isEmpty())
			talPosts.add(title);
	}
	
	@Override
	public boolean isPostValidByPolicy(String forumName, String title,
			String content) {
		if(this.real !=null)
			return this.real.isPostValidByPolicy(forumName, title, content);
		return (!title.equals("")) && (!content.equals(""));
	}
	
	@Override
	public void addFollowingPost(String userName, String forumName,
			String subForumName, String openPost, String title, String content) {
		if(this.real !=null)
			this.real.addFollowingPost( userName,  forumName, subForumName,  openPost,  title,  content);
		if(userName.equals("Tal"))
			talPosts.add(title);
		
	}
	
	@Override
	public Vector<String> getTopics(String forumName) {
		if(this.real !=null)
			return this.real.getTopics(forumName);
		return this.talPosts;
	}
	
	@Override
	public Vector<String> getFriendsList(String userName) {
		if(this.real !=null)
			return this.real.getFriendsList(userName);
		return null;
	}

	@Override
	public Vector<String> getMyComplaints(String userName, String forumName,
			String moderator) {
		if(this.real !=null)
			return this.real.getMyComplaints(userName, forumName, moderator);
		return complaints;
	}

	@Override
	public void fileAComplaint(String userName, String forumName,
			String moderator, String compalint) {
		if(this.real !=null)
			this.real.fileAComplaint(userName, forumName, moderator, compalint);
		if(!userName.equals("Sivan"))
			complaints.add(compalint);
		
	}
}
