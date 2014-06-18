package Server.Forum;
import java.util.Date;
import java.util.Vector;

import DataBase.SecurityHandler;
import Server.Users.*;
import Server.Posts.*;


public class UserSecurity {
	private String memberName;
	private String forumName;
	private String password;
	private QuestionAnswerPair questionAnswerPair;
	private Vector<String> usedPasswords;
	private Date passwordDate;
	private SecurityHandler securityHandler;
	
	public UserSecurity(String memberName,String forumName, String password,
			QuestionAnswerPair questionAnswerPair) {
		this.memberName = memberName;
		this.forumName = forumName;
		this.password = password;
		this.questionAnswerPair = questionAnswerPair;
		this.usedPasswords = new Vector<String>();
		this.usedPasswords.add(password);
		this.passwordDate = new Date();
		this.securityHandler = new SecurityHandler();
	}
	


	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPassword() {
		return password;
	}

	//DB SecurityHandler
	public String setPassword(String password) {
		for (String pass: this.usedPasswords)
			if (pass.compareTo(password)==0){
				return "Password already used, please try again.";
			}
		this.password = password;
		this.passwordDate = new Date();
		this.usedPasswords.add(password);
		this.securityHandler.setPassword(this.memberName,this.forumName,password);
		return "Password was change seccessfuly.";
	}
	
	public boolean isPasswordExpaired(int daysForPassBeforeExpires) {
		Date date = new Date();
		int daysNotChanged=365*(this.passwordDate.getYear()-date.getYear())+
				12*(this.passwordDate.getMonth()-date.getMonth())+
				this.passwordDate.getDay()-date.getDay();
		return daysNotChanged>=daysForPassBeforeExpires;

	}
	public boolean validatePassword(String pass){
		return (pass.compareTo(this.password)==0);
	}

	

}
