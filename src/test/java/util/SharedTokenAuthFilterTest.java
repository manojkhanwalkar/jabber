package util;

import org.junit.Test;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SharedTokenAuthFilterTest {

    private static final String TOKEN = "0123456789abcdef-test-token";

    private static ContainerRequestContext request(String header, AtomicReference<Response> aborted) {
        UriInfo uriInfo = (UriInfo) Proxy.newProxyInstance(UriInfo.class.getClassLoader(),
                new Class<?>[]{UriInfo.class}, (p, m, a) -> "getPath".equals(m.getName()) ? "submit" : null);
        return (ContainerRequestContext) Proxy.newProxyInstance(ContainerRequestContext.class.getClassLoader(),
                new Class<?>[]{ContainerRequestContext.class}, (p, m, a) -> {
                    switch (m.getName()) {
                        case "getHeaderString": return SharedTokenAuthFilter.HEADER.equals(a[0]) ? header : null;
                        case "getMethod": return "POST";
                        case "getUriInfo": return uriInfo;
                        case "abortWith": aborted.set((Response) a[0]); return null;
                        default: return null;
                    }
                });
    }

    private static Response run(SharedTokenAuthFilter filter, String header) {
        AtomicReference<Response> aborted = new AtomicReference<>();
        filter.filter(request(header, aborted));
        return aborted.get();
    }

    @Test
    public void acceptsCorrectToken() {
        assertNull(run(new SharedTokenAuthFilter(TOKEN), TOKEN));
    }

    @Test
    public void rejectsMissingOrWrongToken() {
        assertEquals(401, run(new SharedTokenAuthFilter(TOKEN), null).getStatus());
        assertEquals(401, run(new SharedTokenAuthFilter(TOKEN), "wrong").getStatus());
    }

    @Test
    public void failsClosedWhenNotConfigured() {
        assertEquals(503, run(new SharedTokenAuthFilter(null), TOKEN).getStatus());
        assertEquals(503, run(new SharedTokenAuthFilter("short"), "short").getStatus());
    }
}
