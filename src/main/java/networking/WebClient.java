package networking;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class WebClient {
    private HttpClient client;
    // instantiate the HTTP client by calling the new builder method
    public WebClient(){
        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }
    // Creation of a task method to create a byte response
    public CompletableFuture<String> sendTask(String url, byte[] requestPlayload){
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofByteArray(requestPlayload))
                .uri(URI.create(url))
                .build();
        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
