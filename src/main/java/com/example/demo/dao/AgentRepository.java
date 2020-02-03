package com.example.demo.dao;

import com.example.demo.model.Agent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AgentRepository extends JpaRepository<Agent, Long> {

  @Query(value = "SELECT a FROM Agent a WHERE a.skill1 IN :skill1 and a.skill2 IN :skill2 and a.skill3 IN :skill3")
  List<Agent> findAllBySkills(
      @Param("skill1") List<Boolean> skill1, @Param("skill2") List<Boolean> skill2, @Param("skill3") List<Boolean> skill3);
}
