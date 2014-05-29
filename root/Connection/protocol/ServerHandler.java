package ListenerServer.protocol;
import Users.*;
import Posts.*;
import java.util.Vector;
import Forum.*;

public class ServerHandler implements ServerHandlerInterface{
	private ForumGen forumSystem;
	
	public ServerHandler(){
		this.forumSystem= new ForumGen();
	}

	@Override
	public String initializeSystem (String userName, String password) {
		if(userName.compareTo(this.forumSystem.getSuperAdminName())==0 &&
				password.compareTo(this.forumSystem.getSuperAdminPassword())==0)
			 return Configuration.SUCCESS ;
		 return Configuration.FAIL+" error unauthorized user";
	}
	@Override
	public boolean isInitialize() {
		return this.forumSystem.getIsInit();
	}

	@Override
	public String validateSuperAdmin(String userName, String password) {
		System.out.println("username:"+userName+ "pass: "+password);
		if((userName.compareTo(Configuration.superAdminName)==0) &&
				(password.compareTo(Configuration.superAdminPassword)==0))
			return Configuration.SUCCESS+" 0";
		else
		return Configuration.FAIL+" error : user not superAdmin";
	}

	@Override
	public String createForum(String forumName, String policyName,
			String securityName) {
		if(this.forumSystem.createForum(forumName, new Policy(), new Security()))
			return Configuration.SUCCESS+" created a new forum successfull";
		return Configuration.FAIL+" error acurred during forum creation";
	}

	
	@Override
	public Vector<String> DisplayForums() {
		return this.forumSystem.displayForums();
	}

	@Override
	public Vector<String> getSubForums(String currentForum) {
		Vector<SubForum> subforums=this.forumSystem.getForum(currentForum).getSubForums();
		Vector<String> res = new Vector<String>();
		for(SubForum sf:subforums){
			res.add(sf.getSubject());
		}
		return res;
	}

	@Override
	public String registerToForum(String currentForum, String userName,
			String password, String Email, String remainderQues,
			String remainderAns) {
		QuestionAnswerPair questionAnswerPair=new QuestionAnswerPair(remainderQues, remainderAns);
		return this.forumSystem.getForum(currentForum).regiserToForum(userName, password, Email, questionAnswerPair);
	}

	@Override
	public String createSubForum(String ForumName, String subForumName,
			String subject, String moderatorUserName, String userName) {
		SubForum newSubforum= new SubForum(this.forumSystem.getForum(ForumName), subForumName, subject);
		boolean flag=newSubforum.addModerator(userName, moderatorUserName);
		if(!flag)
			return Configuration.FAIL+" error : requested moderator is not quelafied";
		this.forumSystem.getForum(ForumName).addSubForum(newSubforum);
		return Configuration.SUCCESS+" subforum created successfuly";
		
	}

	@Override
	public String logout(String currentForum, String userName) {
		return this.forumSystem.getForum(currentForum).logout(userName);
	}

	@Override
	public String login(String currentForum, String userName, String password) {
		return this.forumSystem.getForum(currentForum).login(userName,password);
	}

	@Override
	public String deleteSubForum(String forumName, String subForumName) {
		return this.forumSystem.getForum(forumName).deleteSubforum(subForumName);
	}

	@Override
	public Vector<String> getThreads(String currentForum, String currentSubForum) {
		return this.forumSystem.getForum(currentForum).showSubForum(currentSubForum);
	}

	@Override
	public String publishNewThread(String currentForum,
			String currentSubForum, String threadTitle, String threadContent,
			String userName, String password) {
		Member mem= this.forumSystem.getForum(currentForum).getMember(userName);
		OpeningPost newPost =this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).postOpeningPost(mem, threadTitle, threadContent);
		if(newPost!=null)
			return Configuration.SUCCESS+" new post published successfuly";
		return Configuration.FAIL+" error : could not publish post";
	}

	@Override
	public String deleteThread(String threadName, String subForum,
			String Forum, String userName, String password) {
		Member mem= this.forumSystem.getForum(Forum).getMember(userName);
		boolean flag= this.forumSystem.getForum(Forum).getSubForumByName(subForum).deleteThread(mem, threadName);
		if(flag)
			return Configuration.SUCCESS+" thread dleted succssfuly";
		return Configuration.FAIL+" error : could not delete thread";
	}

	@Override
	public String fileComplaint(String currentForum, String currentSubForum,
			String moderatorUsername, String complaint, String userName,
			String password) {
		return this.forumSystem.getForum(currentForum).postModeratorComplaint(userName, moderatorUsername, currentSubForum, complaint);
	}

	@Override
	public String deleteThreadResponse(String responseNumber,
			String currentThreadTitle, String currentForum,
			String currentSubForum, String userName, String password) {
		Member mem= this.forumSystem.getForum(currentForum).getMember(userName);
		return this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).deletePost(mem, responseNumber);
		// TODO mixup between client imp and server imp; need to make adjustments;
	}

	@Override
	public String postThreadResponse(String responseContent,
			String currentThreadTitle, String currentForum,
			String currentSubForum, String userName,
			String title) {
		Member mem= this.forumSystem.getForum(currentForum).getMember(userName);
		return this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).postFollowPost(currentThreadTitle, mem, currentThreadTitle, responseContent);
	}

	@Override
	public Vector<String> getThreadResponses(String currentForum,
			String currentSubForum, String currentThreadTitle) {
		return this.forumSystem.getForum(currentForum).getSubForumByName(currentSubForum).getThread(currentThreadTitle).getFollowPostsStringList();
	}

	@Override
	public String getThreadContent(String currentThreadTitle,
			String currentSubForum, String forumName) {
		return this.forumSystem.getForum(forumName).getSubForumByName(currentSubForum).getThread(currentThreadTitle).getContent();
	}

	@Override
	public String enterNewProperty(String forumName, String property,
			String newValue, String userName, String password) {
		//TODO
		return"";
	}

	@Override
	public String deleteForum(String forumName) {
		//TODO
		return "";
	}

	@Override
	public Vector<String> getAllAdminsInForum(String forumName) {
		return this.forumSystem.getForum(forumName).getAdmins();
	}

	@Override
	public Vector<String> getAllModerators(String forumName, String subForumName) {
		return this.forumSystem.getForum(forumName).getSubForumByName(subForumName).getModeratorsStringList();
	}

	@Override
	public boolean isMember(String userName,String forumName) {
		Member mem = this.forumSystem.getForum(forumName).getMember(userName);
		return (mem!=null);
	}

	@Override
	public boolean isAdmin(String userName,String forumName) {
		return this.forumSystem.getForum(forumName).getAdmins().contains(userName);
	}

	@Override
	public boolean isModerator(String userName, String forumName, String SubforumName) {
		return this.forumSystem.getForum(forumName).getSubForumByName(SubforumName).getModeratorsStringList().contains(userName);	}

	@Override
	public String addAdminToForum(String forumName, String adminName) {
		this.forumSystem.getForum(forumName).addAdmin(adminName);
		if(isAdmin(adminName, forumName))
			return Configuration.SUCCESS+" admin "+adminName+" was successfuly added";
		return Configuration.FAIL+" error : could not add admin";
	}

	@Override
	public String removeAdminFromForum(String forumName, String adminName) {
		return this.forumSystem.getForum(forumName).deleteAdmin(adminName);
	}
	

	
}






















































