package com.example.demo.controller;

import com.example.demo.domain.AgentTaskRequest;
import com.example.demo.domain.AgentTaskResponse;
import com.example.demo.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {
  private TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }
  @PostMapping("/assign")
  public ResponseEntity<AgentTaskResponse> assignTask(@RequestBody AgentTaskRequest request) {
     return new ResponseEntity<>(taskService.saveOrUpdate(request), HttpStatus.CREATED);
  }

  @PatchMapping("/task/{task-id}")
  public ResponseEntity<AgentTaskResponse> patchAgentTasks(@PathVariable("task-id") long taskId) {
    return new ResponseEntity<>(taskService.update(taskId), HttpStatus.ACCEPTED);
  }

  @GetMapping("/tasks")
  public ResponseEntity<List<AgentTaskResponse>> getAgentTasks() {
    return new ResponseEntity<>(taskService.getAgentTasks(), HttpStatus.OK);
  }
}
