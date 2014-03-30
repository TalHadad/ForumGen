import java.util.Vector;


public class Forum{
	
	public static final int CONNECTED = 1;
	public static final int DISCONNECTED = 2;
	public static final int PENDING = 3;
	
	private String name;
	private Administrator admin;
	private Policy policy;
	private Security security;
	private Vector<SubForum> subForums;
	private Vector<Administrator> admins;
	private Vector<Member> members;
	
	public Forum(String name, Administrator admin, Policy policy, Security security){
		this.name=name;
		this.admin = admin;
		this.policy =policy;
		this.security = security;
		this.subForums=new Vector<SubForum>();
		this.admins=new Vector<Administrator>();
	}

	public String getForumName(){
		return this.name;
	}
	//uc6
	public String regiserToForum(String userName, String userPassword, String firstName, String lastName, String eMail){
		// when guest becomes member
		if (isUserExist(userName))
			return "This userName is already used.. \n choose a COOLER userName please.";
		Member member=new Member(userName, userPassword);
		member.setStatus(CONNECTED);
		if (!hasEmailConfirmation()){
			this.members.add(member);
			return "Registration completed successfully";
		}
		member.setStatus(PENDING);
		return "waiting for eMail confirmation.";
	}
	//uc6
	private boolean hasEmailConfirmation() {
		return policy.hasEmailConfirmation();
}
	//uc6
	private boolean isUserExist(String userName) {
		for (int i = 0; i < members.size(); i++)
			if (members.elementAt(i).getName().compareTo(userName) == 0)
				return true;
		return false;
	}
	//uc7
	public String login(String userName, String password){
		Member m;
		if (isValidLoginData(userName,password)){
			if ((m=findUser(userName))!=null){
				if (m.getStatus()==CONNECTED)
					return "You are already logged in.";
				else if (m.getStatus()==PENDING)
					return "You are not registerd";
				else {
					m.setStatus(CONNECTED);
					return "Great success, you are logged in!";
				}
					
			}
			return "We are still waiting for your eMail confirmation.";
		}
		return "Wrong details inserted. There is no such user.";
	}
	//uc7
	private boolean isValidLoginData(String userName, String password) {
		// TODO Auto-generated method stub
		return true;
	}
	//uc7
	private Member findUser(String userName) {
		for (int i = 0; i < members.size(); i++)
			if (members.elementAt(i).getName().compareTo(userName) == 0)
				return members.elementAt(i);
		return null;
	}
	//uc8
	public String logout(String userName, String password){
		Member m;
		if ((m=findUser(userName))!=null){
			if (isConnected(m)){
				m.setStatus(DISCONNECTED);
				return "You have logged out successfully";
			}
			else if (m.getStatus()==PENDING)
				return "Error.. how did you get here?! still waiting for your eMail confirmation.";
			else
				return "Can't get disconnected, you are not even logged in.";
		}
		return "You are not registerd";
	}
	//uc8
	private boolean isConnected(Member m) {
		// TODO Auto-generated method stub
		return m.getStatus()==CONNECTED;
	}
	//uc8
	public String createSubForum(String name,String subject,Moderator moderator){
		if (inUse(name))
			//invalid creation data message
			return "error: subject in use";
		else{
			initializeSubForum(name,subject,moderator);
			return "OK";
		}
	}
	private Boolean enterForum(Guest guest){
		guest.setForum(this);
		return true;
	}
	
	//uc 3
	public String editProperty(Administrator admin,String property, Object newVal){
		//put here the logic property
		if (isvalidRequest(newVal)){
			//(see uc set forum property no 3)that is new val? maybe policy?
			this.policy=(Policy)newVal;
			if (noAdditionalChange(admin))
				return "Propertety edited successfully";
			else
				return "Please require additional changes (what ever that means)";
		}
		return "error in edit property: new val is not valid";
	}
	//uc 3
	private boolean noAdditionalChange(Administrator admin) {
		return admin.hasAdditionalChange();
	}
	//uc 3
	//need to remove if following by "remove middel method" refactor
	private boolean isvalidRequest(Object newVal){
		return validateNewVal(newVal);
	}
	//uc 3
	private boolean validateNewVal(Object newVal) {
		//that is new val?
		return true;
	}
	//uc 9
	private void initializeSubForum(String name, String subject,Moderator moderator) {
		SubForum subForum = new SubForum(name,subject);
		subForum.addMederators(moderator);
		this.subForums.add(subForum);
		return;
	}
	//uc 9
	private boolean inUse(String name) {
		for (SubForum subForum: this.subForums)
			if (subForum.getName().compareTo(name)==0)
				return true;
		return false;
	}
	//uc 9
	public Vector<String> showSubForumList(){
		Vector<String> ans=new Vector<String>();
		for(SubForum subForum:subForums)
			ans.add(subForum.getName()+"-"+subForum.getSubject());
		return ans;
	}
	//11
	public Vector<String> showSubForum(String subForumName){
		for (SubForum subForum:subForums){
			if (subForum.getName().equals(subForumName))
				return subForum.getOpeningPostsStringList();
		}
		return null;
	}
	
}
