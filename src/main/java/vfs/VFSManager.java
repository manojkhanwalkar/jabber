package vfs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VFSManager {


    //TODO - create a map of attribute and VelocityStatsManager

    Map<String,VelocityStatsManager> attrVelocityMap = new HashMap<>();

    String[] attributes = { "ccv" , "ip"};


    public void init() {

        CCVIPValues.init(attributes);

        Arrays.stream(attributes).forEach(attr->{

            attrVelocityMap.put(attr, new VelocityStatsManager(attr));
        });

    }


    public static void main(String[] args) {

        VFSManager vfsManager = new VFSManager();

        vfsManager.init();

        System.out.println(vfsManager.attrVelocityMap);

    }
}
