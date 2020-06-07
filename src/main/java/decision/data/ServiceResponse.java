package decision.data;

import java.util.HashMap;
import java.util.Map;

public class ServiceResponse {

    String serviceName;
    HashMap<String,String> serviceDecision = new HashMap<>();
    String rawResponse;
    String responseId;

    public ServiceResponse() {
    }


    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public HashMap<String, String> getServiceDecision() {
        return serviceDecision;
    }

    public void setServiceDecision(HashMap<String, String> serviceDecision) {
        this.serviceDecision = serviceDecision;
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
                ", serviceDecision=" + serviceDecision +
                ", rawResponse='" + rawResponse + '\'' +
                ", responseId='" + responseId + '\'' +
                '}';
    }
}

