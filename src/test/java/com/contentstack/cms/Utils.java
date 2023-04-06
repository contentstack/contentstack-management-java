package com.contentstack.cms;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import retrofit2.Response;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class Utils {

    private final static Logger log = Logger.getLogger(Utils.class.getName());

    public static JsonObject toJson(Response<ResponseBody> response) throws IOException {
        assert response.body() != null;
        return new Gson().fromJson(response.body().string(), JsonObject.class);
    }

    /**
     * This is used to convert json sting to the JSONObject
     *
     * @param jsonString
     *         the json sting you want to convert as JSONObject
     * @return JSONObject the request body
     */
    public static JSONObject createJSONObject(String jsonString) {
        JSONObject jsonObject = new JSONObject();
        JSONParser jsonParser = new JSONParser();
        if ((jsonString != null) && !(jsonString.isEmpty())) {
            try {
                jsonObject = (JSONObject) jsonParser.parse(jsonString);
            } catch (org.json.simple.parser.ParseException e) {
                log.warning(e.getLocalizedMessage());
            }
        }
        return jsonObject;
    }

    /**
     * This is used to read json file from the local machine. We are referring the files from the project's resource
     * itself 'String path = "src/test/resources/" + file;'
     *
     * @param file
     *         The file is the path of type string from where JSON File has to read
     * @return JSONObject the request body
     */

    public static JSONObject readJson(String file) {
        JSONObject mockJsonObject = null;
        String path = "src/test/resources/" + file;
        try (FileReader fileReader = new FileReader(new File(path).getPath())) {
            Object obj = new JSONParser().parse(fileReader);
            mockJsonObject = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            // handle the exception
            log.warning(e.getLocalizedMessage());
        }
        return mockJsonObject;
    }

}
