import java.util.Vector;


public class ForumGen {
	
	private Vector<Member> members;
	private Vector<Forum> forums;
	
	private SuperAdmin superAdmin; 
	
	private Security security;
	private Logger logger;
	
//	private static Boolean isCreated=false; 
	
	
//	public static ForumGen initialzeSystem(String superAdminName,String superAdminPassword){
//		if (!isCreated){
//			isCreated=true;
//			ForumGen(superAdminName,superAdminPassword);
//		}
//		return null;
//	}
	
	public ForumGen(String superAdminName,String superAdminPassword) {
		this.superAdmin = new SuperAdmin(superAdminName,superAdminPassword);
		this.members=new Vector<Member>();
		this.forums = new Vector<Forum>();
		this.logger = new Logger();
	}
	
	//when creating a forum the super administrator is the admin because there are no members in, yet..
	//after adding some members he will be able to advance a member to a admin mode.
	public Boolean createForum(String forumName, String policy, String security){
		
		Forum fo = new Forum(forumName, superAdmin, policy, security);
		forums.add(fo);
		members.add(0, superAdmin);		
		
		return true;	// created a new forum successfully (need to wrap with try & catch)
	}
	
	public Vector<String> displayForums(){
		Vector<String> forumNames = new Vector<String>();
		for (int i = 0; i < forums.size(); i++){
			forumNames.add(i, forums.elementAt(i).getForumName());
		}
		
		return forumNames;
	}
	
	public Forum getForum(String name){
		
		Forum ans = null;
		
		for (int i = 0; i < forums.size(); i++){
			if (forums.elementAt(i).getForumName().compareTo(name) == 0){
				ans = forums.elementAt(i);
				break;
			}
		}
		
		return ans;
	}
	
	
	public void getSuperAdmin(){
		//TODO
	}
	
	
	public void deleteUser(String userName){
		//TODO
	}
	public void isUserExist(String userName,String userPassword){
		//when log in occur
		//TODO
	}
	public void getMember(String memberName){
		//when posting a new post and need to attach the member to the post  
		//TODO
	}

	
}
