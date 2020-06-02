package Client;

import Server.Server;
import Server.ServerWorker;
import junit.framework.TestCase;
import org.junit.Assert;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatClientTest extends TestCase {
    private ChatClient chatClient;
    private int serverPort = 1400;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Server server;
    private ServerWorker serverWorker;
    private BufferedReader reader;

    public void setUp() {
        try {
            chatClient = new ChatClient("localhost", serverPort);
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

    public void testMsg() {
        try {
            chatClient.msg("admin", "Buna!");
            Assert.assertEquals("msg admin Buna!", reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void testLogin() {
        try {
            chatClient.login("admin", "admin");
            Assert.assertEquals("online admin", reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testLogout() {
        try {
            chatClient.logout();
            Assert.assertEquals("offline admin", reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testAddUserStatusListener() {
        chatClient.addUserStatusListener(new UserListPane(chatClient));
        Assert.assertEquals(1, chatClient.getUserStatusListeners().size());
    }

    public void testAddMessageListener() {
        chatClient.addMessageListener(new MessagePane(chatClient, "admin"));
        Assert.assertEquals(1, chatClient.getMessageListeners().size());
    }
}