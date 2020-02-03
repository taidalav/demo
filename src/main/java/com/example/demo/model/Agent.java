package com.example.demo.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;


@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Agent extends BaseEntity implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long agentId;
  private String firstName;
  private String lastName;
  @Type(type = "yes_no")
  private boolean skill1;
  @Type(type = "yes_no")
  private boolean skill2;
  @Type(type = "yes_no")
  private boolean skill3;

  @OneToMany(mappedBy = "agent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<AgentTasks> tasks;
}
