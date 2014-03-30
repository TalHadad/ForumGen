package implementaion;

import java.util.Vector;


public class ServerHandler {

	public static boolean initializeSystem(String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean isInitialize() {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean validateSuperAdmin(String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean createForum(String forumName, String policy, String security) {
		// TODO Auto-generated method stub
		return true;
	}

	public static Vector<String> DisplayForums() {
		// TODO Auto-generated method stub
		Vector<String> ans = new Vector<String>();
		ans.add("yael");
		ans.add("nahon");
		return ans;
	}

	public static boolean EnterForum(String currentForum) {
		// TODO Auto-generated method stub
		return true;
	}

	public static Vector<String> getSubForums(String currentForum) {
		// TODO Auto-generated method stub
		Vector<String> ans = new Vector<String>();
		ans.add("sub-yael");
		ans.add("sub-nahon");
		return ans;
	}

	public static boolean registerToForum(String currentForum, String userName,
			String password, String firstName, String lastName, String email) {
		// TODO Auto-generated method stub
		return true;
		
	}

	public static boolean createSubForum(String ForumName,String subForumName, String subject,
			String moderatorUserName) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean logout(String currentForum, String userName,
			String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean login(String currentForum, String userName,
			String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean deleteSubForum(String subForumName, String userName,
			String password) {
		// TODO Auto-generated method stub
		return false;
	}

	public static Vector<String> getThreads(String currentForum,
			String currentSubForum) {
		// TODO Auto-generated method stub
		Vector<String> ans = new Vector<String>();
		ans.add("thread-yael");
		ans.add("thread-nahon");
		return ans;
	}

	public static boolean publishNewThread(String currentForum,
			String currentSubForum, String threadTitle, String threadContent,
			String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean deleteThread(String threadName,String subForum,
			String Forum, String userName,
			String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean fileComplaint(String currentForum,
			String currentSubForum, String moderatorUsername, String complaint,
			String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean deleteThreadResponse(String responseNumber,
			String currentThreadTitle, String currentForum,
			String currentSubForum, String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static boolean postThreadResponse(String responseContent,
			String currentThreadTitle, String currentForum,
			String currentSubForum, String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	public static Vector<String> getThreadResponses(String currentForum,
			String currentSubForum, String currentThreadTitle) {
		Vector<String> ans = new Vector<String>();
		ans.add("response-yael");
		ans.add("response-nahon");
		return ans;
	}

	public static String getThreadContent(String currentThreadTitle,
			String currentSubForum, String forumName) {
		// TODO Auto-generated method stub
		return "thread-content-yael";
	}

	public static boolean enterNewProperty(String property, String newValue,
			String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}
	
	//*************added for acceptance tests******************//
	
	public static String getSecurityPolicy() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void cleanAllData() {
		// TODO Auto-generated method stub
	}

	public static Vector<String> getMyPosts(String userName, String forumName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Vector<String> getAllGuests() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Vector<String> getAllMembers() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Vector<String> getAllLoggedIn() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static boolean isMemberInForum(String userName, String forumName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static boolean isSuperAdmin(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isAdmin(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean isModerator(String userName) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static void setAsAdmin(String userName, String forumName) {
		// TODO Auto-generated method stub	
	}

	public static void setAsModerator(String userName, String forumName) {
		// TODO Auto-generated method stub	
	}
	
	public static Vector<String> getPolicyForForum(String forumName) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean isPolicyArgsValid(String validCharsForUserName,
			String validCharsForPassword, int minimalSizeOfPass,
			int maximalTitleSizeForMessage, int maximalContentSizeForMessage) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static boolean isPostValidByPolicy(String forumName, String title,
			String content) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public static Vector<String> getFriendsList(String userName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static Vector<String> getMyComplaints(String userName, String forumName,
			String moderator) {
		// TODO Auto-generated method stub
		return null;
	}
}
