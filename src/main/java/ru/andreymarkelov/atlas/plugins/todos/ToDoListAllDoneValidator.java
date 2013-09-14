package ru.andreymarkelov.atlas.plugins.todos;

import java.util.Map;
import java.util.Set;

import com.atlassian.jira.component.ComponentAccessor;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.util.I18nHelper;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.Validator;
import com.opensymphony.workflow.WorkflowException;

public class ToDoListAllDoneValidator implements Validator {
    @Override
    public void validate(Map transientVars, Map args, PropertySet ps) throws InvalidInputException, WorkflowException {
        String targetCfId = (String) args.get(ToDoListAllDoneConditionFactory.TODO_FIELD_PARAM);
        if (targetCfId != null && targetCfId.length() > 0) {
            CustomField targetCf = ComponentAccessor.getCustomFieldManager().getCustomFieldObject(targetCfId);
            if (targetCf != null) {
                Issue issue = (Issue) transientVars.get("issue");
                Object cfVal = issue.getCustomFieldValue(targetCf);
                if (cfVal != null && !cfVal.toString().equals("[]")) {
                    Set<ToDoItem> items = ToDoUtils.getParsedValue(cfVal.toString());
                    for (ToDoItem item : items) {
                        if (!item.isDone()) {
                            I18nHelper i18n = ComponentAccessor.getJiraAuthenticationContext().getI18nHelper();
                            throw new InvalidInputException(i18n.getText("ru.andreymarkelov.atlas.plugins.todos.validator", targetCf.getName()));
                        }
                    }
                }
            }
        }
    }
}
