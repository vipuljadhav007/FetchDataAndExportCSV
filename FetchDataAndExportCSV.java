import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FetchDataAndExportCSV {
    public static void main(String[] args) {
        try {
            // Fetch data from the URL
            String jsonData = fetchDataFromURL("https://data.sfgov.org/resource/p4e4-a5a7.json");

            // Get the current timestamp in the specified format
            String timestamp = getCurrentTimestamp();

            // Print the timestamp to the console
            System.out.println("Timestamp: " + timestamp);

            // Parse the JSON data into a table and print it to the console
            parseJsonToTableAndPrint(jsonData);

            // Check if any line is related to "roof" using regex
            checkForRoofRelatedLine(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Fetch data from the given URL
    private static String fetchDataFromURL(String url) throws Exception {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = httpClient.execute(httpGet);

        // Check the HTTP response status
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            throw new Exception("HTTP request failed with status code: " + statusCode);
        }

        // Read the response data as a string
        return EntityUtils.toString(response.getEntity());
    }

    // Get the current timestamp in MM-dd-yy-HH-mm-ss format
    private static String getCurrentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy-HH-mm-ss");
        return dateFormat.format(new Date());
    }

    // Parse JSON data into a table and print it to the console
    private static void parseJsonToTableAndPrint(String jsonData) {
        JSONArray jsonArray = new JSONArray(jsonData);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            System.out.println(jsonObject.toString());
        }
    }

    // Check if any line in JSON data is related to "roof" using regex
    private static void checkForRoofRelatedLine(String jsonData) {
        if (jsonData.matches(".*\\broof\\b.*")) {
            System.out.println("Found a line related to 'roof' in the JSON data.");
        } else {
            System.out.println("No line related to 'roof' found in the JSON data.");
        }
    }
}
