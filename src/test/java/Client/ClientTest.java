package Client;

import org.codehaus.groovy.tools.shell.ExitNotification;

import static org.testng.Assert.*;

public class ClientTest {
    @org.testng.annotations.Test
    public void testClientConnection() throws Exception {
        Client client = new Client();
        client.Connect(new ConnectionProtocolClient());
        assertTrue(client.connected);
    }
    @org.testng.annotations.Test
    public void testSendMessage() throws Exception {
        Client client = new Client();
        client.Connect(new ConnectionProtocolClient());
        client.SendMessage("");
        client.SendMessage("j");
        client.SendMessage("\n");
    }

}