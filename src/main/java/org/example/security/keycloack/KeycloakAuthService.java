package org.example.security.keycloack;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class KeycloakAuthService {

    private static final String KEYCLOAK_URL = "http://localhost:8089";  // Keycloak base URL
    private static final String REALM = "API-GATEWAY";  // Realm name
    private static final String CLIENT_ID = "StudentGradingSys";  // Client ID
    private static final String CLIENT_SECRET = "M6AqeLyZqSX8hkTyjrt6boERRY22u4O2";  // Client secret

    public String authenticate(String username, String password) throws IOException {
        String token = null;
        String url = KEYCLOAK_URL + "/realms/" + REALM + "/protocol/openid-connect/token";

        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Data to send in the request body
        String data = "username=" + username +
                "&password=" + password +
                "&client_id=" + CLIENT_ID +
                "&client_secret=" + CLIENT_SECRET +
                "&grant_type=password";

        // Send the request
        try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
            wr.writeBytes(data);
            wr.flush();
        }

        int responseCode = con.getResponseCode();
        if (responseCode == 200) {
            // Read the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder responseBody = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                responseBody.append(inputLine);
            }
            in.close();
            JSONObject jsonResponse = new JSONObject(responseBody.toString());
            token = jsonResponse.getString("access_token");
        }
        return token;
    }
}
