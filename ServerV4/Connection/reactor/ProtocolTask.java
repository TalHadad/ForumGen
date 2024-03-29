package Connection.reactor;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;

import Connection.protocol.*;
import Connection.tokenizer.*;

/**
 * This class supplies some data to the protocol, which then processes the data,
 * possibly returning a reply. This class is implemented as an executor task.
 * 
 */
public class ProtocolTask<T> implements Runnable {

	private final ServerProtocol<T> _protocol;
	private final MessageTokenizer<T> _tokenizer;
	private final ConnectionHandler<T> _handler;
	private Client _client;

	public ProtocolTask(final ServerProtocol<T> protocol, final MessageTokenizer<T> tokenizer, final ConnectionHandler<T> h,Client client) {
		this._protocol = protocol;
		this._tokenizer = tokenizer;
		this._handler = h;
		_client=client;
	}

	// we synchronize on ourselves, in case we are executed by several threads
	// from the thread pool.
	public synchronized void run() {
      // go over all complete messages and process them.
      while (_tokenizer.hasMessage()) {
         T msg = _tokenizer.nextMessage();
         T response = this._protocol.processMessage(msg, _client);
         System.out.println("response: "+response);
         if (response != null && ((StringMessage)response).getMessage()!=null) {
            try {
               ByteBuffer bytes = _tokenizer.getBytesForMessage(response);
               this._handler.addOutData(bytes);
            } catch (CharacterCodingException e) { e.printStackTrace(); }
         }
      }
	}

	public void addBytes(ByteBuffer b) {
		_tokenizer.addBytes(b);
	}
}
