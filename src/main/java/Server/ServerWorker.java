package Server;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorker extends Thread {
    private final Server server;
    private final Socket clientSocket;
    private String connectedUser;
    private InputStream inputStream;
    private OutputStream outputStream;

    public ServerWorker(Server server, Socket clientSocket) {
        this.server = server;
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
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String buff;
        while((buff = reader.readLine()) != null) {
            String[] tokens = StringUtils.split(buff, null, 3);
            if(tokens != null && tokens.length > 0) {
                String cmd = tokens[0];
                if(cmd.equalsIgnoreCase("exit") || cmd.equalsIgnoreCase("logout")) {
                    handleLogout();
                    break;
                }
                else if(cmd.equalsIgnoreCase("login")) {
                    handleLogin(outputStream, tokens);
                }
                else if(cmd.equalsIgnoreCase("msg")) {
                    handleMessage(tokens);
                }
                else {
                    outputStream.write((cmd + " is not a command\nTry >help for the list of commands\n").getBytes());
                }
            }
        }

        clientSocket.close();
    }

    private void handleMessage(String[] tokens) throws IOException {
        String sendTo = tokens[1];
        if(tokens.length < 3) {
            outputStream.write(("Sending message failed\n").getBytes());
        }
        else {
            String body = tokens[2];

            ArrayList<ServerWorker> workersList = server.getWorkers();
            for (ServerWorker sw : workersList) {
                if (sendTo.equalsIgnoreCase(sw.getConnectedUser())) {
                    sw.send(connectedUser + ": " + body + "\n");
                }
            }
        }
    }

    private void handleLogout() throws IOException {
        ArrayList<ServerWorker> workersList = server.getWorkers();
        for(ServerWorker sw : workersList) {
            if(!sw.getConnectedUser().equals(connectedUser) && (sw.getConnectedUser() != null)) {
                sw.send(connectedUser + " is offline\n");
            }
        }
        server.removeWorker(this);
        clientSocket.close();
    }

    private void handleLogin(OutputStream outputStream, String[] tokens) throws IOException {
        if(tokens.length == 3) {
            String username = tokens[1];
            String password = tokens[2];
            ArrayList<ServerWorker> workersList = server.getWorkers();

            if(username.equals("admin") && password.equals("admin")) {
                outputStream.write(("Logged in as " + username + "\n").getBytes());
                this.connectedUser = username;
                for(ServerWorker sw : workersList) {
                    if((sw.getConnectedUser() != null) && !sw.getConnectedUser().equals(connectedUser)) {
                        sw.send(connectedUser + " is online\n");
                    }
                }
            }
            else if(username.equals("guest") && password.equals("guest")) {
                outputStream.write(("Logged in as " + username + "\n").getBytes());
                this.connectedUser = username;
                for(ServerWorker sw : workersList) {
                    if( (sw.getConnectedUser() != null) && !sw.getConnectedUser().equals(connectedUser)) {
                        sw.send(connectedUser + " is online\n");
                    }
                }
            }
            else {
                outputStream.write(("Login failed\n").getBytes());
            }
        }
    }

    private void send(String message) throws IOException {
        if(connectedUser != null) {
            outputStream.write(message.getBytes());
        }
    }

    public String getConnectedUser() {
        return connectedUser;
    }
}
