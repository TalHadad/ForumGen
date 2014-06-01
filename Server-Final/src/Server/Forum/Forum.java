package Server.Forum;

import java.awt.image.ConvolveOp;
import java.util.Vector;

import Server.Users.*;
import Configuration.Configuration;
import Server.Posts.*;

public class Forum {

	private Configuration configuration;
	private String name;
	private Policy policy;
	private Security security;
	private Vector<SubForum> subForums;
	private Vector<Member> members;
	private Vector<String> admins;
	private Vector<String> statuses;
	
	public Forum(String name,Policy policy,Security security){
		this.name = name;
		this.subForums = new Vector<SubForum>();
		this.members = new Vector<Member>();
		this.policy = policy;
		this.admins = new Vector<String>();
		this.statuses = new Vector<String>();
		this.security= security;
		this.configuration = new Configuration();
	}
	
	public Forum(String name, Vector<SubForum> subForums,Vector<Member> members,
			Policy policy,Vector<String> admins,Security security,Vector<String> statuses) {
		super();
		this.name = name;
		this.subForums = subForums;
		this.members = new Vector<Member>();
		this.policy = policy;
		this.admins = new Vector<String>();
		this.security= security;
		this.statuses = statuses;
	}
	///////////////////   getters setters
	public void addSubForum(SubForum sub){subForums.add(sub);}
	public void addMember(Member mem){members.add(mem);}
	public boolean addAdmin(String member){
		if (admins==null)
			this.admins = new Vector<String>();
		admins.add(member); return true;}
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public Vector<String> getSubForums() {
		Vector<String> subForums = new Vector<String>();
		if ( this.subForums == null) 
			return subForums;
		for (SubForum subForum : this.subForums)
			subForums.add(subForum.getName());
		return subForums;
	}
	public void setSubForums(Vector<SubForum> subForums) {this.subForums = subForums;}
	public Vector<Member> getMembers() {return members;}
	public void setMembers(Vector<Member> members) {this.members = members;}
	public Vector<String> getAdmins() {return admins;}
	public void setAdmins(Vector<String> admins) {this.admins = admins;}
	public Policy getPolicy() {return policy;}
	public void setPolicy(Policy policy) {this.policy = policy;}
	
	public Vector<String> showSubForumsList() {
		Vector<String> ans = new Vector<String>();
		for (SubForum subForum: this.subForums)
			ans.add(subForum.getName());
		return ans;
	}

	public String getForumName(){
		return this.name;
	}
	//uc6String memberName, String password,
	public Member regiserToForum(String userName, String userPassword, String eMail,QuestionAnswerPair questionAnswerPair){
		// when guest becomes member
		if (isUserExist(userName))
			return null;
		Member member=new Member(userName,this);
		this.security.addUserSecurity(userName, userPassword, questionAnswerPair);
		member.setStatus(Configuration.CONNECTED);
		if (!hasEmailConfirmation()){
			this.members.add(member);
			return member;
		}
		member.setStatus(Configuration.PENDING);
		return member;
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
	public Member login(String userName, String password){
		Member m;
		if (isValidLoginData(userName,password)){
			if ((m=findUser(userName))!=null){
				if (m.getStatus()==Configuration.CONNECTED)
					return null;
					//return "You are already logged in.";
				else if (m.getStatus()==Configuration.PENDING)
					return null;
					//return "You are not registerd";
				else {
					if (this.security.isPasswordExpaired(userName,this.policy.getExpirationDateForPassword())){
						m.setStatus(Configuration.CONNECTED);
						return m;
						//return "Great success, you are logged in!";
					}
					else
						return m;
						//return "Please change the password.";
				}
					
			}
			return m;
			//return "We are still waiting for your eMail confirmation.";
		}
		return null;
		//return "Wrong details inserted. There is no such user.";
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
	public boolean logout(String userName){
		Member m;
		if ((m=findUser(userName))!=null){
			if (isConnected(m)){
				m.setStatus(Configuration.DISCONNECTED);
				return true;
				//return "You have logged out successfully";
		}
			else if (m.getStatus()==Configuration.PENDING)
				return false;
				//return "Error.. how did you get here?! still waiting for your eMail confirmation.";
			else
				return false;
				//return "Can't get disconnected, you are not even logged in.";
		}
		return false;
		//return "You are not registerd";
	}
	//uc8
	private boolean isConnected(Member m) {
		// TODO Auto-generated method stub
		return m.getStatus()==Configuration.CONNECTED;
	}
	//uc8
	public String createSubForum(String name,String subject){
		if (inUse(name))
			//invalid creation data message
			return "error: subject in use";
		else{
			initializeSubForum(name,subject);
			return "OK";
		}
	}
	private Boolean enterForum(Member member){
		member.setForum(this);
		return true;
	}
	
	//uc 3
	public String editProperty(Member admin,String property, Object newVal){
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
	private boolean noAdditionalChange(Member admin) {
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
	private void initializeSubForum(String name, String subject) {
		SubForum subForum = new SubForum(this, name,subject);
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

	//10
	public void NotifyFriends(Member member,String string) {
		Vector<String> friends = member.getFriends();
		for (Member m: this.members)
			if (friends.contains(m.getName()))
				m.notifyMember(string);
	}
	public boolean addType(String type){
		this.statuses.add(type);
		return true;
		//return "Added new user type "+type+" successfuly.";
	}
	
	public String deleteType(String type){
		if (statusExist(type)){
			for (Member mem:this.members){
				if(mem.getType().compareTo(type)==0)
					mem.setType("");
			}
			return "user type "+type+" removed successfuly";
		}
		return "No such user type.";
	}

	private boolean statusExist(String status) {
		for (String s: this.statuses)
			if (status.compareTo(s)==0)
				return true;
		return false;
	}
	

	
	public Vector<String> getUserTypes(){
		return this.statuses;
		
	}
	
	public String postModeratorComplaint(String writerName, String Mod,String subject, String complaint){
		if(complaint.compareTo("")==0){
			return "error: empty content";
		}
		SubForum s_forum=getSubForum(subject);
		
		if(s_forum!= null && s_forum.havePosted(writerName)){
			for (String admin:this.admins){
				Member adm=getMember(admin);
				if(adm != null)
					adm.notifyMember(complaint);
				
		}
		}
		
		return "not enough posted msgs in sub forum "+subject+" !";
	}

	public SubForum getSubForum(String subject) {
		if(subForums == null) return null;
		for (SubForum sub:this.subForums)
			if (sub.getSubject().compareTo(subject)==0)
				return sub;
		return null;
	}
	
	public Member getMember (String memberName){
		if(members == null) return null;
		for (Member member:this.members)
			if (memberName.compareTo(member.getName())==0)
				return member;
		return null;
	}
	
	//UC 16
	public boolean deleteSubforum(String subject){
		SubForum subForum=getSubForum(subject);
		if(subForum!=null){
			this.subForums.remove(subForum);
			notifyWatchingMembers();
			return true;
			//return"Sub forum, "+subject+" deleted successfuly";
		}
		else
			return false;
			//return "error : sub forum not found";
	}

	private void notifyWatchingMembers() {
		// TODO notify all watching on deleted subforum +return to forum screen
		
	}

	public void notifyAllMembers(String string) {
		// TODO Auto-generated method stub
		
	}

	public  Vector<String> getAllOfMemberPosts(String member){
		Vector<String> res=new Vector<String>();
		for(SubForum sf:this.subForums){
			res.addAll(sf.getMemberPosts(member));
		}
		return res;
	}
	
	public Vector<ModeratorsReport>  getModeratorsInfoList(){
		Vector<ModeratorsReport> res = new Vector<ModeratorsReport>();
		for (SubForum sf:this.subForums){
			res.addAll(sf.getModeratorsReport());
		}
		return res;
		}
	public SubForum getSubForumByName(String SubforumName) {
		if(subForums == null) return null;
		for (SubForum sub:this.subForums)
			if (sub.getName().compareTo(SubforumName)==0)
				return sub;
		return null;
	}
	public String deleteAdmin(String adminName){
		boolean flag=this.admins.remove(adminName);
		if(flag)
			return "admin "+adminName+" was successfuly removed from forum "+getForumName();
		return "error : cold not remove admin, admin not found";
	}

	public boolean removeAdmin(String adminName) {
		// TODO Auto-generated method stub
		this.admins.remove(adminName);
		return true;
	}

	public boolean validateEmailCode(String userName, String code) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean banMember(String memberToBanName) {
		// TODO Auto-generated method stub
		for (Member member: this.members)
			if (memberToBanName.compareTo(member.getName())==0){
				this.members.remove(member);
				return true;
			}
		return false;
	}

	public boolean replaceAdmin(String userName, String newAdminUserName) {
		this.admins.remove(userName);
		this.admins.add(newAdminUserName);
		return true;
	}

	public Vector<String> getPostsOfMember(String memberName) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean setPolicy(String hasEmailPolicy,
			String extendedDeletionPolicy, String minPostForModerator,
			String minSeniorityMonths,
			String onlyApointAdministratorCanRemoveModerators,
			String canRemoveSingleModerators, String expirationDateForPassword,
			String interactiveNotifyingPolicys) {
		// TODO Auto-generated method stub
		return true;
	}

	public Vector<String> getMembersNames() {
		Vector<String> res=new Vector<String>();
		for(Member member:this.members){
			res.add(member.getName());
		}
		return res;
	}

	public boolean isMember(String userName) {
		for(Member member:this.members){
			if (userName.compareTo(member.getName())==0)
				return true;
		}
		return false;
	}

	public boolean isAdmin(String userName) {
		for(Member member:this.members){
			if (userName.compareTo(member.getName())==0)
				return true;
		}
		return false;
	}

	public Vector<String> getSubForumsForModerator(String moderatorUserName) {
		Vector<String> subForums = new Vector<String>();
		for (SubForum subForum: this.subForums)
			if (subForum.isModerator(moderatorUserName))
				subForums.add(subForum.getName());
		return subForums;
				
	}

	public Vector<String> getModerators() {
		Vector<String> moderators = new Vector<String>();
		for (SubForum subForum: this.subForums)
			moderators.addAll(subForum.getModerators());
		return moderators;
	}
	
}
