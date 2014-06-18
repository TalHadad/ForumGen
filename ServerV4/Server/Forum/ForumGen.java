package Server.Forum;
import java.util.Vector;

import Server.Users.*;
import Configuration.*;
import DataBase.ForumsHandler;
import Server.Posts.*;

public class ForumGen {
	
	private Vector<Forum> forums;
	private Member superAdmin;
	private Logger logger;
	private ForumsHandler forumsHandler;
	public static int counter = 0;
	
	
	//DB - ForumsHandler
	public ForumGen() {
		ForumsHandler fh = new ForumsHandler();
		this.forums = fh.getForums();//new Vector<Forum>();
		this.superAdmin = new Member(Configuration.superAdminName);
		this.superAdmin.setStatus(Configuration.CONNECTED);
		this.logger = new Logger();
		this.forumsHandler = fh;
	}
	
	//when creating a forum the super administrator is the admin because there are no members in, yet..
	//after adding some members he will be able to advance a member to a admin mode.
	//DB - ForumsHandler
	public Forum createForum(String forumName, Policy policy){
		if(forumName == null) return null;
		Vector<Member> members = new Vector<Member>();
		members.add(superAdmin);
		Forum forum = new Forum(forumName, null, members, policy, null, new Security(),null);
		this.forums.add(forum);
		System.out.println("forums size; "+this.forums.size());
		forum.addAdmin(Configuration.superAdminName);
		this.forumsHandler.addForum(forumName, policy.hasEmailConfirmation(), policy.getExtendedDeletionPolicy(), policy.getMinSeniorityMonths(), policy.getMinPostForModerator(), policy.getOnlyApointAdministratorCanRemoveModerators(), policy.getCanRemoveSingleModerators(), policy.getMonthsPasswordValidFor());
		return forum;	// created a new forum successfully (need to wrap with try & catch)
	}
	
	public Vector<String> displayForums(){
		Vector<String> forumNames = new Vector<String>();
		for (int i = 0; i < forums.size(); i++){
			forumNames.add(i, forums.elementAt(i).getForumName());
		}
		
		return forumNames;
	}
	
	public Forum getForum(String name){
		Forum ans = null;
		for (int i = 0; i < forums.size(); i++){
			if (forums.elementAt(i).getForumName().compareTo(name) == 0){
				ans = forums.elementAt(i);
				break;
			}
		}
		return ans;
	}
	
	public Vector<Forum> getForums() {
		return forums;
	}

	public void setForums(Vector<Forum> forums) {
		this.forums = forums;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public int getTotalForumsNum(){
		return this.forums.size();
	}
	
	public Vector<ForumReport> getForumsReport(){
		Vector<ForumReport> res =new Vector<ForumReport>();
		for(Forum forum:this.forums){
			res.add(new ForumReport(forum));
			
		}
		return res;
		
	}

	public String getSuperAdminPassword() {
		return Configuration.superAdminPassword;
	}

	public String getSuperAdminName() {
		return Configuration.superAdminName;
	}

//	public String getIsInit() {
//		if(this.isInit == true)
//			return Configuration.SUCCESS;
//		return Configuration.FAIL;
//	}

	public int getNumOfForums() {
		return this.forums.size();
	}

	//DB - ForumsHandler
	public boolean deleteForum(String forumName) {
		for (Forum forum: this.forums)
			if (forum.getName().compareTo(forumName)==0){
				this.forums.remove(forum);
				this.forumsHandler.deleteForum(forumName);
				return true;
			}
		return false;
	}


	
}
