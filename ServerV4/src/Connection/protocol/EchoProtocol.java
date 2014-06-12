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
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			////////////// SYSTEM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 1*
			if (msg.startsWith(Configuration.numOfForumsReport)){ans =handle_numOfForumsReport(msg,client);}
			// 2*
			else if (msg.startsWith(Configuration.initializeSystem)){ans =handle_initializeSystem(msg,client);}
			// 3*
			else if (msg.startsWith(Configuration.isInitialize)){ans =handle_isInitialize(msg,client);}
			// 4*
			else if (msg.startsWith(Configuration.validateSuperAdmin)){ans =handle_validateSuperAdmin(msg,client);}
			// 5*
			else if (msg.startsWith(Configuration.loginSuper)){ans =handle_loginSuper(msg,client);}
			// 6*
			else if (msg.startsWith(Configuration.createForum)){ans =handle_createForum(msg,client);}
			// 7*
			else if (msg.startsWith(Configuration.deleteForum)){ans =handle_deleteForum(msg,client);}
 			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			////////////// FORUM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 1
			else if (msg.startsWith(Configuration.DisplayForums)){ans =handle_DisplayForums(msg,client);}
			// 2
			else if (msg.startsWith(Configuration.registerToForum)){ans =handle_registerToForum(msg,client);}
			// 3
			else if (msg.startsWith(Configuration.login)){ans =handle_login(msg,client);}
			// 4
			else if (msg.startsWith(Configuration.logout)){ans =handle_logout(msg,client);}
			// 5
			else if (msg.startsWith(Configuration.addAdminToForum)){ans =handle_addAdminToForum(msg,client);}
			// 6
			else if (msg.startsWith(Configuration.removeAdminFromForum)){ans =handle_removeAdminFromForum(msg,client);}
			// 7
			else if (msg.startsWith(Configuration.validateByEmail)){ans =handle_validateByEmail(msg,client);}
			// 8
			else if (msg.startsWith(Configuration.banMember)){ans =handle_banMember(msg,client);}
			// 9
			else if (msg.startsWith(Configuration.replaceAdmin)){ans =handle_replaceAdmin(msg,client);}
			// 10
			else if (msg.startsWith(Configuration.postsOfMemberReport)){ans =handle_postsOfMemberReport(msg,client);}
			// 12
			else if (msg.startsWith(Configuration.setPolicy)){ans =handle_setPolicy(msg,client);}// 13
			else if (msg.startsWith(Configuration.membersOfForum)){ans =handle_membersOfForum(msg,client);}

			// 15
			else if (msg.startsWith(Configuration.updateMemberType)){ans =handle_updateMemberType(msg,client);}
			// 16
			else if (msg.startsWith(Configuration.createType)){ans =handle_createType(msg,client);}
			// 17
			else if (msg.startsWith(Configuration.isMember)){ans =handle_isMember(msg,client);}
			// 18
			else if (msg.startsWith(Configuration.isAdmin)){ans =handle_isAdmin(msg,client);}
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			////////////// SUBFORUM //////////////////////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 19
			else if (msg.startsWith(Configuration.getSubForums)){ans =handle_getSubForums(msg,client);}
			// 20
			else if (msg.startsWith(Configuration.getThreads)){ans =handle_getThreads(msg,client);}
			// 21
			else if (msg.startsWith(Configuration.getSubForumsForModerator)){ans =handle_getSubForumsForModerator(msg,client);}
			// 22
			else if (msg.startsWith(Configuration.upgradeToModerator)){ans =handle_upgradeToModerator(msg,client);}
			// 23
			else if (msg.startsWith(Configuration.banModerator)){ans =handle_banModerator(msg,client);}
			// 24
			else if (msg.startsWith(Configuration.removeModerator)){ans =handle_removeModerator(msg,client);}
			// 25
			else if (msg.startsWith(Configuration.replaceModerator)){ans =handle_replaceModerator(msg,client);}
			// 26
			else if (msg.startsWith(Configuration.numOfPostsInSubForumReport)){ans =handle_numOfPostsInSubForumReport(msg,client);}
			// 27
			else if (msg.startsWith(Configuration.listOfModeratorsReport)){ans =handle_listOfModeratorsReport(msg,client);}
			// 28
			else if (msg.startsWith(Configuration.createSubForum)){ans =handle_createSubForum(msg,client);}
			// 29
			else if (msg.startsWith(Configuration.deleteSubForum)){ans =handle_deleteSubForum(msg,client);}
			// 30
			else if (msg.startsWith(Configuration.isModerator)){ans =handle_isModerator(msg,client);}
	 		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			////////////// THREAD //////////////////////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 32
			else if (msg.startsWith(Configuration.getThreadResponses)){ans =handle_getThreadResponses(msg,client);}
			// 33
			else if (msg.startsWith(Configuration.publishNewThread)){ans =handle_publishNewThread(msg,client);}
			// 34
			else if (msg.startsWith(Configuration.deleteThread)){ans =handle_deleteThread(msg,client);}
			// 35
			else if (msg.startsWith(Configuration.editTread)){ans =handle_editTread(msg,client);}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			////////////// RESPONSE //////////////////////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 36
			else if (msg.startsWith(Configuration.getResponseContent)){ans =handle_getResponseContent(msg,client);}
			// 37
			else if (msg.startsWith(Configuration.postThreadResponse)){ans =handle_postThreadResponse(msg,client);}
			// 38
			else if (msg.startsWith(Configuration.deleteThreadResponse)){ans =handle_deleteThreadResponse(msg,client);}
			// 39
			else if (msg.startsWith(Configuration.editResponse)){ans =handle_editResponse(msg,client);}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			////////////// MEMBERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 40
			else if (msg.startsWith(Configuration.fileComplaint)){ans =handle_fileComplaint(msg,client);}
			// 41
			else if (msg.startsWith(Configuration.addFriend)){ans =handle_addFriend(msg,client);}
			// 42
			else if (msg.startsWith(Configuration.removeFriend)){ans =handle_removeFriend(msg,client);}
			// 43
			else if (msg.startsWith(Configuration.responseToFreindRequest)){ans =handle_responseToFreindRequest(msg,client);}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			////////////// messages from server to client //////////////////////////////////////////////////////////////////////////////////////////////////////
			//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// 44
		//	else if (msg.startsWith(Configuration.notifyMember)){ans =handle_notifyMember(msg,client);}
			// 45
		//	else if (msg.startsWith(Configuration.notifyFreindRequest)){ans =handle_notifyFreindRequest(msg,client);}
			else
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


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SYSTEM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 1*
	// <Conf.numOfForumsReport>
	private String handle_numOfForumsReport(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 1){
			System.out.println("Got a numOfForumsReport, but not with 0 params");
			return Configuration.FAIL;
		}

		int num = this.serverHandler.numOfForumsReport();
		return Configuration.SUCCESS+Configuration.DELIMITER1+"The number of forums is the system is "+num+" ." ;
	}

	// 2*
	// <Conf.initializeSystem> <DELIM1> <userName> <DELIM1> <password>
	private String handle_initializeSystem(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a initializeSystem, but not with 2 param");
			return Configuration.FAIL;
		}

		String userName=parsedMsg[1];
		String password=parsedMsg[2].substring(0, parsedMsg[2].length()-1);

		boolean ans = this.serverHandler.initializeSystem(userName, password);
		if(ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"The system is initialized successully." ;
		return Configuration.FAIL+Configuration.DELIMITER1+"Unauthorized user.";
	}

	// 3*
	// <Conf.isInitialize>
	private String handle_isInitialize(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 1){
			System.out.println("Got a isInitialize, but not with 0 params");
			return Configuration.FAIL;
		}

		boolean ans = this.serverHandler.isInitialize();

		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"The system is initialized.";
		return Configuration.FAIL+Configuration.DELIMITER1+"The system is not initialized.";
	}

	// 4*
	// <Conf.validateSuperAdmin> <DELIM1> <userName> <DELIM1> <password>
	private String handle_validateSuperAdmin(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a validateSuperAdmin, but not with 2 params");
			return Configuration.FAIL;
		}

		String userName=parsedMsg[1];
		String password=parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		boolean ans = this.serverHandler.validateSuperAdmin(userName, password);
		if(ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"The user is super administrator.";
		return Configuration.FAIL+Configuration.DELIMITER1+"The user is not super administrator.";
	}

	// 5*
	// <Conf.loginSuper> <DELIM1> <userName> <DELIM1> <pass>
	private String handle_loginSuper(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a LOGIN_SUPER, but not with 2 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1]; 
		String pass = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 

		return null;
		// TODO
		//return this.serverHandler.loginSuper(userName, pass);
	}

	// 6*
	// <Conf.createForum> <DELIM1> <forumName> <DELIM1> <userName> <DELIM1> <policy>
	private String handle_createForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 10){
			System.out.println("Got a createForum, but not with 9 params");
			return Configuration.FAIL;
		}

		String forumName=parsedMsg[1];
		String userName=parsedMsg[2];
		String hasEmailPolicy=parsedMsg[3];
		String extendedDeletionPolicy=parsedMsg[4];
		String minPostForModerator=parsedMsg[5];
		String minSeniorityMonths=parsedMsg[6];
		String onlyApointAdministratorCanRemoveModerators=parsedMsg[7];
		String canRemoveSingleModerators=parsedMsg[8];
		String expirationDateForPassword=parsedMsg[9].substring(0, parsedMsg[9].length()-1); 

		boolean ans = this.serverHandler.createForum( forumName,  userName,  hasEmailPolicy,  extendedDeletionPolicy,
				 minPostForModerator,  minSeniorityMonths,  onlyApointAdministratorCanRemoveModerators, 
				 canRemoveSingleModerators,  expirationDateForPassword);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+forumName+"forum created a successfull";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to create "+forumName+" forum";
	}

	// 7*
	// <Conf.deleteForum> <DELIM1> <forumName> 
	private String handle_deleteForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 2){
			System.out.println("Got a deleteForum, but not with 2 params");
			return Configuration.FAIL;
		}

		String forumName=parsedMsg[1].substring(0, parsedMsg[1].length()-1); 


		boolean ans = this.serverHandler.deleteForum(forumName);
		if(ans)
			return Configuration.SUCCESS+"Delete the forum successfull";
		return Configuration.FAIL+"Fail to delete the forum";
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// FORUM /////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 1
	// <Conf.DisplayForums> 
	private String handle_DisplayForums(String msg, Client client) {
		Vector<String> forums = this.serverHandler.DisplayForums();
		String ans="";
		for (String forum: forums){
			ans+=forum+Configuration.DELIMITER1;
		}
		//remove the last redundant delim1 
		if (ans.compareTo("")==0)
			return "";
		return ans.substring(0, (ans.length()-Configuration.DELIMITER1.length()));
	}

	// 2
	// <Conf.registerToForum> <DELIM1> <currentForum> <DELIM1> <userName> <DELIM1> <password> <DELIM1> <Email> <DELIM1> <remainderQues> <DELIM1> <remainderAns>
	private String handle_registerToForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 7){
			System.out.println("Got a registerToForum, but not with 6 params");
			return Configuration.FAIL;
		}

		String currentForum= parsedMsg[1];
		String userName= parsedMsg[2];
		String password= parsedMsg[3];
		String Email= parsedMsg[4];
		String remainderQues= parsedMsg[5];
		String remainderAns= parsedMsg[6].substring(0, parsedMsg[6].length()-1); 

		return this.serverHandler.registerToForum(currentForum, userName, password, Email, remainderQues, remainderAns);
	}

	// 3
	// <Conf.login> <DELIM1> <currentForum> <DELIM1> <userName><DELIM1> <password>
	private String handle_login(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a login, but not with 3 params");
			return Configuration.FAIL;
		}

		String currentForum=parsedMsg[1];
		String userName= parsedMsg[2];
		String password=parsedMsg[3].substring(0, parsedMsg[3].length()-1);
		return this.serverHandler.login(currentForum, userName, password);
	}

	// 4
	// <Conf.logout> <DELIM1> <currentForum> <DELIM1> <userName>
	private String handle_logout(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a logout, but not with 2 params");
			return Configuration.FAIL;
		}

		String currentForum = parsedMsg[1];
		String userName = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		boolean ans = this.serverHandler.logout(currentForum, userName);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+" logout seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"The logout faild.\nPlease try again..";
	}

	// 5
	// <Conf.addAdminToForum> <DELIM1> <forumName> <DELIM1> <adminName>
	private String handle_addAdminToForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a addAdminToForum, but not with 2 params");
			return Configuration.FAIL;
		}

		String forumName = parsedMsg[1];
		String adminName = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		
		boolean ans = this.serverHandler.addAdminToForum(forumName, adminName);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+adminName+" is now administrator.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Failed to add administrator.";
	}

	// 6
	// <Conf.removeAdminFromForum> <DELIM1> <forumName> <DELIM1> <adminName>
	private String handle_removeAdminFromForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a removeAdminFromForum, but not with 2 params");
			return Configuration.FAIL;
		}

		String forumName = parsedMsg[1];
		String adminName = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		
		boolean ans = this.serverHandler.removeAdminFromForum(forumName, adminName);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+adminName+" removed from administration seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Failed to remove administrator.";
	}

	// 7
	// <Conf.validateByEmail> <DELIM1> <userName> <DELIM1> <code>
	private String handle_validateByEmail(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a validateByEmail, but not with 2 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2];
		String code = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.validateByEmail( userName,  forumName,  code);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+" validated seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Code do not match.";
	}

	// 8
	// <Conf.banMember> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <memberToBanName>
	private String handle_banMember(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a banMember, but not with 3 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2];
		String memberToBanName = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.banMember(userName, forumName, memberToBanName);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+" seccessfuly baned.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to ban "+memberToBanName+" .";
	}

	// 9
	// <Conf.replaceAdmin> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <newAdminUserName>
	private String handle_replaceAdmin(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a replaceAdmin, but not with 3 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String newAdminUserName = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.replaceAdmin(userName, forum, newAdminUserName);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+newAdminUserName+" seccessfuly replaced administration with "+newAdminUserName+" .";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to replaced administration.";
	}

	// 10
	// <Conf.postsOfMemberReport> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <memberName> 
	private String handle_postsOfMemberReport(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a postsOfMemberReport, but not with 3 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String memberName = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		Vector<String> posts = this.serverHandler.postsOfMemberReport(userName, forum, memberName);
		String ans = "";
		for (String post: posts){
			ans += post+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 12
	// <Conf.setPolicy> <DELIM1> <forumName> <DELIM1> <userName> <DELIM1> <hasEmailPolicy> <DELIM1> <extendedDeletionPolicy> <DELIM1> <policyForUpdatingRank> <DELIM1> <minPostForModerator> <DELIM1> <minSeniorityMonths> <DELIM1> <onlyApointAdministratorCanRemoveModerators> <DELIM1> <canRemoveSingleModerators> <DELIM1> <expirationDateForPassword> <DELIM1> <interactiveNotifyingPolicyS>
	private String handle_setPolicy(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 12){
			System.out.println("Got a setPolicy, but not with 11 params");
			return Configuration.FAIL;
		}
		
		String forumName = parsedMsg[1];
		String userName = parsedMsg[2];
		String hasEmailPolicy = parsedMsg[3];
		String extendedDeletionPolicy = parsedMsg[4];
		String minPostForModerator = parsedMsg[5];
		String minSeniorityMonths = parsedMsg[6];
		String onlyApointAdministratorCanRemoveModerators = parsedMsg[7];
		String canRemoveSingleModerators = parsedMsg[8];
		String expirationDateForPassword = parsedMsg[9];
		String interactiveNotifyingPolicys = parsedMsg[10].substring(0, parsedMsg[10].length()-1); 
		
		boolean ans = this.serverHandler.setPolicy( forumName,  userName,
				 hasEmailPolicy,  extendedDeletionPolicy,  minPostForModerator,
				 minSeniorityMonths,  onlyApointAdministratorCanRemoveModerators,
				 canRemoveSingleModerators,  expirationDateForPassword,  interactiveNotifyingPolicys);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"Policy has updated seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to updated policy.";
	}

	// 13
	// <Conf.membersOfForum> <DELIM1> <forumName>
	private String handle_membersOfForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 2){
			System.out.println("Got a membersOfForum, but not with 1 param");
			return Configuration.FAIL;
		}

		String forumName = parsedMsg[1].substring(0, parsedMsg[1].length()-1); 

		Vector<String> membersNames = this.serverHandler.membersOfForum(forumName);
		String ans = "";
		for (String memberName: membersNames)
			ans += memberName+Configuration.DELIMITER1;
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 15
	// <Conf.updateMemberType> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <type> <DELIM1> <memberToUpdate>
	private String handle_updateMemberType(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 5){
			System.out.println("Got a updateMemberType, but not with 4 param");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2];
		String type = parsedMsg[3];
		String memberToUpdate = parsedMsg[4].substring(0, parsedMsg[4].length()-1); 
		
		boolean ans = this.serverHandler.updateMemberType(userName, forumName, type, memberToUpdate);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"Member type has updated seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to updated member type.";
	}

	// 16
	// <Conf.createType> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <type>
	private String handle_createType(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a createType, but not with 3 param");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2];
		String type = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.createType(userName, forumName, type);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+type+"type has added seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Fail to add "+ type+" type.";
	}

	// 17
	// <Conf.isMember> <DELIM1> <userName> <DELIM1> <forumName>
	private String handle_isMember(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a isMember, but not with 2 param");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		
		boolean ans = this.serverHandler.isMember(userName, forumName);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+"is a member in this forum.";
		return Configuration.FAIL+Configuration.DELIMITER1+userName+"is not a member in this forum.";
	}

	// 18
	// <Conf.isAdmin> <DELIM1> <userName> <DELIM1> <forumName>
	private String handle_isAdmin(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a isAdmin, but not with 2 param");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		
		boolean ans = this.serverHandler.isAdmin(userName, forumName);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+"is an administrator in this forum.";
		return Configuration.FAIL+Configuration.DELIMITER1+userName+"is not an administrator in this forum.";
	}

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// SUBFORUM //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// 19
	// <Conf.getSubForums> <DELIM1> <forumName>
	private String handle_getSubForums(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 2){
			System.out.println("Got a getSubForums, but not with 1 param");
			return Configuration.FAIL;
		}

		String forumName = parsedMsg[1].substring(0, parsedMsg[1].length()-1); 
		
		Vector<String> subForums = this.serverHandler.getSubForums(forumName);
		String ans="";
		for(String subForum:subForums){
			ans += subForum+Configuration.DELIMITER1;
		}
		if (ans.compareTo("")==0)
			return "";
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 20
	// <Conf.getThreads> <DELIM1> <currentForum> <DELIM1> <currentSubForum> 
	private String handle_getThreads(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a getThreads, but not with 2 params");
			return Configuration.FAIL;
		}

		String currentForum = parsedMsg[1];
		String currentSubForum = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		
		Vector<String> threads = this.serverHandler.getThreads(currentForum, currentSubForum);
		String ans="";
		for(String thread:threads){
			ans += thread+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 21
	// <Conf.getSubForumsForModerator> <DELIM1> <forumName> <DELIM1> <moderatorUserName>
	private String handle_getSubForumsForModerator(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a getSubForumsForModerator, but not with 2 params");
			return Configuration.FAIL;
		}

		String forumName = parsedMsg[1];
		String moderatorUserName = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		
		Vector<String> subForums = this.serverHandler.getSubForumsForModerator(forumName, moderatorUserName);
		String ans="";
		for(String subForum :subForums){
			ans += subForum+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 22
	// <Conf.upgradeToModerator> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum> <DELIM1> <moderatorUserName>
	private String handle_upgradeToModerator(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 5){
			System.out.println("Got a upgradeToModerator, but not with 4 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String subForum = parsedMsg[3];
		String moderatorUserName = parsedMsg[4].substring(0, parsedMsg[4].length()-1); 
		
		boolean ans = this.serverHandler.upgradeToModerator(userName, forum, subForum, moderatorUserName);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+userName+"is a moderator in this subforum.";
		return Configuration.FAIL+Configuration.DELIMITER1+userName+"Fail to apoint to moderator in this subforum.";
	}

	// 23
	// <Conf.banModerator> <DELIM1> <userName> <DELIM1> <forum><DELIM1> <subForum> <DELIM1> <moderatorUserName>
	private String handle_banModerator(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 5){
			System.out.println("Got a banModerator, but not with 4 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String subForum = parsedMsg[3];
		String moderatorUserName = parsedMsg[4].substring(0, parsedMsg[4].length()-1); 
		
		boolean ans = this.serverHandler.banModerator(userName, forum, subForum, moderatorUserName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 24
	// <Conf.removeModerator> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum> <DELIM1> <moderatorUserName>
	private String handle_removeModerator(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 5){
			System.out.println("Got a removeModerator, but not with 4 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String subForum = parsedMsg[3];
		String moderatorUserName = parsedMsg[4].substring(0, parsedMsg[4].length()-1); 
		
		boolean ans = this.serverHandler.removeModerator(userName, forum, subForum, moderatorUserName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 25
	// <Conf.replaceModerator> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <newModeratorName> <DELIM1> <oldModerator>
	private String handle_replaceModerator(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 5){
			System.out.println("Got a replaceModerator, but not with 4 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String subForum = parsedMsg[3];
		String newModeratorName = parsedMsg[4];
		String oldModerator = parsedMsg[5].substring(0, parsedMsg[5].length()-1); 
		
		boolean ans = this.serverHandler.replaceModerator( userName,  forum,  subForum, newModeratorName,  oldModerator);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 26
	// <Conf.numOfPostsInSubForumReport> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum>
	private String handle_numOfPostsInSubForumReport(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a numOfPostsInSubForumReport, but not with 3 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String subForum = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 

		int totalAmountOfPosts = this.serverHandler.numOfPostsInSubForumReport(userName, forum, subForum);
		return "Total posts: " + totalAmountOfPosts;
	}

	// 27
	// <Conf.listOfModeratorsReport> <DELIM1> <userName> <DELIM1> <forumName>
	private String handle_listOfModeratorsReport(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a listOfModeratorsReport, but not with 2 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		
		Vector<String> moderators = this.serverHandler.listOfModeratorsReport(userName, forumName);
		String ans="";
		for(String moderator:moderators){
			ans += moderator+Configuration.DELIMITER1;
		}
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 28
	//<Conf.createSubForum> <DELIM1> <ForumName> <DELIM1> <subForumName> <DELIM1> <subject> <DELIM1> <moderatorUserName> <DELIM1> <userName>
	private String handle_createSubForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 6){
			System.out.println("Got a handle_createSubForum, but not with 5 params");
			return Configuration.FAIL;
		}

		String ForumName=parsedMsg[1];
		String subForumName=parsedMsg[2];
		String subject=parsedMsg[3];
		String moderatorUserName=parsedMsg[4];
		String userName=parsedMsg[5].substring(0, parsedMsg[5].length()-1); 
		boolean ans = this.serverHandler.createSubForum(ForumName, subForumName, subject, moderatorUserName, userName);
		if (ans)
			return Configuration.SUCCESS+" subforum created successfuly";
		return Configuration.FAIL+" error : requested moderator is not quelafied";
	}

	// 29
	// <Conf.deleteSubForum> <DELIM1> <forumName> <DELIM1> <subForumName>	
	private String handle_deleteSubForum(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 3){
			System.out.println("Got a deleteSubForum, but not with 2 params");
			return Configuration.FAIL;
		}

		String forumName=parsedMsg[1];
		String subForumName=parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		boolean ans = this.serverHandler.deleteSubForum(forumName, subForumName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 30
	// <Conf.isModerator> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <SubforumName>
	private String handle_isModerator(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a isModerator, but not with 3 param");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2];
		String SubforumName = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.isModerator(userName, forumName, SubforumName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// THREAD //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 32
	// <Conf.getThreadResponses> <DELIM1> <currentForum> <DELIM1> <currentSubForum> <DELIM1> <currentThreadTitle>
	private String handle_getThreadResponses(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a getThreadResponses, but not with 3 params");
			return Configuration.FAIL;
		}

		String currentForum = parsedMsg[1];
		String currentSubForum = parsedMsg[2];
		String currentThreadTitle = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		Vector<String> responses = this.serverHandler.getThreadResponses(currentForum, currentSubForum, currentThreadTitle);
		String ans = "";
		for (String response:responses)
			ans+=response+Configuration.DELIMITER1;
		ans = ans.substring(0, ans.length()-Configuration.DELIMITER1.length());
		return ans;
	}

	// 33
	// <Conf.publishNewThread> <DELIM1> <forumName> <DELIM1> <subForumName><DELIM1> <threadTitle> <DELIM1> <threadContent> <DELIM1> <userName>
	private String handle_publishNewThread(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 6){
			System.out.println("Got a publishNewThread, but not with 5 params");
			return Configuration.FAIL;
		}

		String forumName = parsedMsg[1];
		String subForumName = parsedMsg[2];
		String threadTitle = parsedMsg[3];
		String threadContent = parsedMsg[4];
		String userName = parsedMsg[5].substring(0, parsedMsg[5].length()-1); 
		
		boolean ans = this.serverHandler.publishNewThread(forumName, subForumName, threadTitle, threadContent, userName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 34
	// <Conf.deleteThread> <DELIM1> <ID> <DELIM1> <subForum> <DELIM1> <Forum>
	private String handle_deleteThread(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a deleteThread, but not with 3 params");
			return Configuration.FAIL;
		}

		String ID = parsedMsg[1];
		String subForum = parsedMsg[2];
		String Forum = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.deleteThread(ID, subForum, Forum);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 35
	// <Conf.editTread> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum> <DELIM1> <treadID> <DELIM1> <newText>
	private String handle_editTread(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 6){
			System.out.println("Got a editTread, but not with 5 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String subForum = parsedMsg[3];
		String treadID = parsedMsg[4];
		String newText = parsedMsg[5].substring(0, parsedMsg[5].length()-1); 
		
		boolean ans = this.serverHandler.editTread(userName, forum, subForum, treadID, newText);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// RESPONSE //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 36
	// <Conf.getResponseContent> <DELIM1> <treadID> <DELIM1> <responseID> <DELIM1> <subForumName> <DELIM1> <forumName>
	private String handle_getResponseContent(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 5){
			System.out.println("Got a getResponseContent, but not with 4 params");
			return Configuration.FAIL;
		}

		String treadID = parsedMsg[1];
		String responseID = parsedMsg[2];
		String subForumName = parsedMsg[3];
		String forumName = parsedMsg[4].substring(0, parsedMsg[4].length()-1); 
		
		return this.serverHandler.getResponseContent(treadID, responseID, subForumName, forumName);

	}

	// 37
	// <Conf.postThreadResponse> <DELIM1> <responseContent> <DELIM1> <currentThreadTitle> <DELIM1> <currentForum> <DELIM1> <currentSubForum> <DELIM1> <userName> <DELIM1> <title>
	private String handle_postThreadResponse(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 7){
			System.out.println("Got a postThreadResponse, but not with 6 params");
			return Configuration.FAIL;
		}

		String responseContent = parsedMsg[1];
		String currentThreadTitle = parsedMsg[2];
		String currentForum = parsedMsg[3];
		String currentSubForum = parsedMsg[4];
		String userName = parsedMsg[5];
		String title = parsedMsg[6].substring(0, parsedMsg[6].length()-1); 
		
		boolean ans = this.serverHandler.postThreadResponse(responseContent, currentThreadTitle, currentForum, currentSubForum, userName, title);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"Response is added seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Failed to add Response.";
	}

	// 38
	// <Conf.deleteThreadResponse> <DELIM1> <postID> <DELIM1> <responseID> <DELIM1> <forumName> <DELIM1> <subForumName>
	private String handle_deleteThreadResponse(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 5){
			System.out.println("Got a deleteThreadResponse, but not with 4 params");
			return Configuration.FAIL;
		}

		String postID = parsedMsg[1];
		String responseID = parsedMsg[2];
		String forumName = parsedMsg[3];
		String subForumName = parsedMsg[4].substring(0, parsedMsg[4].length()-1); 
		
		boolean ans = this.serverHandler.deleteThreadResponse(postID, responseID, forumName, subForumName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 39
	// <Conf.editResponse> <DELIM1> <userName> <DELIM1> <forum> <DELIM1> <subForum> <DELIM1> <openningPostID> <DELIM1> <responseID> <DELIM1> <newText>
	private String handle_editResponse(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 7){
			System.out.println("Got a editResponse, but not with 6 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forum = parsedMsg[2];
		String subForum = parsedMsg[3];
		String openningPostID = parsedMsg[4];
		String responseID = parsedMsg[5];
		String newText = parsedMsg[6].substring(0, parsedMsg[6].length()-1); 
		
		boolean ans = this.serverHandler.editResponse(userName, forum, subForum, openningPostID, responseID, newText);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// MEMBERS //////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// 40
	// <Conf.fileComplaint> <DELIM1> <currentForum> <DELIM1> <currentSubForum> <DELIM1> <moderatorUsername> <DELIM1> <complaint> <DELIM1> <userName> <DELIM1> <password>
	private String handle_fileComplaint(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 7){
			System.out.println("Got a fileComplaint, but not with 6 params");
			return Configuration.FAIL;
		}

		String currentForum = parsedMsg[1];
		String currentSubForum = parsedMsg[2];
		String moderatorUsername = parsedMsg[3];
		String complaint = parsedMsg[4];
		String userName = parsedMsg[5];
		String password = parsedMsg[6].substring(0, parsedMsg[6].length()-1); 
		
		boolean ans = this.serverHandler.fileComplaint(currentForum, currentSubForum, moderatorUsername, complaint, userName, password);
		if (ans)
			return Configuration.SUCCESS+Configuration.DELIMITER1+"Response is added seccessfuly.";
		return Configuration.FAIL+Configuration.DELIMITER1+"Failed to add Response.";
	}

	// 41
	// <Conf.addFriend> <DELIM1> <forumName> <DELIM1> <userName> <DELIM1> <friendUserName>
	private String handle_addFriend(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a addFriend, but not with 3 params");
			return Configuration.FAIL;
		}

		String forumName = parsedMsg[1];
		String userName = parsedMsg[2];
		String friendUserName = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.addFriend(forumName, userName, friendUserName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 42
	// <Conf.removeFriend> <DELIM1> <userName> <DELIM1> <forumName> <DELIM1> <friendUserName>
	private String handle_removeFriend(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a removeFriend, but not with 3 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String forumName = parsedMsg[2];
		String friendUserName = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.removeFriend(userName, forumName, friendUserName);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}

	// 43
	// <Conf.responseToFreindRequest> <DELIM1> <userName> <DELIM1> <userNameResponser> <DELIM1> <response>
	private String handle_responseToFreindRequest(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 4){
			System.out.println("Got a responseToFreindRequest, but not with 3 params");
			return Configuration.FAIL;
		}

		String userName = parsedMsg[1];
		String userNameResponser = parsedMsg[2];
		String response = parsedMsg[3].substring(0, parsedMsg[3].length()-1); 
		
		boolean ans = this.serverHandler.responseToFreindRequest(userName, userNameResponser, response);
		if (ans)
			return Configuration.SUCCESS;
		return Configuration.FAIL;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	////////////// messages from server to client /////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		/*
		// 44
	// <Conf.notifyMember> <DELIM1> <text> 
	private void handle_notifyMember(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	// 45
	// <Conf.notifyFreindRequest> <DELIM1> <requesterUserName>
	private void handle_notifyFreindRequest(String msg, Client client) {
		String[] parsedMsg =  msg.split(Configuration.DELIMITER1);
		if (parsedMsg.length != 2){
			System.out.println("Got a notifyFreindRequest, but not with 1 param");
			return Configuration.FAIL;
		}

		String requesterUserName=parsedMsg[1];
		return this.serverHandler.notifyFreindRequest(requesterUserName);
	}

	*/








	




}
