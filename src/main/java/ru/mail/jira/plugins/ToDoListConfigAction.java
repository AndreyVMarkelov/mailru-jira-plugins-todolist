/*
 * Created by Andrey Markelov 13-02-2013.
 * Copyright Mail.Ru Group 2013. All rights reserved.
 */
package ru.mail.jira.plugins;

import java.util.ArrayList;
import java.util.List;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.ApplicationProperties;

/**
 * Configure ToDo list fields.
 * 
 * @author Andrey Markelov
 */
public class ToDoListConfigAction
    extends JiraWebActionSupport
{
    /**
     * Unique ID.
     */
    private static final long serialVersionUID = 2582939781550062892L;

    /**
     * Application properties.
     */
    private final ApplicationProperties applicationProperties;

    /**
     * Plugin data.
     */
    private final ToDoData pluginData;

    /**
     * Custom field manager.
     */
    private final CustomFieldManager cfMgr;

    /**
     * ToDO fields.
     */
    private List<ToDoFieldData> fields;

    /**
     * Constructor.
     */
    public ToDoListConfigAction(
        ApplicationProperties applicationProperties,
        ToDoData pluginData,
        CustomFieldManager cfMgr)
    {
        this.applicationProperties = applicationProperties;
        this.pluginData = pluginData;
        this.cfMgr = cfMgr;
        this.fields= new ArrayList<ToDoFieldData>(); 
    }

    @Override
    public String doDefault()
    throws Exception
    {
        List<CustomField> cgList = cfMgr.getCustomFieldObjects();
        for (CustomField cf : cgList)
        {
            if (cf.getCustomFieldType().getKey().equals("ru.mail.jira.plugins.todolist:todo-list-custom-field"))
            {
                fields.add(new ToDoFieldData(cf.getId(), cf.getName()));
            }
        }

        return SUCCESS;
    }

    /**
     * Get context path.
     */
    public String getBaseUrl()
    {
        return applicationProperties.getBaseUrl();
    }

    public List<ToDoFieldData> getFields()
    {
        return fields;
    }

    /**
     * Check administer permissions.
     */
    public boolean hasAdminPermission()
    {
        User user = getLoggedInUser();
        if (user == null)
        {
            return false;
        }

        if (getPermissionManager().hasPermission(Permissions.ADMINISTER, getLoggedInUser()))
        {
            return true;
        }

        return false;
    }
}
