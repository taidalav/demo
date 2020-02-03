package com.example.demo.service;

import com.example.demo.domain.AgentTaskRequest;
import com.example.demo.domain.AgentTaskResponse;
import java.util.List;


public interface TaskService {
  AgentTaskResponse saveOrUpdate(AgentTaskRequest request);
  AgentTaskResponse update(Long taskId);
  List<AgentTaskResponse> getAgentTasks();
}
