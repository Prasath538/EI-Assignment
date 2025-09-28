import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger("ChatApp");
        ConsoleHandler ch = new ConsoleHandler();
        ch.setLevel(Level.ALL);
        logger.addHandler(ch);
        logger.setLevel(Level.INFO);
        int port = 12345;
        AtomicBoolean running = new AtomicBoolean(true);
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            serverSocket.setSoTimeout(2000);
            logger.info("Server started on port " + port);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                running.set(false);
                try {
                    serverSocket.close();
                } catch (IOException e) {
                }
                pool.shutdownNow();
                logger.info("Shutdown hook executed");
            }));
            while (running.get()) {
                try {
                    Socket socket = serverSocket.accept();
                    pool.submit(() -> {
                        try {
                            SocketAdapter adapter = new SocketAdapter(socket);
                            UserSession session = new UserSession(adapter);
                            session.run();
                        } catch (IOException e) {
                            logger.log(Level.WARNING, "Failed to start session", e);
                            try {
                                socket.close();
                            } catch (IOException ex) {
                            }
                        }
                    });
                } catch (SocketTimeoutException e) {
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Server socket error", e);
                }
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not start server", e);
        } finally {
            pool.shutdownNow();
            logger.info("Server stopped");
        }
    }
}
