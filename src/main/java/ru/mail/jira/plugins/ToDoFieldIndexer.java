package ru.mail.jira.plugins;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.fields.CustomField;
import com.atlassian.jira.issue.index.indexers.impl.AbstractCustomFieldIndexer;
import com.atlassian.jira.util.json.JSONArray;
import com.atlassian.jira.util.json.JSONException;
import com.atlassian.jira.util.json.JSONObject;
import com.atlassian.jira.web.FieldVisibilityManager;

public class ToDoFieldIndexer extends AbstractCustomFieldIndexer {
    private final CustomField customField;

    public ToDoFieldIndexer(
            FieldVisibilityManager fieldVisibilityManager,
            CustomField customField) {
        super(fieldVisibilityManager, customField);
        this.customField = customField;
    }

    public void addDocumentFields(final Document doc, final Issue issue, final Field.Index indexType) {
        final Object value = customField.getValue(issue);
        if (value != null) {
            Set<ToDoItem> data = getParsedValue(value.toString());
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

    private Set<ToDoItem> getParsedValue(String value) {
        Set<ToDoItem> items = new LinkedHashSet<ToDoItem>();
        try {
            JSONArray jsonObj = new JSONArray(value.toString());
            for (int i = 0; i < jsonObj.length(); i++) {
                JSONObject obj = jsonObj.getJSONObject(i);
                String todo = obj.getString("id");
                String type = obj.getString("type");
                if (type.equals("done")) {
                    items.add(new ToDoItem(todo, true));
                } else {
                    items.add(new ToDoItem(todo, false));
                }
            }
        } catch (JSONException e) {
            //--> nothing
        }
        return items;
    }
}
