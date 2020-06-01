package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ChatClient {
	 private final String serverName;
	    private final int serverPort;
	    private Socket socket;
	    private InputStream serverIn;
	    private OutputStream serverOut;
	    private BufferedReader bufferedIn;

	    public ChatClient(String serverName, int serverPort) {
	        this.serverName = serverName;
	        this.serverPort = serverPort;
	    }
	
	
	
	 public boolean login(String login, String password) throws IOException {
	        String cmd = "login " + login + " " + password + "\n";
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

}
