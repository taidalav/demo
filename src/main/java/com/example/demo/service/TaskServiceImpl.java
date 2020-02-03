package com.example.demo.service;

import com.example.demo.dao.AgentRepository;
import com.example.demo.dao.AgentTasksRepository;
import com.example.demo.domain.AgentTaskRequest;
import com.example.demo.domain.AgentTaskResponse;
import com.example.demo.exception.AgentNotFoundException;
import com.example.demo.exception.ValidationException;
import com.example.demo.model.Agent;
import com.example.demo.model.AgentTasks;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

  private AgentTasksRepository agentTasksRepository;
  private AgentRepository agentRepository;

  @Autowired
  public TaskServiceImpl(AgentTasksRepository agentTasksRepository, AgentRepository agentRepository) {
    this.agentTasksRepository = agentTasksRepository;
    this.agentRepository = agentRepository;
  }
  @Override
  public AgentTaskResponse saveOrUpdate(AgentTaskRequest request) {
    Optional<AgentTasks> task;
    AgentTaskResponse response = new AgentTaskResponse();
        List<Boolean> skill1 = new ArrayList<Boolean>(){{add(true); add(false);}},
          skill2 = new ArrayList<Boolean>(){{add(false); add(true);}},
          skill3 = new ArrayList<Boolean>(){{add(false); add(true);}};
      if (request.getSkills().isEmpty()) throw new ValidationException("At least one skill is required ");
      for (String str : request.getSkills()) {
        if (str.equalsIgnoreCase("skill1")) {
          skill1 = new ArrayList<Boolean>(){{add(true);}};
        } else if (str.equalsIgnoreCase("skill2")) {
          skill2 = new ArrayList<Boolean>(){{add(true);}};
        } else if (str.equalsIgnoreCase("skill3")) {
          skill3 = new ArrayList<Boolean>(){{add(true);}};
        } else {
          throw new ValidationException("skill is not allowed");
        }
      }
      List<Agent> agents = agentRepository.findAllBySkills(skill1, skill2, skill3);
      if (agents == null) throw new AgentNotFoundException("Could not find agents");
      Agent agent = selectAgent(agents, request.getPriority());
      if (agent == null) throw new AgentNotFoundException("Could not find agents");
      AgentTasks agentTasks = new AgentTasks();
      agentTasks.setComplete(false);
      agentTasks.setAgent(agent);
      agentTasks.setPriority(request.getPriority());
      agentTasks.setTaskName(request.getTaskName());
      agentTasks.setSkills(String.join(",", request.getSkills()));
      AgentTasks tasks = agentTasksRepository.save(agentTasks);
      response.setAgentName(agent.getFirstName()+ " "+agent.getLastName());
      response.setComplete("no");
      response.setPriority(tasks.getPriority());
      response.setSkills(Arrays.asList(agent.isSkill1()?"skill1":"", agent.isSkill2()?"skill2":"",agent.isSkill3()?"skill3":""));
      response.setTaskId(tasks.getTaskId());
    return response;
  }

  @Override
  public AgentTaskResponse update(Long taskId) {
    Optional<AgentTasks> tasks;
    AgentTaskResponse response = new AgentTaskResponse();
    if (taskId != null) {
      tasks = agentTasksRepository.findById(taskId);
      if (tasks.isPresent() && !tasks.get().isComplete()) {
        AgentTasks task = tasks.get();
        task.setComplete(true);
        task = agentTasksRepository.save(task);
        Agent agent = task.getAgent();
        response.setAgentName(agent.getFirstName() + " " + agent.getLastName());
        response.setComplete("yes");
        response.setPriority(task.getPriority());
        response.setSkills(
            Arrays.asList(
                agent.isSkill1() ? "skill1" : "",
                agent.isSkill2() ? "skill2" : "",
                agent.isSkill3() ? "skill3" : ""));
        response.setTaskId(task.getTaskId());
        return response;
      }
    }
    throw new ValidationException("Task is already completed or does not exist");
  }


  @Override
  public List<AgentTaskResponse> getAgentTasks() {
    List<AgentTasks> tasks = agentTasksRepository.findAllIncomplete();
    List<AgentTaskResponse> responseList = new ArrayList<>();
    if (tasks != null) {
      for (AgentTasks task : tasks) {
        AgentTaskResponse response = new AgentTaskResponse();
        response.setTaskId(task.getTaskId());
        response.setSkills(Arrays.asList(task.getSkills().split(",")));
        response.setPriority(task.getPriority());
        response.setComplete("no");
        response.setAgentName(task.getAgent().getFirstName()+ " "+ task.getAgent().getLastName());
        responseList.add(response);
      }
    }
    return responseList;
  }

  private Agent selectAgent(List<Agent> agents, String priority) {
    Map<Long, Agent> agentMap = getAgentMap(agents);
    if (agentMap == null) return null;
    List<Long> agentIds = new ArrayList<>(agentMap.keySet());
    List<AgentTasks> incompleteTasks = agentTasksRepository.findAllByAgentIds(agents);

    if (incompleteTasks != null) {
      for (AgentTasks task : incompleteTasks) {
        if ("high".equalsIgnoreCase(task.getPriority()) ||
            priority.equalsIgnoreCase(task.getPriority()))
          agentIds.remove(task.getAgent().getAgentId());
      }
    } else {
      if (!agents.isEmpty()) return agents.get(0);
      else return null;
    }


    if (!agentIds.isEmpty()) {
      if ("low".equalsIgnoreCase(priority)) {
        return agentMap.get(agentIds.get(0));
      } else if ("high".equalsIgnoreCase(priority)) { //Assign it to agent who is most recently assigned a low priority task
        for (AgentTasks task : incompleteTasks) {
          if(agentIds.contains(task.getAgent().getAgentId()))
            return agentMap.get(task.getAgent().getAgentId());
        }
      }
    }
    return null;
  }

  private Map<Long, Agent> getAgentMap(List<Agent> agents) {
    Map<Long, Agent> map =null;
    if (agents!=null) {
      map = new HashMap<>();
      for (Agent agent : agents) {
        map.put(agent.getAgentId(), agent);
      }
    }
    return map;
  }
}
