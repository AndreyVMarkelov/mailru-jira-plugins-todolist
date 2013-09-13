package ru.andreymarkelov.atlas.plugins.todos;

import java.util.Map;

import com.opensymphony.module.propertyset.PropertySet;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.Validator;
import com.opensymphony.workflow.WorkflowException;

public class ToDoListAllDoneValidator implements Validator {

    @Override
    public void validate(Map transientVars, Map args, PropertySet ps) throws InvalidInputException, WorkflowException {
        
    }
}
