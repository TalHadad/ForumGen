package Connection.protocol.channels;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Vector;

import Connection.protocol.Client;


/**
 * This class represent a channel
 */
public class ForumChannel {

	
	private Vector<Client> _clientsVEC;//will holds all the clients in that channel
	private Client _headOfChannel;//the head of the channel
	private String _channelName;//the name of the channel
	private Vector<SubForumChannel> _subForumChannel;
	/**
	 * the constructor
	 * @param client the creater of that channel
	 * @param channelName the name of the channel
	 */
	public ForumChannel(Client client , String channelName){
		this._clientsVEC=new Vector<Client>();
		this._clientsVEC.add(client);
		this._headOfChannel=client;
		this._channelName=channelName;
		
	}
	/**
	 * adding the client to the clients vector
	 * @param client the client that needs to be added
	 */
	public void addClient(Client client){
		_clientsVEC.add(client);
	}
	/**
	 * remove the client from the clients vector
	 * @param client the client that needs to be removed
	 */
	public void removeClient(Client client){
		if (client.isAdministrator())
			client.administrator(false);
		_clientsVEC.remove(client);
	}
	/**
	 * checking if there's clients in the channel 
	 * @return true if there's not clients in the channel 
	 * @return false otherwise
	 */
	public boolean isEmpty(){
		if(_clientsVEC.size()==0){
			return true;
		}
		else
			return false;
	}
	/**
	 * checking if there's a client in the channel with the same nick
	 * @param nick the nick that needs to be checked
	 * @return true if there's a client in the channel with the same nick
	 * @return false otherwise
	 */
	public boolean NickExistsInChannel(String nick){
		for (int i=0; i<_clientsVEC.size(); i++)
			if (_clientsVEC.get(i).getNick().compareTo(nick)==0)
				return true;
		return false;
	}
	/**
	 * getting all of all the clients nick that in the channel
	 * @return a list of all the clients nick that in the channel
	 */
	public String getNAMES(){//*@ only to the head? what if his gone?
		String ans="";
		for (int i=0; i<_clientsVEC.size(); i++){
			if (_clientsVEC.get(i).isAdministrator())
				ans=ans+"@";
			ans=ans+_clientsVEC.get(i).getNick()+" ";
		}
		return ans;
	}
	/**
	 * this methods send a given message to all the clients in the channel
	 * @param msg the message that need to be send
	 * @param client the client that send the messege
	 */
	public void sendAll(String msg,Client client){
		msg+="\n";
		for (int i=0; i<_clientsVEC.size(); i++)
			if(_clientsVEC.get(i).getNick().compareTo(client.getNick())!=0){
				try {
					ByteBuffer outbuf = ByteBuffer.wrap(msg.getBytes("UTF-8"));
					while (outbuf.remaining() > 0) {
						_clientsVEC.get(i).getSocket().write(outbuf);
					}
				} catch (IOException e) {System.out.println("sendAll faild");}
			}

	}
	//***********the getters********************//
	public Vector<Client> getClients(){return _clientsVEC;}
	public Client getHeadOfChannel(){return _headOfChannel;}
	public String getChannelName(){return _channelName;}
	//********************************************//

}
