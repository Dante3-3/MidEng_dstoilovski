package org.example;


import io.grpc.stub.StreamObserver;
import org.example.ElectionProto.ElectionData;
import org.example.ElectionProto.ElectionDataRequest;
import org.example.ElectionProto.ElectionDataResponse;

/**
 * Diese Klasse hat sozusagen die Logik. Sie ist dafür zuständig dem Server zu erklären wie er die erhaltenen
 * Daten richtig interpretieren bzw in diesem Fall darstellen soll.
 */
public class ElectionServiceImpl extends ElectionServiceGrpc.ElectionServiceImplBase {

    @Override
    public void transferElectionData(ElectionDataRequest request, StreamObserver<ElectionDataResponse> responseObserver) {
        ElectionData data = request.getData();

        System.out.println("Received election data for region: " + data.getRegionName());
        System.out.println("Timestamp: " + data.getTimestamp());

        for (ElectionProto.Party party : data.getCountingDataList()) {
            System.out.println("Party: " + party.getPartyID() + ", Votes: " + party.getAmountVotes());
        }

        ElectionDataResponse response = ElectionDataResponse.newBuilder()
                .setMessage("Election data received successfully for region: " + data.getRegionName())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
