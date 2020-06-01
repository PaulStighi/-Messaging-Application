package Server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class ServerWorker extends Thread {
    private final Socket clientSocket;

    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            handleClientSocket();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException, InterruptedException {
        InputStream inputStream = clientSocket.getInputStream();
        OutputStream outputStream = clientSocket.getOutputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String buff;
        while((buff = reader.readLine()) != null) {
            if("exit".equalsIgnoreCase(buff)) {
                break;
            }
            outputStream.write(("Your message: " + buff + "\n").getBytes());
        }

        clientSocket.close();
    }
}
