package com.example.demo.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

//@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class AgentSkills extends BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private  long skillId;
  private String skill;
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "agentId", nullable = false)
  Agent agent;
}
