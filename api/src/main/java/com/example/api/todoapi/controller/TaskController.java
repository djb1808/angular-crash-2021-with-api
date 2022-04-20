package com.example.api.todoapi.controller;

import java.io.IOException;

import com.example.api.todoapi.model.Task;
import com.example.api.todoapi.persistence.TaskDAO;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("tasks")
public class TaskController 
{

    private TaskDAO taskDao;

    public TaskController(TaskDAO taskDao)
    {
        this.taskDao = taskDao;
    }
    
    @GetMapping("")
    public ResponseEntity<Task[]> getTasks()
    {
        return new ResponseEntity<Task[]>(taskDao.getTaskArray(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Task> deleteTask(@PathVariable int id)
    {
        try {
            boolean deleted = taskDao.deleteTask(id);

            if (deleted)
                return new ResponseEntity<>(HttpStatus.OK);
            else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable int id, @RequestBody Task task)
    {
        try {
            Task updated = taskDao.updateTask(task);

            if (updated == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            else return new ResponseEntity<Task>(updated, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("")
    public ResponseEntity<Task> addTask(@RequestBody Task task)
    {
        try {
            Task created = taskDao.createTask(task);

            if (created == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            else return new ResponseEntity<Task>(created, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
