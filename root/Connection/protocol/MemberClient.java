package ListenerServer.protocol;
import java.nio.channels.SocketChannel;
/**
 * this class represent the client
 */
public class MemberClient {
	private String userName;
	private SocketChannel socket;//the socket to connect to with the client
	/**
	 * the constructor 
	 * @param socket the socket that is given to connect to with the client
	 */
	public MemberClient(String userName,SocketChannel socket){
		this.userName = userName;
		this.socket = socket;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public SocketChannel getSocket() {
		return socket;
	}
	public void setSocket(SocketChannel socket) {
		this.socket = socket;
	}

}
