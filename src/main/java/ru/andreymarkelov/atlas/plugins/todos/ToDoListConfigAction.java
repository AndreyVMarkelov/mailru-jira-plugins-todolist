package ru.andreymarkelov.atlas.plugins.todos;

import java.util.ArrayList;
import java.util.List;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.ApplicationProperties;

public class ToDoListConfigAction extends JiraWebActionSupport {
    private static final long serialVersionUID = 2582939781550062892L;

    private final ApplicationProperties applicationProperties;
    private final ToDoData pluginData;
    private final CustomFieldManager cfMgr;

    private List<ToDoFieldData> fields;

    public ToDoListConfigAction(ApplicationProperties applicationProperties, ToDoData pluginData, CustomFieldManager cfMgr) {
        this.applicationProperties = applicationProperties;
        this.pluginData = pluginData;
        this.cfMgr = cfMgr;
        this.fields= new ArrayList<ToDoFieldData>(); 
    }

    public String doConfigure() throws Exception {
        return "configure";
    }

    @Override
    public String doDefault() throws Exception {
        List<CustomField> cgList = cfMgr.getCustomFieldObjects();
        for (CustomField cf : cgList) {
            if (cf.getCustomFieldType().getKey().equals("ru.mail.jira.plugins.todolist:todo-list-custom-field")) {
                ToDoDataItem data = pluginData.getToDoDataItem(cf.getId());
                if (data != null) {
                    fields.add(new ToDoFieldData(cf.getId(), cf.getName(), data));
                } else {
                    fields.add(new ToDoFieldData(cf.getId(), cf.getName()));
                }
            }
        }
        return SUCCESS;
    }

    public String getBaseUrl() {
        return applicationProperties.getBaseUrl();
    }

    public List<ToDoFieldData> getFields() {
        return fields;
    }

    public boolean hasAdminPermission() {
        User user = getLoggedInUser();
        if (user == null) {
            return false;
        }
        if (getPermissionManager().hasPermission(Permissions.ADMINISTER, getLoggedInUser())) {
            return true;
        }
        return false;
    }
}
