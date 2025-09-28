import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("ChatAppClient");
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        logger.addHandler(ch);
        logger.setLevel(Level.INFO);
        String host = "localhost";
        int port = 12345;
        try (Socket socket = new Socket(host, port)) {
            SocketAdapter adapter = new SocketAdapter(socket);
            Thread reader = new Thread(() -> {
                try {
                    String line;
                    while ((line = adapter.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (SocketException e) {
                    logger.info("Disconnected");
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Client read error", e);
                } finally {
                    adapter.close();
                }
            });
            reader.setDaemon(true);
            reader.start();
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            String input;
            while ((input = console.readLine()) != null) {
                adapter.sendLine(input);
                if ("/quit".equalsIgnoreCase(input.trim())) {
                    break;
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Client error", e);
        }
    }
}
