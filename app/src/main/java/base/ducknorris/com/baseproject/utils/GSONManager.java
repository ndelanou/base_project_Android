package base.ducknorris.com.baseproject.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.List;
import java.util.Map;

/**
 * Created by ndelanou on 17/05/2017.
 */

public class GSONManager {

    public static Gson INSTANCE = initializeGson();

    private static Gson initializeGson() {
        new DateTypeAdapter();
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .serializeNulls()
                .excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(java.util.Date.class, new CustomDateTypeAdapter())
                .registerTypeAdapter(java.sql.Date.class, new CustomDateTypeAdapter()).create();
    }


    /**
     * Converts the given map as a JSON object.
     */
    public static JSONObject convertMapToJSON(Map<Object, Object> attributesMap) throws JSONException {
        JSONObject json = new JSONObject();
        for(Map.Entry<Object,Object> entry : attributesMap.entrySet())  {
            if (entry.getValue() instanceof List) {
                JSONArray array = new JSONArray();
                for (Object element : (List) entry.getValue()) {
                    if (element instanceof Map) {
                        array.put(convertMapToJSON((Map<Object, Object>) element));
                    }
                }
                json.put(""+ entry.getKey(), array);
            } else {
                json.put("" + entry.getKey(), "" + entry.getValue());
            }
        }
        return json;
    }

    private static final class CustomDateTypeAdapter extends TypeAdapter<Date> {

        @Override
        public void write(JsonWriter out, Date value) throws IOException {
            if (value == null) {
                out.nullValue();
                return;
            }
            out.value(value.getTime()/1000);
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            if (in.peek() == JsonToken.NULL) {
                in.nextNull();
                return null;
            }
            String json = in.nextString();
                return new Date(Long.parseLong(json) * 1000);

        }
    }
}