package ru.andreymarkelov.atlas.plugins.todos;

import java.util.Map;
import java.util.Set;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.workflow.condition.AbstractJiraCondition;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

public class ToDoListAllDoneCondition extends AbstractJiraCondition {
    @Override
    public boolean passesCondition(Map transientVars, Map args, PropertySet ps) throws WorkflowException {
        String targetCfId = (String) args.get(ToDoListAllDoneConditionFactory.TODO_FIELD_PARAM);
        if (targetCfId != null && targetCfId.length() > 0) {
            CustomField targetCf = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(targetCfId);
            if (targetCf != null) {
                Object cfVal = getIssue(transientVars).getCustomFieldValue(targetCf);
                if (cfVal != null && !cfVal.toString().equals("[]")) {
                    Set<ToDoItem> items = ToDoUtils.getParsedValue(cfVal.toString());
                    for (ToDoItem item : items) {
                        if (!item.isDone()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
