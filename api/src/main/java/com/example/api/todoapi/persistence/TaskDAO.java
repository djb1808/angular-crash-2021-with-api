package com.example.api.todoapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.example.api.todoapi.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TaskDAO 
{
    private Map<Integer, Task> taskList;
    private ObjectMapper mapper;

    private String filename;
    private static int nextId;

    public TaskDAO(@Value("${task.file}") String filename, ObjectMapper mapper) throws IOException
    {
        this.filename = filename;
        this.taskList = new TreeMap<>();
        this.mapper = mapper;

        load();
    }
    
    private boolean load() throws IOException {
        nextId = 0;

        Task[] taskArr = mapper.readValue(new File(filename), Task[].class);

        for (Task task : taskArr)
        {
            taskList.put(task.getId(), task);
            if (task.getId() > nextId)
                nextId = task.getId();
        }

        return true;
    }

    private boolean save() throws IOException {
        Task[] taskArr = getTaskArray();
        mapper.writeValue(new File(filename), taskArr);
        return true;
    }

    public Task[] getTaskArray()
    {
        List<Task> tasks = new ArrayList<>();
        
        for (Task t : taskList.values())
            tasks.add(t);
        
        Task[] taskArr = new Task[tasks.size()];
        tasks.toArray(taskArr);
        return taskArr;
    }

    public Task getTask(int id)
    {
        return taskList.get(id);
    }

    public Task createTask(Task task) throws IOException
    {
        synchronized(taskList)
        {
            Task newTask;
            newTask = new Task(nextId, task.getText(), task.getDay(), task.isReminder());

            taskList.put(newTask.getId(), newTask);
            save();
            return newTask;
        }
    }

    public boolean deleteTask(int id) throws IOException
    {
        synchronized(taskList)
        {
            if (!taskList.containsKey(id))
                return false;

            taskList.remove(id);
            save();
            return true;
        }
    }

    public Task updateTask(Task task) throws IOException
    {
        synchronized(taskList)
        {
            if (!taskList.containsKey(task.getId()))
                return null;
            
            task.setReminder(!task.isReminder());

            taskList.put(task.getId(), task);
            return task;
        }
    }
}
