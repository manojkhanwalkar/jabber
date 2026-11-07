package util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class JSONUtil {

    static ObjectMapper mapper = new ObjectMapper();


    static {
        mapper.registerModule(new JavaTimeModule());
        // Default typing stays on (the persisted JSON format carries type ids for
        // collections), but only allow-listed types may be instantiated. The previous
        // enableDefaultTyping() accepted any class name, which allows gadget-chain RCE.
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfSubType(java.util.Collection.class)
                .allowIfSubType(java.util.Map.class)
                .allowIfSubType("java.time.")
                .allowIfSubType("data.")
                .allowIfSubType("decision.")
                .allowIfSubType("scheduler.")
                .allowIfSubType("slacker.")
                .allowIfSubType("matcher.")
                .allowIfSubType("vfs.")
                .allowIfSubType("logger.")
                .allowIfSubType("chatter.")
                .allowIfSubType("broker.")
                .build();
        mapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.OBJECT_AND_NON_CONCRETE);
    }


    public static String toJSON(Object object)
    {
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Object fromJSON(String json, Class<?> clazz)
    {
        try {
            return mapper.readValue(json,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String toPrettyJSON(Object object)
    {
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;
    }


}
