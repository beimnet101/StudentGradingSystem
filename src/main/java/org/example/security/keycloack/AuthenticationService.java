package org.example.security.keycloack;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.example.config.Configuration;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.NotAuthorizedException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class AuthenticationService {

    @EJB
    private Configuration config;

    // Authenticate the user by verifying roles from JWT token
    public boolean authenticateService(String token, String requiredRole) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("Token cannot be null or empty.");
        }

        try {
            // Log the token to verify it's being passed correctly
            System.out.println("Token received: " + token);

            // Parse the JWT token to extract claims
            Claims claims = parseToken(token);

            // Log the entire claims object to understand its structure
            System.out.println("Claims: " + claims);

            // Check if the claims object is valid
            if (claims == null) {
                throw new NotAuthorizedException("Authentication failed: Claims not found.");
            }

            // Extract 'realm_access' and 'resource_access' from the claims
            Object realmAccess = claims.get("realm_access");
            Object resourceAccess = claims.get("resource_access");

            // Log the realm_access and resource_access objects to see if they are null or malformed
            System.out.println("Realm Access: " + realmAccess);
            System.out.println("Resource Access: " + resourceAccess);

            // Extract roles from both 'realm_access' and 'resource_access' if they exist
            List<String> realmRoles = null;
            List<String> resourceRoles = null;

            // Extract roles from realm_access if it exists and is of the correct type
            if (realmAccess instanceof Map) {
                realmRoles = (List<String>) ((Map) realmAccess).get("roles");
            }

            // Extract roles from resource_access if it exists and is of the correct type
            if (resourceAccess instanceof Map) {
                Map<String, Object> resourceMap = (Map<String, Object>) resourceAccess;
                Object studentGradingRoles = resourceMap.get("StudentGradingSys");
                if (studentGradingRoles instanceof Map) {
                    resourceRoles = (List<String>) ((Map) studentGradingRoles).get("roles");
                }
            }

            // Log the roles for debugging
            System.out.println("Realm Roles: " + realmRoles);
            System.out.println("Resource Roles: " + resourceRoles);

            // Check for the required role in both the 'realm_access' and 'resource_access' roles
            if ((realmRoles != null && realmRoles.contains(requiredRole)) ||
                    (resourceRoles != null && resourceRoles.contains(requiredRole))) {
                return true;  // Return true if the role is found
            }

            // If no roles match, return false
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            // Instead of returning false here, throw an exception
            throw new NotAuthorizedException("Authentication failed: " + e.getMessage());
        }
    }

    // Parse the JWT token and extract claims using the older parser method
    private Claims parseToken(String token) {
        try {
            // Get the public key from the configuration
            String publicKeyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwcdWHXAf9KqryYJFuu7l3J6IRkavG/vrfTmNjkdhZYuyzCT9ai1edhCZhV5QpH5jUgrBs0n+UrqIs/oj3+OiyMKLLSFcTmEi6Y3HQPfc/t4S78Zl5VuR7/RciXMFmpgpWOba5uRvWpGJ1yK0xjQu31+/VQd4ZsPsVi0X5C8xf78a3UMgZdISK3QapB8P/gR3G9ghaDGcMrzxo4JYxCw/4hw87pPDjMpTGGmbY6lYq9w0braqUxTH97vYezzdcCnUo1eUC11gJtPp7AaP9f4v7q/Ix7gTgbSZ4OVtTYi4D/xVf5CJl8A+Rg/qRAwDKDsVGMIqXuGhtmCnjwwg5zRydwIDAQAB";

            if (publicKeyString == null || publicKeyString.isEmpty()) {
                throw new IllegalArgumentException("Public key cannot be null or empty.");
            }

            // Log the public key for debugging purposes
            System.out.println("Public Key: " + publicKeyString);

            // Convert the string to RSAPublicKey
            RSAPublicKey publicKey = getPublicKeyFromString(publicKeyString);

            // Use the older Jwts.parser() method to parse the token
            return Jwts.parser()
                    .setSigningKey(publicKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new IllegalArgumentException("Invalid signature in token: " + e.getMessage());
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse token: " + e.getMessage());
        }
    }

    // Convert the public key string to RSAPublicKey
    private RSAPublicKey getPublicKeyFromString(String publicKeyString) throws Exception {
        // Clean the public key string
        String cleanPublicKey = publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        // Decode the Base64 string into bytes
        byte[] decodedKey = Base64.getDecoder().decode(cleanPublicKey);

        // Generate RSAPublicKey from the decoded bytes
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decodedKey);
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }
}
