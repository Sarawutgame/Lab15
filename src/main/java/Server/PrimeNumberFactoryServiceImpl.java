package Server;
import com.proto.prime.PrimeFactoryGrpc;
import com.proto.prime.PrimeFactoryRequest;
import com.proto.prime.PrimeFactoryResponse;
import io.grpc.stub.StreamObserver;
public class PrimeNumberFactoryServiceImpl extends PrimeFactoryGrpc.PrimeFactoryImplBase {
    //เป็นชื่อ func ใน rpc ที่อยู่ใน proto โดยมีค่าทีเข้าเป็น PrimeFactoryRequest
    @Override
    public void primeDecomposition(PrimeFactoryRequest request, StreamObserver<PrimeFactoryResponse> responseObserver) {
        // รับค่า
        int number = request.getNumber();
        int minus = 2;
        while (number != 1){
            if(number % minus == 0){
                // ส่ง Response
                PrimeFactoryResponse response = PrimeFactoryResponse.newBuilder().setPrime(minus).build();
                responseObserver.onNext(response);
                number = number / minus;
                minus = 2;
            }
            else{
                minus = minus + 1;
            }
        }
        responseObserver.onCompleted();
    }
}
