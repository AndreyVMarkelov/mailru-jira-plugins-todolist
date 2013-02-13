package ru.mail.jira.plugins;

import java.util.Map;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.customfields.impl.AbstractSingleFieldType;
import com.atlassian.jira.issue.customfields.impl.FieldValidationException;
import com.atlassian.jira.issue.customfields.manager.GenericConfigManager;
import com.atlassian.jira.issue.customfields.persistence.CustomFieldValuePersister;
import com.atlassian.jira.issue.customfields.persistence.PersistenceFieldType;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.fields.layout.field.FieldLayoutItem;
import com.atlassian.jira.util.NotNull;
import com.atlassian.util.concurrent.Nullable;

public class ToDoListCf
    extends AbstractSingleFieldType<String>
{
    /**
     * Constructor.
     */
    protected ToDoListCf(
        CustomFieldValuePersister customFieldValuePersister,
        GenericConfigManager genericConfigManager)
    {
        super(customFieldValuePersister, genericConfigManager);
    }

    @Override
    @NotNull
    public Map getVelocityParameters(
        Issue issue,
        CustomField field,
        FieldLayoutItem fieldLayoutItem)
    {
        Map<String, Object> params = super.getVelocityParameters(issue, field, fieldLayoutItem);

        return params;
    }

    @Override
    public String getSingularObjectFromString(String str)
    throws FieldValidationException
    {
        return str;
    }

    @Override
    public String getStringFromSingularObject(String str)
    {
        return str;
    }

    @Override
    @NotNull
    protected PersistenceFieldType getDatabaseType()
    {
        return PersistenceFieldType.TYPE_UNLIMITED_TEXT;
    }

    @Override
    @Nullable
    protected Object getDbValueFromObject(String str)
    {
        return str;
    }

    @Override
    @Nullable
    protected String getObjectFromDbValue(
        @NotNull Object obj)
    throws FieldValidationException
    {
        return (null == obj) ? "" : obj.toString();
    }
}
