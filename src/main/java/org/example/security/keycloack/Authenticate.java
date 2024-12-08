package org.example.security.keycloack;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Singleton
public class Authenticate {
//private passwordAuthentication pauth = new passwordAuthentication();
	private String key="";
	private String id="";
	private Keycloak keycloak;

	private static final String StudentGradingRealm = "API-GATEWAY";

	//private static final String baseUrl = "http://10.57.40.154:8180";
	private static final String baseUrl = "http://localhost:8089";
	private static  final String cliendId="Leve3C3nHl5E1YqcFgU6UIgdFFwt3C8m";
	private  static  final  String clientSecreret="StudentGradingSys";

	@PostConstruct
	public void connect(){
		Keycloak keycloak = KeycloakBuilder.builder()
				.serverUrl(baseUrl)
				.realm("master")
				.clientId("admin-cli")
				.username("admin")
				.password("admin")
				.build();
		this.keycloak = keycloak;

	}
	public boolean authenticateBackOffice(String username, String password) {
		try {
			String value =getKeyCloakResponse(username,password);
			if (value!=null)
				return true;
		} catch (IOException e) {
			return false;
		}
		return false;
	}


	public boolean createUser(String username, String password,String name) {
		if(keycloak.realm(StudentGradingRealm).users().search(username).isEmpty()) {
			UserRepresentation userRep = new UserRepresentation();
			userRep.setUsername(username);
			userRep.setEnabled(true);
			userRep.setEmailVerified(true);
			userRep.setFirstName(name);

			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			passwordCred.setValue(password);
			passwordCred.setTemporary(false);
			userRep.setCredentials(Arrays.asList(passwordCred));
			try {
				Response response = keycloak.realm(StudentGradingRealm).users().create(userRep);

				if (response.getStatus() == 201)
					return true;
			}catch (Exception e){
				return false;
			}

		}
		return false;
	}


	public boolean assignRoleToUser(String username, String roleName) {
		try {
			// Search for the user in Keycloak by username
			UserRepresentation user = keycloak.realm(StudentGradingRealm).users().search(username).stream()
					.findFirst()
					.orElseThrow(() -> new RuntimeException("User not found in Keycloak"));

			// Check if the role is one of the predefined roles: admin, teacher, student
			if (!Arrays.asList("admin", "teacher", "student").contains(roleName)) {
				throw new RuntimeException("Invalid role name: " + roleName);
			}

			// Get the role from Keycloak realm based on roleName
			RoleRepresentation role = keycloak.realm(StudentGradingRealm).roles().get(roleName).toRepresentation();
			if (role == null) {
				throw new RuntimeException("Role not found in Keycloak: " + roleName);
			}

			// Get the user resource using the user ID
			UserResource userResource = keycloak.realm(StudentGradingRealm).users().get(user.getId());

			// Assign the role to the user
			userResource.roles().realmLevel().add(Arrays.asList(role));

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String getAccessToken(String username, String password) {
		try {
			return getKeyCloakResponse(username, password);
		} catch (IOException e) {
			e.printStackTrace();
			return null; // Return null in case of an exception
		}
	}








	public boolean createUserBackOffice(String username, String password) {
		System.out.println("insider backoffice user creation");
		if(keycloak.realm(StudentGradingRealm).users().search(username).isEmpty()) {
			System.out.println("back office user not found");
			UserRepresentation userRep = new UserRepresentation();
			userRep.setUsername(username);
			userRep.setEnabled(true);
			userRep.setEmailVerified(true);

			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			passwordCred.setValue(password);
			passwordCred.setTemporary(false);
			userRep.setCredentials(Arrays.asList(passwordCred));
			try {
				System.out.println("trying to create backoffice user");
				Response response = keycloak.realm(StudentGradingRealm).users().create(userRep);
				System.out.println("response code: "+ response.getStatus());
				if (response.getStatus() == 201)
					return true;
			}catch (Exception e){
				return false;
			}

		}
		return false;
	}
	public UserRepresentation getUser(String username) {
		return keycloak.realm(StudentGradingRealm).users().search(username).get(0);
	}


	private String getKeyCloakResponse(String username, String password) throws IOException {

		String url = baseUrl + "/realms/" + StudentGradingRealm + "/protocol/openid-connect/token";

		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			Map<String, String> requestData = new HashMap<>();
			requestData.put("username", username);
			requestData.put("password", password);
			requestData.put("client_id", "StudentGradingSys");
			requestData.put("client_secret", "PYRwrixMX3fh3rNXDQwgp2Ou0pglksPI");
			requestData.put("grant_type", "password");

			String data = "";
			for (String key : requestData.keySet()) {
				data += key + "=" + requestData.get(key) + "&";
			}
			data = data.substring(0, data.length() - 1);

			con.setRequestProperty("Content-Length", String.valueOf(data.length()));

			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(data);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			if (responseCode == 200) {

				BufferedReader in = new BufferedReader(new InputStreamReader(
						con.getInputStream()));
				String inputLine;
				StringBuffer responseBody = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					responseBody.append(inputLine);
				}
				in.close();
				System.out.println("Response Body: " + responseBody);
				return new JSONObject(responseBody.toString()).getString("access_token");

			} else {
				System.out.println("Error123: " + con.getResponseMessage());
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return null;
	}

	public boolean changePasswordBackoffice(String username, String newPassword) {
		try {
			UserRepresentation user = keycloak.realm(StudentGradingRealm).users().search(username).get(0);
			if(user != null) {
				CredentialRepresentation newCred = new CredentialRepresentation();
				newCred.setType("password");
				newCred.setValue(newPassword);
				newCred.setTemporary(false);

				UserResource userResource = keycloak.realm(StudentGradingRealm).users().get(user.getId());
				userResource.resetPassword(newCred);

				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean changePassword(String username, String newPassword) {
		try {
			UserRepresentation user = keycloak.realm(StudentGradingRealm).users().search(username).get(0);
			if(user != null) {
				CredentialRepresentation newCred = new CredentialRepresentation();
				newCred.setType("password");
				newCred.setValue(newPassword);
				newCred.setTemporary(false);

				UserResource userResource = keycloak.realm(StudentGradingRealm).users().get(user.getId());
				userResource.resetPassword(newCred);

				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}



	public boolean authenticate(String username, String password, String requiredRole) {
		try {
			// Get the access token from Keycloak
			String token = getKeyCloakResponse(username, password);

			if (token != null) {
				// Parse the JWT token to extract roles
				Claims claims = Jwts.parser().parseClaimsJws(token).getBody();
				List<String> roles = (List<String>) claims.get("realm_access.roles");

				// Check if the required role is present in the user's roles
				return roles.contains(requiredRole);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	public boolean clearMobileAttribute(String username) {
		try {
			UserRepresentation user = keycloak.realm(StudentGradingRealm).users().search(username).get(0);

			// check if user exists
			if(user == null) throw new Exception("User doesnt exist on keycloak to delete attribute");

			Map<String, List<String>> empty = new HashMap<>();
			user.setAttributes(empty);
			keycloak.realm(StudentGradingRealm).users().get(user.getId()).update(user);
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

}
