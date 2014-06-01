package Connection.protocol;

public interface ServerProtocolFactory<T> {
   AsyncServerProtocol<T> create();
}
