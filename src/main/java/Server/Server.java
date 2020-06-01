package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    private final int serverPort;
    private ArrayList<ServerWorker> workers = new ArrayList<ServerWorker>();

    public Server (int serverPort) {
        this.serverPort = serverPort;
    }

    public ArrayList<ServerWorker> getWorkers() {
        return workers;
    }

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (true) {
                System.out.println("Accepting client connection");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted from " + clientSocket);
                ServerWorker worker = new ServerWorker(this, clientSocket);
                workers.add(worker);
                worker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeWorker(ServerWorker serverWorker) {
        workers.remove(serverWorker);
    }
}
