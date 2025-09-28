import java.io.IOException;

public interface ProtocolAdapter {
    String readLine() throws IOException;
    void sendLine(String line) throws IOException;
    void close();
}
