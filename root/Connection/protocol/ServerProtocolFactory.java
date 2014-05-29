package ListenerServer.protocol;

public interface ServerProtocolFactory<T> {
   AsyncServerProtocol<T> create();
}
