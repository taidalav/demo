package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgentTaskRequest {
  private Long taskId;
  private String taskName;
  private String priority;
  private String complete;
  private List<String> skills;
}

