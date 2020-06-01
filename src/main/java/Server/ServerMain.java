package Server;

public class ServerMain {
    public static void main(String[] args) {
        int port = 1400;
        Server server = new Server(port);
        server.start();
    }
}
