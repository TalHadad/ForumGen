package ListenerServer.protocol;

import java.io.IOException;
import java.util.Vector;



import java.util.logging.Logger;

import Forum.Configuration;
import ListenerServer.tokenizer.*;

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
			if (msg.startsWith(Configuration.LOGIN_SUPER)){ans =handle_LOGIN_SUPER(msg,client);}
			else if (msg.startsWith(Configuration.LOGIN_MEMBER)){ans =handle_LOGIN_MEMBER(msg,client);}
			else if (msg.startsWith(Configuration.LOGOUT_MEMBER)){ans =handle_LOGOUT_MEMBER(msg,client);}
			else if (msg.startsWith(Configuration.OPENING_POST)){ans =handle_OPENING_POST(msg,client);}
			else if (msg.startsWith(Configuration.FOLLOW_POST)){ans =handle_FOLLOW_POST(msg,client);}
			else if (msg.startsWith(Configuration.REGISTER)){ans =handle_REGISTER(msg,client);}
			else if (msg.startsWith(Configuration.FORUM_CREATE)){ans =handle_FORUM_CREATE(msg,client);}
			else if (msg.startsWith(Configuration.FORUM_REMOVE)){ans =handle_FORUM_REMOVE(msg,client);}
			else if (msg.startsWith(Configuration.SUBFORUM_CREATE)){ans =handle_SUBFORUM_CREATE(msg,client);}
			else if (msg.startsWith(Configuration.SUBFORUM_REMOVE)){ans =handle_SUBFORUM_REMOVE(msg,client);}
			else if (msg.startsWith(Configuration.DISPLAY_FORUMS)){ans =handle_DISPLAY_FORUMS(msg,client);}
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
	

	private String handle_LOGIN_SUPER(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 3){
			System.out.println("Got a LOGIN_SUPER, but not with 2 params");
			return Configuration.FAIL;
		}
			
		String userName = parsedMsg[1]; 
		String pass = parsedMsg[2].substring(0, parsedMsg[2].length()-1); 
		
		return this.serverHandler.validateSuperAdmin(userName, pass);
	}
	
	private String handle_FOLLOW_POST(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 7){
			System.out.println("Got a FOLLOW_POST, but not with 6 params");
			return Configuration.FAIL;
		}
			
		String userName = parsedMsg[1]; 
		String forumSubject = parsedMsg[2]; 
		String subForumTitle = parsedMsg[3]; 
		String openingPostTitle = parsedMsg[4];
		String title = parsedMsg[5]; 
		String content = parsedMsg[6];
		return this.serverHandler.postThreadResponse(content, openingPostTitle, forumSubject, subForumTitle, userName, title);
	}
	private String handle_OPENING_POST(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	private String handle_REGISTER(String msg, Client client) {
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
	private String handle_FORUM_CREATE(String msg, Client client) {
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
	private String handle_FORUM_REMOVE(String msg, Client client) {
		// TODO Auto-generated method stub
		return null;
	}
	private String handle_SUBFORUM_CREATE(String msg, Client client) {
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
	private String handle_SUBFORUM_REMOVE(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 3){
			System.out.println("Got a UBFORUM_REMOVE, but not with 2 params");
			return Configuration.FAIL;
		}
		
		String forumName=parsedMsg[1];
		String subForumName=parsedMsg[2];
		return this.serverHandler.deleteSubForum(forumName, subForumName);
	}
	private String handle_LOGOUT_MEMBER(String msg, Client client) {
		String[] parsedMsg =  msg.split(" ");
		if (parsedMsg.length != 3){
			System.out.println("Got a LOGOUT_MEMBER, but not with 2 params");
			return Configuration.FAIL;
		}
		
		String forumName=parsedMsg[1];
		String userName=parsedMsg[2];
		return this.serverHandler.logout(forumName, userName);
	}
	
	private String handle_LOGIN_MEMBER(String msg, Client client) {
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

	private String handle_DISPLAY_FORUMS(String msg, Client client) {
		Vector<String> ans=this.serverHandler.DisplayForums();
		String res="";
		for(String s:ans){
			res+=" "+s;
		}
		System.out.println("RES "+res);
		return res;
	}


}
