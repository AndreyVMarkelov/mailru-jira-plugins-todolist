package ru.andreymarkelov.atlas.plugins.todos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.plugin.workflow.AbstractWorkflowPluginFactory;
import com.atlassian.jira.plugin.workflow.WorkflowPluginValidatorFactory;
import com.opensymphony.workflow.loader.AbstractDescriptor;
import com.opensymphony.workflow.loader.ValidatorDescriptor;

public class ToDoListAllDoneValidatorFactory extends AbstractWorkflowPluginFactory implements WorkflowPluginValidatorFactory{
    public static final String TODO_FIELD_PARAM = "todofieldparam";
    public static final String TODO_FIELDS = "todofields";
    public static final String TODO_FIELD_NAME = "todofieldname";

    @Override
    public Map<String, ?> getDescriptorParams(Map<String, Object> validatorParams) {
        Map<String, String> result = new HashMap<String, String>();
        result.put(TODO_FIELD_PARAM, extractSingleParam(validatorParams, TODO_FIELD_PARAM));
        return result;
    }

    private String getParam(AbstractDescriptor descriptor, String param) {
        if (!(descriptor instanceof ValidatorDescriptor)) {
            throw new IllegalArgumentException("Descriptor must be a ValidatorDescriptor.");
        }

        ValidatorDescriptor validatorDescriptor = (ValidatorDescriptor) descriptor;
        String value = (String) validatorDescriptor.getArgs().get(param);
        if (value!=null && value.trim().length() > 0) {
            return value;
        } else  {
            return "";
        }
    }

    public String getToDoCustomFieldName(String cfId) {
        if (cfId != null && cfId.length() > 0) {
            CustomField cf = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(cfId);
            if (cf != null) {
                return cf.getName();
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public List<CustomField> getToDoCustomFields() {
        List<CustomField> res = new ArrayList<CustomField>();
        List<CustomField> cgList = ComponentAccessor.getCustomFieldManager().getCustomFieldObjects();
        for (CustomField cf : cgList) {
            if (cf.getCustomFieldType().getKey().equals("ru.mail.jira.plugins.todolist:todo-list-custom-field")) {
                res.add(cf);
            }
        }
        return res;
    }

    @Override
    protected void getVelocityParamsForEdit(Map<String, Object> velocityParams, AbstractDescriptor descriptor) {
        velocityParams.put(TODO_FIELD_PARAM, getParam(descriptor, TODO_FIELD_PARAM));
        velocityParams.put(TODO_FIELDS, getToDoCustomFields());
    }

    @Override
    protected void getVelocityParamsForInput(Map<String, Object> velocityParams) {
        velocityParams.put(TODO_FIELD_PARAM, "");
        velocityParams.put(TODO_FIELDS, getToDoCustomFields());
    }

    @Override
    protected void getVelocityParamsForView(Map<String, Object> velocityParams, AbstractDescriptor descriptor) {
        String param = getParam(descriptor, TODO_FIELD_PARAM);
        velocityParams.put(TODO_FIELD_PARAM, param);
        velocityParams.put(TODO_FIELD_NAME, getToDoCustomFieldName(param));
    }
}
