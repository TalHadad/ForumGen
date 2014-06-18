package Server.Forum;

import Server.Users.*;
import java.util.Vector;

public class ForumReport {
	
	private String ForumName;
	private Vector<String> MembersNames = new Vector<String>() ;

	public ForumReport(Forum forum) {
		this.ForumName=forum.getForumName();
		for(Member mem:forum.getMembers()){
			this.MembersNames.add(mem.getName());
		}
	}

	public String getForumName() {
		return ForumName;
	}

	public void setForumName(String forumName) {
		ForumName = forumName;
	}

	public Vector<String> getMembersNames() {
		return MembersNames;
	}

	public void setMembersNames(Vector<String> membersNames) {
		MembersNames = membersNames;
	}

}
