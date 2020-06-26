package vfs;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TransactionData {


    HashMap<String,String> attrValueMap = new HashMap<>();

    Date date;

    public HashMap<String, String> getAttrValueMap() {
        return attrValueMap;
    }

    public void setAttrValueMap(HashMap<String, String> attrValueMap) {
        this.attrValueMap = attrValueMap;
    }


    @JsonIgnore
    public String get(String attr)
    {
        return  attrValueMap.get(attr);
    }

    @JsonIgnore
    public void put(String attr, String value)
    {
          attrValueMap.put(attr,value);
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
