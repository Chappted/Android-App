package de.ka.chapptedapi;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * A mocking utility class.
 * <p>
 * Created by Thomas Hofmann on 09.01.18.
 */
public class MockUtil {

    /**
     * Retrieves a json file with the given name, used for mocking responses.
     *
     * @param context  the base context
     * @param fileName the file name
     * @return the string containing the mocked response
     */
    public static String getJsonFromFile(Context context, String fileName) {

        StringBuilder responseStrBuilder = new StringBuilder();

        try {

            InputStream inputStream = context.getAssets().open(fileName);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream, "UTF-8"));

            String inputStr;
            while ((inputStr = reader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            new JSONObject(responseStrBuilder.toString());
            reader.close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return responseStrBuilder.toString();

    }

}
