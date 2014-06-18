package Connection.protocol;
import java.nio.channels.SocketChannel;
/**
 * this class represent the client
 */
public class Client {
	private String user;//the user name of the client
	private String nick;//the nick name of the client
	private String channel;//the channel name that the client is in
	private SocketChannel socket;//the socket to connect to with the client
	private boolean isRegistered;//flag that is true if the client has a user name and a nick name
	private boolean administrator;//flag that is true if the client is the head of the channel
	/**
	 * the constructor 
	 * @param socket the socket that is given to connect to with the client
	 */
	public Client(SocketChannel socket){
		this.setUser(null);
		this.setNick(null);
		this.setChannel(null);
		this.setSocket(socket);
		this.register(false);
		this.administrator=false;
	}
	//****************getters*********************//
	public String getUser() {return user;}
	public String getNick() {return nick;}
	public String getChannel() {return channel;}
	public SocketChannel getSocket() {return socket;}
	public boolean isRegistered() {return isRegistered;}
	public boolean isAdministrator(){return this.administrator;}
	//****************setters*********************//
	public void setUser(String user) {this.user = user;}
	public void setNick(String nick) {this.nick = nick;}
	public void setChannel(String channel) {this.channel = channel;}
	public void setSocket(SocketChannel socket) {this.socket = socket;}
	public void register(boolean isRegistered) {this.isRegistered = isRegistered;}
	public void administrator(boolean administrator){this.administrator=administrator;}
}
