package vfs;

import matcher.Profiles;
import util.Connection;
import util.JSONUtil;

import java.util.Date;

public class VFSClient {

    public static void main(String[] args) {

        CCVIPValues.init("ccv","ip");

        for (int i=0;i<100;i++) {

            TransactionData transactionData = new TransactionData();
            transactionData.setCcv(CCVIPValues.get("ccv"));
            transactionData.setIp(CCVIPValues.get("ip"));

            transactionData.setDate(new Date());


            VelocityStatsList velocityStats = submit(transactionData);

            System.out.println(velocityStats);

        }

    }


    private static VelocityStatsList submit(TransactionData transactionData)
    {
        // send to scheduler a jar file and client name and get back a job id .

        // System.out.println(profileId);

        Connection app = new Connection("https://localhost:8880/");

        String result = app.sendSimple(JSONUtil.toJSON(transactionData),"transaction");

        return (VelocityStatsList) JSONUtil.fromJSON(result,VelocityStatsList.class);

    }


}
