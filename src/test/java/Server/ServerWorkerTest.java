package Server;

import junit.framework.TestCase;
import org.junit.Assert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerWorkerTest extends TestCase {
    private int serverPort = 1400;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Server server;
    private ServerWorker serverWorker;
    private BufferedReader reader;

    public ServerWorkerTest(String s) {
        super(s);
    }

    public void setUp() {
        try {
            server = new Server(serverPort);
            server.start();
            serverSocket = new ServerSocket(serverPort);
            clientSocket = serverSocket.accept();
            serverWorker = new ServerWorker(server, clientSocket);
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testHandleClientSocket() throws Exception {
        outputStream.write(("login guest guest").getBytes());
        Assert.assertEquals("guest", serverWorker.getConnectedUser());

        outputStream.write(("msg admin Salut!").getBytes());
        Assert.assertEquals("msg admin Salut!", reader.readLine());

        outputStream.write(("logout").getBytes());
        Assert.assertEquals("offline guest", reader.readLine());

        outputStream.write(("help").getBytes());
        Assert.assertEquals("The accepted commands are:", reader.readLine());
    }
}