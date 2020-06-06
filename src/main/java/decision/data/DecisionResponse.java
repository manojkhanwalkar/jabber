package decision.data;

import java.util.ArrayList;
import java.util.List;

public class DecisionResponse {

    String requestId;
    String responseId;
    List<ServiceResponse> rawResponses = new ArrayList<>();

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

    public List<ServiceResponse> getRawResponses() {
        return rawResponses;
    }

    public void setRawResponses(List<ServiceResponse> rawResponses) {
        this.rawResponses = rawResponses;
    }
}
