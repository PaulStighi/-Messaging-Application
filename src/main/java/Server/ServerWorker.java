package Server;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;

public class ServerWorker extends Thread {
    private final Socket clientSocket;
    private String connectedUser;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String buff;
        while((buff = reader.readLine()) != null) {
            String[] tokens = StringUtils.split(buff);
            if(tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if(cmd.equalsIgnoreCase("exit")) {
                    break;
                }
                else if(cmd.equalsIgnoreCase("login")) {
                    handleLogin(outputStream, tokens);
                }
                else {
                    outputStream.write((cmd + " is not a command\nTry >help for the list of commands").getBytes());
                }
            }
        }

        clientSocket.close();
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if(tokens.length == 3) {
            String username = tokens[1];
            String password = tokens[2];

            if(username.equals("admin") && password.equals("admin")) {
                outputStream.write(("Logged in as " + username + "\n").getBytes());
                this.connectedUser = username;
            }
            else if(username.equals("guest") && password.equals("guest")) {
                outputStream.write(("Logged in as " + username + "\n").getBytes());
                this.connectedUser = username;
            }
            else {
                outputStream.write(("Login failed\n").getBytes());
            }
        }
    }
}
