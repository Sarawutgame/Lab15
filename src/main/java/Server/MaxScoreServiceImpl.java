package Server;

import com.proto.maxscore.MaxScoreGrpc;
import com.proto.maxscore.MaxScoreRequest;
import com.proto.maxscore.MaxScoreResponse;
import io.grpc.stub.StreamObserver;

public class MaxScoreServiceImpl extends MaxScoreGrpc.MaxScoreImplBase {
    @Override
    public StreamObserver<MaxScoreRequest> findMaxScore(StreamObserver<MaxScoreResponse> responseObserver) {
//        return super.findMaxScore(responseObserver);
        StreamObserver<MaxScoreRequest> stream = new StreamObserver<MaxScoreRequest>() {
            int count = 0;
            int maxScore = 0;
            @Override
            public void onNext(MaxScoreRequest value) {
                count++;
                if(maxScore < value.getScore()){
                    maxScore = value.getScore();
                }
                MaxScoreResponse response = MaxScoreResponse.newBuilder()
                        .setHighScore(maxScore).build();
                responseObserver.onNext(response);
            }
            @Override
            public void onError(Throwable t) {}
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
        return stream;
    }
}
