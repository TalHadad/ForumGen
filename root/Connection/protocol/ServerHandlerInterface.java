package ListenerServer.protocol;

import java.util.Vector;

public interface ServerHandlerInterface {


    public String initializeSystem(String userName, String password);

	public boolean isInitialize();

	public String validateSuperAdmin(String userName, String password);

	public String createForum(String forumName, String policyName, String securityName);
	
	public Vector<String> DisplayForums();

	public Vector<String> getSubForums(String currentForum);

        
	public String registerToForum(String currentForum, String userName,
			String password, String Email, String remainderQues, String remainderAns);
	public String createSubForum(String ForumName,String subForumName, String subject,
			String moderatorUserName,String userName);

	public String logout(String currentForum, String userName) ;

	public String login(String currentForum, String userName,
			String password) ;

	public String deleteSubForum(String forumName,String subForumName);

	public Vector<String> getThreads(String currentForum,
			String currentSubForum) ;

	public String publishNewThread(String currentForum,
			String currentSubForum, String threadTitle, String threadContent,
			String userName, String password);

	public String deleteThread(String threadName,String subForum,
			String Forum, String userName,
			String password);

	public String fileComplaint(String currentForum,
			String currentSubForum, String moderatorUsername, String complaint,
			String userName, String password);

	public String deleteThreadResponse(String responseNumber,
			String currentThreadTitle, String currentForum,
			String currentSubForum, String userName, String password);

	public String postThreadResponse(String responseContent,
			String currentThreadTitle, String currentForum,
			String currentSubForum, String userName,String title);

	public Vector<String> getThreadResponses(String currentForum,
			String currentSubForum, String currentThreadTitle);

	public String getThreadContent(String currentThreadTitle,
			String currentSubForum, String forumName);

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
