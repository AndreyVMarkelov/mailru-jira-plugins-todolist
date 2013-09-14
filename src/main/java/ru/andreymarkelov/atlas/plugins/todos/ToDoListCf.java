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
import com.atlassian.jira.issue.customfields.view.CustomFieldParams;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.config.FieldConfig;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.util.ErrorCollection;
import com.atlassian.jira.util.NotNull;
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
        Set<ToDoItem> items = ToDoUtils.getParsedValue(super.getChangelogValue(field, value));
        for (ToDoItem item : items) {
            sb.append(item.getTodo()).append(": ").append((item.isDone()) ? "+" : "-").append("\n");
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

    private Set<ToDoItem> getItems(Issue issue, CustomField field) {
        Set<ToDoItem> items = new LinkedHashSet<ToDoItem>();
        if (issue != null) {
            Object value = issue.getCustomFieldValue(field);
            if (value != null) {
                items = ToDoUtils.getParsedValue(value.toString());
            }
        }
        return items;
    }

    @Override
    @Nullable
    protected String getObjectFromDbValue(@NotNull Object obj) throws FieldValidationException {
        return (null == obj) ? "" : obj.toString();
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
        params.put("i18n", getI18nBean());
        params.put("currtime", Long.valueOf(System.currentTimeMillis()).toString());

        Set<ToDoItem> items = getItems(issue, field);
        if (issue != null) {
            Object value = issue.getCustomFieldValue(field);
            if (isValueData(value)) {
                params.put("data", value.toString());
            } else {
                String data = "[]";
                String defValue = super.getDefaultValue(field.getRelevantConfig(issue));
                if (defValue != null && !items.isEmpty()) {
                    data = defValue;
                }
                params.put("data", data);
            }
        }
        params.put("editable", Boolean.valueOf(isEditable(issue, field)));
        params.put("items", items);
        return params;
    }

    private boolean isEditable(Issue issue, CustomField field) {
        // if permissions are not configured we can edit
        ToDoDataItem data = pluginData.getToDoDataItem(field.getId());
        if (data == null) return true;

        // if default value is set and option is set we cannot edit
        if (issue != null) {
            String defValue = super.getDefaultValue(field.getRelevantConfig(issue));
            if (defValue != null && defValue.length() > 0  && !defValue.toString().equals("[]") && data.isNobody()) return false;
        }

        // if option reporter only is set and user is not repoter we cannot edit
        String loggedUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser().getName();
        if (data.isReporter() && issue != null && issue.getReporterUser() != null && !loggedUser.equals(issue.getReporterUser().getName())) return false;

        return true;
    }

    private boolean isValueData(Object value) {
        return value != null && value.toString().length() > 0 && !value.toString().equals("[]") ? true : false;
    }

    @Override
    public void validateFromParams(CustomFieldParams fieldParams, ErrorCollection errors, FieldConfig fc) {
        super.validateFromParams(fieldParams, errors, fc);
    }
}
