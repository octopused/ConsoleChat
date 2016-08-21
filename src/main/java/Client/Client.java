package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Client {
    BufferedReader in;
    PrintWriter out;
    Boolean connected = false;

    public boolean Connect(ConnectionProtocolClient connectionProtocol) {
        if (connectionProtocol.ready) {
            this.in = connectionProtocol.in;
            this.out = connectionProtocol.out;
            MessageReciever messageReciever = new MessageReciever(in);
            messageReciever.start();
            this.connected = true;
        } else {
            System.err.println("Can't connect");
        }
        return this.connected;
    }

    public void SendMessage(String message) {
        if (this.connected) {
            this.out.println(message);
        }
    }

    private class MessageReciever extends Thread {
        BufferedReader in;
        public MessageReciever(BufferedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            try {
                String messageFromServer;
                while (true) {
                    messageFromServer = in.readLine();
                    if (messageFromServer != null) {
                        System.out.println(messageFromServer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
