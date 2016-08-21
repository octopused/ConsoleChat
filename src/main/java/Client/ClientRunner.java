package Client;

import java.util.Scanner;

public class ClientRunner {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String message;
        Client client = new Client();
        if (client.Connect(new ConnectionProtocolClient())) {
            while (true) {
                message = scanner.nextLine();
                if (message != null) {
                    client.SendMessage(message);
                }
            }
        }
    }
}
