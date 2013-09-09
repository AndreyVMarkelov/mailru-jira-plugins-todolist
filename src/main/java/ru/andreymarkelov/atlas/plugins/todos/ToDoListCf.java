package ru.andreymarkelov.atlas.plugins.todos;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.AbstractSingleFieldType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.persistence.PersistenceFieldType;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.util.NotNull;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.util.concurrent.Nullable;

public class ToDoListCf extends AbstractSingleFieldType<String> {
    private final ToDoData pluginData;

    protected ToDoListCf(CustomFieldValuePersister customFieldValuePersister, GenericConfigManager genericConfigManager, ToDoData pluginData) {
        super(customFieldValuePersister, genericConfigManager);
        this.pluginData = pluginData;
    }

    @Override
    public String getChangelogValue(CustomField field, String value) {
        StringBuilder sb = new StringBuilder();
        Set<ToDoItem> items = getParsedValue(super.getChangelogValue(field, value));
        for (ToDoItem item : items) {
            sb.append(item.getTodo()).append(" - ").append((item.isDone()) ? "\u2611" : "\u2610").append("\n");
        }
        return sb.toString();
    }

    @Override
    @NotNull
    protected PersistenceFieldType getDatabaseType() {
        return PersistenceFieldType.TYPE_UNLIMITED_TEXT;
    }

    @Override
    @Nullable
    protected Object getDbValueFromObject(String str) {
        return str;
    }

    @Override
    @Nullable
    protected String getObjectFromDbValue(@NotNull Object obj) throws FieldValidationException {
        return (null == obj) ? "" : obj.toString();
    }

    private Set<ToDoItem> getParsedValue(String value) {
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

    @Override
    public String getSingularObjectFromString(String str) throws FieldValidationException {
        return str;
    }

    @Override
    public String getStringFromSingularObject(String str) {
        return str;
    }

    @Override
    @NotNull
    public Map getVelocityParameters(Issue issue, CustomField field, FieldLayoutItem fieldLayoutItem) {
        Map<String, Object> params = super.getVelocityParameters(issue, field, fieldLayoutItem);

        Set<ToDoItem> items = new LinkedHashSet<ToDoItem>();
        if (issue != null) {
            Object value = issue.getCustomFieldValue(field);
            String defValue = super.getDefaultValue(field.getRelevantConfig(issue));

            if (value != null && !value.toString().isEmpty() && !value.toString().equals("[]")) {
                items = getParsedValue(value.toString());
                params.put("data", value.toString());
            } else {
                String data;
                if (defValue != null) {
                    items = getParsedValue(value.toString());
                    if (!items.isEmpty()) {
                        data = defValue;
                    } else {
                        data = "[]";
                    }
                } else {
                    data = "[]";
                }
                params.put("data", data);
            }
        }
        params.put("i18n", getI18nBean());
        params.put("editable", isEditable(issue, field));
        params.put("currtime", Long.valueOf(System.currentTimeMillis()).toString());
        params.put("items", items);

        return params;
    }

    private boolean isEditable(Issue issue, CustomField field) {
        if (issue == null) return true;
        ToDoDataItem data = pluginData.getToDoDataItem(field.getId());
        if (data == null) return true;

        String defValue = super.getDefaultValue(field.getRelevantConfig(issue));
        if (defValue != null && data.isNobody()) return false;
        if (data.isReporter() && !ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser().getName().equals(issue.getReporterUser().getName())) return false;

        return true;
    }
}
