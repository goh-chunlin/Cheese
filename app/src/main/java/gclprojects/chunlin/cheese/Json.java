package gclprojects.chunlin.cheese;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class Json {

     static String getJson(String url) {
        HttpURLConnection connection = null;

        try{
            URL desiredUrl = new URL(url);
            connection = (HttpURLConnection)desiredUrl.openConnection();
            connection.connect();

            int status = connection.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder stringBuilder = new StringBuilder();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line)
                                .append("\n");
                    }
                    reader.close();

                    return stringBuilder.toString();
            }
        } catch (IOException ex) {

            Log.e("JSON Parsing Error", ex.getMessage());

        } finally {

            if (connection != null) {

                connection.disconnect();

            }

        }

        return "";
    }

    static boolean isJsonValid(String input) {
        try {

            new JSONObject(input);

        } catch (JSONException ex) {

            try {

                new JSONArray(input);

            } catch (JSONException ex1) {

                Log.e("JSON Validity Error", ex.getMessage());

                return false;

            }
        }

        return true;
    }
}
