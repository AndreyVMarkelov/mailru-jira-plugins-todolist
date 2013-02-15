/*
 * Created by Andrey Markelov 13-02-2013.
 * Copyright Mail.Ru Group 2013. All rights reserved.
 */
package ru.mail.jira.plugins;

/**
 * ToDo list information.
 * 
 * @author Andrey Markelov
 */
public class ToDoFieldData
{
    /**
     * Custom field ID.
     */
    private String id;

    /**
     * Custom field name.
     */
    private String name;

    /**
     * Default constructor.
     */
    public ToDoFieldData() {}

    /**
     * Constructor.
     */
    public ToDoFieldData(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "ToDoFieldData[id=" + id + ", name=" + name + "]";
    }
}
