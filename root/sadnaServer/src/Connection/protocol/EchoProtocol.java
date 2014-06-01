package Connection.protocol;

import java.io.IOException;
import java.util.Vector;



import java.util.logging.Logger;

import Configuration.Configuration;
import Connection.tokenizer.*;

/**
 * this class represent the protocol:
 *  for every message return the right answer
 */
public class EchoProtocol implements AsyncServerProtocol<StringMessage> {
	private boolean _shouldClose = false;
	private boolean _connectionTerminated = false;
	private Vector<Client> _clientsVEC;//holds all the clients that is connected to the server
	private Vector<Channel> _channelsVEC;//holds all the Channel that is available
	private ServerHandler serverHandler= new ServerHandler();

	public EchoProtocol(){
		_shouldClose = false;
		set_connectionTerminated(true);
		_clientsVEC=new Vector<Client>();
		_channelsVEC = new Vector<Channel>();
		System.out.println("protocol created");
	}
	/**
	 * processes a message<BR>
	 * this simple interface prints the message to the screen, then composes a simple
	 * reply and sends it back to the client
	 *
	 * @param msg the message to process
	 * @return the reply that should be sent to the client, or null if no reply needed
	 */
	@Override
	public StringMessage processMessage(StringMessage clientMsg,Client client) {  
		String msg=clientMsg.getMessage();
		String ans = null;
		System.out.println("msg: " +msg.substring(0, msg.length()-1));
		_clientsVEC.add(client);//adding the client in to the vector that holds all the clients
		if (msg != null) {//for every message sending to the right method to process it 
			if (msg.startsWith(Configuration.DisplayForums)){ans =handle_DisplayForums(msg,client);}
			else if (msg.startsWith(Configuration.registerToForum)){ans =handle_registerToForum(msg,client);}
			else if (msg.startsWith(Configuration.loginSuper)){ans =handle_loginSuper(msg,client);}
			else if (msg.startsWith(Configuration.login)){ans =handle_login(msg,client);}
			else if (msg.startsWith(Configuration.logout)){ans =handle_logout(msg,client);}
			else if (msg.startsWith(Configuration.getSubForums)){ans =handle_getSubForums(msg,client);}
			else if (msg.startsWith(Configuration.getThreads)){ans =handle_getThreads(msg,client);}
			else if (msg.startsWith(Configuration.getResponseContent)){ans =handle_getResponseContent(msg,client);}
			else if (msg.startsWith(Configuration.getThreadResponses)){ans =handle_getThreadResponses(msg,client);}
			else if (msg.startsWith(Configuration.publishNewThread)){ans =handle_publishNewThread(msg,client);}
			else if (msg.startsWith(Configuration.postThreadResponse)){ans =handle_postThreadResponse(msg,client);}
			else if (msg.startsWith(Configuration.deleteThread)){ans =handle_deleteThread(msg,client);}
			else if (msg.startsWith(Configuration.deleteThreadResponse)){ans =handle_deleteThreadResponse(msg,client);}
			else if (msg.startsWith(Configuration.fileComplaint)){ans =handle_fileComplaint(msg,client);}
			else if (msg.startsWith(Configuration.getSubForumsForModerator)){ans =handle_getSubForumsForModerator(msg,client);}
			else if (msg.startsWith(Configuration.addAdminToForum)){ans =handle_addAdminToForum(msg,client);}
			else if (msg.startsWith(Configuration.removeAdminFromForum)){ans =handle_removeAdminFromForum(msg,client);}
			else if (msg.startsWith(Configuration.validateByEmail)){ans =handle_validateByEmail(msg,client);}
			else if (msg.startsWith(Configuration.addFriend)){ans =handle_addFriend(msg,client);}
			else if (msg.startsWith(Configuration.removeFriend)){ans =handle_removeFriend(msg,client);}
			else if (msg.startsWith(Configuration.responseToFreindRequest)){ans =handle_responseToFreindRequest(msg,client);}
			else if (msg.startsWith(Configuration.banMember)){ans =handle_banMember(msg,client);}
			else if (msg.startsWith(Configuration.editTread)){ans =handle_editTread(msg,client);}
			else if (msg.startsWith(Configuration.editResponse)){ans =handle_editResponse(msg,client);}
			else if (msg.startsWith(Configuration.upgradeToModerator)){ans =handle_upgradeToModerator(msg,client);}
			else if (msg.startsWith(Configuration.banModerator)){ans =handle_banModerator(msg,client);}
			else if (msg.startsWith(Configuration.removeModerator)){ans =handle_removeModerator(msg,client);}
			else if (msg.startsWith(Configuration.replaceAdmin)){ans =handle_replaceAdmin(msg,client);}
			else if (msg.startsWith(Configuration.replaceModerator)){ans =handle_replaceModerator(msg,client);}
			else if (msg.startsWith(Configuration.numOfPostsInSubForumReport)){ans =handle_numOfPostsInSubForumReport(msg,client);}
			else if (msg.startsWith(Configuration.postsOfMemberReport)){ans =handle_postsOfMemberReport(msg,client);}
			else if (msg.startsWith(Configuration.listOfModeratorsReport)){ans =handle_listOfModeratorsReport(msg,client);}
			else if (msg.startsWith(Configuration.createForum)){ans =handle_createForum(msg,client);}
			else if (msg.startsWith(Configuration.setPolicy)){ans =handle_setPolicy(msg,client);}
			else if (msg.startsWith(Configuration.numOfForumsReport)){ans =handle_numOfForumsReport(msg,client);}
			else if (msg.startsWith(Configuration.membersOfForum)){ans =handle_membersOfForum(msg,client);}
			else if (msg.startsWith(Configuration.createSubForum)){ans =handle_createSubForum(msg,client);}
			else if (msg.startsWith(Configuration.deleteForum)){ans =handle_deleteForum(msg,client);}
			else if (msg.startsWith(Configuration.deleteSubForum)){ans =handle_deleteSubForum(msg,client);}
			else if (msg.startsWith(Configuration.updateMemberType)){ans =handle_updateMemberType(msg,client);}
			else if (msg.startsWith(Configuration.createType)){ans =handle_createType(msg,client);}
			else if (msg.startsWith(Configuration.isMember)){ans =handle_isMember(msg,client);}
			else if (msg.startsWith(Configuration.isAdmin)){ans =handle_isAdmin(msg,client);}
			else if (msg.startsWith(Configuration.isModerator)){ans =handle_isModerator(msg,client);}
			else if (msg.startsWith(Configuration.initializeSystem)){ans =handle_initializeSystem(msg,client);}
			else if (msg.startsWith(Configuration.isInitialize)){ans =handle_isInitialize(msg,client);}
			else if (msg.startsWith(Configuration.validateSuperAdmin)){ans =handle_validateSuperAdmin(msg,client);}
			else if (msg.startsWith(Configuration.notifyMember)){ans =handle_notifyMember(msg,client);}
			else if (msg.startsWith(Configuration.notifyFreindRequest)){ans =handle_notifyFreindRequest(msg,client);}
			ans = Configuration.FAIL;
		}
		StringMessage SM=new StringMessage(ans);
		return SM;
	}

	/**
	 * detetmine whether the given message is the termination message
	 *
	 * @param msg the message to examine
	 * @return false - this simple protocol doesn't allow termination...
	 */
	@Override
	public boolean isEnd(StringMessage msg) {
		return msg.equals("bye");
	}

	/**
	 * Is the protocol in a closing state?.
	 * When a protocol is in a closing state, it's handler should write out all pending data, 
	 * and close the connection.
	 * @return true if the protocol is in closing state.
	 */
	@Override
	public boolean shouldClose() {
		return this._shouldClose;
	}

	/**
	 * Indicate to the protocol that the client disconnected.
	 */
	@Override
	public void connectionTerminated() {
		this.set_connectionTerminated(true);
	}
	/**
	 * a getter for _connectionTerminated
	 */
	public boolean get_connectionTerminated() {
		return _connectionTerminated;
	}
	/**
	 * a setter for _connectionTerminated
	 */
	public void set_connectionTerminated(boolean _connectionTerminated) {
		this._connectionTerminated = _connectionTerminated;
	}
	/**
	 * this method handle only LOGIN_SUPER messages.
	 * @param msg the message
	 * @param client the client that send the LOGIN_SUPER message
	 */

	///////////////////////////////////////////////////////////////////////
	/////////////// Messages handlers /////////////////////////////////////
	///////////////////////////////////////////////////////////////////////


	// <Conf.loginSuper> <DELIM1> <userName> <DELIM1> <pass>
	private String handle_loginSuper(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a LOGIN_SUPER, but not with 2 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1]; 
		String pass = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 

		return this.serverHandler.loginSuper(userName, pass);
	}
	
	// <Conf.registerToForum> <DELIM1> <currentForum> <DELIM1> <userName> <DELIM1> <password> <DELIM1> <Email> <DELIM1> <remainderQues> <DELIM1> <remainderAns>
	private String handle_registerToForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 7){
			System.out.println("Got a REGISTER, but not with 6 params");
			return Configuration.FAIL;
		}

		String currentForum= parsedMsg[1];
		String userName= parsedMsg[2];
		String password= parsedMsg[3];
		String Email= parsedMsg[4];
		String remainderQues= parsedMsg[5];
		String remainderAns= parsedMsg[6];
		return this.serverHandler.registerToForum(currentForum, userName, password, Email, remainderQues, remainderAns);
	}
	
	// <Conf.createForum> <DELIM1> <forumName> <DELIM1> <userName><DELIM1> <policy>
	private String handle_createForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 4){
			System.out.println("Got a FORUM_CREATE, but not with 3 params");
			return Configuration.FAIL;
		}

		String forumName=parsedMsg[1];
		String policyName=parsedMsg[2];
		String securityName=parsedMsg[3];

		return this.serverHandler.createForum(forumName, policyName, securityName);
	}
	
	// <Conf.deleteForum> <DELIM1> <forumName> 
	private String handle_deleteForum(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//<Conf.createSubForum> <DELIM1> <ForumName> <DELIM1> <subForumName><DELIM1> <subject> <DELIM1> <moderatorUserName> <DELIM1> <userName>
	private String handle_createSubForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 6){
			System.out.println("Got a SUBFORUM_CREATE, but not with 5 params");
			return Configuration.FAIL;
		}

		String ForumName=parsedMsg[1];
		String subForumName=parsedMsg[2];
		String subject=parsedMsg[3];
		String moderatorUserName=parsedMsg[4];
		String userName=parsedMsg[5];
		return this.serverHandler.createSubForum(ForumName, subForumName, subject, moderatorUserName, userName);
	}
	
	// <Conf.deleteSubForum> <DELIM1> <forumName> <DELIM1> <subForumName>	
	private String handle_deleteSubForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 3){
			System.out.println("Got a UBFORUM_REMOVE, but not with 2 params");
			return Configuration.FAIL;
		}

		String forumName=parsedMsg[1];
		String subForumName=parsedMsg[2];
		return this.serverHandler.deleteSubForum(forumName, subForumName);
	}
	
	// <Conf.logout> <DELIM1> <currentForum> <DELIM1> <userName>
	private String handle_logout(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 3){
			System.out.println("Got a LOGOUT_MEMBER, but not with 2 params");
			return Configuration.FAIL;
		}

		String forumName=parsedMsg[1];
		String userName=parsedMsg[2];
		return this.serverHandler.logout(forumName, userName);
	}
	
	// <Conf.login> <DELIM1> <currentForum> <DELIM1> <userName><DELIM1> <password>
	private String handle_login(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 4){
			System.out.println("Got a LOGIN_MEMBER, but not with 3 params");
			return Configuration.FAIL;
		}

		String currentForum=parsedMsg[1];
		String userName= parsedMsg[2];
		String password=parsedMsg[3].substring(0, parsedMsg[3].length()-1);
		return this.serverHandler.login(currentForum, userName, password);
	}
	
	// <Conf.DisplayForums> 
	private String handle_DisplayForums(String msg, Client client) {
		String ans=this.serverHandler.DisplayForums();
		String res="";
		for(char c=ans.charAt(0)){
			res+=" "+s;
		}
		System.out.println("RES "+res);
		return res;
	}
	
	// <Conf.handle_notifyFreindRequest> <DELIM1> <requesterUserName>
	private String handle_notifyFreindRequest(String msg, Client client) {

		return null;
	}
	
	// <Conf.notifyMember> <DELIM1> <text> 
	private String handle_notifyMember(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.validateSuperAdmin> <DELIM1> <userName> <DELIM1> <password>
	private String handle_validateSuperAdmin(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.isInitialize>
	private String handle_isInitialize(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.initializeSystem> <DELIM1> <userName> <DELIM1> <password>
	private String handle_initializeSystem(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	// <Conf.isModerator> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <SubforumName>
	private String handle_isModerator(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.isAdmin> <DELIM1> <userName> <DELIM1> <forumName>
	private String handle_isAdmin(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.isMember> <DELIM1> <userName> <DELIM1> <forumName>
	private String handle_isMember(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.createType> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <type>
	private String handle_createType(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.updateMemberType> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <type> <DELIM1> <memberToUpdate>
	private String handle_updateMemberType(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.membersOfForum> <DELIM1> <forumName>
	private String handle_membersOfForum(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.numOfForumsReport>
	private String handle_numOfForumsReport(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.setPolicy> <DELIM1> <forumName> <DELIM1> <userName> <DELIM1> <hasEmailPolicy> <DELIM1> <extendedDeletionPolicy> <DELIM1> <policyForUpdatingRank> <DELIM1> <minPostForModerator> <DELIM1> <minSeniorityMonths> <DELIM1> <onlyApointAdministratorCanRemoveModerators> <DELIM1> <canRemoveSingleModerators> <DELIM1> <expirationDateForPassword> <DELIM1> <interactiveNotifyingPolicyS>
	private String handle_setPolicy(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.listOfModeratorsReport> <DELIM1> <userName> <DELIM1> <forumName>
	private String handle_listOfModeratorsReport(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.postsOfMemberReport> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <memberName> 
	private String handle_postsOfMemberReport(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.numOfPostsInSubForumReport> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum>
	private String handle_numOfPostsInSubForumReport(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.registerToForum> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <newModeratorName> <DELIM1> <oldModerator>
	private String handle_replaceModerator(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.replaceAdmin> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <newAdminUserName>
	private String handle_replaceAdmin(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.removeModerator> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum> <DELIM1> <moderatorUserName>
	private String handle_removeModerator(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.banModerator> <DELIM1> <userName> <DELIM1> <forum><DELIM1> <subForum> <DELIM1> <moderatorUserName>
	private String handle_banModerator(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.upgradeToModerator> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum> <DELIM1> <moderatorUserName>
	private String handle_upgradeToModerator(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.editResponse> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum> <DELIM1> <openningPostID> <DELIM1> <responseID> <DELIM1> <newText>
	private String handle_editResponse(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.editTread> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum> <DELIM1> <treadID> <DELIM1> <newText>
	private String handle_editTread(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.banMember> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <memberToBanName>
	private String handle_banMember(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.responseToFreindRequest> <DELIM1> <userName> <DELIM1> <userNameResponser> <DELIM1> <response>
	private String handle_responseToFreindRequest(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.removeFriend> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <friendUserName>
	private String handle_removeFriend(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.addFriend> <DELIM1> <forumName> <DELIM1> <userName> <DELIM1> <friendUserName>
	private String handle_addFriend(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.validateByEmail> <DELIM1> <userName> <DELIM1> <code>
	private String handle_validateByEmail(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.removeAdminFromForum> <DELIM1> <forumName> <DELIM1> <adminName>
	private String handle_removeAdminFromForum(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.addAdminToForum> <DELIM1> <forumName> <DELIM1> <adminName>
	private String handle_addAdminToForum(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.getSubForumsForModerator> <DELIM1> <forumName> <DELIM1> <moderatorUserName>
	private String handle_getSubForumsForModerator(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.fileComplaint> <DELIM1> <currentForum> <DELIM1> <currentSubForum> <DELIM1> <moderatorUsername> <DELIM1> <complaint> <DELIM1> <userName> <DELIM1> <password>
	private String handle_fileComplaint(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.deleteThreadResponse> <DELIM1> <postID> <DELIM1> <responseID> <DELIM1> <forumName> <DELIM1> <subForumName>
	private String handle_deleteThreadResponse(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.deleteThread> <DELIM1> <ID> <DELIM1> <subForum> <DELIM1> <Forum>
	private String handle_deleteThread(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	// <Conf.postThreadResponse> <DELIM1> <responseContent> <DELIM1> <currentThreadTitle> <DELIM1> <currentForum> <DELIM1> <currentSubForum> <DELIM1> <userName> <DELIM1> <title>
	private String handle_postThreadResponse(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.publishNewThread> <DELIM1> <forumName> <DELIM1> <subForumName><DELIM1> <threadTitle> <DELIM1> <threadContent> <DELIM1> <userName>
	private String handle_publishNewThread(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.getThreadResponses> <DELIM1> <currentForum> <DELIM1> <currentSubForum> <DELIM1> <currentThreadTitle>
	private String handle_getThreadResponses(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.getResponseContent> <DELIM1> <treadID> <DELIM1> <responseID><DELIM1> <subForumName> <DELIM1> <forumName>
	private String handle_getResponseContent(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.getThreads> <DELIM1> <currentForum> <DELIM1> <currentSubForum> 
	private String handle_getThreads(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// <Conf.getSubForums> <DELIM1> <forumName> <DELIM1> <moderatorUserName>
	private String handle_getSubForums(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}


	//	private String handle_FOLLOW_POST(String msg, Client client) {
	//		String[] parsedMsg =  msg.split(" ");
	//		if (parsedMsg.length != 7){
	//			System.out.println("Got a FOLLOW_POST, but not with 6 params");
	//			return Configuration.FAIL;
	//		}
	//			
	//		String userName = parsedMsg[1]; 
	//		String forumSubject = parsedMsg[2]; 
	//		String subForumTitle = parsedMsg[3]; 
	//		String openingPostTitle = parsedMsg[4];
	//		String title = parsedMsg[5]; 
	//		String content = parsedMsg[6];
	//		return this.serverHandler.postThreadResponse(content, openingPostTitle, forumSubject, subForumTitle, userName, title);
	//	}
	//	private String handle_OPENING_POST(String msg, Client client) {
	// TODO Auto-generated method stub
	//		return null;
	//	}

}
