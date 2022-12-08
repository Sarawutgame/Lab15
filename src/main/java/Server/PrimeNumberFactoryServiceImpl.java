package Server;

import com.proto.prime.PrimeFactoryGrpc;
import com.proto.prime.PrimeFactoryRequest;
import com.proto.prime.PrimeFactoryResponse;
import io.grpc.stub.StreamObserver;

public class PrimeNumberFactoryServiceImpl extends PrimeFactoryGrpc.PrimeFactoryImplBase {

    @Override
    public void primeDecomposition(PrimeFactoryRequest request, StreamObserver<PrimeFactoryResponse> responseObserver) {
//        super.primeDecomposition(request, responseObserver);
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
//                System.out.println(number);
            }
            else{
                minus = minus + 1;
            }
        }
        responseObserver.onCompleted();

    }
}
