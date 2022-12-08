package Client;

import com.proto.maxscore.MaxScoreGrpc;
import com.proto.maxscore.MaxScoreRequest;
import com.proto.maxscore.MaxScoreResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MaxScoreClient2 {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50052).usePlaintext().build();
        MaxScoreGrpc.MaxScoreStub asynceClient = MaxScoreGrpc.newStub(channel);
        CountDownLatch latch =  new CountDownLatch(1);
        StreamObserver<MaxScoreRequest> stream = asynceClient.findMaxScore(new StreamObserver<MaxScoreResponse>() {
            @Override
            public void onNext(MaxScoreResponse value) {
                System.out.println("Server To Client >> Max Score is " + value.getHighScore());
            }
            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onCompleted() {
                System.out.println("Server is Done");
                latch.countDown();
            }
        });
        ArrayList<Integer> number = new ArrayList<Integer>();
        System.out.println("Input In");
        while (true){
            Scanner input = new Scanner(System.in);
            int input_num = input.nextInt();
            if(input_num == -1){
                break;
            }
            number.add(input_num);
        }
        for (int i = 0; i < number.size(); i++){
            try {
                MaxScoreRequest msr = MaxScoreRequest.newBuilder().setScore(number.get(i)).build();
                stream.onNext(msr);
                System.out.println("Client To Server : " + msr.getScore());
                TimeUnit.SECONDS.sleep(1);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        stream.onCompleted();
        try {
            latch.await(20L, TimeUnit.SECONDS);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
