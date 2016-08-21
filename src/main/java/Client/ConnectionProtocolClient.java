package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionProtocolClient {
    public BufferedReader in;
    public PrintWriter out;
    public boolean ready = false;
    public ConnectionProtocolClient() {
        try {
            Socket socket = new Socket(Constants.hostName, Constants.portNumber);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.ready = true;
        } catch (IOException e) {
            System.err.println("Can't connect to " + Constants.hostName);
        }
    }
}
