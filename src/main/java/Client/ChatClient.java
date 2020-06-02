package Client;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChatClient {
    private final String serverName;
    private final int serverPort;
    private Socket socket;
    private InputStream serverIn;
    private OutputStream serverOut;
    private BufferedReader bufferedIn;

    private static ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
    private static ArrayList<MessageListener> messageListeners = new ArrayList<>();


    public ChatClient(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }


    public void msg(String sendTo, String msgBody) throws IOException {
        String cmd = "msg " + sendTo + " " + msgBody + "\n";
        serverOut.write(cmd.getBytes());
    }

    public boolean login(String Username, String Password) throws IOException {
        String cmd = "login " + Username + " " + Password + "\n";
        serverOut.write(cmd.getBytes());

        String response = bufferedIn.readLine();
        System.out.println("Response Line:" + response);

        if (response.equalsIgnoreCase("online " + Username)) {
            startMessageReader();
            return true;
        } else {
            return false;
        }
    }

    private void startMessageReader() {
        Thread t = new Thread() {
            @Override
            public void run() {
                readMessageLoop();
            }
        };
        t.start();
    }

    private void readMessageLoop() {
        try {
            String line;
            while ((line = bufferedIn.readLine()) != null) {
                String[] tokens = StringUtils.split(line);
                if (tokens != null && tokens.length > 0) {
                    String cmd = tokens[0];
                    if (cmd.equalsIgnoreCase("online")) {
                        handleOnline(tokens);
                    } else if (cmd.equalsIgnoreCase("offline")) {
                        handleOffline(tokens);
                    } else if (cmd.equalsIgnoreCase("msg")) {
                        String[] tokensMsg = StringUtils.split(line, null, 3);
                        handleMessage(tokensMsg);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void logout() throws IOException {
        String cmd = "logout\n";
        serverOut.write(cmd.getBytes());
    }


    public boolean connect() {
        try {
            this.socket = new Socket(serverName, serverPort);
            System.out.println("Client port is " + socket.getLocalPort());
            this.serverOut = socket.getOutputStream();
            this.serverIn = socket.getInputStream();
            this.bufferedIn = new BufferedReader(new InputStreamReader(serverIn));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void handleMessage(String[] tokensMsg) {
        String Username = tokensMsg[1];
        String msgBody = tokensMsg[2];

        for (MessageListener listener : messageListeners) {
            listener.onMessage(Username, msgBody);
        }
    }

    private void handleOffline(String[] tokens) {
        String Username = tokens[1];
        for (UserStatusListener listener : userStatusListeners) {
            listener.offline(Username);
        }
    }

    private void handleOnline(String[] tokens) {
        String Username = tokens[1];
        for (UserStatusListener listener : userStatusListeners) {
            listener.online(Username);
        }
    }

    public void addUserStatusListener(UserStatusListener listener) {
        userStatusListeners.add(listener);
    }

    public void removeUserStatusListener(UserStatusListener listener) {
        userStatusListeners.remove(listener);
    }

    public void addMessageListener(MessageListener listener) {
        messageListeners.add(listener);
    }

    public void removeMessageListener(MessageListener listener) {
        messageListeners.remove(listener);
    }

}
