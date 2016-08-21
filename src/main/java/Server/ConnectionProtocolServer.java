package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ruslanka on 21.08.16.
 */
public class ConnectionProtocolServer {
    public BufferedReader in;
    public PrintWriter out;
    private ServerSocket server;
    public ConnectionProtocolServer() {
        try {
            server = new ServerSocket(Constants.portNumber);
            System.out.println("Server started successfully on " + Constants.portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void WaitingForAClient() {
        try {
            Socket socket = server.accept();
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
