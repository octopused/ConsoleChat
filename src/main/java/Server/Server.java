package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ruslanka on 19.08.16.
 */
public class Server {
    LinkedList<String> prevMessages = new LinkedList<String>();
    Map<String, Connection> connections = new HashMap<String, Connection>();

    // Open server to accept clients
    public Server(ConnectionProtocolServer connectionProtocol) {
        while (true) {
            connectionProtocol.WaitingForAClient();
            Connection connection = new Connection(connectionProtocol.in, connectionProtocol.out);
            connection.start();
        }
    }

    // Store prev X number of messages
    private void storeMessage(String msg) {
        while (prevMessages.size() >= Constants.maxMessagesStored) {
            prevMessages.removeLast();
        }
        prevMessages.addFirst(msg);
    }

    // Get string of last X messages
    private String GetPrevMessages() {
        String prevMessagesInString = "";
        for (String singleMessage : prevMessages) {
            prevMessagesInString += singleMessage + "\n";
        }
        if (prevMessagesInString != "") {
            prevMessagesInString = "Previous " + Constants.maxMessagesStored + " messages:\n" + prevMessagesInString;
        }
        return prevMessagesInString;
    }

    // Thread class to communicate with client
    private class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private String userName = "";
        private String messageFromUser = "";

        public Connection(BufferedReader in, PrintWriter out) {
            this.in = in;
            this.out = out;
        }

        // Promt username
        public boolean Authenticate() {
            boolean success = false;
            if (connections.containsKey(this.messageFromUser)) {
                out.println("User " + this.messageFromUser + " already logged the chat. Pick another username.");
            } else {
                this.userName = this.messageFromUser;
                connections.put(this.userName, this);
                success = true;
            }
            return success;
        }

        // Send a single message to all authenticated users
        private void SendMessageToChat(String msg) {
            for (Map.Entry<String, Connection> entry : connections.entrySet()) {
                entry.getValue().out.println(msg);
            }
        }

        // Simple commands
        private void CloseConnection() {
            out.println("closing...");
        }
        private void GetCommandList() {
            out.println("command list...");
        }

        // Find a compatible command and execute it
        private boolean PerformCommand() {
            boolean success = false;
            if (this.messageFromUser.equalsIgnoreCase("/exit")) {
                this.CloseConnection();
                success = true;
            } else if (this.messageFromUser.equalsIgnoreCase("/help")) {
                this.GetCommandList();
                success = true;
            }
            return success;
        }

        @Override
        public void run() {
            try {
                out.println("Connected. Please type your wished username...");
                this.messageFromUser = in.readLine();
                while (!this.Authenticate()) {
                    this.messageFromUser = in.readLine();
                }

                out.println(GetPrevMessages());
                this.SendMessageToChat("User " + this.userName + " has joined the chat");

                while (true) {
                    this.messageFromUser = in.readLine();
                    if (this.messageFromUser != null) {
                        if (!this.PerformCommand()) {
                            storeMessage(this.userName + ": " + this.messageFromUser);
                            this.SendMessageToChat(this.userName + ": " + this.messageFromUser);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
