package util;

import decision.engine.Rule;
import decision.engine.RuleSet;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class JSONUtilTest {

    public static class Holder {
        public List<Object> items = new ArrayList<>();
    }

    @Test
    public void projectTypesWithCollectionsRoundTrip() {
        RuleSet ruleSet = new RuleSet();
        ruleSet.getRules().add(new Rule());
        String json = JSONUtil.toJSON(ruleSet);
        assertNotNull(json);
        RuleSet back = (RuleSet) JSONUtil.fromJSON(json, RuleSet.class);
        assertNotNull(back);
        assertEquals(1, back.getRules().size());
    }

    @Test
    public void arbitraryClassNamesAreRejected() {
        // A type id outside the allow-list must not be instantiated. java.util.Date is
        // harmless but stands in for a gadget class: the old enableDefaultTyping() accepted it.
        String json = "{\"items\":[\"java.util.ArrayList\",[[\"java.util.Date\",0]]]}";
        com.fasterxml.jackson.databind.ObjectMapper permissive = new com.fasterxml.jackson.databind.ObjectMapper();
        permissive.enableDefaultTyping();
        try {
            assertNotNull(permissive.readValue(json, Holder.class).items.get(0));
        } catch (java.io.IOException e) {
            throw new AssertionError("payload should be accepted by the permissive mapper", e);
        }
        assertNull(JSONUtil.fromJSON(json, Holder.class));
    }
}
