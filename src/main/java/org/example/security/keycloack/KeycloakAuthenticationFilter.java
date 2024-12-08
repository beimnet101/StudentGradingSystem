import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import javax.annotation.Priority;
import javax.ejb.Singleton;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import java.io.IOException;
import java.util.List;

//@Provider
//@Singleton
//@Priority(Priorities.AUTHENTICATION)
//public class KeycloakAuthFilter implements ContainerRequestFilter {
//
//    private static final String KEYCLOAK_PUBLIC_KEY = "<Your Keycloak Public Key>";
//    private static final String REALM_NAME = "API-GATEWAY";
//    private static final String CLIENT_ID = "StudentGradingSys";
//
//    @Context
//    private HttpHeaders httpHeaders;
//
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        String authorizationHeader = httpHeaders.getHeaderString(HttpHeaders.AUTHORIZATION);
//
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Authorization header must be provided").build());
//            return;
//        }
//
//        String token = authorizationHeader.substring(7);
//
//        try {
//            // Parse the JWT token to validate and extract claims
//            Claims claims = Jwts.parserBuilder()
//                    .setSigningKey(KEYCLOAK_PUBLIC_KEY)
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody();
//
//            // Extract roles from the claims (assuming roles are in the "realm_access" claim)
//            List<String> roles = (List<String>) claims.get("realm_access").get("roles");
//
//            // Set the roles in the request context for further checks
//            requestContext.setProperty("roles", roles);
//
//        } catch (Exception e) {
//            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Invalid or expired token").build());
//        }
//    }
//}
        //