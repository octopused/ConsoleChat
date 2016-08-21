package Server;

/**
 * Created by ruslanka on 19.08.16.
 */
public class ServerRunner {
    public static void main(String[] args) {
        new Server(new ConnectionProtocolServer());
    }
}
