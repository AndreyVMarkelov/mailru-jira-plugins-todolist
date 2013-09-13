package ru.andreymarkelov.atlas.plugins.todos;

import java.util.Map;

import com.atlassian.jira.workflow.condition.AbstractJiraCondition;
import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.WorkflowException;

public class ToDoListAllDoneCondition extends AbstractJiraCondition {

    @Override
    public boolean passesCondition(Map transientVars, Map args, PropertySet ps) throws WorkflowException {
        return false;
    }
}
