package com.example.demo.dao;

import com.example.demo.model.Agent;
import com.example.demo.model.AgentTasks;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentTasksRepository extends JpaRepository<AgentTasks, Long> {
  @Query(value = "SELECT a FROM AgentTasks a WHERE a.complete = false and a.agent IN :agentIds ORDER BY a.taskId DESC")
  List<AgentTasks> findAllByAgentIds(@Param("agentIds") List<Agent> agentIds);

  @Query(value = "SELECT a FROM AgentTasks a WHERE a.complete = false")
  List<AgentTasks> findAllIncomplete();
}
