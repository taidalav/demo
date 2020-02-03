package com.example.demo.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class AgentTasks extends BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long taskId;
  private String taskName;
  private String priority;
  private String skills;
  @Type(type = "yes_no")
  private boolean complete;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agentId", nullable = false)
  Agent agent;
}
