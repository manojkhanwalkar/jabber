package decision.data;

import java.util.ArrayList;
import java.util.List;

public class DecisionResponse {


    String finalDecision;

    public String getFinalDecision() {
        return finalDecision;
    }

    public void setFinalDecision(String finalDecision) {
        this.finalDecision = finalDecision;
    }

    public DecisionResponse() {
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public ArrayList<ServiceResponse> getRawResponses() {
        return rawResponses;
    }

    public void setRawResponses(ArrayList<ServiceResponse> rawResponses) {
        this.rawResponses = rawResponses;
    }

    public void addRawResponse(ServiceResponse serviceResponse) {
        rawResponses.add(serviceResponse);
    }

    String requestId;
    String responseId;
    ArrayList<ServiceResponse> rawResponses = new ArrayList<>();



}
