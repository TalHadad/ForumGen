package Forum;
import java.util.Date;
import java.util.Vector;

import Users.*;
import Posts.*;
public class UserSecurity {
	private String memberName;
	private String password;
	private QuestionAnswerPair questionAnswerPair;
	private Vector<String> usedPasswords;
	private Date passwordDate;
	
	public UserSecurity(String memberName, String password,
			QuestionAnswerPair questionAnswerPair) {
		this.memberName = memberName;
		this.password = password;
		this.questionAnswerPair = questionAnswerPair;
		this.usedPasswords = new Vector<String>();
		this.usedPasswords.add(password);
		this.passwordDate = new Date();
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

	public String setPassword(String password) {
		for (String pass: this.usedPasswords)
			if (pass.compareTo(password)==0){
				return "Password already used, please try again.";
			}
		this.password = password;
		this.passwordDate = new Date();
		this.usedPasswords.add(password);
		return "Password was change seccessfuly.";
	}
	
	public boolean isPasswordExpaired(Date policyDate) {
		Date date=new Date();
		int daysNotChanged=365*(this.passwordDate.getYear()-date.getYear())+
				12*(this.passwordDate.getMonth()-date.getMonth())+
				this.passwordDate.getDay()-date.getDay();
		int daysPolicy=365*(policyDate.getYear())+12*(policyDate.getMonth())+policyDate.getDay()-date.getDay();
		return daysNotChanged>=daysPolicy;
		
	}

	

}
