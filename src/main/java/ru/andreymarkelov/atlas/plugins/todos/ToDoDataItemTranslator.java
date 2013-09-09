package ru.andreymarkelov.atlas.plugins.todos;

import org.apache.log4j.Logger;

import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

public class ToDoDataItemTranslator {
    private static final Logger logger = Logger.getLogger(ToDoDataItemTranslator.class);

    public static ToDoDataItem getToDoDataItemFromString(Object object) {
        if (object == null) {
            return null;
        }

        try {
            JSONObject jsonObj = new JSONObject(object.toString());
            ToDoDataItem data = new ToDoDataItem();
            if (jsonObj.has("isReporter")) {
                data.setReporter(jsonObj.getBoolean("isReporter"));
            }
            if (jsonObj.has("isNobody")) {
                data.setNobody(jsonObj.getBoolean("isNobody"));
            }
            return data;
        } catch (JSONException e) {
            logger.error("Error parse JSON", e);
            return null;
        }
    }

    public static String storeToDoDataItemToString(ToDoDataItem obj) {
        if (obj == null) {
            return null;
        }

        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("isReporter", obj.isReporter());
            jsonObj.put("isNobody", obj.isNobody());
        } catch (JSONException e) {
            logger.error("Error write JSON", e);
            return null;
        }
        return jsonObj.toString();
    }

    private ToDoDataItemTranslator() {
    }
}
