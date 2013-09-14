package ru.andreymarkelov.atlas.plugins.todos;

import java.util.LinkedHashSet;
import java.util.Set;

import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;

public class ToDoUtils {
    public static Set<ToDoItem> getParsedValue(String value) {
        Set<ToDoItem> items = new LinkedHashSet<ToDoItem>();
        try {
            JSONArray jsonObj = new JSONArray(value.toString());
            for (int i = 0; i < jsonObj.length(); i++) {
                JSONObject obj = jsonObj.getJSONObject(i);
                String todo = obj.getString("id");
                String type = obj.getString("type");
                if (type.equals("done")) {
                    items.add(new ToDoItem(todo, true));
                } else {
                    items.add(new ToDoItem(todo, false));
                }
            }
        } catch (JSONException e) {
            //--> nothing
        }
        return items;
    }

    private ToDoUtils() {}
}
