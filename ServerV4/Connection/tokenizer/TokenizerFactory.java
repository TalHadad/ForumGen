package Connection.tokenizer;

public interface TokenizerFactory<T> {
   MessageTokenizer<T> create();
}
