import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketAdapter implements ProtocolAdapter {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public SocketAdapter(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }

    @Override
    public void sendLine(String line) throws IOException {
        if (line == null) {
            return;
        }
        writer.println(line);
    }

    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
        }
    }
}
