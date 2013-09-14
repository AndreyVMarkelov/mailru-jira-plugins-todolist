package ru.andreymarkelov.atlas.plugins.todos;

import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.index.indexers.impl.AbstractCustomFieldIndexer;
import com.atlassian.jira.web.FieldVisibilityManager;

public class ToDoFieldIndexer extends AbstractCustomFieldIndexer {
    private final CustomField customField;

    public ToDoFieldIndexer(FieldVisibilityManager fieldVisibilityManager, CustomField customField) {
        super(fieldVisibilityManager, customField);
        this.customField = customField;
    }

    public void addDocumentFields(final Document doc, final Issue issue, final Field.Index indexType) {
        final Object value = customField.getValue(issue);
        if (value != null) {
            Set<ToDoItem> data = ToDoUtils.getParsedValue(value.toString());
            for (ToDoItem todo : data) {
                doc.add(new Field(getDocumentFieldId(), todo.getTodo(), Field.Store.YES, indexType));
                doc.add(new Field(getDocumentFieldId(), todo.getTodo().concat(":").concat(Boolean.toString(todo.isDone())), Field.Store.YES, indexType));
            }
        }
    }

    @Override
    public void addDocumentFieldsNotSearchable(final Document doc, final Issue issue) {
        addDocumentFields(doc, issue, Field.Index.NO);
    }

    @Override
    public void addDocumentFieldsSearchable(final Document doc, final Issue issue) {
        addDocumentFields(doc, issue, Field.Index.NOT_ANALYZED_NO_NORMS);
    }
}
