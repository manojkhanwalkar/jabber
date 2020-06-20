package decision.wf;

import decision.data.Event;
import decision.engine.RuleSet;
//import decision.engine.RulesContainer;
import decision.engine.RulesEvaluator;
import decision.wf.ServiceRuleSetTuple;
import decision.wf.Workflow;
import decision.wf.WorkflowManager;
import netscape.javascript.JSObject;
import util.JSONUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static decision.wf.Workflow.firstRuleSet;
import static decision.wf.Workflow.firstService;

public class WorkFlowReaderWriter {


    static       String dir = "/home/manoj/data/decision/";

    public static void main(String[] args) throws Exception {

       createWFFiles();

        get();

    }

    public static WorkflowManager get()
    {
        WorkflowManager workflowManager = new WorkflowManager();

        List<File> files = new ArrayList<>();

        listf(dir,files);

        System.out.println(files);

        files.stream().forEach(file-> {


            Workflow workflow = readFromFile(file.getAbsolutePath());


            workflowManager.addWorkFlow(workflow.getName(),workflow);
        });

        return workflowManager;

    }

    public static void listf(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if(fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    listf(file.getAbsolutePath(), files);
                }
            }
    }


    private static Workflow readFromFile(String fileName)
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File(fileName))))
        {

            String line = reader.readLine();

            System.out.println(line);

            Workflow workflow = (Workflow) JSONUtil.fromJSON(line,Workflow.class);

            return workflow;

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;

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
