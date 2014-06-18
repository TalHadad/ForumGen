package Server.Forum;
import java.util.Date;
import java.util.Vector;

import Server.Users.*;
import Server.Posts.*;
public class Security {
	
	private Vector<UserSecurity> security;
	//private SecurityHandler securityHandler;

	public Security() {
		this.security = new Vector<UserSecurity>();
		//this.securityHandler = new SecurityHandler();
	}
	
	//DB - SECURITY
	public void addUserSecurity (String memberName,String forumName, String password,QuestionAnswerPair questionAnswerPair){
		UserSecurity userSecurity= new UserSecurity(memberName,forumName,password,questionAnswerPair);
		//this.securityHandler.addSecurity(memberName);
		this.security.add(userSecurity);
	}

	public boolean isPasswordExpired(String userName, int i) {
		for (UserSecurity userSecurity: this.security){
			if (userSecurity.getMemberName().compareTo(userName)==0)
				return (userSecurity.isPasswordExpaired(i));
		}
		return true;
	}

	public boolean validatePassword(String user,String pass){
		for (UserSecurity userSecurity: this.security){
			if (userSecurity.getMemberName().compareTo(user)==0)
				return userSecurity.validatePassword(pass);
		}
		return false;
	}
}
