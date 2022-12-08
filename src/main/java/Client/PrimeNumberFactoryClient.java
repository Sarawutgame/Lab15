package Client;

import com.proto.prime.PrimeFactoryGrpc;
import com.proto.prime.PrimeFactoryRequest;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class PrimeNumberFactoryClient {
    public static void main(String[] args) {
        System.out.println("Hello GRPC Client");
        // กำหนด Add
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50052).usePlaintext()
                .build();
        // ทำท่อเชื่่อม
        PrimeFactoryGrpc.PrimeFactoryBlockingStub stream = PrimeFactoryGrpc
                .newBlockingStub(channel);
        //เลขที่ใส่
        int num_in = 108;
        // สร้าง Request
        PrimeFactoryRequest request = PrimeFactoryRequest.newBuilder().setNumber(num_in).build();

        System.out.println("Client To Server : " + num_in);

        //เรียกใช้ Service ตาม proto
        stream.primeDecomposition(request).forEachRemaining(primeFactoryResponse -> {
            System.out.println("Server To Client : "+ primeFactoryResponse.getPrime());
        });
    }
}
