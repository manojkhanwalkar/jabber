package decision;

import decision.data.Event;
import decision.engine.RuleSet;
//import decision.engine.RulesContainer;
import decision.engine.RulesEvaluator;
import decision.wf.ServiceRuleSetTuple;
import decision.wf.Workflow;
import util.JSONUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static decision.wf.Workflow.firstRuleSet;
import static decision.wf.Workflow.firstService;

public class RulesTester {

    public static void main(String[] args) throws Exception {

        createWFFiles();


        //      WorkflowManager workflowManager = new WorkflowManager();

    }

    private static void writeToFile(File file, Workflow workflow) throws Exception
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            String str = JSONUtil.toJSON(workflow);

            writer.write(str);
            writer.flush();



        }
    }


    public static void createWFFiles() throws Exception
    {
        String dir = "/home/manoj/data/decision/";


        {
            File wf = new File(dir+"/client2/wf3");

                RuleSet inputRuleSet = RuleSet.inputRuleSet(firstRuleSet);

                ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple(firstService, inputRuleSet);


                Workflow workflow = new Workflow("WF3");
                workflow.addFirst(serviceRuleSetTuple);

                RuleSet usservice = RuleSet.inputRuleSet1("USServiceRuleSet");

                ServiceRuleSetTuple USServiceRulesTuple = new ServiceRuleSetTuple("USService", usservice);


                workflow.add("USService", usservice);

               writeToFile(wf,workflow);


        }

        {
            File wf = new File(dir+"/client1/wf1");

            RuleSet inputRuleSet = RuleSet.inputRuleSet(firstRuleSet);

            ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple(firstService,inputRuleSet);


            Workflow workflow = new Workflow("WF1");

            workflow.addFirst(serviceRuleSetTuple);

            writeToFile(wf,workflow);



        }

        {

            File wf = new File(dir+"/client1/wf2");
            RuleSet inputRuleSet = RuleSet.inputRuleSet1(firstRuleSet);

            ServiceRuleSetTuple serviceRuleSetTuple = new ServiceRuleSetTuple(firstService,inputRuleSet);

            Workflow workflow = new Workflow("WF2");

            workflow.addFirst(serviceRuleSetTuple);

            writeToFile(wf,workflow);
        }



    }




}
