package decision.wf;

import decision.data.DecisionRequest;
import decision.data.DecisionResponse;
import decision.data.ServiceResponse;
import decision.engine.RuleSet;
import decision.engine.RulesEvaluator;
import decision.service.Service;
import decision.service.ServiceLocator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static decision.engine.Rule.lastStep;

public class WorkflowEvaluator {

    static ServiceResponse process(String next, DecisionRequest request, DecisionResponse decisionResponse)
    {

        String[] services = next.split(",");

        Future<ServiceResponse>[] futures = new Future[services.length];

        for (int i=0;i<services.length;i++)
        {
            futures[i]= CompletableFuture.supplyAsync(()->{
                Service service = ServiceLocator.getInstance().get(next).get();
                ServiceResponse serviceResponse=service.evaluate(request.getEvent());

                return serviceResponse;
            })  ;
        }

        List<ServiceResponse> serviceResponses = new ArrayList<>();
        for (Future<ServiceResponse> future : futures)
        {
            ServiceResponse serviceResponse = null;
            try {
                serviceResponse = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            decisionResponse.addRawResponse(serviceResponse);
            serviceResponses.add(serviceResponse);

        }

        return merge(serviceResponses);

    }

    static ServiceResponse merge(List<ServiceResponse>serviceResponses)
    {
        ServiceResponse mergedResponse = new ServiceResponse();
        for (ServiceResponse serviceResponse : serviceResponses)
        {
            mergedResponse.addServiceDecisionElements(serviceResponse.getServiceDecisionElements());
        }

        return mergedResponse;
    }

    static DecisionResponse process(Workflow wf, DecisionRequest request)
    {
        List<ServiceRuleSetTuple> workflow = wf.serviceRuleSetTuples;

        DecisionResponse decisionResponse = new DecisionResponse();

        decisionResponse.setRequestId(request.getRequestId());
        decisionResponse.setResponseId(UUID.randomUUID().toString());



        String next =  wf.first.getServiceName();
        RuleSet ruleSet = wf.first.getRuleSet();

        while(true)
        {
            ServiceResponse serviceResponse = process(next,request,decisionResponse);

            if (ruleSet==null) // last service processed
                break;

            next =  RulesEvaluator.evaluate(ruleSet,serviceResponse);

           if (next.equals(lastStep))
               break;

           int curr = getLocation(next,workflow);
           if (curr<0)
               ruleSet=null;
           else
                ruleSet = workflow.get(curr).getRuleSet();

        }



        wf.OnTerminate(decisionResponse);
        return decisionResponse;




    }

    private static int getLocation(String name,List<ServiceRuleSetTuple> workflow )
    {
        for (int i=0;i<workflow.size();i++)
        {
            if (workflow.get(i).serviceName.equalsIgnoreCase(name))
                return i;
        }

        return -1;
    }
}
