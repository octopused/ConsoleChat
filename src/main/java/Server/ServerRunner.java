package Server;

public class ServerRunner {
    public static void main(String[] args) {
        new Server(new ConnectionProtocolServer());
    }
}
