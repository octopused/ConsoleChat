package Server;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by ruslanka on 19.08.16.
 */
public class ServerTest {
    @Test
    public void open() throws Exception {
        new Server(new ConnectionProtocolServer());
    }

}