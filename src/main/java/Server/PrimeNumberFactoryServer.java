package Server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class PrimeNumberFactoryServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50052)
                .addService(new PrimeNumberFactoryServiceImpl()).build();
        server.start();
        System.out.println("Server Start");
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("receive shutdown");
            server.shutdown();
        }));
        server.awaitTermination();

    }
}
