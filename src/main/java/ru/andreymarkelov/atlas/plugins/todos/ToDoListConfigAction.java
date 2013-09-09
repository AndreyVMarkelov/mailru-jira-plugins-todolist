package ru.andreymarkelov.atlas.plugins.todos;

import java.util.ArrayList;
import java.util.List;
import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.CustomFieldManager;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.security.Permissions;
import com.atlassian.jira.security.xsrf.RequiresXsrfCheck;
import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.ApplicationProperties;

public class ToDoListConfigAction extends JiraWebActionSupport {
    private static final long serialVersionUID = 2582939781550062892L;

    private final ApplicationProperties applicationProperties;
    private final ToDoData pluginData;
    private final CustomFieldManager cfMgr;

    private List<ToDoFieldData> fields;
    private String currentField;
    private ToDoFieldData currentFieldData;
    private String reporter;
    private String nobody;

    public ToDoListConfigAction(ApplicationProperties applicationProperties, ToDoData pluginData, CustomFieldManager cfMgr) {
        this.applicationProperties = applicationProperties;
        this.pluginData = pluginData;
        this.cfMgr = cfMgr;
        this.fields= new ArrayList<ToDoFieldData>(); 
    }

    public String doConfigure() throws Exception {
        currentFieldData = null;
        if (currentField != null) {
            CustomField cf = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(currentField);
            if (cf != null) {
                ToDoDataItem data = pluginData.getToDoDataItem(cf.getId());
                if (data != null) {
                    currentFieldData = new ToDoFieldData(cf.getId(), cf.getName(), data);
                    reporter = Boolean.toString(data.isReporter());
                    nobody = Boolean.toString(data.isNobody());
                } else {
                    currentFieldData = new ToDoFieldData(cf.getId(), cf.getName());
                    reporter = Boolean.FALSE.toString();
                    nobody = Boolean.FALSE.toString();
                }
            }
        }

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

    @Override
    @RequiresXsrfCheck
    protected String doExecute() throws Exception {
        pluginData.setToDoDataItem(currentField, new ToDoDataItem(Boolean.parseBoolean(reporter), Boolean.parseBoolean(nobody)));
        return getRedirect("ToDoListConfigAction!default.jspa");
    }

    public String getBaseUrl() {
        return applicationProperties.getBaseUrl();
    }

    public String getCurrentField() {
        return currentField;
    }

    public ToDoFieldData getCurrentFieldData() {
        return currentFieldData;
    }

    public List<ToDoFieldData> getFields() {
        return fields;
    }

    public String getNobody() {
        return nobody;
    }

    public String getReporter() {
        return reporter;
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

    public void setCurrentField(String currentField) {
        this.currentField = currentField;
    }

    public void setNobody(String nobody) {
        this.nobody = nobody;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }
}
