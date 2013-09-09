package ru.andreymarkelov.atlas.plugins.todos;

import java.util.List;

public class ToDoDataItem {
    private boolean isReporter;
    private boolean isNobody;
    private List<String> groups;

    public ToDoDataItem() {}

    public ToDoDataItem(boolean isReporter, boolean isNobody) {
        this.isReporter = isReporter;
        this.isNobody = isNobody;
    }

    public List<String> getGroups() {
        return groups;
    }

    public boolean isNobody() {
        return isNobody;
    }

    public boolean isReporter() {
        return isReporter;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public void setNobody(boolean isNobody) {
        this.isNobody = isNobody;
    }

    public void setReporter(boolean isReporter) {
        this.isReporter = isReporter;
    }

    @Override
    public String toString() {
        return "ToDoDataItem[isReporter=" + isReporter + ", isNobody=" + isNobody + ", groups=" + groups + "]";
    }
}
