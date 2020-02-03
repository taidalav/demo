package com.example.demo;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.demo.domain.AgentTaskRequest;
import com.example.demo.domain.AgentTaskResponse;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TaskControllerTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void assignTaskShoudReturnAgentWhenAvailable() {
    AgentTaskRequest request = new AgentTaskRequest();
    request.setSkills(Arrays.asList("skill1", "skill2"));
    request.setTaskName("skill1and2");
    request.setPriority("low");
    ResponseEntity<AgentTaskResponse> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/assign", request,
        AgentTaskResponse.class);
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());

  }

  @Test
  public void getAllAssignedAgents() {
    AgentTaskRequest request = new AgentTaskRequest();
    request.setSkills(Arrays.asList("skill1", "skill2"));
    request.setTaskName("skill1and2");
    request.setPriority("low");
    ResponseEntity<AgentTaskResponse> responseEntity = this.restTemplate.postForEntity("http://localhost:" + port + "/assign", request,
        AgentTaskResponse.class);
    ResponseEntity<AgentTaskResponse[]> listResponseEntity =
        this.restTemplate.getForEntity("http://localhost:" + port + "/tasks",
            AgentTaskResponse[].class);
    assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
    assertNotNull(responseEntity.getBody());
  }

}
