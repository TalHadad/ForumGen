package Server.Forum;
import java.util.Vector;

import Server.Users.*;
import Configuration.*;
import Server.Posts.*;

public class ForumGen {
	
	private Vector<Forum> forums;
	private Logger logger;
	private Member superAdmin;
	
	public ForumGen() {
		this.forums = new Vector<Forum>();
		this.logger = new Logger();
	}
	
	//when creating a forum the super administrator is the admin because there are no members in, yet..
	//after adding some members he will be able to advance a member to a admin mode.
	public Forum createForum(String forumName, Policy policy){
		if(forumName == null) return null;
		Forum forum = new Forum(forumName, null, null, policy, null, null,null);
		
		this.forums.add(forum);
		System.out.println("forums size; "+this.forums.size());
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


	public Forum getForum(Forum forum) {
		for (int i = 0; i < this.forums.size(); i++)
			if (this.forums.elementAt(i).getForumName().equals(forum))
				return this.forums.elementAt(i);
		return null;
	}

	public boolean deleteForum(String forumName) {
		for (Forum forum: this.forums)
			if (forum.getName().compareTo(forumName)==0){
				this.forums.remove(forum);
				return true;
			}
		return false;
	}


	
}
