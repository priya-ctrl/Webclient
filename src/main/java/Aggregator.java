import networking.WebClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Send the list of tasks to our workers and aggregate the responses.
public class Aggregator  {
    private WebClient webClient;
    public Aggregator(){
        this.webClient = new WebClient();
    }
    // Take a list of HTTP worker addresses and tasks
    public List<String> sendTasksToWorkers(List<String> workersAddresses, List<String> tasks){
        CompletableFuture<String> [] futures = new CompletableFuture[workersAddresses.size()];
        for (int i = 0; i < workersAddresses.size() ; i++){
            String workerAddress = workersAddresses.get(i);
            String task = tasks.get(i);
//Conversion of string representation into a byte array
            byte[] requestPayLoad = task.getBytes();
            futures[i] = webClient.sendTask(workerAddress,requestPayLoad);
        }

//        List<String> results = new ArrayList<>();
//        for (int i = 0 ; i < tasks.size(); i++){
//            results.add(futures[i].join());
    // Using the streaming API instead - allocate an array list to run in a loop wait and get a response
        List<String> results = Stream.of(futures).map(CompletableFuture::join).collect(Collectors.toList());
            return results;
    }

}
