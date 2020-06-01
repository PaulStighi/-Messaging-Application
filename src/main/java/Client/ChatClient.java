package Client;

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
	    
	    private ArrayList<UserStatusListener> userStatusListeners = new ArrayList<>();
	    private ArrayList<MessageListener> messageListeners = new ArrayList<>();


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

	        if ("ok login".equalsIgnoreCase(response)) {
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
