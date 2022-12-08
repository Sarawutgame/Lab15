package Client;

import com.proto.average.AverageGrpc;
import com.proto.average.AverageRequest;
import com.proto.average.AverageResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AverageClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50052).usePlaintext().build();

        CountDownLatch latch = new CountDownLatch(1);

        AverageGrpc.AverageStub asynceClient = AverageGrpc.newStub(channel);
        //สร้างท่อ
        StreamObserver<AverageRequest> stream = asynceClient.computeAverage(new StreamObserver<AverageResponse>() {
            @Override
            public void onNext(AverageResponse value) {
                System.out.println("Avereage is " + value.getAverageOutput());
            }

            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onCompleted() {
                System.out.println("Completed sending from Server");
            }
        });

        //ส่งค่า
        AverageRequest avereq1 = AverageRequest.newBuilder().setNumber(1).build();
        AverageRequest avereq2 = AverageRequest.newBuilder().setNumber(2).build();
        AverageRequest avereq3 = AverageRequest.newBuilder().setNumber(3).build();
        AverageRequest avereq4 = AverageRequest.newBuilder().setNumber(4).build();
        AverageRequest avereq5 = AverageRequest.newBuilder().setNumber(5).build();
        AverageRequest avereq6 = AverageRequest.newBuilder().setNumber(6).build();
        AverageRequest avereq7 = AverageRequest.newBuilder().setNumber(7).build();
        AverageRequest avereq8 = AverageRequest.newBuilder().setNumber(8).build();
        AverageRequest avereq9 = AverageRequest.newBuilder().setNumber(9).build();
        AverageRequest avereq10 = AverageRequest.newBuilder().setNumber(10).build();

        System.out.println("Sent massage");
        stream.onNext(avereq1);
        stream.onNext(avereq2);
        stream.onNext(avereq3);
        stream.onNext(avereq4);
        stream.onNext(avereq5);
        stream.onNext(avereq6);
        stream.onNext(avereq7);
        stream.onNext(avereq8);
        stream.onNext(avereq9);
        stream.onNext(avereq10);

        stream.onCompleted();

        try {
            latch.await(3L, TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
