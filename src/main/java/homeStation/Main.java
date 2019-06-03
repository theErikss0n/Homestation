package homeStation;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;

public class Main {

    public static void main(String[] args) throws IOException {
        URI baseUri = UriBuilder.fromUri("http://localhost/").port(9998).build();

        // start server with rest-ressources
        ResourceConfig resourceConfig = new ResourceConfig(HomeResource.class, SensordataRessource.class);
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);

        // stop server prompt
        System.out.println("Press enter to stop the server...");
        System.in.read();
    }
}

