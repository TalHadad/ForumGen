package ListenerServer.tokenizer;

public interface TokenizerFactory<T> {
   MessageTokenizer<T> create();
}
