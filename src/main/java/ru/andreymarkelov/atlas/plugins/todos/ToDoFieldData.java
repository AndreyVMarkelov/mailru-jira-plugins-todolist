package ru.andreymarkelov.atlas.plugins.todos;

public class ToDoFieldData {
    private String id;
    private String name;
    private ToDoDataItem data;

    public ToDoFieldData(String id, String name) {
        this(id, name, null);
    }

    public ToDoFieldData(String id, String name, ToDoDataItem data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public ToDoDataItem getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setData(ToDoDataItem data) {
        this.data = data;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ToDoFieldData[id=" + id + ", name=" + name + ", data=" + data + "]";
    }
}
