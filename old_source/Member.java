import java.util.Vector;


public class Member extends Guest{
	
	private String password;
	private Vector<AbstractPost> posts;
	private Integer status = 0;						// public field !!	1- logged in ; 2- logged out ; 3- pending
	
	public Member(String name, String password) {
		super(name);
		this.password = password;
		this.posts = new Vector<AbstractPost>();
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
	public void notifyFriendsAboutPost() {
		// TODO Auto-generated method stub
		
	}
	
	

}
