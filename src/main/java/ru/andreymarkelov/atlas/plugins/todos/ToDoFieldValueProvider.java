package ru.andreymarkelov.atlas.plugins.todos;

import com.atlassian.jira.issue.customfields.CustomFieldValueProvider;
import com.atlassian.jira.issue.customfields.MultiSelectCustomFieldValueProvider;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.transport.FieldValuesHolder;

public class ToDoFieldValueProvider implements CustomFieldValueProvider {
    private final CustomFieldValueProvider customFieldValueProvider = new MultiSelectCustomFieldValueProvider();

    public Object getStringValue(CustomField customField, FieldValuesHolder fieldValuesHolder) {
        Object values = customFieldValueProvider.getStringValue(customField, fieldValuesHolder);
        return values;
    }

    public Object getValue(CustomField customField, FieldValuesHolder fieldValuesHolder) {
        return getStringValue(customField, fieldValuesHolder);
    }
}

