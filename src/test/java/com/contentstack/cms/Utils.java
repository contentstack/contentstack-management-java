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

    public static JSONObject readJson(String file) {
        JSONObject mockJsonObject = null;
        String path = "src/test/resources/" + file;
        try {
            Object obj = new JSONParser().parse(new FileReader(new File(path).getPath()));
            mockJsonObject = (JSONObject) obj;
        } catch (IOException | ParseException e) {
            log.warning(e.getLocalizedMessage());
        }
        return mockJsonObject;
    }


}
