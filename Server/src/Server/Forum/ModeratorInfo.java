package Server.Forum;

import java.util.Date;

public class ModeratorInfo {
	
	private String administratorName;
	private String moderatorName;
	private Date apointDate;
	
	
	
	public ModeratorInfo(String administratorName,
			String moderatorName) {
		this.administratorName = administratorName;
		this.moderatorName = moderatorName;
		this.apointDate=new Date();
		
	}
	public String getAdministratorName() {
		return administratorName;
	}
	public void setAdministratorName(String administratorName) {
		this.administratorName = administratorName;
	}
	public String getModeratorName() {
		return moderatorName;
	}
	public void setModeratorName(String moderatorName) {
		this.moderatorName = moderatorName;
	}
	public Date getApointdate(){
		return this.apointDate;
	}

	

}
