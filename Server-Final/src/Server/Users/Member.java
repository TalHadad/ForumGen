package Server.Users;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Vector;

import Server.Forum.*;
import Server.Posts.*;

public class Member{
	
	
	private Vector<AbstractPost> posts;
	private Integer status = 0;						// public field !!	1- logged in ; 2- logged out ; 3- pending
	private String type="";
	private String name;
	private Vector<String> friends;
	private Forum forum;
	private Boolean hasAdditionalChangeFlage;//uc 3
	private Date joinDate;

	
	public Member(String name,Forum forum) {
		this.name=name;
		this.friends = new  Vector<String>();
		this.posts = new Vector<AbstractPost>();
		this.forum = forum;
		this.hasAdditionalChangeFlage=false;
		this.joinDate=new Date();

	}
	public int getSeniorityMonths(){
		Date date = new Date();
		return (this.joinDate.getMonth()-(date.getMonth())) + 12*(this.joinDate.getYear()-date.getYear());
	}
	//uc 3
	public boolean hasAdditionalChange() {
		return this.hasAdditionalChangeFlage;
	}
	public void setForum(Forum forum) {
		this.forum = forum;
		
	}
	//uc6
	public String getName(){
		return this.name;
	}

	//uc6
	public void setStatus(int status) {
		this.status=status;
	}
	//uc7
	public int getStatus() {
		return this.status;
	}
	//uc10
	public  Vector<String> getFriends() {
		return this.friends;
	}
	
	public void notifyMember(String string) {
		// TODO Auto-generated method stub
		
	}
	
	public String getType() {
		return this.type;
	}
	
	public boolean setType(String type){
		this.type=type;
		return true;
	}
	public boolean isPending() {
		if (this.status == 3)
			return true;
		return false;
	}
	public boolean removeFriend(String friendUserName) {
		for (int i = 0; i < this.friends.size(); i++)
			if (this.friends.elementAt(i).equals(friendUserName)){
				this.friends.remove(i);
				return true;
			}
		return false;
	}
	
	// may need to check if the member approves a friend request
	public boolean addFriend(String friendName) {
		this.friends.add(friendName);
		return true;
	}

}
