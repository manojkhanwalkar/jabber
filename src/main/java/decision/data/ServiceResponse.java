package decision.data;

import java.util.HashMap;

public class ServiceResponse {

    String serviceName;
    HashMap<String,String> serviceDecisionElements = new HashMap<>();
    String rawResponse;
    String responseId;
    String serviceDecision;

    public String getServiceDecision() {
        return serviceDecision;
    }

    public void setServiceDecision(String serviceDecision) {
        this.serviceDecision = serviceDecision;
    }

    public ServiceResponse() {
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public HashMap<String, String> getServiceDecisionElements() {
        return serviceDecisionElements;
    }

    public void setServiceDecisionElements(HashMap<String, String> serviceDecisionElements) {
        this.serviceDecisionElements = serviceDecisionElements;
    }

    public String getRawResponse() {
        return rawResponse;
    }

    public void setRawResponse(String rawResponse) {
        this.rawResponse = rawResponse;
    }

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    @Override
    public String toString() {
        return "ServiceResponse{" +
                "serviceName='" + serviceName + '\'' +
                ", serviceDecisionElements=" + serviceDecisionElements +
                ", rawResponse='" + rawResponse + '\'' +
                ", responseId='" + responseId + '\'' +
                ", serviceDecision='" + serviceDecision + '\'' +
                '}';
    }
}

