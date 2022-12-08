package Server;
import com.proto.average.AverageGrpc;
import com.proto.average.AverageRequest;
import com.proto.average.AverageResponse;
import io.grpc.stub.StreamObserver;
//extends มาตามที่ Gen โดย proto
public class AverageServiceImpl extends AverageGrpc.AverageImplBase {
    //เป็นชื่อ func ใน rpc ที่อยู่ใน proto โดยมีค่าทีเข้าเป็น computeAverage
    @Override
    public StreamObserver<AverageRequest> computeAverage(StreamObserver<AverageResponse> responseObserver) {
        //การทำท่อ
        StreamObserver<AverageRequest> stream = new StreamObserver<AverageRequest>(){
            double sumNumber = 0;
            int count = 0;
            @Override
            public void onNext(AverageRequest value) {
                count++;
                System.out.println("Message No."+ count +" From Client >> Number is " + value.getNumber());
                sumNumber = sumNumber + value.getNumber();
            }
            @Override
            public void onError(Throwable t) {}
            @Override
            public void onCompleted() {
                double avg = (sumNumber * 1.0) / count;
                AverageResponse response = AverageResponse.newBuilder()
                        .setAverageOutput(avg).build();
                responseObserver.onNext(response);
                responseObserver.onCompleted();
                System.out.println("Finish");
            }
        };
        return stream;
    }
}
