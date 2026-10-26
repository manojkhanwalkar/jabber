package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Requires every API request to carry the shared token from the JABBER_SHARED_TOKEN
 * environment variable in the X-Jabber-Token header. The services accept jars to
 * execute, worker registrations and job results, so they must not be callable by
 * anyone who can reach the port. Fails closed: if the token is not configured,
 * every request is rejected.
 */
@Priority(Priorities.AUTHENTICATION)
public class SharedTokenAuthFilter implements ContainerRequestFilter {

    public static final String HEADER = "X-Jabber-Token";
    public static final String ENV_VAR = "JABBER_SHARED_TOKEN";
    static final int MIN_TOKEN_LENGTH = 16;

    private static final Logger LOG = LoggerFactory.getLogger(SharedTokenAuthFilter.class);

    private final byte[] expected;

    public SharedTokenAuthFilter() {
        this(System.getenv(ENV_VAR));
    }

    public SharedTokenAuthFilter(String token) {
        if (token == null || token.length() < MIN_TOKEN_LENGTH) {
            LOG.error("{} is not set or shorter than {} characters; all API requests will be rejected",
                    ENV_VAR, MIN_TOKEN_LENGTH);
            this.expected = null;
        } else {
            this.expected = token.getBytes(StandardCharsets.UTF_8);
        }
    }

    /** Token value clients should send, or null when not configured. */
    public static String clientToken() {
        return System.getenv(ENV_VAR);
    }

    @Override
    public void filter(ContainerRequestContext ctx) {
        if (expected == null) {
            ctx.abortWith(reject(Response.Status.SERVICE_UNAVAILABLE, "authentication not configured"));
            return;
        }
        String supplied = ctx.getHeaderString(HEADER);
        if (supplied == null
                || !MessageDigest.isEqual(expected, supplied.getBytes(StandardCharsets.UTF_8))) {
            LOG.warn("Rejected unauthenticated request: {} {}", ctx.getMethod(),
                    ctx.getUriInfo().getPath().replaceAll("[\\r\\n]", "_"));
            ctx.abortWith(reject(Response.Status.UNAUTHORIZED, "unauthorized"));
        }
    }

    private static Response reject(Response.Status status, String message) {
        return Response.status(status).type(MediaType.TEXT_PLAIN_TYPE).entity(message).build();
    }
}
