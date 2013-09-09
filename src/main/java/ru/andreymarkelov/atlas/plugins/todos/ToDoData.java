package ru.andreymarkelov.atlas.plugins.todos;

public interface ToDoData {
    ToDoDataItem getToDoDataItem(String cfId);

    void setToDoDataItem(String cfId, ToDoDataItem item);
}
