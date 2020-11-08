
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import io.vertx.mutiny.ext.web.client.HttpRequest;
import io.vertx.mutiny.ext.web.client.WebClient;
import io.vertx.ext.web.client.WebClientOptions;
import io.vertx.mutiny.core.MultiMap;
import io.vertx.mutiny.core.Vertx;
import io.vertx.mutiny.core.buffer.Buffer;
import io.smallrye.mutiny.Uni;

@Path("{any: .*}")
public class SvelteController {
    private static final String HOSTNAME = "localhost";
    private static final int PORT = 3000;
    private static final boolean SSL = false;
    private static final boolean TRUST_ALL = true;

    private WebClient client;

    @Inject
    Vertx vertx;


    @PostConstruct
    void initialize() {
        this.client = WebClient.create(
            vertx,
            new WebClientOptions()
                .setDefaultHost(HOSTNAME)
                .setDefaultPort(PORT)
                .setSsl(SSL)
                .setTrustAll(TRUST_ALL));
    }

    @GET
    public Uni<Response> get(@Context UriInfo uri, @Context HttpHeaders headers) throws MalformedURLException {

        HttpRequest<Buffer> buffer = client.get(getPathAndQuery(uri));
        MultiMap requestHeaders = buffer.headers();

        headers.getRequestHeaders().forEach((headerName,values)->{
            values.forEach((value)->{
                requestHeaders.set(headerName, value);
            });
        });

        return buffer
                .send()
                .map(resp -> {
                    ResponseBuilder builder = Response
                        .ok(resp.bodyAsString())
                        .status(resp.statusCode());

                    resp.headers().forEach((header)->{
                        builder.header(header.getKey(),header.getValue());
                    });
                    return builder.build();
                });
    }


    private String getPathAndQuery(UriInfo uri) throws MalformedURLException {
        MultivaluedMap<String, String> queriesMap = uri.getQueryParameters();
        String[] queries = new String[queriesMap.size()];
        int i = 0;
        for (Entry<String, List<String>> item : queriesMap.entrySet()) {
            queries[i] = item.getKey() + "=" + item.getValue().get(0);
            i++;
        }
        String queryString = String.join("&", queries);
        String pathAndQuery = uri.getPath() + (queryString.equals("") ? "" : queryString);
        if (!pathAndQuery.startsWith("/"))
            pathAndQuery = "/" + pathAndQuery;

        return pathAndQuery;
    }
    
}
