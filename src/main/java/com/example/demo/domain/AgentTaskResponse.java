package com.example.demo.domain;

import java.util.List;
import lombok.Data;

@Data
public class AgentTaskResponse {
  private long taskId;
  private String agentName;
  private String complete;
  private String priority;
  private List<String> skills;
}
