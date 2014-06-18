package Server.Forum;

import java.util.Vector;

public class ModeratorsReport {
	private ModeratorInfo info;
	private String SubforumSubject;
	private Vector<String> PostsTitles;
	
	public ModeratorsReport(ModeratorInfo info, String subforumSubject,Vector<String> Posts){
		this.info=info;
		this.SubforumSubject=subforumSubject;
		this.PostsTitles=Posts;
	}

	public ModeratorInfo getInfo() {
		return info;
	}

	public void setInfo(ModeratorInfo info) {
		this.info = info;
	}

	public String getSubforumSubject() {
		return SubforumSubject;
	}

	public void setSubforumSubject(String subforumSubject) {
		SubforumSubject = subforumSubject;
	}

	public Vector<String> getPostsTitles() {
		return PostsTitles;
	}

	public void setPostsTitles(Vector<String> postsTitles) {
		PostsTitles = postsTitles;
	}
	
	
	
}
