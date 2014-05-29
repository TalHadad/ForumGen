package Forum;
import java.util.Date;
import java.util.Vector;

import Users.*;
import Posts.*;
public class Security {
	
	private Vector<UserSecurity> security;

	public Security() {
		this.security = new Vector<UserSecurity>();
	}
	
	public void addUserSecurity (String memberName,String password,QuestionAnswerPair questionAnswerPair){
		UserSecurity userSecurity= new UserSecurity(memberName,password,questionAnswerPair);
	}

	public boolean isPasswordExpaired(String userName,Date expirationDateForPassword) {
		for (UserSecurity userSecurity: this.security){
			if (userSecurity.getMemberName().compareTo(userName)==0)
				return (userSecurity.isPasswordExpaired(expirationDateForPassword));
		}
		return true;
	}
	


}
