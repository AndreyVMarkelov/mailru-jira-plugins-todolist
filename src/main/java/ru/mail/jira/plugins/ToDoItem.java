package ru.mail.jira.plugins;

public class ToDoItem
{
    private String todo;

    private boolean done;

    /**
     * Constructor.
     */
    public ToDoItem(
        String todo,
        boolean done)
    {
        this.todo = todo;
        this.done = done;
    }

    public String getTodo()
    {
        return todo;
    }

    public boolean isDone()
    {
        return done;
    }

    @Override
    public String toString()
    {
        return "ToDoItem[todo=" + todo + ", done=" + done + "]";
    }
}
