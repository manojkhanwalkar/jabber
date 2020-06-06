package decision.data;

public class ServiceResponse {

    String serviceName;
    String serviceDecision;
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

    public String getServiceDecision() {
        return serviceDecision;
    }

    public void setServiceDecision(String serviceDecision) {
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
}

