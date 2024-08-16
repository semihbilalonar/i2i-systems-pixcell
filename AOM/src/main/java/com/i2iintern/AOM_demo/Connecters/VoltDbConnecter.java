package com.i2iintern.AOM_demo.Connecters;
import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;
import java.io.IOException;

public class VoltDbConnecter {
    private final String dbUrl = "35.198.145.16";
    private final int port = 32776;
    public Client client() throws IOException {
        Client client;
        ClientConfig config;
        config = new ClientConfig();
        client = ClientFactory.createClient(config);
        client.createConnection(dbUrl, port);
        return client;

    }

}
