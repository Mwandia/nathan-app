package ke.co.nathanaccess.data;

import com.fasterxml.jackson.databind.ObjectMapper;

import ke.co.nathanaccess.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            return httpCall(username, password);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }

    }

    public void logout() {
        // TODO: revoke authentication
    }

    private Result<LoggedInUser> httpCall(String username, String password) {
        try {
            // Set url and connect to server
            String uri = "localhost/nathan/api/authenticate/login.php";

            URL url = new URL(uri);
            HttpURLConnection client = null;

            try {
                client = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                return new Result.Error(new IOException("Error logging in", e));
            }

            // Add POST body parameters
            client.setRequestMethod("POST");
            client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; utf-8");
            client.setRequestProperty("email", username);
            client.setRequestProperty("password", password);
            client.setRequestProperty("Accept", "application/json");
            client.setDoOutput(true);

            // Set size of content (in bytes)
            client.setChunkedStreamingMode(0);

            String requestBody = "{'email': " + username + "," +
                    "'password': " + password + "}";

            try (OutputStream os = client.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            String resp = "";
            try (
                    BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream(), "utf-8"))
            ) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                resp = response.toString();
            }

            Map<String, String> jsonMap = new HashMap<String, String>();

            ObjectMapper objectMapper = new ObjectMapper();
            jsonMap = objectMapper.readValue(resp, HashMap.class);

            int response = Integer.parseInt(jsonMap.get("status"));

            if (response == 0) {
                LoggedInUser user =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                username);
                return new Result.Success<>(user);
            }

            return new Result.Error(new IOException("Error logging in. Incorrect email or password"));
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }
}
