/*
 * Created by Andrey Markelov 13-02-2013.
 * Copyright Mail.Ru Group 2013. All rights reserved.
 */
package ru.mail.jira.plugins;

/**
 * ToDo item.
 * 
 * @author Andrey Markelov
 */
public class ToDoItem
{
    /**
     * ToDo.
     */
    private String todo;

    /**
     * ToDo state.
     */
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
